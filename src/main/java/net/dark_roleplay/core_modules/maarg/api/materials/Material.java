package net.dark_roleplay.core_modules.maarg.api.materials;

import static net.dark_roleplay.core_modules.maarg.api.arg.TextureGeneratorType.TEXTURES;
import static net.dark_roleplay.core_modules.maarg.api.arg.TextureGeneratorType.TINT;

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

import net.dark_roleplay.core_modules.maarg.DRPCMMaARG.ClientProxy;
import net.dark_roleplay.core_modules.maarg.api.arg.TextureGeneratorType;
import net.dark_roleplay.core_modules.maarg.api.exceptions.InvalidTextureType;
import net.dark_roleplay.core_modules.maarg.handler.LogHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.ProgressManager.ProgressBar;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class Material {

	static Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

	private String type;
	private String formatKey;
	private String name;

	private JsonObject unserializedItems = null;

	private Map<String, String> translations = new HashMap<String, String>();
	private Map<String, ResourceLocation> textures = null;
	private Map<String, ItemStack> items = null;

	private int tint = 0;

	private String translationKey;
	private String translation;

	private TextureGeneratorType textureGeneratorType;


	public Material(String type, String formatKey, String name, String translationKey, int tint){
		this.type = type;
		this.formatKey = formatKey;
		this.name = name;
		this.textureGeneratorType = TINT;
		this.tint = tint;
		this.translationKey = translationKey;
	}

	public Material(String type, String formatKey, String name, String translationKey, Map<String, ResourceLocation> textures, JsonObject items){
		this.type = type;
		this.formatKey = formatKey;
		this.name = name;
		this.textureGeneratorType = TEXTURES;
		this.textures = textures;
		this.translationKey = translationKey;
		this.unserializedItems = items;
	}

	public void init() {
		this.items = new HashMap<String, ItemStack>();

		for(Entry<String, JsonElement> entry : this.unserializedItems.entrySet()){
			try {
				JsonObject obj = entry.getValue().getAsJsonObject();

				Item item = JsonUtils.getItem(obj, "item");
				int amount = JsonUtils.getInt(obj, "amount", 1);
				int meta = JsonUtils.getInt(obj, "meta", 0);

				this.items.put(entry.getKey(), new ItemStack(item, amount, meta));
			}catch(JsonSyntaxException e) {
				LogHelper.error(e);
			}

		}
		this.unserializedItems = null;
	}

	/**
	 * Used to format a String, replaces all occurrences of %type% with with the Materials Name
	 * @param input - The String you want to Format
	 * @return The input String with all occurrences of %type% replace with this materials name.
	 */
	public String getNamed(String input){
		return input.replaceAll(this.formatKey, this.name);
	}

	public TextureGeneratorType getGeneratorType(){
		return this.textureGeneratorType;
	}

	public String getType() {
		return this.type;
	}

	public boolean hasTexture(String key){
		return this.textures.containsKey(key);
	}

	public String getName(){
		return this.name;
	}

	public boolean hasItem(String key) {
		return this.items == null ? null : this.items.containsKey(key);
	}

	public ItemStack getItem(String key) {
		return this.items == null ? null : this.items.containsKey(key) ? this.items.get(key) : null;
	}

	//Client Side Only from bellow
	protected Map<String, BufferedImage> bufferedTextures = null;

	@SideOnly(Side.CLIENT)
	public void initResources() {
		this.bufferedTextures = new HashMap<String, BufferedImage>();

		ProgressBar progressBar = ProgressManager.push("Loading wood Textures for " + this.type, this.textures.size());
		for(Map.Entry<String, ResourceLocation> texture : this.textures.entrySet()) {
			progressBar.step(String.format("Loading %s texture: %s", texture.getKey(), texture.getValue().toString()));
			try{
				this.bufferedTextures.put(texture.getKey(), ImageIO.read(ClientProxy.getResource(texture.getValue()).getInputStream()));
			}catch(Exception e) {
				LogHelper.error(String.format("Failed to load %s for %s", texture.getValue().toString(), texture.getKey()));
				LogHelper.error(e);
			}
		}
		ProgressManager.pop(progressBar);
	}

	@SideOnly(Side.CLIENT)
	public BufferedImage getTexture(String key) throws InvalidTextureType{
		if(this.textureGeneratorType != TEXTURES) throw new InvalidTextureType(String.format("Attempted to access a Texture on a Material with the TextureGeneratorType  %s", this.textureGeneratorType.toString()));

		if(this.bufferedTextures == null) return null;
		if(!this.bufferedTextures.containsKey(key)) return null;

		return this.bufferedTextures.get(key);
	}

	@SideOnly(Side.CLIENT)
	public void cleanTextures() {
		this.bufferedTextures = null;
	}

	@SideOnly(Side.CLIENT)
	public int getTint() throws InvalidTextureType{
		if(this.textureGeneratorType != TINT) throw new InvalidTextureType(String.format("Attempted to access a Tint Value on a Material with the TextureGeneratorType  %s", this.textureGeneratorType.toString()));
		return this.tint;
	}

	@SideOnly(Side.CLIENT)
	public String getTranslation() {
		if(this.translation == null)
			this.translation = I18n.format(this.translationKey);
		return this.translation;
	}

	public void printDebug() {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("Material: %s\n", this.name));
		builder.append(String.format("|- Type: %s\n", this.type));
		builder.append(String.format("|- Items: %d\n", this.items.size()));
		for(ItemStack stack : this.items.values()) {
			builder.append(String.format("|   |- %s\n", stack.toString()));
		}
		LogHelper.info(builder.toString());
	}
}
