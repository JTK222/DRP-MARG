package net.dark_roleplay.marg.common.material;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dark_roleplay.marg.MargClient;
import net.dark_roleplay.marg.MargServer;
import net.dark_roleplay.marg.common.providers.TextProvider;
import net.dark_roleplay.marg.common.providers.TextureProvider;
import net.dark_roleplay.marg.util.DistConsumer;

import java.util.Map;

public class Material {

	public static final Codec<Material> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.STRING.fieldOf("materialType").forGetter(Material::getMaterialType),
			Codec.STRING.fieldOf("name").forGetter(Material::getMaterialName),
			Codec.STRING.optionalFieldOf("requiredMod", null).forGetter(Material::getRequiredMods),
			MaterialProperties.CODEC.fieldOf("properties").forGetter(Material::getProperties),
			Codec.unboundedMap(Codec.STRING, Codec.STRING).fieldOf("textures").forGetter((mat) -> mat.getTextureProvider().getTextures()),
			Codec.unboundedMap(Codec.STRING, Codec.STRING).fieldOf("items").forGetter(Material::getItems),
			Codec.unboundedMap(Codec.STRING, Codec.STRING).fieldOf("blocks").forGetter(Material::getBlocks)
	).apply(i, Material::new));

	private String materialType;
	private String materialName;
	private String requiredMods;

	private MaterialProperties properties;
	private TextureProvider textureProvider;
	private TextProvider textProvider;
	private Map<String, String> items;
	private Map<String, String> blocks;

	public Material(String materialType, String materialName, String requiredMods, MaterialProperties properties, Map<String, String> textures, Map<String, String> items, Map<String, String> blocks) {
		this.materialType = materialType;
		this.materialName = materialName;
		this.requiredMods = requiredMods;
		this.properties = properties;

		this.textProvider = new TextProvider(materialName);
		this.textureProvider = DistConsumer.safeConsumeForDist(textures, () -> MargClient::createTextureProvider, () -> MargServer::createTextureProvider);
		this.items = items;
		this.blocks = blocks;

		for(Map.Entry<String, String> texture : textures.entrySet())
			textProvider.addEntry("texture%" + texture.getKey(), texture.getValue());

		for(Map.Entry<String, String> item : items.entrySet())
			textProvider.addEntry("item%" + item.getKey(), item.getValue());

		for(Map.Entry<String, String> block : blocks.entrySet())
			textProvider.addEntry("block%" + block.getKey(), block.getValue());

		//TODO add item & block providers
	}

	public String getMaterialType() {
		return materialType;
	}

	public String getMaterialName() {
		return materialName;
	}

	public String getRequiredMods() {
		return requiredMods;
	}

	public TextureProvider getTextureProvider(){
		return textureProvider;
	}

	public TextProvider getTextProvider() {
		return textProvider;
	}

	public MaterialProperties getProperties() {
		return properties;
	}

	public Map<String, String> getItems() {
		return items;
	}

	public Map<String, String> getBlocks() {
		return blocks;
	}
}
