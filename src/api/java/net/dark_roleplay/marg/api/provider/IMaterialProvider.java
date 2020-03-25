package net.dark_roleplay.marg.api.provider;

import net.dark_roleplay.marg.api.materials.IMaterial;

import java.util.Collection;

public interface IMaterialProvider {

    IMaterial registerMaterial(IMaterial material);
    IMaterial getMaterial(String name);
    Collection<IMaterial> getRegisteredMaterials();

}
