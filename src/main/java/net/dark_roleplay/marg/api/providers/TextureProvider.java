package net.dark_roleplay.marg.api.providers;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class TextureProvider {

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

    protected TextureProvider(Map<String, ResourceLocation> textureLocations){
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

    public boolean hasTexture(String key){
        return textureLocations.containsKey(key);
    }

    public ResourceLocation getTextureLocation(String key){
        return textureLocations.get(key);
    }

    public LazyOptional<BufferedImage> getTexture(String key){
        return textures.containsKey(key) ? textures.get(key) : LazyOptional.empty();
    }

    public Set<Map.Entry<String, ResourceLocation>> getTexturePaths(){
        return textureLocations.entrySet();
    }

    public static class Builder{

        private Map<String, ResourceLocation> textureLocations = new HashMap<>();

        public Builder addTexture(String key, ResourceLocation value){
            this.textureLocations.put(key, value);
            return this;
        }

        public TextureProvider build(){
            return new TextureProvider(textureLocations);
        }
    }
}
