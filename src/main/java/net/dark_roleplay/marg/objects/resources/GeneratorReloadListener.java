package net.dark_roleplay.marg.objects.resources;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import net.dark_roleplay.marg.handler.Generator;
import net.dark_roleplay.marg.handler.MaterialLoader;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.Unit;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class GeneratorReloadListener implements IFutureReloadListener{
	@Override
	public CompletableFuture<Void> reload(IStage stage, IResourceManager resourceManager, IProfiler preparationsProfiler, IProfiler reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {

		CompletableFuture jsonGenerators = CompletableFuture.allOf(MaterialLoader.loadJsonGeneratorsClient(resourceManager, backgroundExecutor));
		//CompletableFuture textureGenerators = CompletableFuture.allOf(MaterialLoader.loadTextureGeneratorsClient(resourceManager, backgroundExecutor));
		CompletableFuture allLoaded = CompletableFuture.allOf(jsonGenerators); //TODO Maybe readd texture generator
		return allLoaded.thenRunAsync(() -> Generator.generateClientResources(), backgroundExecutor).thenCompose(stage::markCompleteAwaitingOthers);
	}
}
