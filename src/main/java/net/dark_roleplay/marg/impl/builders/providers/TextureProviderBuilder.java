package net.dark_roleplay.marg.impl.builders.providers;

import net.dark_roleplay.marg.impl.providers.MargTextureProvider;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class TextureProviderBuilder {

    private Map<String, ResourceLocation> textureLocations = new HashMap<>();

    public TextureProviderBuilder addTexture(String key, ResourceLocation value) {
        this.textureLocations.put(key, value);
        return this;
    }

    public MargTextureProvider create() {
        return new MargTextureProvider(textureLocations);
    }
}
