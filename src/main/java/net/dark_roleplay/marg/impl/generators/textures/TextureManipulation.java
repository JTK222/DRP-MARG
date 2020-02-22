package net.dark_roleplay.marg.impl.generators.textures;

import net.dark_roleplay.marg.impl.generators.textures.util.TextureCache;
import net.dark_roleplay.marg.impl.generators.textures.util.TexturePair;
import net.dark_roleplay.marg.impl.generators.textures.util.Axis2D;
import net.dark_roleplay.marg.impl.generators.textures.util.TextureEditors;
import net.dark_roleplay.marg.impl.generators.textures.util.TextureManipulationType;

import java.awt.image.BufferedImage;

public final class TextureManipulation {

    private final TextureManipulationType type;
    private final String textureName;
    private final int textureID;
    private final int angle;
    private final Axis2D axis;
    private final int tint;

    public TextureManipulation(String textureName, TextureManipulationType type, int textureID, int angle, int tint, Axis2D axis){
        this.textureName = textureName;
        this.type = type;
        this.textureID = textureID;
        this.angle = angle;
        this.axis = axis;
        this.tint = tint;
    }

    public void apply(BufferedImage[] requiredResources, TextureCache localCache, TexturePair pair){
        switch (this.type){
            case NONE:
                return;
            case MASK:
                pair.setImage(TextureEditors.maskImage(pair.getImage(), !this.textureName.isEmpty() ? localCache.getCachedImage(pair.getMaterial().getTextProvider().apply(this.textureName)) : requiredResources[this.textureID]));
                break;
            case OVERLAY:
                pair.setImage(TextureEditors.overlayImage(pair.getImage(), !this.textureName.isEmpty() ? localCache.getCachedImage(pair.getMaterial().getTextProvider().apply(this.textureName)) : requiredResources[this.textureID]));
                break;
            case FLIP:
                pair.setImage(TextureEditors.flipImage(pair.getImage(), this.axis == Axis2D.X));
                break;
            case ROTATE:
                pair.setImage(TextureEditors.rotateImage(pair.getImage(), this.angle));
                break;
        }
    }

    public TextureManipulationType getType() {
        return type;
    }

    public String getTextureName() {
        return textureName;
    }

    public int getTextureID() {
        return textureID;
    }

    public int getAngle() {
        return angle;
    }

    public Axis2D getAxis() {
        return axis;
    }

    public int getTint() {
        return tint;
    }
}
