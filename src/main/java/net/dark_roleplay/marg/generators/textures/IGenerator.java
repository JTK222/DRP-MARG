package net.dark_roleplay.marg.generators.textures;

import net.dark_roleplay.marg.api.materials.Material;

public interface IGenerator {

    public int getVersion();

    public boolean needsToGenerate(Material material);

    public void prepareGenerator();

    public void generate();
}
