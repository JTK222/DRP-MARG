package net.dark_roleplay.marg.assets.reaload_listeners;

import net.dark_roleplay.marg.assets.textures.generator.TextureGenerator;
import net.dark_roleplay.marg.handler.Generator;
import net.dark_roleplay.marg.handler.MaterialLoader;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class TextureGenReloadListener implements IFutureReloadListener {

    @Override
    public CompletableFuture<Void> reload(IStage stage, IResourceManager resourceManager, IProfiler preparationsProfiler, IProfiler reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {

        CompletableFuture<TextureGenerator>[] textureLoaders = MaterialLoader.loadTextureGeneratorsClientV2(resourceManager, backgroundExecutor);
        for(CompletableFuture<TextureGenerator> textureLoader : textureLoaders){
            textureLoader //TODO Add Version Checks
                    .thenApplyAsync(loader ->  {loader.prepareGenerator(); return loader;}, backgroundExecutor)
                    .thenAcceptAsync(loader -> loader.generate(), backgroundExecutor);
        }

        CompletableFuture textureGenerators = CompletableFuture.allOf(textureLoaders);
        return textureGenerators.thenCompose(stage::markCompleteAwaitingOthers);
    }
}