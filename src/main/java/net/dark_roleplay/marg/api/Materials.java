package net.dark_roleplay.marg.api;

import java.util.Set;

import net.dark_roleplay.marg.api.materials.Material;

public class Materials {

	public static Set<Material> getMaterials() { return MaterialRegistry.getMaterials(); }

	public static Set<Material> getMaterialsForType(String type) {
		return MaterialRegistry.getMaterialsForType(type);
	}
}
