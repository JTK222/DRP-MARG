package net.dark_roleplay.marg.api.provider;

import net.dark_roleplay.marg.api.materials.IMaterialType;

public interface IMaterialTypeProvider {

    IMaterialType getType(String name);
    IMaterialType registerType(IMaterialType materialType);
}
