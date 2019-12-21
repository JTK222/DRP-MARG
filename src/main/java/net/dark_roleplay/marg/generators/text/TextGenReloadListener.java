package net.dark_roleplay.marg.generators.text;

import net.dark_roleplay.marg.Marg;
import net.dark_roleplay.marg.generators.textures.generator.TextureGenerator;
import net.dark_roleplay.marg.util.LoadingHelper;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class TextGenReloadListener implements IFutureReloadListener {

    @Override
    public CompletableFuture<Void> reload(IStage stage, IResourceManager resourceManager, IProfiler preparationsProfiler, IProfiler reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
        System.out.println("Debug");
        CompletableFuture<TextGenerator>[] textLoaders = LoadingHelper.loadGenerators(resourceManager, backgroundExecutor,"marg/text_generator" , (loc, reader) -> Marg.MARG_GSON.fromJson(reader, TextGenerator.class));
        for(CompletableFuture<TextGenerator> textLoader : textLoaders){
            textLoader //TODO Add Version Checks
                    .thenApplyAsync(loader ->  {loader.prepareGenerator(); return loader;}, backgroundExecutor)
                    .thenAcceptAsync(loader -> loader.generate(), backgroundExecutor);
        }

        CompletableFuture textureGenerators = CompletableFuture.allOf(textLoaders);
        return textureGenerators.thenCompose(stage::markCompleteAwaitingOthers);
    }
}