package net.dark_roleplay.marg.client.generators.textures.generator.processors;

import net.dark_roleplay.marg.client.generators.textures.texture.TextureData;

import java.util.AbstractMap;

public class TextureProcessors {

	public static TextureData overlay(TextureData source, TextureProcessorData data){
		TextureData input = data.getTexture().getTextureData()[data.getTextureDataIndex()];

		int width = source.getWidth(), height = source.getHeight();

		final int[] srcPixel = new int[4];
		final int[] inPixel = new int[4];

		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++){
				source.getARGB(x, y, srcPixel);
				input.getARGB(x, y, inPixel);


				int a = inPixel[0] + (srcPixel[0] * (255 - inPixel[0]) / 255);
				int r = (inPixel[1] * inPixel[0] / 255) + (srcPixel[1] * srcPixel[0] * (255 - inPixel[0]) / 65025);
				int g = (inPixel[2] * inPixel[0] / 255) + (srcPixel[2] * srcPixel[0] * (255 - inPixel[0]) / 65025);
				int b = (inPixel[3] * inPixel[0] / 255) + (srcPixel[3] * srcPixel[0] * (255 - inPixel[0]) / 65025);
				source.setPixel(x, y, a << 24 | r << 16 | g << 8 | b);
			}

		return source;
	}

	public static TextureData mask(TextureData source, TextureProcessorData data){
		TextureData input = data.getTexture().getTextureData()[data.getTextureDataIndex()];

		int width = source.getWidth(), height = source.getHeight();

		final int[] srcPixel = new int[4];
		final int[] inPixel = new int[4];

		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++){
				source.getARGB(x, y, srcPixel);
				input.getARGB(x, y, inPixel);

				int a = srcPixel[0] & inPixel[0];
				int r = srcPixel[1] & inPixel[1];
				int g = srcPixel[2] & inPixel[2];
				int b = srcPixel[3] & inPixel[3];;
				source.setPixel(x, y, a << 24 | r << 16 | g << 8 | b);
			}

		return source;
	}

//	/**
//	 * Rotates the sourceImage by the passed in rotationAngle
//	 *
//	 * @param sourceImage
//	 * @param rotationAngle
//	 *                      in degrees
//	 * @return a modified copy of the sourceImage
//	 */
//	public static BufferedImage rotateImage(BufferedImage sourceImage, int rotationAngle) {
//		double			sin			= Math.abs(Math.sin(Math.toRadians(rotationAngle))), cos = Math.abs(Math.cos(Math.toRadians(rotationAngle)));
//
//		int				width		= sourceImage.getWidth(null), height = sourceImage.getHeight(null);
//
//		int				newWidth	= (int) Math.floor(width * cos + height * sin);
//		int				newHeight	= (int) Math.floor(height * cos + width * sin);
//
//		BufferedImage	copy		= new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
//		Graphics2D graphics	= copy.createGraphics();
//
//		graphics.translate( (newWidth - width) / 2, (newHeight - height) / 2);
//		graphics.rotate(Math.toRadians(rotationAngle), width / 2, height / 2);
//		graphics.drawRenderedImage(sourceImage, null);
//		graphics.dispose();
//
//		return copy;
//	}
//
//	/**
//	 * Flips the Source Image.
//	 *
//	 * @param sourceImage
//	 * @param flipOnX     if true the sourceImage is flipped horizontally,
//	 *                    otherwise vertically
//	 * @return a modified copy of the sourceImage
//	 */
//	public static BufferedImage flipImage(BufferedImage sourceImage, boolean flipOnX) {
//		BufferedImage	copy		= new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
//		Graphics2D		graphics	= copy.createGraphics();
//
//		AffineTransform at			= new AffineTransform();
//		if(flipOnX) {
//			at.concatenate(AffineTransform.getScaleInstance(1, -1));
//			at.concatenate(AffineTransform.getTranslateInstance(0, -sourceImage.getHeight()));
//		} else {
//			at.concatenate(AffineTransform.getScaleInstance( -1, 1));
//			at.concatenate(AffineTransform.getTranslateInstance( -sourceImage.getWidth(), 0));
//		}
//
//		graphics.transform(at);
//		graphics.drawRenderedImage(sourceImage, null);
//		graphics.dispose();
//
//		return sourceImage;
//	}


	private static AbstractMap.SimpleEntry<TextureData, TextureData> equalizeTextures(TextureData source, TextureData input){
		int srcWidth = source.getWidth(), srcHeight = source.getHeight();
		int inWidth = input.getWidth(), inHeight = input.getHeight();

		TextureData workerData = source;

		if(srcWidth > inWidth){
			input = enlargeTexture(input, srcWidth/inWidth);
		}else if(srcWidth < inWidth){
			workerData = enlargeTexture(source, inWidth/srcWidth);
			srcWidth = inWidth;
			srcHeight = inHeight;
		}

		return new AbstractMap.SimpleEntry(workerData, input);
	}

	private static TextureData enlargeTexture(TextureData source, int factor){
		int srcWidth = source.getWidth();
		int srcHeight = source.getHeight();

		TextureData worker = new TextureData(source.getWidth() * factor, source.getHeight() * factor);
		for(int x = 0; x < srcWidth; x++)
			for(int y = 0; y < srcHeight; y++)
				for(int x2 = 0; x2 < factor; x2++)
					for(int y2 = 0; y2 < factor; y2++)
						worker.setPixel(x * factor + x2, y * factor + y2, source.getPixel(x, y));

		return worker;
	}
}
