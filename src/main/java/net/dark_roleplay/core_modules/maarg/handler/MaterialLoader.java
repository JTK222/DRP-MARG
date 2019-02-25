package net.dark_roleplay.core_modules.maarg.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.io.FilenameUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.dark_roleplay.core_modules.maarg.References;
import net.dark_roleplay.core_modules.maarg.objects.other.generators.JsonGenerator;
import net.dark_roleplay.core_modules.maarg.objects.other.generators.LanguageGenerator;
import net.dark_roleplay.core_modules.maarg.objects.other.generators.TextureGenerator;
import net.dark_roleplay.core_modules.maarg.objects.other.loaders.GeneralMaterialLoader;
import net.dark_roleplay.library.resources.ResourceHelper;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MaterialLoader {

	private static GeneralMaterialLoader loader = new GeneralMaterialLoader();

	@SuppressWarnings("deprecation")
	public static void loadCommonGenerators() {
		Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

		Loader.instance().getActiveModList().forEach(mod -> {

			ResourceHelper.findFiles(mod, "data/" + mod.getModId() + "/arg/materials", null, (root, file) -> {
				Loader.instance().setActiveModContainer(mod);

				String relative = root.relativize(file).toString();
				if (!"json".equals(FilenameUtils.getExtension(file.toString())) || relative.startsWith("_"))
					return true;

				String name = FilenameUtils.removeExtension(relative).replaceAll("\\\\", "/");
				ResourceLocation key = new ResourceLocation(mod.getModId(), "arg/materials/" + name);

				try (BufferedReader reader = Files.newBufferedReader(file)) {
					JsonObject json = JsonUtils.fromJson(GSON, reader, JsonObject.class);
					loader.loadMaterial(json, key);
				}catch (JsonParseException e) {
					LogHelper.error(String.format("Parsing error loading Material %s", key, e));
					return false;
				} catch (IOException e) {
					LogHelper.error(String.format("Couldn't read Material %s from %s", key, file), e);
					return false;
				}
				return true;
			}, true, true);

			ResourceHelper.findFiles(mod, "data/" + mod.getModId() + "/arg/json_generators", null, (root, file) -> {
				Loader.instance().setActiveModContainer(mod);


				String relative = root.relativize(file).toString();
				if (!"json".equals(FilenameUtils.getExtension(file.toString())) || relative.startsWith("_"))
					return true;

				String name = FilenameUtils.removeExtension(relative).replaceAll("\\\\", "/");
				ResourceLocation key = new ResourceLocation(mod.getModId(),"/arg/json_generators" +  name);

				try (BufferedReader reader = Files.newBufferedReader(file)) {
					JsonObject json = JsonUtils.fromJson(GSON, reader, JsonObject.class);
					MaterialRegistry.addJsonGenerator(new JsonGenerator(key, json, References.FOLDER_ARG_DATA));
				}catch (JsonParseException e) {
					LogHelper.error(String.format("Parsing error server side json generator %s", key, e));
					return false;
				} catch (IOException e) {
					LogHelper.error(String.format("Couldn't read server side json generator %s from %s", key, file), e);
					return false;
				}
				return true;
			}, true, true);
		});
	}

	@SuppressWarnings("deprecation")
	@SideOnly(Side.CLIENT)
	public static void loadClientGenerators() {
		Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

		Loader.instance().getActiveModList().forEach(mod -> {
			ResourceHelper.findFiles(mod, "assets/" + mod.getModId() + "/arg/json_generators", null, (root, file) -> {
				Loader.instance().setActiveModContainer(mod);

				String relative = root.relativize(file).toString();
				if (!"json".equals(FilenameUtils.getExtension(file.toString())) || relative.startsWith("_"))
					return true;

				String name = FilenameUtils.removeExtension(relative).replaceAll("\\\\", "/");
				ResourceLocation key = new ResourceLocation(mod.getModId(), name);

				try (BufferedReader reader = Files.newBufferedReader(file)) {
					JsonObject json = JsonUtils.fromJson(GSON, reader, JsonObject.class);
					MaterialRegistry.addJsonGeneratorClient(new JsonGenerator(key, json, References.FOLDER_ARG));
				}catch (JsonParseException e) {
					LogHelper.error(String.format("Parsing error client side json generator %s", key, e));
					return false;
				} catch (IOException e) {
					LogHelper.error(String.format("Couldn't read client side json generator %s from %s", key, file), e);
					return false;
				}
				return true;
			}, true, true);

			ResourceHelper.findFiles(mod, "assets/" + mod.getModId() + "/arg/texture_generators", null,
					(root, file) -> {
						Loader.instance().setActiveModContainer(mod);

						String relative = root.relativize(file).toString();
						if (!"json".equals(FilenameUtils.getExtension(file.toString())) || relative.startsWith("_"))
							return true;

						String name = FilenameUtils.removeExtension(relative).replaceAll("\\\\", "/");
						ResourceLocation key = new ResourceLocation(mod.getModId(), name);

						try (BufferedReader reader = Files.newBufferedReader(file)) {
							JsonObject json = JsonUtils.fromJson(GSON, reader, JsonObject.class);
							MaterialRegistry.addTextureGenerator(new TextureGenerator(key ,json));
						}catch (JsonParseException e) {
							LogHelper.error(String.format("Parsing error client side texture generator %s", key, e));
							return false;
						} catch (IOException e) {
							LogHelper.error(String.format("Couldn't read client side texture generator %s from %s", key, file), e);
							return false;
						}
						return true;
					}, true, true);

			ResourceHelper.findFiles(mod, "assets/" + mod.getModId() + "/arg/language_generators", null,
					(root, file) -> {
						Loader.instance().setActiveModContainer(mod);

						String relative = root.relativize(file).toString();
						if (!"json".equals(FilenameUtils.getExtension(file.toString())) || relative.startsWith("_"))
							return true;

						String name = FilenameUtils.removeExtension(relative).replaceAll("\\\\", "/");
						ResourceLocation key = new ResourceLocation(mod.getModId(), name);

						try (BufferedReader reader = Files.newBufferedReader(file)) {
							JsonObject json = JsonUtils.fromJson(GSON, reader, JsonObject.class);
							MaterialRegistry.addLanguageGeneratorClient(new LanguageGenerator(key ,json));
						}catch (JsonParseException e) {
							LogHelper.error(String.format("Parsing error client side language generator %s", key, e));
							return false;
						} catch (IOException e) {
							LogHelper.error(String.format("Couldn't read client side language generator %s from %s", key, file), e);
							return false;
						}
						return true;
					}, true, true);
		});
	}

}
