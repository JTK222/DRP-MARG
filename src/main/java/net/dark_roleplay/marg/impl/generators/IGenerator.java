package net.dark_roleplay.marg.impl.generators;

import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.marg.impl.materials.MargMaterial;

public interface IGenerator<T extends IGenerator> {

    public int getVersion();

    public boolean needsToGenerate(IMaterial material);

    public T prepareGenerator();

    public void generate();
}
