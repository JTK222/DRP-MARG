package net.dark_roleplay.marg.impl.providers;

import net.dark_roleplay.marg.api.provider.IGraphicsProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class MargTextureProvider implements IGraphicsProvider {

    private static final Set<String> EMPTY = new HashSet<>();

    private static final BufferedImage NONE_TEXTURE = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    private static final BufferedImage ERROR_TEXTURE = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

    static{
        Graphics gfx = ERROR_TEXTURE.getGraphics();
        gfx.setColor(new Color(0, 0, 0));
        gfx.fillRect(0, 0, 16, 16);
        gfx.setColor(new Color(255, 0, 255));
        gfx.fillRect(0, 0, 8, 8);
        gfx.fillRect(8, 8, 8, 8);
    }

    private Map<String, ResourceLocation> textureLocations;
    private Map<String, LazyOptional<BufferedImage>> textures;

    public MargTextureProvider(Map<String, ResourceLocation> textureLocations){
        this.textureLocations = textureLocations;
        this.textures = new HashMap<>();
        this.textures.put("none", LazyOptional.of(() -> NONE_TEXTURE));

        for(Map.Entry<String, ResourceLocation> entry : textureLocations.entrySet()){
            textures.put(entry.getKey(), LazyOptional.of(() -> {
                try {
                    ResourceLocation correctPath = new ResourceLocation(entry.getValue().getNamespace(), "textures/" + entry.getValue().getPath() + ".png");
                    return ImageIO.read(Minecraft.getInstance().getResourceManager().getResource(correctPath).getInputStream());
                } catch (IOException e) {
                    return ERROR_TEXTURE;
                }
            }));
        }
    }

    @Override
    public boolean hasTint(String key){  return false; }

    @Override
    public int getTint(String key) { return -1; }

    @Override
    public boolean hasTexture(String key){
        return textureLocations.containsKey(key);
    }

    @Override
    public ResourceLocation getTextureLocation(String key){
        return textureLocations.get(key);
    }

    @Override
    public LazyOptional<BufferedImage> getTexture(String key){
        return textures.containsKey(key) ? textures.get(key) : LazyOptional.empty();
    }

    @Override
    public Set<String> getTextures() {
        return this.textureLocations.keySet();
    }

    @Override
    public Set<String> getTints() { return EMPTY; }
}
