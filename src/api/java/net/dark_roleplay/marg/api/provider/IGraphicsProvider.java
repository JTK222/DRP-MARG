package net.dark_roleplay.marg.api.provider;

import net.dark_roleplay.marg.api.textures.helper.TextureHolder;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;

import java.awt.image.BufferedImage;
import java.util.Set;

public interface IGraphicsProvider {

    boolean hasTint(String key);
    int getTint(String key);

    boolean hasTexture(String key);
    ResourceLocation getTextureLocation(String key);
    LazyOptional<TextureHolder> getTexture(String key);

    Set<String> getTextures();
    Set<String> getTints();
}
