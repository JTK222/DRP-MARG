package net.dark_roleplay.marg;

import net.dark_roleplay.marg.data.text.TextGeneratorData;
import net.dark_roleplay.marg.data.texture.TextureGeneratorData;
import net.dark_roleplay.marg.impl.generators.text.TextGenerator;
import net.dark_roleplay.marg.impl.generators.textures.TextureGenerator;
import net.dark_roleplay.marg.util.FileUtil;
import net.dark_roleplay.marg.common.resource_packs.MargResourcePackFinder;
import net.dark_roleplay.marg.common.resource_packs.ResourcePackUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;

public class MargClient {

	public static void modStatic(){
		IResourceManager resManager = Minecraft.getInstance().getResourceManager();
		if(resManager instanceof IReloadableResourceManager){
			IReloadableResourceManager relResManager = (IReloadableResourceManager) resManager;

			relResManager.addReloadListener(
					(stage, resourceManager, preparationsProvider, reloadProfiler, backgroundExecutor, gameExecutor) ->
							ResourcePackUtil.createGeneratorReloadListener(stage, resourceManager, backgroundExecutor, "marg/texture_generator", TextureGeneratorData.class, TextureGenerator::new)
			);

			((IReloadableResourceManager) Minecraft.getInstance().getResourceManager()).addReloadListener(
					(stage, resourceManager, preparationsProfiler, reloadProfiler, backgroundExecutor, gameExecutor) ->
							ResourcePackUtil.createGeneratorReloadListener(stage, resourceManager, backgroundExecutor, "marg/text_generator", TextGeneratorData.class, data -> new TextGenerator(data, resourceManager, true)));
		}
	}

	public static void modConstructor(){
		Minecraft.getInstance().getResourcePackList().addPackFinder(new MargResourcePackFinder(FileUtil.RESOURCE_PACK_FOLDER));
	}
}
