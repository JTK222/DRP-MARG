package net.dark_roleplay.marg.generators.textures;

import net.dark_roleplay.marg.impl.materials.MargMaterial;

public interface IGenerator {

    public int getVersion();

    public boolean needsToGenerate(MargMaterial material);

    public void prepareGenerator();

    public void generate();
}
