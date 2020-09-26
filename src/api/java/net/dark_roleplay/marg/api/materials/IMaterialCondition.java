package net.dark_roleplay.marg.api.materials;

import java.util.Set;
import java.util.function.Consumer;

public interface IMaterialCondition extends Iterable<IMaterial> {
    Set<IMaterial> getMaterials();

    boolean doesAccept(IMaterial material);
}
