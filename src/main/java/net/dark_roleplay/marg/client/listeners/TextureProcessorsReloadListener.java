package net.dark_roleplay.marg.client.listeners;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.serialization.JsonOps;
import net.dark_roleplay.marg.api.materials.MaterialRegistry;
import net.dark_roleplay.marg.client.generators.textures.generator.TextureGenerator;
import net.dark_roleplay.marg.client.generators.textures.texture.TextureHolder;
import net.dark_roleplay.marg.client.generators.textures.util.TextureUtils;
import net.dark_roleplay.marg.client.providers.ClientTextureProvider;
import net.dark_roleplay.marg.common.material.Material;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class TextureProcessorsReloadListener implements IFutureReloadListener {
	private static final Gson GSON = new Gson();
	private static final Logger LOGGER = LogManager.getLogger();
	private static final String TEX_GENERATORS_FOLDER = "marg_generators/textures/";
	private static final List<TextureGenerator> TEXTURE_GENERATORS = new ArrayList<>();
	private static final Map<ResourceLocation, TextureHolder> BASE_TEXTURES = new HashMap<>();

	@Override
	public final CompletableFuture<Void> reload(IFutureReloadListener.IStage stage, IResourceManager resourceManager, IProfiler preparationsProfiler, IProfiler reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
		return CompletableFuture
				.supplyAsync(() -> this.loadJsons(resourceManager, preparationsProfiler), backgroundExecutor)
				.thenApplyAsync(jsons -> loadGenerator(jsons, resourceManager, preparationsProfiler), backgroundExecutor)
				.thenCompose(stage::markCompleteAwaitingOthers).thenAcceptAsync(val -> {
					//synchronized Processing
				}, gameExecutor);
	}

	protected Map<ResourceLocation, JsonElement> loadJsons(IResourceManager resourceManager, IProfiler profiler) {
		Map<ResourceLocation, JsonElement> map = Maps.newHashMap();

		for (ResourceLocation resourceLocation : resourceManager.getAllResourceLocations(TEX_GENERATORS_FOLDER, name -> name.endsWith(".json"))) {
			try (
					IResource iresource = resourceManager.getResource(resourceLocation);
					InputStream inputstream = iresource.getInputStream();
					Reader reader = new BufferedReader(new InputStreamReader(inputstream, StandardCharsets.UTF_8));
			) {
				JsonElement jsonelement = JSONUtils.fromJson(this.GSON, reader, JsonElement.class);
				if (jsonelement != null) {
					JsonElement jsonelement1 = map.put(resourceLocation, jsonelement);
					if (jsonelement1 != null) {
						throw new IllegalStateException("Duplicate data file ignored with ID " + resourceLocation);
					}
				} else {
					LOGGER.error("Couldn't load data file {} from {} as it's null or empty", resourceLocation, resourceLocation);
				}
			} catch (IllegalArgumentException | IOException | JsonParseException jsonparseexception) {
				LOGGER.error("Couldn't parse data file {} from {}", resourceLocation, resourceLocation, jsonparseexception);
			}
		}

		return map;
	}

	protected Map<ResourceLocation, TextureGenerator> loadGenerator(Map<ResourceLocation, JsonElement> objects, IResourceManager resourceManager, IProfiler profiler) {
		profiler.startSection("marg");

		//region Loading
		profiler.startSection("loading");

		//region Generator
		profiler.startSection("generators");
		Map<ResourceLocation, TextureGenerator> generators = Maps.newHashMap();

		for (Map.Entry<ResourceLocation, JsonElement> generator : objects.entrySet()) {
			JsonElement json = generator.getValue();
			TextureGenerator.CODEC.parse(JsonOps.INSTANCE, json)
					.resultOrPartial(errorMessage -> {
						System.out.println(errorMessage);
					})//TODO log error
					.ifPresent(gen -> generators.put(generator.getKey(), gen));
		}
		profiler.endSection();
		//endregion

		//region Material Textures
		profiler.startSection("material_textures");
		for(Material mat : MaterialRegistry.getInstance().getMaterials()){
			ClientTextureProvider prov = (ClientTextureProvider) mat.getTextureProvider();
			for(Map.Entry<String, String> textureEntry : prov.getTextures().entrySet()){
				ResourceLocation textureLocation = new ResourceLocation(textureEntry.getValue());
				TextureHolder texture = TextureUtils.loadTexture(textureLocation, resourceManager);
				prov.setTexture(textureEntry.getKey(), texture);
			}
		}
		profiler.endSection();
		//endregion

		//region Generator Textures
		profiler.startSection("generator_textures");
		for (Map.Entry<ResourceLocation, TextureGenerator> entry : generators.entrySet()) {
			TextureGenerator generator = entry.getValue();
			Map<String, String> userInputs = generator.getUserInputs();
			for(Map.Entry<String, String> textureEntry : userInputs.entrySet()){
				ResourceLocation textureLocation = new ResourceLocation(textureEntry.getValue());
				TextureHolder texture = BASE_TEXTURES.computeIfAbsent(textureLocation, loc -> TextureUtils.loadTexture(new ResourceLocation(loc.getNamespace(), loc.getPath()), resourceManager));
				generator.addTexture(textureEntry.getKey(), texture);
			}
		}
		profiler.endSection();
		//endregion

		profiler.endSection();
		//endregion

		//region Processing
		profiler.startSection("processing");


		for (Map.Entry<ResourceLocation, TextureGenerator> entry : generators.entrySet()) {
			TextureGenerator generator = entry.getValue();
			generator.getCondition().forEach(material -> {
				generator.generate(material);
			});
		}

		profiler.endSection();
		//endregion

		//region Saving
		profiler.startSection("saving");

		profiler.endSection();
		//endregion

		profiler.endSection();
		return generators;
	}
}
