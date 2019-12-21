package net.dark_roleplay.marg.generators.textures;

import net.dark_roleplay.marg.generators.textures.generator.TextureGenerator;
import net.dark_roleplay.marg.util.LoadingHelper;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class TextureGenReloadListener implements IFutureReloadListener {

    @Override
    public CompletableFuture<Void> reload(IStage stage, IResourceManager resourceManager, IProfiler preparationsProfiler, IProfiler reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {

        CompletableFuture<TextureGenerator>[] textureLoaders = LoadingHelper.loadGenerators(resourceManager, backgroundExecutor,"marg/texture_generator" , (loc, reader) -> new TextureGenerator(loc, reader));
        for(CompletableFuture<TextureGenerator> textureLoader : textureLoaders){
            textureLoader //TODO Add Version Checks
                    .thenApplyAsync(loader ->  {loader.prepareGenerator(); return loader;}, backgroundExecutor)
                    .thenAcceptAsync(loader -> loader.generate(), backgroundExecutor);
        }

        CompletableFuture textureGenerators = CompletableFuture.allOf(textureLoaders);
        return textureGenerators.thenCompose(stage::markCompleteAwaitingOthers);
    }
}