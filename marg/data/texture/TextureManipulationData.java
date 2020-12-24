package net.dark_roleplay.marg.data.texture;

import net.dark_roleplay.marg.util.texture.Axis2D;
import net.dark_roleplay.marg.util.texture.TextureManipulationType;

import java.util.Objects;

public class TextureManipulationData {

    private TextureManipulationType type;
    private int angle;
    private String textureName;
    private int textureID;
    private Axis2D axis;
    private int tintIndex;

    public TextureManipulationType getType() {
        return type;
    }

    public void setType(TextureManipulationType type) {
        this.type = type;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public String getTextureName() {
        return textureName;
    }

    public void setTextureName(String textureName) {
        this.textureName = textureName;
    }

    public int getTextureID() {
        return textureID;
    }

    public void setTextureID(int textureID) {
        this.textureID = textureID;
    }

    public Axis2D getAxis() {
        return axis;
    }

    public void setAxis(Axis2D axis) {
        this.axis = axis;
    }

    public int getTintIndex() {
        return tintIndex;
    }

    public void setTintIndex(int tintIndex) {
        this.tintIndex = tintIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextureManipulationData that = (TextureManipulationData) o;
        return angle == that.angle &&
                textureID == that.textureID &&
                tintIndex == that.tintIndex &&
                type == that.type &&
                Objects.equals(textureName, that.textureName) &&
                axis == that.axis;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, angle, textureName, textureID, axis, tintIndex);
    }

    @Override
    public String toString() {
        return "TextureManipulationData{" +
                "type=" + type +
                ", angle=" + angle +
                ", textureName='" + textureName + '\'' +
                ", textureID=" + textureID +
                ", axis=" + axis +
                ", tintIndex=" + tintIndex +
                '}';
    }
}
