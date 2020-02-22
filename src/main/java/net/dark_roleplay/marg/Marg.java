package net.dark_roleplay.marg;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import net.dark_roleplay.marg.api.Materials;
import net.dark_roleplay.marg.api.materials.BaseMaterialCondition;
import net.dark_roleplay.marg.impl.adapters.generators.text.TextGeneratorAdapter;
import net.dark_roleplay.marg.impl.adapters.generators.text.TextTaskAdapter;
import net.dark_roleplay.marg.impl.adapters.generators.textures.TextureManipulationAdapter;
import net.dark_roleplay.marg.impl.adapters.materials.MaterialConditionAdapter;
import net.dark_roleplay.marg.impl.adapters.materials.MaterialPropertyAdapter;
import net.dark_roleplay.marg.impl.adapters.providers.TextureProviderAdapter;
import net.dark_roleplay.marg.impl.adapters.providers.TintProviderAdapter;
import net.dark_roleplay.marg.impl.generators.text.TextGenerator;
import net.dark_roleplay.marg.impl.generators.text.TextTask;
import net.dark_roleplay.marg.impl.generators.textures.TextureManipulation;
import net.dark_roleplay.marg.impl.materials.MargMaterial;
import net.dark_roleplay.marg.impl.materials.MargMaterialProperties;
import net.dark_roleplay.marg.impl.providers.MargTextureProvider;
import net.dark_roleplay.marg.impl.providers.MargTintProvider;
import net.dark_roleplay.marg.util.DebugPrinter;
import net.dark_roleplay.marg.util.gson.GsonWrapper;
import net.dark_roleplay.marg.impl.adapters.materials.MaterialAdapter;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;

@Mod(Marg.MODID)
public class Marg {

	public static final Logger LOGGER = LogManager.getLogger();

	public static final Gson MARG_GSON;
	public static boolean wasInitialized = false;

	static{
		GsonWrapper wrapper = new GsonWrapper();
		GsonBuilder builder = new GsonBuilder();

		builder.registerTypeAdapter(BaseMaterialCondition.class, new MaterialConditionAdapter(wrapper));

		builder.registerTypeAdapter(MargMaterial.class, new MaterialAdapter(wrapper));
		builder.registerTypeAdapter(MargMaterialProperties.class, new MaterialPropertyAdapter(wrapper));
		builder.registerTypeAdapter(MargTextureProvider.class, new TextureProviderAdapter(wrapper));
		builder.registerTypeAdapter(MargTintProvider.class, new TintProviderAdapter(wrapper));

		builder.registerTypeAdapter(TextTask.class, new TextTaskAdapter(wrapper));
		builder.registerTypeAdapter(TextGenerator.class, new TextGeneratorAdapter(wrapper));

		builder.registerTypeAdapter(TextureManipulation.class, new TextureManipulationAdapter(wrapper));


		MARG_GSON = builder.create();
		wrapper.setGson(MARG_GSON);

		setupVanillaMaterials();
		for(String matKey : Materials.getRegisteredMaterials()){
			DebugPrinter.logMaterial(Materials.getMaterial(matKey));
		}
	}

	public static final String	MODID	= "marg";

	public static File			FOLDER_LOGS;

	public static File			FOLDER_ASSETS;
	public static File			FOLDER_DATA;

	public Marg() {
		setupFolders();

		DistExecutor.runWhenOn(Dist.CLIENT, () -> MargClient::run);
	}

	private void setupFolders() {
		FOLDER_LOGS = new File("./mod_data/logs/");
		FOLDER_LOGS.mkdirs();

		File baseFolder = new File("./mod_data/marg/");
		FOLDER_DATA = new File(baseFolder, "data_pack");
		FOLDER_ASSETS = new File(baseFolder, "resource_pack");

		new File(FOLDER_DATA, "data").mkdirs();
		new File(FOLDER_ASSETS, "assets").mkdirs();

		try {
			if( !new File(FOLDER_DATA, "pack.mcmeta").exists()) Files.copy(Thread.currentThread().getContextClassLoader().getResourceAsStream("/data/mcmeta_template"), new File(FOLDER_DATA, "pack.mcmeta").toPath());
			if( !new File(FOLDER_ASSETS, "pack.mcmeta").exists()) Files.copy(Thread.currentThread().getContextClassLoader().getResourceAsStream("/assets/mcmeta_template"), new File(FOLDER_ASSETS, "pack.mcmeta").toPath());
			if( !new File(FOLDER_ASSETS, "pack.png").exists()) Files.copy(Thread.currentThread().getContextClassLoader().getResourceAsStream("/assets/pack.png"), new File(FOLDER_ASSETS, "pack.png").toPath());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void setupVanillaMaterials() {
		JsonReader reader = new JsonReader(new InputStreamReader(Marg.class.getClassLoader().getResourceAsStream("data/marg/marg_materials/vanilla_wood.json")));
		MARG_GSON.fromJson(reader, MargMaterial.class);
		wasInitialized = true;
	}
}
