package net.dark_roleplay.marg.client;

import net.dark_roleplay.marg.impl.generators.IGenerator;
import net.dark_roleplay.marg.data.text.TextGeneratorData;
import net.dark_roleplay.marg.impl.generators.text.TextGenerator;
import net.dark_roleplay.marg.impl.generators.textures.TextureGenerator;
import net.dark_roleplay.marg.util.LoadingHelper;
import net.dark_roleplay.marg.data.texture.TextureGeneratorData;
import net.dark_roleplay.marg.util.FileUtil;
import net.dark_roleplay.marg.util.MargGson;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;

public class MargClient {

	public static void run() {
		if (Minecraft.getInstance() == null) return; //Nop out, running DataGen
		Minecraft.getInstance().getResourcePackList().addPackFinder(new MargResourcePackFinder(FileUtil.RESOURCE_PACK_FOLDER));

		if (Minecraft.getInstance().getResourceManager() instanceof IReloadableResourceManager) {
			((IReloadableResourceManager) Minecraft.getInstance().getResourceManager()).addReloadListener(
					(stage, resourceManager, preparationsProfiler, reloadProfiler, backgroundExecutor, gameExecutor) ->
							createGeneratorReloadListener(stage, resourceManager, backgroundExecutor, "marg/texture_generator", TextureGeneratorData.class, TextureGenerator::new));

			((IReloadableResourceManager) Minecraft.getInstance().getResourceManager()).addReloadListener(
					(stage, resourceManager, preparationsProfiler, reloadProfiler, backgroundExecutor, gameExecutor) ->
							createGeneratorReloadListener(stage, resourceManager, backgroundExecutor, "marg/text_generator", TextGeneratorData.class, data -> new TextGenerator(data, resourceManager, true)));
		}
	}

	private static <A, B extends IGenerator> CompletableFuture<Void> createGeneratorReloadListener(IFutureReloadListener.IStage stage, IResourceManager resourceManager, Executor backgroundExecutor, String path, Class<A> dataClass, Function<A, B> generatorConst) {
		CompletableFuture<B>[] generators = LoadingHelper.loadGenerators(resourceManager, backgroundExecutor, path, (loc, reader) -> generatorConst.apply(MargGson.NEW_GSON.fromJson(reader, dataClass)));
		for (CompletableFuture<B> generator : generators)
			generator
					.thenApplyAsync(gen -> gen.prepareGenerator())
					.thenAcceptAsync(loader -> loader.generate(), backgroundExecutor);

		CompletableFuture allGenerators = CompletableFuture.allOf(generators);
		return allGenerators.thenCompose(stage::markCompleteAwaitingOthers);
	}
}
