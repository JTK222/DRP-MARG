package net.dark_roleplay.marg.listeners;

import net.dark_roleplay.marg.impl.generators.IGenerator;
import net.dark_roleplay.marg.util.LoadingHelper;
import net.dark_roleplay.marg.client.MargResourcePackFinder;
import net.dark_roleplay.marg.data.text.TextGeneratorData;
import net.dark_roleplay.marg.impl.generators.text.TextGenerator;
import net.dark_roleplay.marg.util.FileUtil;
import net.dark_roleplay.marg.util.MargGson;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;

@EventBusSubscriber(bus = Bus.FORGE)
public class ServerStarting {


	@SubscribeEvent
	public static void serverStarting(FMLServerAboutToStartEvent event) {
		((ResourcePackList<?>) ObfuscationReflectionHelper.getPrivateValue(MinecraftServer.class, event.getServer(), "field_195577_ad")).addPackFinder(new MargResourcePackFinder(FileUtil.DATA_PACK_FOLDER));

		event.getServer().getResourceManager().addReloadListener(
				(stage, resourceManager, preparationsProfiler, reloadProfiler, backgroundExecutor, gameExecutor) ->
						createGeneratorReloadListener(stage, resourceManager, backgroundExecutor, "marg/text_generator", TextGeneratorData.class, data -> new TextGenerator(data, resourceManager,false)));

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
