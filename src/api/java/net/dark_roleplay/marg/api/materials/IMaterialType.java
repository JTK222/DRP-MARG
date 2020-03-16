package net.dark_roleplay.marg.api.materials;

import java.util.Set;

public interface IMaterialType {

    public Set<IMaterial> getMaterials();
    public void addMaterial(IMaterial material);
    public String getTypeName();
}
