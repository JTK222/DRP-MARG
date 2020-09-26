package net.dark_roleplay.marg.util.texture;

import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.marg.api.textures.helper.TextureHolder;

import java.awt.image.BufferedImage;

public class TexturePair {

    private final IMaterial material;
    private TextureHolder image;

    public TexturePair(IMaterial material, TextureHolder image){
        this.material = material;
        this.image = image.clone();
    }

    public IMaterial getMaterial() {
        return material;
    }

    public TextureHolder getImage() {
        return image;
    }

    public void setImage(TextureHolder image) {
        this.image = image;
    }
}
