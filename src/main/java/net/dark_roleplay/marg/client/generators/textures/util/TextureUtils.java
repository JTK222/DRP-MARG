package net.dark_roleplay.marg.client.generators.textures.util;

import net.dark_roleplay.marg.client.generators.textures.texture.TextureData;
import net.dark_roleplay.marg.client.generators.textures.texture.TextureHolder;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.lwjgl.stb.STBImage;
import org.lwjgl.stb.STBImageWrite;
import org.lwjgl.system.MemoryStack;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class TextureUtils {

	public static TextureHolder loadTexture(ResourceLocation location, IResourceManager resourceManager){
		location = new ResourceLocation(location.getNamespace(), "textures/" + location.getPath() + ".png");
		try(InputStream input = new BufferedInputStream(resourceManager.getResource(location).getInputStream()); MemoryStack stack = MemoryStack.stackPush()){
			byte[] imageData = IOUtils.toByteArray(input);

			ByteBuffer inputBuf = stack.malloc(imageData.length);
			inputBuf.put(imageData);
			inputBuf.flip();

			IntBuffer width = stack.mallocInt(1);
			IntBuffer height = stack.mallocInt(1);
			IntBuffer channels = stack.mallocInt(1);

			ByteBuffer decodedImage = STBImage.stbi_load_from_memory(inputBuf, width, height, channels, 4);

			//TODO Support for width & height ratio

			int frameWidth = width.get();
			int fHeight = height.get();
			int frames = fHeight/frameWidth;
			TextureData[] textures = new TextureData[frames];
			int frameHeight = fHeight/frames;

			for(int i = 0; i < frames; i++){
				TextureData texture = new TextureData(frameWidth, frameHeight);
				for(int x = 0; x < frameWidth; x++)
					for(int y = 0; y < frameHeight; y++)
						texture.setPixel(x, y, decodedImage.getInt());
				textures[i] = texture;
			}

			return new TextureHolder(location, textures);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void writeTexture(ResourceLocation outputLoc, TextureHolder textureHolder){
		TextureData[] textures = textureHolder.getTextureData();
		boolean needsMetaFile = textures.length > 1;

		int width = textures[0].getWidth(), height = textures[0].getHeight();
		String fileName = FileSystems.getDefault().getPath("./mod_data/marg/resource_pack/assets/" + outputLoc.getNamespace() + "/textures/" + outputLoc.getPath() + ".png").normalize().toAbsolutePath().toString();

		int totalHeight = height * textures.length;

		try(MemoryStack stack = MemoryStack.stackPush()){
			ByteBuffer outputBuf = stack.malloc(textures.length * (width * totalHeight) * 4);

			for(int i = 0; i < textures.length; i++) {
				TextureData texture = textures[i];
				for(int x = 0; x < width; x++)
					for(int y = 0; y < height; y++)
						outputBuf.putInt(texture.getPixel(x, y));
			}

			outputBuf.rewind();

			STBImageWrite.stbi_write_png(fileName, width, totalHeight, 4, outputBuf, width * 4);
		}

		if(needsMetaFile){
			Path metaFile = FileSystems.getDefault().getPath("./mod_data/marg/resource_pack/assets/" + outputLoc.getNamespace() + "/textures/" + outputLoc.getPath() + ".png.mcmeta").normalize();
			String metaFileContent = ""; //TODO add meta content

			try {
				if(!Files.exists(metaFile))
					Files.createDirectories(metaFile.getParent());
				Files.createFile(metaFile);
			} catch (IOException e) {
				e.printStackTrace();
			}

			try(Writer writer = new OutputStreamWriter(new FileOutputStream(metaFile.toFile()), StandardCharsets.UTF_8.name())){
				writer.write(metaFileContent);
			} catch (UnsupportedEncodingException | FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
