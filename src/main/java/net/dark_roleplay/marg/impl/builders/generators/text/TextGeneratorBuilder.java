package net.dark_roleplay.marg.impl.builders.generators.text;

import net.dark_roleplay.marg.api.materials.IMaterialCondition;
import net.dark_roleplay.marg.impl.generators.text.TextGenerator;
import net.dark_roleplay.marg.impl.generators.text.TextTask;

import java.util.Set;

public class TextGeneratorBuilder {

    private int version = 1;
    private boolean isClient = true;
    private IMaterialCondition materialRequirements;
    private Set<String> customKeys;
    private Set<TextTask> tasks;

    public TextGeneratorBuilder setVersion(int version) {
        this.version = version;
        return this;
    }

    public TextGeneratorBuilder isClient(boolean isClient) {
        this.isClient = isClient;
        return this;
    }

    public TextGeneratorBuilder setMaterialRequirements(IMaterialCondition materialRequirements) {
        this.materialRequirements = materialRequirements;
        return this;
    }

    public TextGeneratorBuilder setCustomKeys(Set<String> customKeys) {
        this.customKeys = customKeys;
        return this;
    }

    public TextGeneratorBuilder setTasks(Set<TextTask> tasks) {
        this.tasks = tasks;
        return this;
    }

    public TextGenerator create() {
        return new TextGenerator(version, isClient, materialRequirements, customKeys, tasks);
    }
}