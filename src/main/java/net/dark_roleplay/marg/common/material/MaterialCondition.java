package net.dark_roleplay.marg.common.material;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dark_roleplay.marg.api.materials.MaterialRegistry;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class MaterialCondition implements Iterable<Material> {

	private static List<String> EMPTY = Collections.EMPTY_LIST;
	public static Codec<MaterialCondition> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.STRING.fieldOf("materialType").forGetter(MaterialCondition::getTypeName),
			Codec.STRING.listOf().optionalFieldOf("textures", EMPTY).forGetter(MaterialCondition::getTextures),
			Codec.STRING.listOf().optionalFieldOf("items", EMPTY).forGetter(MaterialCondition::getItems),
			Codec.STRING.listOf().optionalFieldOf("blocks", EMPTY).forGetter(MaterialCondition::getBlocks)
	).apply(i, MaterialCondition::new));

	private final String typeName;
	private final List<String> textures;
	private final List<String> items;
	private final List<String> blocks;

	private MaterialType type;

	public MaterialCondition(String type, List<String> textures, List<String> items, List<String> blocks) {
		this.typeName = type;
		this.textures = textures;
		this.items = items;
		this.blocks = blocks;
	}

	public String getTypeName() {
		return typeName;
	}

	public List<String> getTextures() {
		return textures;
	}

	public List<String> getItems() {
		return items;
	}

	public List<String> getBlocks() {
		return blocks;
	}

	public MaterialType getType(){
		return type != null ? type : MaterialRegistry.getInstance().getMaterialType(typeName);
	}

	@Override
	public Iterator<Material> iterator() {
		return getType().getMaterials().stream().filter(this::isValidMaterial).collect(Collectors.toList()).iterator();
	}

	private boolean isValidMaterial(Material material){
		for(String tex : textures)
			if(!material.getTextureProvider().getTextures().containsKey(tex)) return false;
		for(String item : items)
			if(!material.getItems().containsKey(item)) return false;
		for(String block : blocks)
			if(!material.getBlocks().containsKey(block)) return false;
		return true;
	}
}
