package net.dark_roleplay.marg.generators.base;

import net.dark_roleplay.marg.api.materials.Material;

import java.util.Set;

public abstract class GeneratorData {

    private final int version;
    private final Set<Material> materials;
    private boolean needsUpdate = false;

    public GeneratorData(int version, Set<Material> materials){
        this.version = version;
        this.materials = materials;
    }

    public void checkUpdate(int lastVersion){
        this.needsUpdate = lastVersion < this.version;
    }

    public boolean needsUpdate(){
        return this.needsUpdate;
    }

    public Set<Material> getMaterials(){
        return this.materials;
    }

    public int getVersion(){
        return this.version;
    }
}
