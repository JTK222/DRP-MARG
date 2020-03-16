package net.dark_roleplay.marg.data;

import java.util.Arrays;
import java.util.Objects;

public class MaterialRequirementData {
    private String type;
    private String[] textures;
    private String[] items;
    private String[] blocks;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getTextures() {
        return textures;
    }

    public void setTextures(String[] textures) {
        this.textures = textures;
    }

    public String[] getItems() {
        return items;
    }

    public void setItems(String[] items) {
        this.items = items;
    }

    public String[] getBlocks() {
        return blocks;
    }

    public void setBlocks(String[] blocks) {
        this.blocks = blocks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaterialRequirementData that = (MaterialRequirementData) o;
        return Objects.equals(type, that.type) &&
                Arrays.equals(textures, that.textures) &&
                Arrays.equals(items, that.items) &&
                Arrays.equals(blocks, that.blocks);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(type);
        result = 31 * result + Arrays.hashCode(textures);
        result = 31 * result + Arrays.hashCode(items);
        result = 31 * result + Arrays.hashCode(blocks);
        return result;
    }

    @Override
    public String toString() {
        return "MaterialRequirementData{" +
                "type='" + type + '\'' +
                ", textures=" + Arrays.toString(textures) +
                ", items=" + Arrays.toString(items) +
                ", blocks=" + Arrays.toString(blocks) +
                '}';
    }
}
