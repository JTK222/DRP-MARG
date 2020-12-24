package net.dark_roleplay.marg.common.material;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MaterialType {

	private String typeName;
	private Map<String, Material> materials = new HashMap<>();

	public MaterialType(String typeName){
		this.typeName = typeName;
	}

	public void addMaterial(Material material){
		materials.put(material.getMaterialName(), material);
	}

	public Collection<Material> getMaterials(){
		return materials.values();
	}

	public String getTypeName(){
		return typeName;
	}
}
