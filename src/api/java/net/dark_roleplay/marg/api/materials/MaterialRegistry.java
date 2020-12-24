package net.dark_roleplay.marg.api.materials;

import net.dark_roleplay.marg.common.material.MaterialType;

public interface MaterialRegistry {
	public static MaterialRegistry getInstance(){return null;}
	public MaterialType getMaterialType(String typeName);
}
