package net.dark_roleplay.marg.data.texture;

import net.dark_roleplay.marg.data.MaterialRequirementData;

import java.util.Arrays;
import java.util.Objects;

public class TextureGeneratorData {

    private int generatorVersion;
    private MaterialRequirementData material;
    private String[] requiredTextures;
    private TextureTaskData[] tasks;

    public int getGeneratorVersion() {
        return generatorVersion;
    }

    public void setGeneratorVersion(int generatorVersion) {
        this.generatorVersion = generatorVersion;
    }

    public MaterialRequirementData getMaterial() {
        return material;
    }

    public void setMaterial(String setMaterial) {
        this.material = material;
    }

    public String[] getRequiredTextures() {
        return requiredTextures;
    }

    public void setRequiredTextures(String[] requiredTextures) {
        this.requiredTextures = requiredTextures;
    }

    public TextureTaskData[] getTasks() {
        return tasks;
    }

    public void setTasks(TextureTaskData[] tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextureGeneratorData that = (TextureGeneratorData) o;
        return generatorVersion == that.generatorVersion &&
                Objects.equals(material, that.material) &&
                Arrays.equals(requiredTextures, that.requiredTextures) &&
                Arrays.equals(tasks, that.tasks);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(generatorVersion, material);
        result = 31 * result + Arrays.hashCode(requiredTextures);
        result = 31 * result + Arrays.hashCode(tasks);
        return result;
    }

    @Override
    public String toString() {
        return "TextureGeneratorData{" +
                "generatorVersion=" + generatorVersion +
                ", material=" + material +
                ", requiredTextures=" + Arrays.toString(requiredTextures) +
                ", tasks=" + Arrays.toString(tasks) +
                '}';
    }
}
