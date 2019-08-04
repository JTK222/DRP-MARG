package net.dark_roleplay.marg.objects.other.generators.client;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.dark_roleplay.marg.Marg;
import net.dark_roleplay.marg.api.materials.Material;
import net.dark_roleplay.marg.handler.LogHelper;
import net.dark_roleplay.marg.helpers.FileHelpers;
import net.dark_roleplay.marg.objects.other.generators.textures.TextureEditors;
import net.dark_roleplay.marg.objects.other.generators.textures.TextureGeneratorCache;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResource;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class ClientTextureGenerator extends ClientSideGenerator {

	private static final String	TYPE_OVERLAY			= "overlay";
	private static final String	TYPE_MASK				= "mask";
	private static final String	TYPE_ROTATE				= "rotate";
	private static final String	TYPE_FLIP				= "flip";

	private BufferedImage[]		requiredTextures		= null;

	private JsonArray			requiredTexturesJson	= null;
	private JsonArray			textures				= null;

	private boolean				isCacheGenerator		= false;

	public ClientTextureGenerator(ResourceLocation file, JsonObject json) {
		super(file, json, 2);
		isCacheGenerator = JSONUtils.getBoolean(this.json, "cache_generator", false);
	}

	@Override
	public void prepare() {
		textures = JSONUtils.getJsonArray(this.json, "textures", new JsonArray());
		requiredTexturesJson = JSONUtils.getJsonArray(this.json, "required_textures", new JsonArray());
	}

	@Override
	public boolean shouldGenerate(Set<Material> set) {
		if(isCacheGenerator) return true;

		boolean needsToGenerate = false;

		for(int i = 0; !needsToGenerate && i < this.textures.size(); i++ ) {
			JsonObject	file	= textures.get(i).getAsJsonObject();

			String		output	= JSONUtils.getString(file, "output", null);
			if(output == null) continue;

			for(Material mat : set) {
				needsToGenerate |= !FileHelpers.doesFileExistClient(mat.getType().getNamed(output, mat));
			}
		}

		return needsToGenerate;
	}

	@Override
	public int getGeneratorPass() { return isCacheGenerator ? 0 : 1; }

	@Override
	public void generate(Set<Material> materials) {
		boolean			errored	= false;

		StringBuilder	builder	= new StringBuilder();
		builder.append(String.format("There were some errors trying to load texture files from: %s\n", this.getFile().toString()));

		this.requiredTextures = new BufferedImage[requiredTexturesJson.size()];

		//requiredTexturesJson.iterator().
		for(int i = 0; i < requiredTexturesJson.size(); i++ ) {
			try {
				IResource res = Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation(requiredTexturesJson.get(i).getAsString()));
				if(res == null) {
					errored = true;
					builder.append("Couldn't find File: " + requiredTexturesJson.get(i).getAsString() + "\n");
					continue;
				}
				InputStream is = res.getInputStream();
				this.requiredTextures[i] = ImageIO.read(is);
				is.close();
			} catch(IOException e) {
				e.printStackTrace();
				errored = true;
				builder.append("Couldn't find File: " + requiredTexturesJson.get(i).getAsString() + "\n");
			}
		}

		if(errored) {
			LogHelper.error(builder.toString());
			return;
		}

		textures.forEach(json -> {
			JsonObject					textureObj	= json.getAsJsonObject();

			Map<String, BufferedImage>	base		= new HashMap<String, BufferedImage>();

			final String				BASE_KEY	= "base";

			// Gather base Textures for the Texture IGenerator
			for(Material mat : materials) {
				if(textureObj.get(BASE_KEY).isJsonPrimitive() && textureObj.get(BASE_KEY).getAsJsonPrimitive().isNumber()) {
					base.put(mat.getName(), this.requiredTextures[textureObj.get(BASE_KEY).getAsInt()]);
				} else if(mat.hasTexture(textureObj.get(BASE_KEY).getAsString())) {
					base.put(mat.getName(), mat.getTexture(textureObj.get(BASE_KEY).getAsString()));
				}
			}

			JsonArray manipulations = JSONUtils.getJsonArray(textureObj, "manipulations", new JsonArray());

			for(int i = 0; i < manipulations.size(); i++ ) {
				JsonObject	manipulation	= manipulations.get(i).getAsJsonObject();
				boolean		cached			= manipulation.has("cached_texture");
				JsonElement	texture			= manipulation.has("texture") ? manipulation.get("texture") : manipulation.get("cached_texture");

				switch(manipulation.get("type").getAsString()) {
					case TYPE_OVERLAY:
						if(texture != null) {
							for(Material mat : materials)
								base.put(mat.getName(), TextureEditors.overlayImage(base.get(mat.getName()), !cached ? this.requiredTextures[texture.getAsInt()] : TextureGeneratorCache.getFromCache(mat.getType().getNamed(texture.getAsString(), mat))));
						}
						break;
					case TYPE_MASK:
						if(texture != null) {
							for(Material mat : materials) {
								base.put(mat.getName(), TextureEditors.maskImage(base.get(mat.getName()), !cached ? this.requiredTextures[texture.getAsInt()] : TextureGeneratorCache.getFromCache(mat.getType().getNamed(texture.getAsString(), mat))));
							}
						}
						break;
					case TYPE_ROTATE:
						int angle = JSONUtils.getInt(manipulation, "angle", 0);
						for(Material mat : materials) {
							base.put(mat.getName(), TextureEditors.rotateImage(base.get(mat.getName()), angle));
						}
						break;
					case TYPE_FLIP:
						boolean axis = JSONUtils.getBoolean(manipulation, "horizontal", true);
						for(Material mat : materials) {
							base.put(mat.getName(), TextureEditors.flipImage(base.get(mat.getName()), axis));
						}
						break;
					default:
						break;
				}
			}

			for(String key : base.keySet()) {
				if(textureObj.has("output")) {
					File outputFile = new File(Marg.FOLDER_ASSETS + "/assets/" + textureObj.get("output").getAsString().replaceAll("%wood%", key).replaceFirst(":", "/") + ".png");

					try {
						outputFile.getParentFile().mkdirs();
						ImageIO.write(base.get(key), "png", outputFile);
					} catch(IOException e) {
						e.printStackTrace();
					}
				} else if(textureObj.has("cache")) {
					TextureGeneratorCache.addToTemporaryCache(textureObj.get("cache").getAsString().replace("%wood%", key), base.get(key));
				} else if(textureObj.has("global_cache")) {
					TextureGeneratorCache.addToGlobalCache(textureObj.get("global_cache").getAsString().replace("%wood%", key), base.get(key));
				}
			}
		});

		TextureGeneratorCache.clearTemporaryCache();
	}

}
