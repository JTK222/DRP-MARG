package net.dark_roleplay.marg.api.materials;

import net.dark_roleplay.marg.api.provider.IGraphicsProvider;
import net.dark_roleplay.marg.api.provider.ITextProvider;

public interface IMaterial {

    IGraphicsProvider getGraphicsProvider();
    ITextProvider getTextProvider();
    String getName();
    IMaterialType getType();
    IMaterialProperties getProperties();

}
