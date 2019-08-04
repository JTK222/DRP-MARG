package net.dark_roleplay.marg.assets.textures.generator;

import com.google.gson.stream.JsonReader;
import net.dark_roleplay.marg.assets.textures.TextureCache;
import net.dark_roleplay.marg.handler.LogHelper;
import net.dark_roleplay.marg.objects.other.generators.textures.TextureEditors;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Manipulation {
    private ManipulationType type;

    private boolean useCache = false;
    private String textureName = "";
    private int textureID = 0;
    private int angle = 0;
    private Axis2D axis = Axis2D.X;

    public Manipulation(JsonReader reader) throws IOException {

        reader.beginObject();
        while(reader.hasNext()) {
            String name = reader.nextName();

            switch (name) {
                case "type":
                    this.type = ManipulationType.getByName(reader.nextString());
                    break;
                case "cached_texture":
                    this.textureName = reader.nextString();
                    this.useCache = true;
                    break;
                case "texture":
                    this.textureID = reader.nextInt();
                    break;
                case "angle":
                    this.angle = reader.nextInt();
                    break;
                case "axis":
                    this.axis = Axis2D.getByName(reader.nextString());
                    break;
            }
        }
        reader.endObject();
    }

    public void apply(BufferedImage[] requiredResources, TextureCache localCache, TexturePair pair){
        switch (this.type){
            case NONE:
                return;
            case MASK:
                pair.setImage(TextureEditors.maskImage(pair.getImage(), this.useCache ? localCache.getCachedImage(pair.getMaterial().getNamed(this.textureName)) : requiredResources[this.textureID]));
                break;
            case OVERLAY:
                pair.setImage(TextureEditors.overlayImage(pair.getImage(), this.useCache ? localCache.getCachedImage(pair.getMaterial().getNamed(this.textureName)) : requiredResources[this.textureID]));
                break;
            case FLIP:
                pair.setImage(TextureEditors.flipImage(pair.getImage(), this.axis == Axis2D.X));
                break;
            case ROTATE:
                pair.setImage(TextureEditors.rotateImage(pair.getImage(), this.angle));
                break;
        }
    }

    public void printDebug(StringBuilder builder) {
        builder.append(String.format("|  |  |  |- Manipulation:\n"));
        builder.append(String.format("|  |  |  |  |- Type: %s\n", this.type.toString()));
        builder.append(String.format("|  |  |  |  |- UseCache: %s\n", this.useCache));
        builder.append(String.format("|  |  |  |  |- Name: %s\n", this.textureName));
        builder.append(String.format("|  |  |  |  |- ID: %d\n", this.textureID));
        builder.append(String.format("|  |  |  |  |- Angle: %d\n", this.angle));
        builder.append(String.format("|  |  |  |  |- Axis: %s\n", this.axis.toString()));
    }

    private enum ManipulationType{
        NONE,
        MASK,
        OVERLAY,
        FLIP,
        ROTATE;

        public static ManipulationType getByName(String value){
            try{
                return valueOf(value.toUpperCase());
            }catch(IllegalArgumentException e){}
            return NONE;
        }
    }

    private enum Axis2D{ X, Y;

        public static Axis2D getByName(String value){
            try{
                return valueOf(value.toUpperCase());
            }catch(IllegalArgumentException e){}
            return X;
        }}
}
