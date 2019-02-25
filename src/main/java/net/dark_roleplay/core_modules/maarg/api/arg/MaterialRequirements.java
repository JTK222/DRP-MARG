package net.dark_roleplay.core_modules.maarg.api.arg;

import net.dark_roleplay.core_modules.maarg.api.materials.Material;

public class MaterialRequirements {

	private String[] requiredTextures;
	
	public MaterialRequirements(String... requiredTextures) {
		this.requiredTextures = requiredTextures;
	}
	
	public boolean doesFulfillRequirements(Material mat) {
		for(String texture : requiredTextures)
			if(!mat.hasTexture(texture)) return false;
		return true;
	}
	
}
