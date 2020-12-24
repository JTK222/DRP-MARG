package net.dark_roleplay.marg.api.materials;

import net.dark_roleplay.marg.common.material.Material;
import net.dark_roleplay.marg.common.material.MaterialType;

import java.util.HashMap;
import java.util.Map;

public final class MaterialRegistry {
	private static MaterialRegistry INSTANCE = new MaterialRegistry();

	public static MaterialRegistry getInstance(){
		return INSTANCE;
	}

	private MaterialRegistry(){}

	private Map<String, MaterialType> MATERIAL_TYPES = new HashMap();

	public MaterialType getMaterialType(String typeName){
		return MATERIAL_TYPES.get(typeName);
	}

	public void registerMaterial(Material material){
		MaterialType type = MATERIAL_TYPES.computeIfAbsent(material.getMaterialType(), name -> new MaterialType(name));
		type.addMaterial(material);
	}
}
