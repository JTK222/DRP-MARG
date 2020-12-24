package net.dark_roleplay.marg.data.texture;

import net.dark_roleplay.marg.client.textures.TextureInputType;
import net.dark_roleplay.marg.client.textures.TextureOutputType;

import java.util.Arrays;
import java.util.Objects;

public class TextureTaskData {

    private TextureInputType inputType;
    private TextureOutputType outputType;

    private String inputName;
    private int inputID;
    private String outputName;

    private TextureManipulationData[] manipulations;

    public TextureInputType getInputType() {
        return inputType;
    }

    public void setInputType(TextureInputType inputType) {
        this.inputType = inputType;
    }

    public TextureOutputType getOutputType() {
        return outputType;
    }

    public void setOutputType(TextureOutputType outputType) {
        this.outputType = outputType;
    }

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public int getInputID() {
        return inputID;
    }

    public void setInputID(int inputID) {
        this.inputID = inputID;
    }

    public String getOutputName() {
        return outputName;
    }

    public void setOutputName(String outputName) {
        this.outputName = outputName;
    }

    public TextureManipulationData[] getManipulations() {
        return manipulations;
    }

    public void setManipulations(TextureManipulationData[] manipulations) {
        this.manipulations = manipulations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextureTaskData that = (TextureTaskData) o;
        return inputID == that.inputID &&
                inputType == that.inputType &&
                outputType == that.outputType &&
                Objects.equals(inputName, that.inputName) &&
                Objects.equals(outputName, that.outputName) &&
                Arrays.equals(manipulations, that.manipulations);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(inputType, outputType, inputName, inputID, outputName);
        result = 31 * result + Arrays.hashCode(manipulations);
        return result;
    }

    @Override
    public String toString() {
        return "TextureTaskData{" +
                "inputType=" + inputType +
                ", outputType=" + outputType +
                ", inputName='" + inputName + '\'' +
                ", inputID=" + inputID +
                ", outputName='" + outputName + '\'' +
                ", manipulations=" + Arrays.toString(manipulations) +
                '}';
    }
}
