package net.dark_roleplay.marg.client.generators.textures.generator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dark_roleplay.marg.client.generators.textures.texture.TextureHolder;
import net.dark_roleplay.marg.client.generators.textures.util.TextureCache;
import net.dark_roleplay.marg.client.providers.ClientTextureProvider;
import net.dark_roleplay.marg.common.material.Material;
import net.dark_roleplay.marg.common.material.MaterialCondition;
import net.dark_roleplay.marg.common.providers.TextureProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextureGenerator {
	private final int generatorVersion;
	private final MaterialCondition condition;
	private final Map<String, String> userInputs;
	private final List<TextureGeneratorTask> tasks;
	private final Map<String, TextureHolder> textures;

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
		this.textures = new HashMap<>();
	}

	public void generate(Material material){
		ClientTextureProvider textProv = (ClientTextureProvider) material.getTextureProvider();
		TextureCache globalCache = TextureCache.getGlobalCacheFor(material);
		TextureCache localCache = new TextureCache(globalCache);
		for(TextureGeneratorTask task : tasks)
			task.generate(material, textProv, textures, globalCache, localCache);
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

	public void addTexture(String key, TextureHolder texture){
		this.textures.put(key, texture);
	}
}
