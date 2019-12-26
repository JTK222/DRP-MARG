package net.dark_roleplay.marg.api.materials;

import java.util.Set;

public interface IMaterialType {

    Set<IMaterial> getMaterials();
    void addMaterial(IMaterial material);
    String getTypeName();
}
