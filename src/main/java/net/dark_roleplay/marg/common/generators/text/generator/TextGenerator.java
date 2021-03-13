package net.dark_roleplay.marg.common.generators.text.generator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dark_roleplay.marg.common.material.Material;
import net.dark_roleplay.marg.common.material.MaterialCondition;
import net.minecraft.resources.IResourceManager;
import net.minecraftforge.fml.LogicalSide;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TextGenerator {
	private final int generatorVersion;
	private final MaterialCondition condition;
	private final List<String> customs;
	private final List<TextGeneratorTask> tasks;

	public static final Codec<TextGenerator> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.INT.optionalFieldOf("generatorVersion", 0).forGetter(TextGenerator::getGeneratorVersion),
			MaterialCondition.CODEC.fieldOf("material").forGetter(TextGenerator::getCondition),
			Codec.STRING.listOf().optionalFieldOf("customs", new ArrayList <>()).forGetter(TextGenerator::getCustoms),
			TextGeneratorTask.CODEC.listOf().fieldOf("tasks").forGetter(TextGenerator::getTasks)
	).apply(i, TextGenerator::new));

	public TextGenerator(int generatorVersion, MaterialCondition condition, List<String> customs, List<TextGeneratorTask> tasks) {
		this.generatorVersion = generatorVersion;
		this.condition = condition;
		this.customs = customs;
		this.tasks = tasks;
	}

	public void setLogicalSide(LogicalSide side){
		for(TextGeneratorTask task : tasks)
			task.setLogicalSide(side);
	}

	public void generate(IResourceManager manager, Set<Material> materials) {
		for(TextGeneratorTask task : tasks)
			task.generate(manager, materials, this.customs);
	}

	public int getGeneratorVersion() {
		return generatorVersion;
	}

	public MaterialCondition getCondition() {
		return condition;
	}

	public List<String> getCustoms() {
		return customs;
	}

	public List<TextGeneratorTask> getTasks() {
		return tasks;
	}
}
