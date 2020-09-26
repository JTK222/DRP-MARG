package net.dark_roleplay.marg;

import net.dark_roleplay.marg.api.MargAPI;
import net.dark_roleplay.marg.data.text.TextGeneratorData;
import net.dark_roleplay.marg.impl.generators.text.TextGenerator;
import net.dark_roleplay.marg.impl.providers.MaterialRegistry;
import net.dark_roleplay.marg.impl.providers.MaterialTypeRegistry;
import net.dark_roleplay.marg.util.FileUtil;
import net.dark_roleplay.marg.util.MaterialLoader;
import net.dark_roleplay.marg.util.resource_packs.MargResourcePackFinder;
import net.dark_roleplay.marg.util.resource_packs.ResourcePackUtil;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Marg.MODID)
public class Marg {

	public static final Logger LOGGER = LogManager.getLogger();

	static{
		MargAPI.setMaterialProvider(new MaterialRegistry());
		MargAPI.setMaterialTypeProvider(new MaterialTypeRegistry());

		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> MargClient::modStatic);
	}

	public static final String	MODID	= "marg";

	public Marg() {
		MaterialLoader.loadMaterialFiles();
		FileUtil.setupFolders();

		MinecraftForge.EVENT_BUS.addListener(this::serverStarting);
		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> MargClient::modConstructor);
	}

	public void serverStarting(FMLServerAboutToStartEvent event) {
		((ResourcePackList) ObfuscationReflectionHelper.getPrivateValue(MinecraftServer.class, event.getServer(), "field_195577_ad")).addPackFinder(new MargResourcePackFinder(FileUtil.DATA_PACK_FOLDER));

		IResourceManager listener = event.getServer().getDataPackRegistries().getResourceManager();
		if(listener instanceof IReloadableResourceManager){
			IReloadableResourceManager reloadListener = (IReloadableResourceManager) listener;
			reloadListener.addReloadListener(
					(stage, resourceManager, preparationsProfiler, reloadProfiler, backgroundExecutor, gameExecutor) ->
							ResourcePackUtil.createGeneratorReloadListener(stage, resourceManager, backgroundExecutor, "marg/text_generator", TextGeneratorData.class, data -> new TextGenerator(data, resourceManager,false)));

		}
	}
}
