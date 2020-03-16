package net.dark_roleplay.marg.api.materials;

import java.util.Set;
import java.util.function.Consumer;

public interface IMaterialCondition {
    Set<IMaterial> getMaterials();

    boolean doesAccept(IMaterial material);

    /**
     * Passed in {@link Consumer} will be executed for all materials that do
     * fulfill this requirements, and belong to this {@link IMaterialType}
     *
     * @param consumer
     */
    void forEach(Consumer<IMaterial> consumer);
}
