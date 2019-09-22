package net.dark_roleplay.marg.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiFunction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.google.gson.stream.JsonReader;
import net.dark_roleplay.marg.resource_generators.IGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public class LoadingHelper {

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

	public static <T extends IGenerator> CompletableFuture<T>[] loadGenerators(IResourceManager manager, Executor backgroundExecutor, String path, BiFunction<ResourceLocation, JsonReader, T> generatorGen) {
		return manager.getAllResourceLocations(path, fileName -> fileName.endsWith(".json")).stream().map(location ->
				CompletableFuture.<T>supplyAsync(() -> {
					try {
						IResource resource = Minecraft.getInstance().getResourceManager().getResource(location);
						try (JsonReader reader = new JsonReader(new BufferedReader(new InputStreamReader(resource.getInputStream())))) {
							return generatorGen.apply(location, reader);
						}
					} catch (IOException e) {
						e.printStackTrace();
					} catch (IllegalStateException e){
						LogHelper.error(String.format("There was an error trying to load %s", location), e);
					}
					return null;
				}, backgroundExecutor)
		).toArray(size -> new CompletableFuture[size]);
	}
}
