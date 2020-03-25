package net.dark_roleplay.marg.api.materials;

import net.dark_roleplay.marg.api.provider.IGraphicsProvider;
import net.dark_roleplay.marg.api.provider.ITextProvider;

public interface IMaterial {

    /**
     * A simple Getter for the material name,
     * without any modid or material type.
     * @return the material name as a String, without any pre-/suffixes
     */
    String getName();

    /**
     * Provides the materials Properties, containing hardness, resistance and more.
     * @return the corresponding {@link IMaterialProperties] object for this material
     */
    IMaterialProperties getProperties();

    IGraphicsProvider getGraphicsProvider();
    ITextProvider getTextProvider();

    IMaterialType getMaterialType();
    String getMaterialTypeName();
    void setMaterialType(IMaterialType type);

}
