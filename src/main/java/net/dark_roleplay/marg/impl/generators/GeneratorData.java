package net.dark_roleplay.marg.impl.generators;

import net.dark_roleplay.marg.impl.materials.MargMaterial;

import java.util.Set;

public abstract class GeneratorData {

    private final int version;
    private final Set<MargMaterial> materials;
    private boolean needsUpdate = false;

    public GeneratorData(int version, Set<MargMaterial> materials){
        this.version = version;
        this.materials = materials;
    }

    public void checkUpdate(int lastVersion){
        this.needsUpdate = lastVersion < this.version;
    }

    public boolean needsUpdate(){
        return this.needsUpdate;
    }

    public Set<MargMaterial> getMaterials(){
        return this.materials;
    }

    public int getVersion(){
        return this.version;
    }
}
