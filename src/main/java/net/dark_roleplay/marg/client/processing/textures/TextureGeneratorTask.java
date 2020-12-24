package net.dark_roleplay.marg.client.processing.textures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dark_roleplay.marg.util.EnumDecoder;

import java.util.List;

public class TextureGeneratorTask {
	public static final Codec<TextureGeneratorTask> CODEC = RecordCodecBuilder.create(i -> i.group(
			InputType.CODEC.fieldOf("inputType").forGetter(TextureGeneratorTask::getInputType),
			OutputType.CODEC.fieldOf("outputType").forGetter(TextureGeneratorTask::getOutputType),
			Codec.STRING.optionalFieldOf("inputName", "").forGetter(TextureGeneratorTask::getInputName),
			Codec.INT.optionalFieldOf("inputID", -1).forGetter(TextureGeneratorTask::getInputID),
			Codec.STRING.fieldOf("outputName").forGetter(TextureGeneratorTask::getOutputName),
			TextureGeneratorManipulation.CODEC.listOf().fieldOf("manipulations").forGetter(TextureGeneratorTask::getManipulations)
	).apply(i, TextureGeneratorTask::new));

	private final InputType inputType;
	private final OutputType outputType;

	private final String inputName;
	private final int inputID;
	private final String outputName;
	private final List<TextureGeneratorManipulation> manipulations;

	public TextureGeneratorTask(InputType inputType, OutputType outputType, String inputName, int inputID, String outputName, List<TextureGeneratorManipulation> manipulations) {
		this.inputType = inputType;
		this.outputType = outputType;
		this.inputName = inputName;
		this.inputID = inputID;
		this.outputName = outputName;
		this.manipulations = manipulations;
	}

	public InputType getInputType() {
		return inputType;
	}

	public OutputType getOutputType() {
		return outputType;
	}

	public String getInputName() {
		return inputName;
	}

	public int getInputID() {
		return inputID;
	}

	public String getOutputName() {
		return outputName;
	}

	public List<TextureGeneratorManipulation> getManipulations() {
		return manipulations;
	}

	enum InputType {
		none,
		generator,
		material,
		cache;
		private static Codec<InputType> CODEC = Codec.STRING.comapFlatMap(new EnumDecoder<InputType>(InputType.class)::decode, InputType::toString).stable();
	}

	enum OutputType{
		FILE,
		GLOBAL_CACHE,
		LOCAL_CACHE;
		private static Codec<OutputType> CODEC = Codec.STRING.comapFlatMap(new EnumDecoder<OutputType>(OutputType.class)::decode, OutputType::toString).stable();
	}
}
