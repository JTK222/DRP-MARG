package net.dark_roleplay.marg;

import net.dark_roleplay.marg.generators.textures.generator.TextureGenerator;
import net.dark_roleplay.marg.impl.generators.IGenerator;
import net.dark_roleplay.marg.impl.generators.text.TextGenerator;
import net.dark_roleplay.marg.util.LoadingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class MargClient {

    public static void run() {
        if (Minecraft.getInstance() == null) return; //Nop out, running DataGen
        Minecraft.getInstance().getResourcePackList().addPackFinder(new MargResourcePackFinder(Marg.FOLDER_ASSETS, "Generated Asset Holder"));

        if (Minecraft.getInstance().getResourceManager() instanceof IReloadableResourceManager) {
            ((IReloadableResourceManager) Minecraft.getInstance().getResourceManager()).addReloadListener(
                    (stage, resourceManager, preparationsProfiler, reloadProfiler, backgroundExecutor, gameExecutor) ->
                            createGeneratorReloadListener(stage, resourceManager, backgroundExecutor, "marg/texture_generator", TextureGenerator.class));


            ((IReloadableResourceManager) Minecraft.getInstance().getResourceManager()).addReloadListener(
                    (stage, resourceManager, preparationsProfiler, reloadProfiler, backgroundExecutor, gameExecutor) ->
                            createGeneratorReloadListener(stage, resourceManager, backgroundExecutor, "marg/text_generator", TextGenerator.class));
        }
    }

    private static <A extends IGenerator> CompletableFuture<Void> createGeneratorReloadListener(IFutureReloadListener.IStage stage, IResourceManager resourceManager, Executor backgroundExecutor, String path, Class<A> generatorClass){
        CompletableFuture<A>[] generators = LoadingHelper.loadGenerators(resourceManager, backgroundExecutor, path, (loc, reader) -> Marg.MARG_GSON.fromJson(reader, generatorClass));
        for(CompletableFuture<A> generator : generators)
            generator
                    .thenApplyAsync(gen -> gen.prepareGenerator())
                    .thenAcceptAsync(loader -> loader.generate(), backgroundExecutor);

        CompletableFuture allGenerators = CompletableFuture.allOf(generators);
        return allGenerators.thenCompose(stage::markCompleteAwaitingOthers);
    }

}
