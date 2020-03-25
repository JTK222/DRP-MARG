package net.dark_roleplay.marg.impl.generators;

import net.dark_roleplay.marg.api.materials.IMaterial;

public interface IGenerator<T extends IGenerator> {

    int getVersion();

    boolean needsToGenerate(IMaterial material);

    T prepareGenerator();

    void generate();
}
