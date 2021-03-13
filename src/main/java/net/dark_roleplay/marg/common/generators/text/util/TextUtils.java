package net.dark_roleplay.marg.common.generators.text.util;

import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.LogicalSide;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class TextUtils {

	public static String loadTextFile(ResourceLocation location, IResourceManager resourceManager){
		try(InputStream input = resourceManager.getResource(location).getInputStream()){
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length;
			while ((length = input.read(buffer)) != -1) {
				result.write(buffer, 0, length);
			}
			return result.toString(StandardCharsets.UTF_8.name());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}


	public static void writeTextFile(ResourceLocation outputLoc, LogicalSide side, String output){
		Path outputFile = null;
		if(side.isClient())
			outputFile = FileSystems.getDefault().getPath("./mod_data/marg/resource_pack/assets/" + outputLoc.getNamespace() + "/" + outputLoc.getPath()).normalize();
		if(side.isServer())
			outputFile = FileSystems.getDefault().getPath("./mod_data/marg/data_pack/assets/" + outputLoc.getNamespace() + "/" + outputLoc.getPath()).normalize();


		try {
			if(!Files.exists(outputFile))
				Files.createDirectories(outputFile.getParent());
				Files.createFile(outputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try(Writer writer = new OutputStreamWriter(new FileOutputStream(outputFile.toFile()), StandardCharsets.UTF_8.name())){
			writer.write(output);
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
