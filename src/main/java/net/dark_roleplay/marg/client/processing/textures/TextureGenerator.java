package net.dark_roleplay.marg.client.processing.textures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dark_roleplay.marg.common.material.MaterialCondition;

import java.util.List;
import java.util.Map;

public class TextureGenerator {
	private final int generatorVersion;
	private final MaterialCondition condition;
	private final Map<String, String> userInputs;
	private final List<TextureGeneratorTask> tasks;

	public static final Codec<TextureGenerator> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.INT.optionalFieldOf("generatorVersion", 0).forGetter(TextureGenerator::getGeneratorVersion),
			MaterialCondition.CODEC.fieldOf("material").forGetter(TextureGenerator::getCondition),
			Codec.unboundedMap(Codec.STRING, Codec.STRING).fieldOf("requiredTextures").forGetter(TextureGenerator::getUserInputs),
			TextureGeneratorTask.CODEC.listOf().fieldOf("tasks").forGetter(TextureGenerator::getTasks)
	).apply(i, TextureGenerator::new));

	public TextureGenerator(Integer generatorVersion, MaterialCondition condition, Map<String, String> userInputs, List<TextureGeneratorTask> tasks) {
		this.generatorVersion = generatorVersion;
		this.condition = condition;
		this.userInputs = userInputs;
		this.tasks = tasks;
	}

	public int getGeneratorVersion() {
		return generatorVersion;
	}

	public MaterialCondition getCondition() {
		return condition;
	}

	public Map<String, String> getUserInputs() {
		return userInputs;
	}

	public List<TextureGeneratorTask> getTasks() {
		return tasks;
	}
}
