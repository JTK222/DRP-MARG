package net.dark_roleplay.marg.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.dark_roleplay.marg.api.materials.Material;
import net.dark_roleplay.marg.helpers.VersionHelper;
import net.dark_roleplay.marg.objects.other.generators.LanguageGenerator;
import net.dark_roleplay.marg.objects.other.generators.client.ClientJsonGenerator;
import net.dark_roleplay.marg.objects.other.generators.client.ClientSideGenerator;

public class Generator {

	public static boolean FORCE_GENERATE = false;

	public static void generateCommonResources() {
		// for(ClientJsonGenerator gen : MaterialRegistry.jsonGeneratorsCommon)
		// {
		// gen.generateJsons(MaterialRegistry.getMaterialsForType(gen.getType()));
		// }

		MaterialRegistry.jsonGeneratorsCommon = new HashSet<ClientJsonGenerator>();
	}

	public static void generateClientResources() {
		VersionHelper.loadVersionFile();

		Map<String, Set<Material>> sortedMaterials = new HashMap<String, Set<Material>>();

		for(Material mat : MaterialRegistry.getMaterials()) {
			mat.initResources();
		}

		// Handles json generators, texture generators, and hopefully language
		// generators
		for(int i = 0; i < 2; i++ ) {
			for(ClientSideGenerator gen : MaterialRegistry.clientSideGenerators) {
				if(gen.getGeneratorPass() != i) continue;
				if( !sortedMaterials.containsKey(gen.getGeneratorType())) sortedMaterials.put(gen.getGeneratorType(), MaterialRegistry.getMaterialsForType(gen.getGeneratorType()));
				if( !VersionHelper.requiresUpdate(gen.getFile(), gen.getVersion())) continue;
				gen.prepare();
				if(gen.shouldGenerate(sortedMaterials.get(gen.getGeneratorType()))) {
					gen.generate(sortedMaterials.get(gen.getGeneratorType()));
					VersionHelper.addVersionEntry(gen.getFile(), gen.getVersion());
				}
			}
		}

		for(LanguageGenerator gen : MaterialRegistry.languageGeneratorsClient) {
			if( !sortedMaterials.containsKey(gen.getType())) sortedMaterials.put(gen.getType(), MaterialRegistry.getMaterialsForType(gen.getType()));
			gen.generateLanguages(sortedMaterials.get(gen.getType()));
		}

		Map<String, String> files = LanguageGenerator.getLanguageFiles();
		for(String key : files.keySet()) {
			File fl = new File("." + "/drpcmmaarg/lang/" + key + ".lang");
			fl.getParentFile().mkdirs();
			try {
				fl.createNewFile();
			} catch(IOException e) {
				e.printStackTrace();
			}
			try(BufferedWriter writer = new BufferedWriter(new FileWriter(fl))) {
				writer.write(files.get(key));
				;
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		LanguageGenerator.clearLanguageFiles();

		MaterialRegistry.clientSideGenerators.clear();
		// MaterialRegistry.textureGenerators = new HashSet<TextureGenerator>();

		VersionHelper.updateVersionFile();
		FORCE_GENERATE = false;
	}

}
