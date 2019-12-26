package net.dark_roleplay.marg.generators.textures.task;

import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.marg.impl.materials.MargMaterial;

import java.awt.image.BufferedImage;

public class TexturePair {

    private final IMaterial material;
    private BufferedImage image;

    public TexturePair(IMaterial material, BufferedImage image){
        this.material = material;
        this.image = image;
    }

    public IMaterial getMaterial() {
        return material;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
