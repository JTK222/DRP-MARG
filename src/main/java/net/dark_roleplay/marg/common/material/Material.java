package net.dark_roleplay.marg.common.material;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Map;

public class Material {

	public static final Codec<Material> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.STRING.fieldOf("materialType").forGetter(Material::getMaterialType),
			Codec.STRING.fieldOf("name").forGetter(Material::getMaterialName),
			Codec.STRING.optionalFieldOf("requiredMod", null).forGetter(Material::getRequiredMods),
			MaterialProperties.CODEC.fieldOf("properties").forGetter(Material::getProperties),
			Codec.unboundedMap(Codec.STRING, Codec.STRING).fieldOf("textures").forGetter(Material::getTextures),
			Codec.unboundedMap(Codec.STRING, Codec.STRING).fieldOf("items").forGetter(Material::getItems),
			Codec.unboundedMap(Codec.STRING, Codec.STRING).fieldOf("blocks").forGetter(Material::getBlocks)
	).apply(i, Material::new));

	private String materialType;
	private String materialName;
	private String requiredMods;

	private MaterialProperties properties;
	private Map<String, String> textures;
	private Map<String, String> items;
	private Map<String, String> blocks;


	public Material(String materialType, String materialName, String requiredMods, MaterialProperties properties, Map<String, String> textures, Map<String, String> items, Map<String, String> blocks) {
		this.materialType = materialType;
		this.materialName = materialName;
		this.requiredMods = requiredMods;
		this.properties = properties;

		this.textures = textures;
		this.items = items;
		this.blocks = blocks;
		//TODO handle textures, items and blocks
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

	public MaterialProperties getProperties() {
		return properties;
	}

	public Map<String, String> getTextures() {
		return textures;
	}

	public Map<String, String> getItems() {
		return items;
	}

	public Map<String, String> getBlocks() {
		return blocks;
	}
}
