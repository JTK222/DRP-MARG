package net.dark_roleplay.marg.client.generators.textures.generator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dark_roleplay.marg.client.generators.textures.generator.processors.TextureProcessorData;
import net.dark_roleplay.marg.client.generators.textures.texture.TextureHolder;
import net.dark_roleplay.marg.client.generators.textures.util.TextureCache;
import net.dark_roleplay.marg.client.generators.textures.util.TextureUtils;
import net.dark_roleplay.marg.client.providers.ClientTextureProvider;
import net.dark_roleplay.marg.common.material.Material;
import net.dark_roleplay.marg.common.providers.TextureProvider;
import net.dark_roleplay.marg.util.EnumDecoder;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;

public class TextureGeneratorTask {
	public static final Codec<TextureGeneratorTask> CODEC = RecordCodecBuilder.create(i -> i.group(
			InputType.CODEC.fieldOf("inputType").forGetter(TextureGeneratorTask::getInputType),
			OutputType.CODEC.fieldOf("outputType").forGetter(TextureGeneratorTask::getOutputType),
			Codec.STRING.fieldOf("inputName").forGetter(TextureGeneratorTask::getInputName),
			Codec.STRING.fieldOf("outputName").forGetter(TextureGeneratorTask::getOutputName),
			TextureGeneratorManipulation.CODEC.listOf().fieldOf("manipulations").forGetter(TextureGeneratorTask::getManipulations)
	).apply(i, TextureGeneratorTask::new));

	private final InputType inputType;
	private final OutputType outputType;

	private final String inputName;
	private final String outputName;
	private final List<TextureGeneratorManipulation> manipulations;

	public TextureGeneratorTask(InputType inputType, OutputType outputType, String inputName, String outputName, List<TextureGeneratorManipulation> manipulations) {
		this.inputType = inputType;
		this.outputType = outputType;
		this.inputName = inputName;
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

	public String getOutputName() {
		return outputName;
	}

	public List<TextureGeneratorManipulation> getManipulations() {
		return manipulations;
	}

	public void generate(Material mat, ClientTextureProvider material, Map<String, TextureHolder> textures, TextureCache global, TextureCache localCache){
		TextureHolder input = null;
		switch(this.inputType){
			case material:
				input = material.getTexture(inputName).clone();
				break;
			case cache:
				input = localCache.getCachedTexture(inputName).clone();
				break;
			case generator:
				input = textures.get(inputName).clone();
				break;
		}

		for(TextureGeneratorManipulation man : manipulations) {
			TextureProcessorData data = man.getData();
			data.setTexture(textures, localCache);
			input.applyProcessor(man.getManipulationType().getProcessor(), data);
		}

		switch(this.outputType){
			case FILE:
				TextureUtils.writeTexture(new ResourceLocation(mat.getTextProvider().apply(this.outputName)), input);
				break;
			case GLOBAL_CACHE:
				global.addTexture(this.outputName, input);
				break;
			case LOCAL_CACHE:
				localCache.addTexture(this.outputName, input);
				break;
		}
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
