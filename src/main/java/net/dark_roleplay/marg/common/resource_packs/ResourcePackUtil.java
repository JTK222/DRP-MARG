package net.dark_roleplay.marg.common.resource_packs;

public class ResourcePackUtil {

//	public static <A, B extends IGenerator> CompletableFuture<Void> createGeneratorReloadListener(IFutureReloadListener.IStage stage, IResourceManager resourceManager, Executor backgroundExecutor, String path, Class<A> dataClass, Function<A, B> generatorConst) {
//		CompletableFuture<B>[] generators = LoadingHelper.loadGenerators(resourceManager, backgroundExecutor, path, (loc, reader) -> generatorConst.apply(MargGson.NEW_GSON.fromJson(reader, dataClass)));
//		for (CompletableFuture<B> generator : generators)
//			generator
//					.thenApplyAsync(gen -> gen.prepareGenerator())
//					.thenAcceptAsync(loader -> loader.generate(), backgroundExecutor);
//
//		CompletableFuture allGenerators = CompletableFuture.allOf(generators);
//		return allGenerators.thenCompose(stage::markCompleteAwaitingOthers);
//	}
}
