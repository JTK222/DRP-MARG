package net.dark_roleplay.marg.handler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import net.dark_roleplay.marg.api.materials.Material;
import net.dark_roleplay.marg.assets.IGenerator;
import net.dark_roleplay.marg.objects.other.generators.LanguageGenerator;
import net.dark_roleplay.marg.objects.other.generators.client.ClientJsonGenerator;
import net.dark_roleplay.marg.objects.other.generators.client.ClientSideGenerator;

public class MaterialRegistry {

	private static Set<Material>				materials					= new HashSet<Material>();
	private static Map<String, Material>		materialsMap				= new HashMap<String, Material>();


	protected static Set<IGenerator>			clientGenerators			= new HashSet<>();
	protected static Set<ClientSideGenerator>	clientSideGenerators		= new HashSet<ClientSideGenerator>();

	protected static Set<ClientJsonGenerator>	jsonGeneratorsCommon		= new HashSet<ClientJsonGenerator>();

	protected static Set<LanguageGenerator>		languageGeneratorsClient	= new HashSet<LanguageGenerator>();

	public static Map<String, Integer>			lastModVersions				= new HashMap<String, Integer>();
	public static Map<String, Integer>			modVersions					= new HashMap<String, Integer>();

	public static void register(Material material) {
		if(materialsMap.containsKey(material.getType() + ":" + material.getName())) return;
		materials.add(material);
		materialsMap.put(material.getType() + ":" + material.getName(), material);
	}

	public static void addJsonGenerator(ClientJsonGenerator gen) {
		jsonGeneratorsCommon.add(gen);
	}

	public static void addClientSideGenerator(ClientSideGenerator gen) {
		clientSideGenerators.add(gen);
	}

	public static void addClientGenerator(IGenerator gen){
		clientGenerators.add(gen);
	}

	public static void addLanguageGeneratorClient(LanguageGenerator gen) {
		languageGeneratorsClient.add(gen);
	}

	// public static void addTextureGenerator(TextureGenerator gen) {
	// textureGenerators.add(gen);
	// }

	public static Set<Material> getMaterials() { return materials; }

	public static Set<Material> getMaterialsForType(String type) {
		return materials.parallelStream().filter(material -> type.equals(material.getType().getName())).collect(Collectors.toSet());
	}
}
