package net.dark_roleplay.marg.impl.builders.generators.textures;

import net.dark_roleplay.marg.impl.generators.textures.TextureManipulation;
import net.dark_roleplay.marg.impl.generators.textures.TextureTask;
import net.dark_roleplay.marg.impl.generators.textures.util.TextureInputType;
import net.dark_roleplay.marg.impl.generators.textures.util.TextureOutputType;

import java.util.ArrayList;
import java.util.List;

public class TextureTaskBuilder {
    private TextureInputType inputType;
    private TextureOutputType outputType;
    private String inputName = "";
    private int inputID = 0;
    private String outputName;

    private List<TextureManipulation> textureManipulations = new ArrayList<>();

    public TextureTaskBuilder setInputType(TextureInputType inputType) {
        this.inputType = inputType;
        return this;
    }

    public TextureTaskBuilder setOutputType(TextureOutputType outputType) {
        this.outputType = outputType;
        return this;
    }

    public TextureTaskBuilder setInputName(String inputName) {
        this.inputName = inputName;
        return this;
    }

    public TextureTaskBuilder setInputID(int inputID) {
        this.inputID = inputID;
        return this;
    }

    public TextureTaskBuilder setOutputName(String outputName) {
        this.outputName = outputName;
        return this;
    }

    public TextureTaskBuilder addTextureManipulations(TextureManipulation textureManipulation) {
        this.textureManipulations.add(textureManipulation);
        return this;
    }

    public TextureTask create() {
        return new TextureTask(inputType, outputType, inputName, inputID, outputName, textureManipulations.toArray(new TextureManipulation[this.textureManipulations.size()]));
    }
}