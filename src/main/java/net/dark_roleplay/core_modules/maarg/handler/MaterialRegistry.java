package net.dark_roleplay.core_modules.maarg.handler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import net.dark_roleplay.core_modules.maarg.api.materials.Material;
import net.dark_roleplay.core_modules.maarg.objects.other.generators.JsonGenerator;
import net.dark_roleplay.core_modules.maarg.objects.other.generators.LanguageGenerator;
import net.dark_roleplay.core_modules.maarg.objects.other.generators.TextureGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MaterialRegistry {

	private static Set<Material> materials = new HashSet<Material>();
	private static Map<String, Material> materialsMap = new HashMap<String, Material>();

	protected static Set<JsonGenerator> jsonGeneratorsCommon = new HashSet<JsonGenerator>();

	protected static Set<JsonGenerator> jsonGeneratorsClient = new HashSet<JsonGenerator>();
	protected static Set<LanguageGenerator> languageGeneratorsClient = new HashSet<LanguageGenerator>();
	protected static Set<TextureGenerator> textureGenerators = new HashSet<TextureGenerator>();

	public static Map<String, Integer> lastModVersions = new HashMap<String, Integer>();
	public static Map<String, Integer> modVersions = new HashMap<String, Integer>();

//	@SideOnly(Side.CLIENT)
//	private static Set<ResourceLocation> languageGenerators = new HashSet<ResourceLocation>();

	public static void register(Material material) {
		if(materialsMap.containsKey(material.getType() + ":" + material.getName())) return;
			materials.add(material);
			materialsMap.put(material.getType() + ":" + material.getName(), material);
	}

	public static void addJsonGenerator(JsonGenerator gen) {
		jsonGeneratorsCommon.add(gen);
	}

	@SideOnly(Side.CLIENT)
	public static void addJsonGeneratorClient(JsonGenerator gen) {
		jsonGeneratorsClient.add(gen);
	}

	@SideOnly(Side.CLIENT)
	public static void addLanguageGeneratorClient(LanguageGenerator gen) {
		languageGeneratorsClient.add(gen);
	}

	@SideOnly(Side.CLIENT)
	public static void addTextureGenerator(TextureGenerator gen) {
		textureGenerators.add(gen);
	}

	public static Set<Material> getMaterials(){
		return materials;
	}

	public static Set<Material> getMaterialsForType(String type){
		return materials.parallelStream().filter(material -> type.equals(material.getType())).collect(Collectors.toSet());
	}
}
