package net.dark_roleplay.marg.impl.builders.generators.textures;

import net.dark_roleplay.marg.impl.generators.textures.TextureManipulation;
import net.dark_roleplay.marg.impl.generators.textures.util.Axis2D;
import net.dark_roleplay.marg.impl.generators.textures.util.TextureManipulationType;

public class TextureManipulationBuilder {
    private String textureName = "";
    private TextureManipulationType type;
    private int textureID = 0;
    private int angle;
    private int tint;
    private Axis2D axis;

    public TextureManipulationBuilder setTextureName(String textureName) {
        this.textureName = textureName;
        return this;
    }

    public TextureManipulationBuilder setType(TextureManipulationType type) {
        this.type = type;
        return this;
    }

    public TextureManipulationBuilder setTextureID(int textureID) {
        this.textureID = textureID;
        return this;
    }

    public TextureManipulationBuilder setAngle(int angle) {
        this.angle = angle;
        return this;
    }

    public TextureManipulationBuilder setTint(int tint) {
        this.tint = tint;
        return this;
    }

    public TextureManipulationBuilder setAxis(Axis2D axis) {
        this.axis = axis;
        return this;
    }

    public TextureManipulation create() {
        return new TextureManipulation(textureName, type, textureID, angle, tint, axis);
    }
}