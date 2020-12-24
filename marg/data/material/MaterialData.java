package net.dark_roleplay.marg.data.material;

import java.util.Map;
import java.util.Objects;

public class MaterialData {
    private String name;
    private String requiredMod = "minecraft";
    private String materialType;
    private MaterialPropertiesData properties;
    private Map<String, String> textures;
    private Map<String, String> items;
    private Map<String, String> blocks;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public MaterialPropertiesData getProperties() {
        return properties;
    }

    public void setProperties(MaterialPropertiesData properties) {
        this.properties = properties;
    }

    public Map<String, String> getTextures() {
        return textures;
    }

    public void setTextures(Map<String, String> textures) {
        this.textures = textures;
    }

    public Map<String, String> getItems() {
        return items;
    }

    public void setItems(Map<String, String> items) {
        this.items = items;
    }

    public Map<String, String> getBlocks() {
        return blocks;
    }

    public void setBlocks(Map<String, String> blocks) {
        this.blocks = blocks;
    }

    public String getRequiredMod() {
        return requiredMod;
    }

    public void setRequiredMod(String requiredMod) {
        this.requiredMod = requiredMod;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaterialData that = (MaterialData) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(requiredMod, that.requiredMod) &&
                Objects.equals(materialType, that.materialType) &&
                Objects.equals(properties, that.properties) &&
                Objects.equals(textures, that.textures) &&
                Objects.equals(items, that.items) &&
                Objects.equals(blocks, that.blocks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, requiredMod, materialType, properties, textures, items, blocks);
    }

    @Override
    public String toString() {
        return "MaterialData{" +
                "name='" + name + '\'' +
                ", requiredMod='" + requiredMod + '\'' +
                ", materialType='" + materialType + '\'' +
                ", properties=" + properties +
                ", textures=" + textures +
                ", items=" + items +
                ", blocks=" + blocks +
                '}';
    }
}
