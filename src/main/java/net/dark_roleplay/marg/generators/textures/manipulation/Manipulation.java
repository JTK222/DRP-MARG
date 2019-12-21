package net.dark_roleplay.marg.generators.textures.manipulation;

import net.dark_roleplay.marg.generators.textures.TextureCache;
import net.dark_roleplay.marg.generators.textures.editors.TextureEditors;
import net.dark_roleplay.marg.generators.textures.task.TexturePair;

import java.awt.image.BufferedImage;

public final class Manipulation{

    private final ManipulationType type;
    private final boolean useCache;
    private final String textureName;
    private final int textureID;
    private final int angle;
    private final Axis2D axis;
    private final int tint;

    public Manipulation(String textureName, ManipulationType type, int textureID, int angle, int tint, Axis2D axis, boolean useCache){
        this.textureName = textureName;
        this.type = type;
        this.useCache = useCache;
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
                pair.setImage(TextureEditors.maskImage(pair.getImage(), this.useCache ? localCache.getCachedImage(pair.getMaterial().getTextProv().searchAndReplace(this.textureName)) : requiredResources[this.textureID]));
                break;
            case OVERLAY:
                pair.setImage(TextureEditors.overlayImage(pair.getImage(), this.useCache ? localCache.getCachedImage(pair.getMaterial().getTextProv().searchAndReplace(this.textureName)) : requiredResources[this.textureID]));
                break;
            case FLIP:
                pair.setImage(TextureEditors.flipImage(pair.getImage(), this.axis == Axis2D.X));
                break;
            case ROTATE:
                pair.setImage(TextureEditors.rotateImage(pair.getImage(), this.angle));
                break;
        }
    }

    public ManipulationType getType() {
        return type;
    }

    public boolean usesCache() {
        return useCache;
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
