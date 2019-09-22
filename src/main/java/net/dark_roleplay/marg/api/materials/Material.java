package net.dark_roleplay.marg.api.materials;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.dark_roleplay.marg.api.exceptions.InvalidTextureType;
import net.dark_roleplay.marg.helpers.LogHelper;
import net.dark_roleplay.marg.objects.other.generators.TextureGeneratorType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public final class Material {

	static Gson								GSON				= new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

	private MaterialType					type;
	private String							name;

	// TODO add Items
	private JsonObject						unserializedItems	= null;

	private Map<String, ResourceLocation>	textures			= new HashMap<String, ResourceLocation>();
	private Map<String, ItemStack>			items				= null;

	private int								tint				= 0;

	private String							translationKey;
	private String							translation;

	private TextureGeneratorType textureGeneratorType;

	public Material(MaterialType type, String name, String translationKey, int tint) {
		this.type = type;
		this.name = name;
		this.textureGeneratorType = TextureGeneratorType.TINT;
		this.tint = tint;
		this.translationKey = translationKey;
	}

	public Material(MaterialType type, String name, String translationKey) {
		this.type = type;
		this.name = name;
		this.textureGeneratorType = TextureGeneratorType.TEXTURES;
		this.translationKey = translationKey;
	}

	public void setTexture(String key, ResourceLocation location) {
		this.textures.put(key, location);
	}

	public void init() {
		this.items = new HashMap<String, ItemStack>();

		if(this.unserializedItems == null) return;
		for(Entry<String, JsonElement> entry : this.unserializedItems.entrySet()) {
			try {
				JsonObject	obj		= entry.getValue().getAsJsonObject();

				Item		item	= JSONUtils.getItem(obj, "item");
				int			amount	= JSONUtils.getInt(obj, "amount", 1);
				int			meta	= JSONUtils.getInt(obj, "meta", 0);

				// this.items.put(entry.getKey(), new ItemStack(item, amount,
				// meta));
			} catch(JsonSyntaxException e) {
				LogHelper.error(e);
			}

		}
		this.unserializedItems = null;
	}

	public TextureGeneratorType getGeneratorType() { return this.textureGeneratorType; }

	public MaterialType getType() { return this.type; }

	public boolean hasTexture(String key) {
		return this.textures.containsKey(key);
	}

	public String getName() { return this.name; }

	public String getNamed(String input) { return this.type.getNamed(input, this);}

	public boolean hasItem(String key) {
		return this.items == null ? null : this.items.containsKey(key);
	}

	public ItemStack getItem(String key) {
		return this.items == null ? null : this.items.containsKey(key) ? this.items.get(key) : null;
	}

	// Client Side Only from bellow
	protected Map<String, BufferedImage> bufferedTextures = null;

	public void initResources() {
		this.bufferedTextures = new HashMap<String, BufferedImage>();

		this.bufferedTextures.put("none", new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB));

		for(Map.Entry<String, ResourceLocation> texture : this.textures.entrySet()) {
			try {
				this.bufferedTextures.put(texture.getKey(), ImageIO.read(Minecraft.getInstance().getResourceManager().getResource(texture.getValue()).getInputStream()));
			} catch(Exception e) {
				LogHelper.error(String.format("Failed to load %s for %s", texture.getValue().toString(), texture.getKey()));
				for(StackTraceElement element : e.getStackTrace())
					LogHelper.error(element.toString());
			}
		}
	}

	public BufferedImage getTexture(String key) throws InvalidTextureType {
		if(this.textureGeneratorType != TextureGeneratorType.TEXTURES) throw new InvalidTextureType(String.format("Attempted to access a Texture on a Material with the TextureGeneratorType  %s", this.textureGeneratorType.toString()));

		if(this.bufferedTextures == null) return null;
		if( !this.bufferedTextures.containsKey(key)) return null;

		return this.bufferedTextures.get(key);
	}

	public void cleanTextures() {
		this.bufferedTextures = null;
	}

	public int getTint() throws InvalidTextureType {
		if(this.textureGeneratorType != TextureGeneratorType.TINT) throw new InvalidTextureType(String.format("Attempted to access a Tint Value on a Material with the TextureGeneratorType  %s", this.textureGeneratorType.toString()));
		return this.tint;
	}

	public String getTranslation() {
		if(this.translation == null) this.translation = I18n.format(this.translationKey);
		return this.translation;
	}

	public void printDebug() {
		//TODO Improve debug log
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("Material: %s\n", this.name));
		builder.append(String.format("|- Type: %s\n", this.type.getName()));
		if(this.items != null) {
			builder.append(String.format("|- Items: %d\n", this.items.size()));
			for(ItemStack stack : this.items.values()) {
				builder.append(String.format("|   |- %s\n", stack.toString()));
			}
		}
		LogHelper.info(builder.toString());
	}
}
