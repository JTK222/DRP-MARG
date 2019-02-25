package net.dark_roleplay.core_modules.maarg.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import net.dark_roleplay.core_modules.maarg.References;
import net.dark_roleplay.core_modules.maarg.api.materials.Material;
import net.dark_roleplay.core_modules.maarg.objects.other.generators.JsonGenerator;
import net.dark_roleplay.core_modules.maarg.objects.other.generators.LanguageGenerator;
import net.dark_roleplay.core_modules.maarg.objects.other.generators.TextureGenerator;
import net.minecraftforge.client.resource.VanillaResourceType;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Generator {

	public static boolean FORCE_GENERATE = false;

	public static void generateCommonResources() {
		for(JsonGenerator gen : MaterialRegistry.jsonGeneratorsCommon) {
			gen.generateJsons(MaterialRegistry.getMaterialsForType(gen.getType()));
		}

		MaterialRegistry.jsonGeneratorsCommon = new HashSet<JsonGenerator>();
	}

	@SideOnly(Side.CLIENT)
	public static void generateClientResources() {
		Map<String, Set<Material>> sortedMaterials = new HashMap<String, Set<Material>>();

		for(Material mat : MaterialRegistry.getMaterials()) {
			mat.initResources();
		}

		for(JsonGenerator gen : MaterialRegistry.jsonGeneratorsClient) {
			if(!sortedMaterials.containsKey(gen.getType())) sortedMaterials.put(gen.getType(), MaterialRegistry.getMaterialsForType(gen.getType()));
			gen.generateJsons(sortedMaterials.get(gen.getType()));
		}

		for(TextureGenerator gen : MaterialRegistry.textureGenerators.parallelStream().filter(generatir -> generatir.isCacheCreator()).collect(Collectors.toSet())) {
			if(!sortedMaterials.containsKey(gen.getGeneratorType())) sortedMaterials.put(gen.getGeneratorType(), MaterialRegistry.getMaterialsForType(gen.getGeneratorType()));
			gen.generateTextures(sortedMaterials.get(gen.getGeneratorType()));
		}

		for(TextureGenerator gen : MaterialRegistry.textureGenerators.parallelStream().filter(generatir -> !generatir.isCacheCreator()).collect(Collectors.toSet())) {
			if(!sortedMaterials.containsKey(gen.getGeneratorType())) sortedMaterials.put(gen.getGeneratorType(), MaterialRegistry.getMaterialsForType(gen.getGeneratorType()));
			gen.generateTextures(sortedMaterials.get(gen.getGeneratorType()));
		}

		for(LanguageGenerator gen : MaterialRegistry.languageGeneratorsClient) {
			if(!sortedMaterials.containsKey(gen.getType())) sortedMaterials.put(gen.getType(), MaterialRegistry.getMaterialsForType(gen.getType()));
			gen.generateLanguages(sortedMaterials.get(gen.getType()));
		}

		Map<String, String> files = LanguageGenerator.getLanguageFiles();
		for(String key : files.keySet()) {
			File fl = new File(References.FOLDER_ARG + "/drpcmmaarg/lang/" + key + ".lang");
			fl.getParentFile().mkdirs();
			try {
				fl.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fl))){
	            writer.write(files.get(key));;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		LanguageGenerator.clearLanguageFiles();


		MaterialRegistry.jsonGeneratorsClient = new HashSet<JsonGenerator>();
		MaterialRegistry.textureGenerators = new HashSet<TextureGenerator>();

		FORCE_GENERATE = false;

		if(ForgeVersion.getBuildVersion() >= 2742) {
			FMLClientHandler.instance().refreshResources(VanillaResourceType.LANGUAGES);
		}
	}

}
