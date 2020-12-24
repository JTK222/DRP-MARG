package net.dark_roleplay.marg.data.lang;

import net.dark_roleplay.marg.data.MaterialRequirementData;

import java.util.Map;
import java.util.Objects;

public class LangGeneratorData {
	private int generatorVersion = 0;
	private MaterialRequirementData material;
	private Map<String, String> langEntries;

	public int getGeneratorVersion() {
		return generatorVersion;
	}

	public void setGeneratorVersion(int generatorVersion) {
		this.generatorVersion = generatorVersion;
	}

	public MaterialRequirementData getMaterial() {
		return material;
	}

	public void setMaterial(MaterialRequirementData material) {
		this.material = material;
	}

	public Map<String, String> getLangEntries() {
		return langEntries;
	}

	public void setLangEntries(Map<String, String> langEntries) {
		this.langEntries = langEntries;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		LangGeneratorData that = (LangGeneratorData) o;
		return generatorVersion == that.generatorVersion &&
				Objects.equals(material, that.material) &&
				Objects.equals(langEntries, that.langEntries);
	}

	@Override
	public int hashCode() {
		return Objects.hash(generatorVersion, material, langEntries);
	}

	@Override
	public String toString() {
		return "LangGeneratorData{" +
				"generatorVersion=" + generatorVersion +
				", material=" + material +
				", langEntries=" + langEntries +
				'}';
	}
}
