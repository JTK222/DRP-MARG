package net.dark_roleplay.marg.data.text;

import net.dark_roleplay.marg.data.MaterialRequirementData;

import java.util.Arrays;
import java.util.Objects;

public class TextGeneratorData {

    private int generatorVersion = 0;
    private String[] customKeys;
    private MaterialRequirementData material;
    private TextTaskData[] tasks;

    public int getGeneratorVersion() {
        return generatorVersion;
    }

    public void setGeneratorVersion(int generatorVersion) {
        this.generatorVersion = generatorVersion;
    }

    public MaterialRequirementData getMaterial() {
        return material;
    }

    public void setMaterial(MaterialRequirementData material) {
        this.material = material;
    }

    public TextTaskData[] getTasks() {
        return tasks;
    }

    public void setTasks(TextTaskData[] tasks) {
        this.tasks = tasks;
    }

    public String[] getCustomKeys() {
        return customKeys;
    }

    public void setCustomKeys(String[] customKeys) {
        this.customKeys = customKeys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextGeneratorData that = (TextGeneratorData) o;
        return generatorVersion == that.generatorVersion &&
                Arrays.equals(customKeys, that.customKeys) &&
                Objects.equals(material, that.material) &&
                Arrays.equals(tasks, that.tasks);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(generatorVersion, material);
        result = 31 * result + Arrays.hashCode(customKeys);
        result = 31 * result + Arrays.hashCode(tasks);
        return result;
    }

    @Override
    public String toString() {
        return "TextGeneratorData{" +
                "generatorVersion=" + generatorVersion +
                ", customKeys=" + Arrays.toString(customKeys) +
                ", material=" + material +
                ", tasks=" + Arrays.toString(tasks) +
                '}';
    }
}
