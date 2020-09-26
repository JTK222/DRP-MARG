package net.dark_roleplay.marg.impl.providers;

import com.mojang.datafixers.util.Pair;
import net.dark_roleplay.marg.io.TextureDataIO;
import net.dark_roleplay.marg.api.textures.helper.TextureHolder;
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
import java.util.stream.Collectors;

public final class TextureProvider implements IGraphicsProvider {

    private static final Set<String> EMPTY = new HashSet<>();

    private static final TextureHolder NONE_TEXTURE;
    private static final TextureHolder ERROR_TEXTURE;

    static{
        BufferedImage tmp = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics gfx = tmp.getGraphics();
        gfx.setColor(new Color(0, 0, 0));
        gfx.fillRect(0, 0, 16, 16);
        gfx.setColor(new Color(255, 0, 255));
        gfx.fillRect(0, 0, 8, 8);
        gfx.fillRect(8, 8, 8, 8);
        ERROR_TEXTURE = new TextureHolder(TextureDataIO.loadFromBufferedImage(tmp));

        tmp = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        NONE_TEXTURE = new TextureHolder(TextureDataIO.loadFromBufferedImage(tmp));
    }

    private Map<String, ResourceLocation> textureLocations;
    private Map<String, LazyOptional<TextureHolder>> textures;

    public TextureProvider(Map<String, String> textureLocations){
        this.textureLocations = textureLocations.entrySet().stream().map(entry -> new Pair<>(entry.getKey(), new ResourceLocation(entry.getValue()))).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond));
        this.textures = new HashMap<>();
        this.textures.put("none", LazyOptional.of(() -> NONE_TEXTURE));

        for(Map.Entry<String, ResourceLocation> entry : this.textureLocations.entrySet()){
            textures.put(entry.getKey(), LazyOptional.of(() -> {
                try {
                    ResourceLocation correctPath = new ResourceLocation(entry.getValue().getNamespace(), "textures/" + entry.getValue().getPath() + ".png");
                    return new TextureHolder(TextureDataIO.loadFromBufferedImage(ImageIO.read(Minecraft.getInstance().getResourceManager().getResource(correctPath).getInputStream())));
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
    public LazyOptional<TextureHolder> getTexture(String key){
        return textures.containsKey(key) ? textures.get(key) : LazyOptional.empty();
    }

    @Override
    public Set<String> getTextures() {
        return this.textureLocations.keySet();
    }

    @Override
    public Set<String> getTints() { return EMPTY; }
}
