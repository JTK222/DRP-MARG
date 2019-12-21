package net.dark_roleplay.marg;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import net.dark_roleplay.marg.api.materials.Material;
import net.dark_roleplay.marg.api.materials.MaterialRequirement;
import net.dark_roleplay.marg.generators.text.TextGenReloadListener;
import net.dark_roleplay.marg.generators.text.TextGenerator;
import net.dark_roleplay.marg.generators.textures.generator.TextureGenerator;
import net.dark_roleplay.marg.generators.textures.manipulation.Manipulation;
import net.dark_roleplay.marg.util.gson.generators.text.TextGeneratorAdapter;
import net.dark_roleplay.marg.util.gson.generators.textures.ManipulationAdapter;
import net.dark_roleplay.marg.util.gson.generators.textures.TextureGeneratorAdapter;
import net.dark_roleplay.marg.util.gson.materials.MaterialAdapter;
import net.dark_roleplay.marg.generators.textures.TextureGenReloadListener;
import net.dark_roleplay.marg.generators.textures.task.Task;
import net.dark_roleplay.marg.util.gson.generators.textures.TaskAdapter;
import net.dark_roleplay.marg.util.gson.materials.MaterialRequirementAdapter;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.ResourcePackList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.OnlyIns;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
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
		GsonBuilder builder = new GsonBuilder();

		builder.registerTypeAdapter(Material.class, new MaterialAdapter());

		builder.registerTypeAdapter(MaterialRequirement.class, new MaterialRequirementAdapter());

		builder.registerTypeAdapter(TextureGenerator.class, new TextureGeneratorAdapter());
		builder.registerTypeAdapter(Task.class, new TaskAdapter());
		builder.registerTypeAdapter(Manipulation.class, new ManipulationAdapter());

		builder.registerTypeAdapter(TextGenerator.class, new TextGeneratorAdapter());

		MARG_GSON = builder.create();
		setupVanillaMaterials();
	}

	public static final String	MODID	= "marg";

	public static File			FOLDER_LOGS;

	public static File			FOLDER_ASSETS;
	public static File			FOLDER_DATA;

	public Marg() {
		setupFolders();

		DistExecutor.runWhenOn(Dist.CLIENT, () -> MargClient::run);
	}

	public void loadComplete(FMLLoadCompleteEvent event) {
		//TODO add Debug Printing
//		Material.getMaterials().forEach(mat -> {
//			mat.printDebug();
//		});
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
		MARG_GSON.fromJson(reader, Material.class);
		wasInitialized = true;
	}
}
