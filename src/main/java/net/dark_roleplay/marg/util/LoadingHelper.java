package net.dark_roleplay.marg.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.google.gson.stream.JsonReader;
import net.dark_roleplay.marg.impl.generators.IGenerator;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public class LoadingHelper {

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

	public static <T extends IGenerator> CompletableFuture<T>[] loadGenerators(IResourceManager manager, Executor backgroundExecutor, String path, BiFunction<ResourceLocation, JsonReader, T> generatorGen) {
		return manager.getAllResourceLocations(path, fileName -> fileName.endsWith(".json")).stream().map(location ->
				CompletableFuture.<T>supplyAsync(() -> {
					try {
						IResource resource = manager.getResource(location);
						try (JsonReader reader = new JsonReader(new BufferedReader(new InputStreamReader(resource.getInputStream())))) {
							return generatorGen.apply(location, reader);
						}
					} catch (IOException e) {
						e.printStackTrace();
					} catch (IllegalStateException e){
						e.printStackTrace();
					}
					return null;
				}, backgroundExecutor)
		).toArray(size -> new CompletableFuture[size]);
	}
}
