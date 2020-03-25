package net.dark_roleplay.marg.impl.providers;

import net.dark_roleplay.marg.api.provider.IGraphicsProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;

import java.awt.image.BufferedImage;
import java.util.*;

public final class TintProvider implements IGraphicsProvider {

    private static final Set<String> EMPTY = new HashSet<>();

    private static final int NONE = 0xFFFFFFFF;
    private static final int ERROR = 0xFF00FFFF;

    private Map<String, Integer> tints = new HashMap<>();

    public TintProvider(Map<String, Integer> tints){
        this.tints = tints;
    }

    @Override
    public boolean hasTint(String key) {
        return this.tints.containsKey(key);
    }

    @Override
    public int getTint(String key) {
        Integer val = this.tints.get(key);
        if(val == null)
            return -1;
        return val;
    }

    @Override
    public boolean hasTexture(String key) {return false;}

    @Override
    public ResourceLocation getTextureLocation(String key) {return null;}

    @Override
    public LazyOptional<BufferedImage> getTexture(String key) {return null;}

    @Override
    public Set<String> getTextures() { return EMPTY; }

    @Override
    public Set<String> getTints() {
        return this.tints.keySet();
    }

    public static class Builder {

    }
}
