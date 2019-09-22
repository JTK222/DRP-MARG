package net.dark_roleplay.marg.resource_generators.textures.generator;

import net.dark_roleplay.marg.api.materials.Material;

import java.awt.image.BufferedImage;

public class TexturePair {

    private final Material material;
    private BufferedImage image;

    public TexturePair(Material material, BufferedImage image){
        this.material = material;
        this.image = image;
    }

    public Material getMaterial() {
        return material;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
