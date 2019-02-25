package net.dark_roleplay.core_modules.maarg.objects.other.generators;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.dark_roleplay.core_modules.maarg.DRPCMMaARG.ClientProxy;
import net.dark_roleplay.core_modules.maarg.References;
import net.dark_roleplay.core_modules.maarg.api.materials.Material;
import net.dark_roleplay.core_modules.maarg.handler.Generator;
import net.dark_roleplay.core_modules.maarg.handler.LogHelper;
import net.dark_roleplay.core_modules.maarg.objects.other.generators.textures.TextureEditors;
import net.dark_roleplay.core_modules.maarg.objects.other.generators.textures.TextureGeneratorCache;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class TextureGenerator implements IVersionedGenerator{

	private static final String TYPE_KEY = "type";
	private static final String OUTPUT_KEY = "outputs";
	private static final String REQUIRED_KEY = "required_textures";
	private static final String TEXTURES_KEY = "textures";
	private static final String CACHE_GEN_KEY = "cache_generator";

	private static final String VERSION_KEY = "version";


	private String[] outputFiles = null;
	private BufferedImage[] requiredTextures = null;
	private JsonArray texturesArr = null;

	private ResourceLocation file = null;

	private String type = "none";

	private int version = 0;

	private boolean generatesCache = false;

	private boolean errored = false;

	public TextureGenerator(ResourceLocation file, JsonObject mainJsonObject){
		this.file = file;

		this.type = JsonUtils.getString(mainJsonObject, TYPE_KEY, "none");
		this.version = JsonUtils.getInt(mainJsonObject, VERSION_KEY, 0);

		this.generatesCache = JsonUtils.getBoolean(mainJsonObject, CACHE_GEN_KEY, false);

		StringBuilder builder = new StringBuilder();
		builder.append("There were some errors trying to load texture files from: ");
		builder.append(this.file.toString());
		builder.append("\n");

		JsonArray outputArr = JsonUtils.getJsonArray(mainJsonObject, OUTPUT_KEY, new JsonArray());
		this.outputFiles = new String[outputArr.size()];
		for(int i = 0; i < outputArr.size(); i++) {
			this.outputFiles[i] = outputArr.get(i).getAsString();
		}

		JsonArray requiredTexturesArr = JsonUtils.getJsonArray(mainJsonObject, REQUIRED_KEY, new JsonArray());
		this.requiredTextures = new BufferedImage[requiredTexturesArr.size()];

		for(int i = 0; i < requiredTexturesArr.size(); i ++){
			try {
			IResource res = ClientProxy.getResource(new ResourceLocation(requiredTexturesArr.get(i).getAsString()));
				if(res == null) {
					this.errored = true;
					builder.append("Couldn't find File: " + requiredTexturesArr.get(i).getAsString() + "\n");
					continue;
				}
				InputStream is = res.getInputStream();
				this.requiredTextures[i] = ImageIO.read(is);
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
				this.errored = true;
				builder.append("Couldn't find File: " + requiredTexturesArr.get(i).getAsString() + "\n");
			}
		}

		this.texturesArr = JsonUtils.getJsonArray(mainJsonObject, TEXTURES_KEY, new JsonArray());

		if(this.errored) LogHelper.error(builder.toString());
	}

	public void generateTextures(Set<Material> set2){
		if(this.errored) return;

		Set<Material> workingMats = new HashSet<Material>();

		//Collect usable Materials for this generator
		for(int i = 0; i < this.texturesArr.size(); i++){
			JsonObject obj = this.texturesArr.get(i).getAsJsonObject();
			for(Material mat : set2){
				if(obj.get("base").isJsonPrimitive() && obj.get("base").getAsJsonPrimitive().isString()) {
					if(mat.hasTexture(obj.get("base").getAsString())) {
						workingMats.add(mat);
					}
				}
			}
		}

		//Check if file exists, if not then generate
		if(!Generator.FORCE_GENERATE && this.outputFiles != null) {
			boolean needsToCreate = false;
			outerLoop: for(String gerneralOutputFile : this.outputFiles) {
				for(Material mat : workingMats){
					File outputFile = new File(References.FOLDER_ARG + gerneralOutputFile.replaceAll("%wood%", mat.getName()).replaceFirst(":", "/") + ".png");
					if(outputFile.exists())
						continue;
					else
						needsToCreate = true;
					break outerLoop;
				}
			}

			if(!needsToCreate)
				return;
		}

		//Iterate over texture generators
		for(int i = 0; i < this.texturesArr.size(); i++){
			JsonObject obj = this.texturesArr.get(i).getAsJsonObject();

			Map<String, BufferedImage> base = new HashMap<String, BufferedImage>();

			final String BASE_KEY = "base";

			//Gather base Textures for the Texture Generator
			for(Material mat : workingMats){
				if(obj.get(BASE_KEY).isJsonPrimitive() && obj.get(BASE_KEY).getAsJsonPrimitive().isNumber()) {
					base.put(mat.getName(), this.requiredTextures[obj.get(BASE_KEY).getAsInt()]);
				}else {
					if(mat.hasTexture(obj.get(BASE_KEY).getAsString()))
						base.put(mat.getName(), mat.getTexture(obj.get(BASE_KEY).getAsString()));
				}
			}

			final String MANIPULATIONS_KEY = "manipulations";

			//Iterate over Manipulations
			JsonArray manipulations = JsonUtils.getJsonArray(obj, MANIPULATIONS_KEY, new JsonArray());

			for(int j = 0; j < manipulations.size(); j++){

				final String TEXTURE_KEY = "texture";
				final String CACHED_TEXTURE_KEY = "cached_texture";
				final String MAN_TYPE_KEY = "type";

				final String TYPE_OVERLAY = "overlay";
				final String TYPE_MASK = "mask";
				final String TYPE_ROTATE = "rotate";
				final String TYPE_FLIP = "flip";

				JsonObject manipulation = manipulations.get(j).getAsJsonObject();
				boolean cached = manipulation.has(CACHED_TEXTURE_KEY);
				JsonElement texture = manipulation.has(TEXTURE_KEY) ? manipulation.get(TEXTURE_KEY) : manipulation.get(CACHED_TEXTURE_KEY);
				switch(manipulation.get(MAN_TYPE_KEY).getAsString()){
					case TYPE_OVERLAY:
						if(texture != null){
							for(Material mat : workingMats)
								base.put(mat.getName(), TextureEditors.overlayImage(base.get(mat.getName()), !cached ? this.requiredTextures[texture.getAsInt()] : TextureGeneratorCache.getFromCache(mat.getNamed(texture.getAsString()))));
						}
						break;
					case TYPE_MASK:
						if(texture != null){
							for(Material mat : workingMats){
								base.put(mat.getName(), TextureEditors.maskImage(base.get(mat.getName()), !cached ? this.requiredTextures[texture.getAsInt()] : TextureGeneratorCache.getFromCache(mat.getNamed(texture.getAsString()))));
							}
						}
						break;
					case TYPE_ROTATE:
						int angle = JsonUtils.getInt(manipulation, "angle", 0);
						for(Material mat : workingMats){
							base.put(mat.getName(), TextureEditors.rotateImage(base.get(mat.getName()), angle));
						}
						break;
					case TYPE_FLIP:
						boolean axis = JsonUtils.getBoolean(manipulation, "horizontal", true);
						for(Material mat : workingMats){
							base.put(mat.getName(), TextureEditors.flipImage(base.get(mat.getName()), axis));
						}
						break;
					default:
						break;
				}
			}

			for(String key : base.keySet()){
				if(obj.has("output")){
					File outputFile = new File(References.FOLDER_ARG + "/" + obj.get("output").getAsString().replaceAll("%wood%", key).replaceFirst(":", "/") + ".png");

					try {
						outputFile.getParentFile().mkdirs();
						ImageIO.write(base.get(key), "png", outputFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else if(obj.has("cache")){
					TextureGeneratorCache.addToTemporaryCache(obj.get("cache").getAsString().replace("%wood%", key), base.get(key));
				}else if(obj.has("global_cache")){
					TextureGeneratorCache.addToGlobalCache(obj.get("global_cache").getAsString().replace("%wood%", key), base.get(key));
				}
			}
		}
		TextureGeneratorCache.clearTemporaryCache();
	}

	public boolean isCacheCreator() {
		return this.generatesCache;
	}

	@Override
	public String getGeneratorType() {
		return "texture_generator";
	}

	@Override
	public int getVersion() {
		return this.version;
	}

	@Override
	public ResourceLocation getFile() {
		return this.file;
	}
}
