package net.dark_roleplay.marg.impl.generators.textures;

import net.dark_roleplay.marg.util.texture.TextureCache;
import net.dark_roleplay.marg.util.texture.TexturePair;
import net.dark_roleplay.marg.util.texture.Axis2D;
import net.dark_roleplay.marg.util.texture.TextureEditors;
import net.dark_roleplay.marg.util.texture.TextureManipulationType;
import net.dark_roleplay.marg.data.texture.TextureManipulationData;

import java.awt.image.BufferedImage;

public final class TextureManipulation {

    private final TextureManipulationType type;
    private final String textureName;
    private final int textureID;
    private final int angle;
    private final Axis2D axis;
    private final int tint;

    public TextureManipulation(TextureManipulationData data){
        this.type = data.getType();
        this.textureName = data.getTextureName();
        this.textureID = data.getTextureID();
        this.angle = data.getAngle();
        this.axis = data.getAxis();
        this.tint = data.getTintIndex();
    }

    public void apply(BufferedImage[] requiredResources, TextureCache localCache, TexturePair pair){
        switch (this.type){
            case NONE:
                return;
            case MASK:
                pair.setImage(TextureEditors.maskImage(pair.getImage(), !this.textureName.isEmpty() ? localCache.getCachedImage(pair.getMaterial().getTextProvider().apply(this.textureName)) : requiredResources[this.textureID]));
                break;
            case OVERLAY:
                pair.setImage(TextureEditors.overlayImage(pair.getImage(), (this.textureName == null || this.textureName.isEmpty()) ? requiredResources[this.textureID] : localCache.getCachedImage(pair.getMaterial().getTextProvider().apply(this.textureName))));
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
