package net.dark_roleplay.marg.io;

import net.dark_roleplay.marg.client.textures.TextureData;
import net.minecraft.resources.IResource;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TextureDataIO {

	private static final int staticAlpha = 0xFF << 24;

	public static TextureData loadDataFromResources(IResource resource){

		try(InputStream stream = new BufferedInputStream(resource.getInputStream())){
			BufferedImage img = ImageIO.read(stream);
			return loadFromBufferedImage(img);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static TextureData loadFromBufferedImage(BufferedImage image) {
		DataBuffer buffer = image.getRaster().getDataBuffer();

		final int width = image.getWidth();
		final int height = image.getHeight();
		final boolean hasAlpha = image.getAlphaRaster() != null;

		TextureData texture = new TextureData(width, height);

		if(buffer instanceof DataBufferByte){
			final byte[] pixels = ((DataBufferByte) buffer).getData();

			final int pixelLength = 4;
			for (int pixel = 0, y = 0, x = 0; pixel + 3 < pixels.length; pixel += pixelLength) {
				int argb = !hasAlpha ? staticAlpha : ((int) pixels[pixel] & 0xff) << 24;
				argb |= (int) pixels[pixel + 1] & 0xff; // blue
				argb |= ((int) pixels[pixel + 2] & 0xff) << 8; // green
				argb |= ((int) pixels[pixel + 3] & 0xff) << 16; // red
				texture.setPixel(y, x, argb);
				x++;
				if (x == width) {
					x = 0;
					y++;
				}
			}
		}else if(buffer instanceof DataBufferInt){
			final int[] pixels = ((DataBufferInt) buffer).getData();

			for (int pixel = 0, y = 0, x = 0; pixel < pixels.length; pixel ++) {
				texture.setPixel(y, x, pixels[pixel]);

				x++;
				if(x == width){
					x = 0;
					y++;
				}
			}
		}

		return texture;
	}
}
