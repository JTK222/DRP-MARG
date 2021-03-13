package net.dark_roleplay.marg.common.generators.text.generator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dark_roleplay.marg.common.generators.text.util.TextUtils;
import net.dark_roleplay.marg.common.material.Material;
import net.dark_roleplay.marg.common.providers.TextProvider;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.LogicalSide;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextGeneratorTask {

	private static Pattern regexPattern = Pattern.compile("\\$\\{custom\\}");

	public static final Codec<TextGeneratorTask> CODEC = RecordCodecBuilder.create(i -> i.group(
			ResourceLocation.CODEC.fieldOf("input").forGetter(TextGeneratorTask::getInput),
			Codec.STRING.fieldOf("output").forGetter(TextGeneratorTask::getOutput)
	).apply(i, TextGeneratorTask::new));

	private final ResourceLocation input;
	private final String output;
	private LogicalSide side;

	public TextGeneratorTask(ResourceLocation input, String output) {
		this.input = input;
		this.output = output;
	}

	public void setLogicalSide(LogicalSide side){
		this.side = side;
	}

	public void generate(IResourceManager manager, Set<Material> materials, List<String> customs){
		//load input
		String content = TextUtils.loadTextFile(input, manager);
		if(content == null) return;

		Matcher nameMatcher = regexPattern.matcher(output);
		if((materials == null || materials.isEmpty()) && customs != null){
			Matcher contentMatcher = regexPattern.matcher(content);
			for(String custom : customs){
				TextUtils.writeTextFile(new ResourceLocation(nameMatcher.replaceAll(custom)), this.side, contentMatcher.replaceAll(custom));
			}
		}else if(materials != null) {
			boolean isCombined = nameMatcher.find();
			for(Material material : materials){
				TextProvider prov = material.getTextProvider();
				String materialOutputLoc = prov.apply(output);
				String materialContent = prov.apply(content);
				if(isCombined){
					Matcher materialContentMatcher = regexPattern.matcher(materialContent);
					nameMatcher = regexPattern.matcher(materialOutputLoc);
					for(String custom : customs)
						TextUtils.writeTextFile(new ResourceLocation(nameMatcher.replaceAll(custom)), this.side, materialContentMatcher.replaceAll(custom));
				}else{
					TextUtils.writeTextFile(new ResourceLocation(materialOutputLoc), this.side, materialContent);
				}
			}
		}
	}

	public ResourceLocation getInput() {
		return input;
	}

	public String getOutput() {
		return output;
	}

}
