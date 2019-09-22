package net.dark_roleplay.marg.api;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import net.dark_roleplay.marg.api.materials.Material;

public class MaterialRegistry {

	private static Set<Material>				materials					= new HashSet<Material>();
	private static Map<String, Material>		materialsMap				= new HashMap<String, Material>();

	public static Map<String, Integer>			lastModVersions				= new HashMap<String, Integer>();
	public static Map<String, Integer>			modVersions					= new HashMap<String, Integer>();

	public static void register(Material material) {
		if(materialsMap.containsKey(material.getType() + ":" + material.getName())) return;
		materials.add(material);
		materialsMap.put(material.getType() + ":" + material.getName(), material);
	}

	public static Set<Material> getMaterials() { return materials; }

	public static Set<Material> getMaterialsForType(String type) {
		return materials.parallelStream().filter(material -> type.equals(material.getType().getName())).collect(Collectors.toSet());
	}
}
