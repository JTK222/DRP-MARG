package net.dark_roleplay.marg.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import com.google.gson.stream.JsonReader;
import net.dark_roleplay.marg.assets.textures.generator.TextureGenerator;
import net.dark_roleplay.marg.objects.other.generators.client.ClientJsonGenerator;
import net.dark_roleplay.marg.objects.other.generators.client.ClientTextureGenerator;
import net.dark_roleplay.marg.objects.other.loaders.GeneralMaterialLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class MaterialLoader {

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

	private static GeneralMaterialLoader loader = new GeneralMaterialLoader();

	//PORT
	public static void loadCommonGenerators() {
//		Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
//		
//		ModList.get().getMods().forEach(modInfo -> {
//
//			ResourceHelper.findFiles(modInfo, "data/" + modInfo.getModId() + "/arg/materials", null, (root, file) -> {
//				Loader.instance().setActiveModContainer(mod);
//
//				String relative = root.relativize(file).toString();
//				if (!"json".equals(FilenameUtils.getExtension(file.toString())) || relative.startsWith("_"))
//					return true;
//
//				String name = FilenameUtils.removeExtension(relative).replaceAll("\\\\", "/");
//				ResourceLocation key = new ResourceLocation(mod.getModId(), "arg/materials/" + name);
//
//				try (BufferedReader reader = Files.newBufferedReader(file)) {
//					JsonObject json = JsonUtils.fromJson(GSON, reader, JsonObject.class);
//					loader.loadMaterial(json, key);
//				}catch (JsonParseException e) {
//					LogHelper.error(String.format("Parsing error loading Material %s", key, e));
//					return false;
//				} catch (IOException e) {
//					LogHelper.error(String.format("Couldn't read Material %s from %s", key, file), e);
//					return false;
//				}
//				return true;
//			}, true, true);
//
//			ResourceHelper.findFiles(modInfo, "data/" + modInfo.getModId() + "/arg/json_generators", null, (root, file) -> {
//				Loader.instance().setActiveModContainer(mod);
//
//
//				String relative = root.relativize(file).toString();
//				if (!"json".equals(FilenameUtils.getExtension(file.toString())) || relative.startsWith("_"))
//					return true;
//
//				String name = FilenameUtils.removeExtension(relative).replaceAll("\\\\", "/");
//				ResourceLocation key = new ResourceLocation(mod.getModId(),"/arg/json_generators" +  name);
//
//				try (BufferedReader reader = Files.newBufferedReader(file)) {
//					JsonObject json = JsonUtils.fromJson(GSON, reader, JsonObject.class);
//					MaterialRegistry.addJsonGenerator(new JsonGenerator(key, json, References.FOLDER_ARG_DATA));
//				}catch (JsonParseException e) {
//					LogHelper.error(String.format("Parsing error server side json generator %s", key, e));
//					return false;
//				} catch (IOException e) {
//					LogHelper.error(String.format("Couldn't read server side json generator %s from %s", key, file), e);
//					return false;
//				}
//				return true;
//			}, true, true);
//		});
	}
	
	public static CompletableFuture[] loadJsonGeneratorsClient(IResourceManager manager, Executor backgroundExecutor) {

		return manager.getAllResourceLocations("marg/json_generators", fileName -> fileName.endsWith(".json")).stream().map(location ->
			CompletableFuture.runAsync(() -> {
				try {
					IResource resource = Minecraft.getInstance().getResourceManager().getResource(location);
					try(BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))){
						JsonObject json = JSONUtils.fromJson(GSON, reader, JsonObject.class);
						MaterialRegistry.addClientSideGenerator(new ClientJsonGenerator(location, json));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}, backgroundExecutor)
		).toArray(size -> new CompletableFuture[size]);
	}
	
	public static CompletableFuture[] loadTextureGeneratorsClient(IResourceManager manager, Executor backgroundExecutor) {
		return manager.getAllResourceLocations("marg/texture_generators2", fileName -> fileName.endsWith(".json")).stream().map(location ->
			CompletableFuture.runAsync(() -> {
				try {
					IResource resource = Minecraft.getInstance().getResourceManager().getResource(location);
					try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
						JsonObject json = JSONUtils.fromJson(GSON, reader, JsonObject.class);
						MaterialRegistry.addClientSideGenerator(new ClientTextureGenerator(location, json));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}, backgroundExecutor)
		).toArray(size -> new CompletableFuture[size]);
	}

	public static CompletableFuture<TextureGenerator>[] loadTextureGeneratorsClientV2(IResourceManager manager, Executor backgroundExecutor) {
		return manager.getAllResourceLocations("marg/texture_generators", fileName -> fileName.endsWith(".json")).stream().map(location ->
				CompletableFuture.<TextureGenerator>supplyAsync(() -> {
					try {
						IResource resource = Minecraft.getInstance().getResourceManager().getResource(location);
						try (JsonReader reader = new JsonReader(new BufferedReader(new InputStreamReader(resource.getInputStream())))) {
							return new TextureGenerator(location, reader);
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

	
	public static void loadClientGenerators(IResourceManager manager) {
		
		
//		Minecraft.getInstance().getResourceManager().getAllResourceLocations("/marg/language_generators", (fileName) -> {return fileName.endsWith(".json");}).forEach((location) -> {
//			try {
//				IResource resource = Minecraft.getInstance().getResourceManager().getResource(location);
//				try(BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))){
//					JsonObject json = JsonUtils.fromJson(GSON, reader, JsonObject.class);
//					MaterialRegistry.addLanguageGeneratorClient(new LanguageGenerator(location ,json));
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		});
	}
}
