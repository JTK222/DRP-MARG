package net.dark_roleplay.marg.impl.materials;

import net.dark_roleplay.marg.api.MaterialTypes;
import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.marg.api.materials.IMaterialType;

import java.util.HashSet;
import java.util.Set;

public class MargMaterialType implements IMaterialType {
    private final Set<IMaterial> materials = new HashSet<>();
    private final String name;

    public MargMaterialType(String name){
        this.name = name;
        MaterialTypes.registerType(name, this);
    }

    @Override
    public Set<IMaterial> getMaterials(){
        return this.materials;
    }

    @Override
    public void addMaterial(IMaterial material){
        this.materials.add(material);
    }

    @Override
    public String getTypeName(){
        return this.name;
    }

}
