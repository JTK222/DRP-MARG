package net.dark_roleplay.marg.objects.other.generators.client;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Set;

import net.dark_roleplay.marg.api.materials.Material;
import net.dark_roleplay.marg.helpers.FileHelpers;
import org.apache.commons.io.IOUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.dark_roleplay.marg.handler.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResource;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class ClientJsonGenerator extends ClientSideGenerator {

	private JsonArray jsonFiles = null;

	public ClientJsonGenerator(ResourceLocation file, JsonObject json) {
		super(file, json, 0);
	}

	@Override
	public void prepare() {
		jsonFiles = JSONUtils.getJsonArray(this.json, "jsons", new JsonArray());
	}

	@Override
	public boolean shouldGenerate(Set<Material> set) {
		boolean needsToGenerate = false;

		for(int i = 0; !needsToGenerate && i < this.jsonFiles.size(); i++ ) {
			JsonObject	file	= jsonFiles.get(i).getAsJsonObject();

			String		output	= JSONUtils.getString(file, "output", null);
			if(output == null) continue;

			for(Material mat : set) {
				needsToGenerate |= !FileHelpers.doesFileExistClient(mat.getType().getNamed(output, mat));
			}
		}

		return needsToGenerate;
	}

	@Override
	public void generate(Set<Material> set) {
		StringBuilder builder = new StringBuilder();
		builder.append("There were some errors trying to generate JSON files from: ");
		builder.append(this.getFile());
		builder.append("\n");

		boolean errored = false;

		for(int i = 0; i < this.jsonFiles.size(); i++ ) {
			try {
				JsonObject	file			= jsonFiles.get(i).getAsJsonObject();

				// Read input file
				String		input			= null;
				IResource	inputResource	= Minecraft.getInstance().getResourceManager().getResource(new ResourceLocation(JSONUtils.getString(file, "input", "error:none")));
				if(inputResource == null) {
					errored = true;
					builder.append("Couldn't find File: " + JSONUtils.getString(file, "input", "error:none") + "\n");
					continue;
				}

				try(InputStream inputStream = new BufferedInputStream(inputResource.getInputStream())) {
					input = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
				}

				// Get output location
				String output = JSONUtils.getString(file, "output", null);
				if(output == null) continue;

				// Generate files for materials.
				for(Material mat : set) {
					File dest = FileHelpers.getFileClient(mat.getType().getNamed(output, mat));
					if(dest.exists()) continue;

					dest.getParentFile().mkdirs();

					String outputContent = mat.getType().getNamed(input, mat);
					try {
						Files.write(dest.toPath(), outputContent.getBytes(StandardCharsets.UTF_8));
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			} catch(Exception error) {

			}
		}

		if(errored) {
			LogHelper.error(builder.toString());
		}
	}
}