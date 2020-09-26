package net.dark_roleplay.marg.util.resource_packs;

import net.dark_roleplay.marg.impl.generators.IGenerator;
import net.dark_roleplay.marg.util.LoadingHelper;
import net.dark_roleplay.marg.util.MargGson;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;

public class ResourcePackUtil {

	public static <A, B extends IGenerator> CompletableFuture<Void> createGeneratorReloadListener(IFutureReloadListener.IStage stage, IResourceManager resourceManager, Executor backgroundExecutor, String path, Class<A> dataClass, Function<A, B> generatorConst) {
		CompletableFuture<B>[] generators = LoadingHelper.loadGenerators(resourceManager, backgroundExecutor, path, (loc, reader) -> generatorConst.apply(MargGson.NEW_GSON.fromJson(reader, dataClass)));
		for (CompletableFuture<B> generator : generators)
			generator
					.thenApplyAsync(gen -> gen.prepareGenerator())
					.thenAcceptAsync(loader -> loader.generate(), backgroundExecutor);

		CompletableFuture allGenerators = CompletableFuture.allOf(generators);
		return allGenerators.thenCompose(stage::markCompleteAwaitingOthers);
	}
}
