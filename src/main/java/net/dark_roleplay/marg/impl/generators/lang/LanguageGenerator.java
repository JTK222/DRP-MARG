package net.dark_roleplay.marg.impl.generators.lang;

import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.marg.api.materials.IMaterialCondition;
import net.dark_roleplay.marg.data.lang.LangGeneratorData;
import net.dark_roleplay.marg.impl.generators.IGenerator;
import net.dark_roleplay.marg.impl.materials.MargMaterialCondition;
import net.minecraft.client.Minecraft;

import java.util.HashMap;
import java.util.Map;

public class LanguageGenerator implements IGenerator<LanguageGenerator> {

	protected static Map<String, String> langEntries	= new HashMap<String, String>();

	private int version = 0;
	private IMaterialCondition materialRequirements;

	public LanguageGenerator(LangGeneratorData data) {
		this.version = data.getGeneratorVersion();
		this.materialRequirements = new MargMaterialCondition(data.getMaterial());
		this.langEntries = data.getLangEntries();
	}

	@Override
	public int getVersion() {
		return this.version;
	}

	@Override
	public boolean needsToGenerate(IMaterial material) {
		return true;
	}

	@Override
	public LanguageGenerator prepareGenerator() {
		return this;
	}

	@Override
	public void generate() {
		String lang = Minecraft.getInstance().getLanguageManager().getCurrentLanguage().getName();
	}
}
