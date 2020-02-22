package net.dark_roleplay.marg.impl.builders.generators.text;

import net.dark_roleplay.marg.impl.generators.text.TextTask;
import net.minecraft.util.ResourceLocation;

public class TextTaskBuilder {
    private String generatorLoc;
    private ResourceLocation input;
    private String output;

    public TextTaskBuilder setGeneratorLoc(String generatorLoc) {
        this.generatorLoc = generatorLoc;
        return this;
    }

    public TextTaskBuilder setInput(ResourceLocation input) {
        this.input = input;
        return this;
    }

    public TextTaskBuilder setOutput(String output) {
        this.output = output;
        return this;
    }

    public TextTask create() {
        return new TextTask(generatorLoc, input, output);
    }
}