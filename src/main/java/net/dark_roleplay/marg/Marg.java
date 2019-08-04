package net.dark_roleplay.marg;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import net.dark_roleplay.marg.api.materials.Material;
import net.dark_roleplay.marg.api.materials.MaterialType;
import net.dark_roleplay.marg.assets.reaload_listeners.TextureGenReloadListener;
import net.dark_roleplay.marg.handler.MaterialRegistry;
import net.dark_roleplay.marg.objects.other.MargResourcePackFinder;
import net.dark_roleplay.marg.objects.resources.GeneratorReloadListener;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Marg.MODID)
public class Marg {

	public static final String	MODID	= "marg";

	public static File			FOLDER_LOGS;

	public static File			FOLDER_ASSETS;
	public static File			FOLDER_DATA;

	public Marg() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClientStuff);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadComplete);
		setupFolders();
		setupVanillaMaterials();
	}

	public void setupClientStuff(FMLClientSetupEvent event) {
		Minecraft.getInstance().getResourcePackList().addPackFinder(new MargResourcePackFinder(FOLDER_ASSETS, "Generated Asset Holder"));

		if(Minecraft.getInstance().getResourceManager() instanceof IReloadableResourceManager) {
			((IReloadableResourceManager) Minecraft.getInstance().getResourceManager()).addReloadListener(new GeneratorReloadListener());
			((IReloadableResourceManager) Minecraft.getInstance().getResourceManager()).addReloadListener(new TextureGenReloadListener());
		}
	}

	public void loadComplete(FMLLoadCompleteEvent event) {
		MaterialRegistry.getMaterials().forEach(mat -> {
			mat.printDebug();
		});
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

	public void setupVanillaMaterials() {
		MaterialType	woodType	= new MaterialType("wood");
		String[]		woods		= {"acacia", "birch", "dark_oak", "jungle", "oak", "spruce"};

		for(String wood : woods) {
			Material mat = new Material(woodType, wood, String.format("drpmaarg.material.%s", wood));
			mat.setTexture("log_side", new ResourceLocation("minecraft", String.format("textures/block/%s_log.png", wood)));
			mat.setTexture("log_top", new ResourceLocation("minecraft", String.format("textures/block/%s_log_top.png", wood)));
			mat.setTexture("stripped_log_side", new ResourceLocation("minecraft", String.format("textures/block/stripped_%s_log.png", wood)));
			mat.setTexture("stripped_log_top", new ResourceLocation("minecraft", String.format("textures/block/stripped_%s_log_top.png", wood)));
			mat.setTexture("planks", new ResourceLocation("minecraft", String.format("textures/block/%s_planks.png", wood)));

			MaterialRegistry.register(mat);
		}
	}
}
