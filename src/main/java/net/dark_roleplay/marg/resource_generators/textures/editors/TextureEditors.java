package net.dark_roleplay.marg.resource_generators.textures.editors;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class TextureEditors {

	/**
	 * Masks the sourceImage with the maskImage
	 *
	 * @param sourceImage
	 * @param maskImage
	 * @return a modified copy of the sourceImage, masked with the maskImage
	 */
	public static BufferedImage maskImage(BufferedImage sourceImage, BufferedImage maskImage) {
		int sizeComp = TextureResize.areTexturesSameSize(sourceImage, maskImage);
		if(sizeComp == 1) {
			maskImage = TextureResize.resizeTexture(maskImage, sourceImage);
			sourceImage = copyImage(sourceImage);
		} else if(sizeComp == 2) {
			sourceImage = TextureResize.resizeTexture(sourceImage, maskImage);
		} else {
			sourceImage = copyImage(sourceImage);
		}

		WritableRaster	raster		= sourceImage.getRaster();
		Raster			maskData	= maskImage.getRaster();

		int[]			pixel		= new int[4];
		int[]			pixel2		= new int[4];
		int				width		= sourceImage.getWidth(null);
		int				height		= sourceImage.getHeight(null);

		for(int y = 0; y < height; y++ ) {
			for(int x = 0; x < width; x++ ) {
				pixel = maskData.getPixel(x, y, pixel);
				pixel2 = raster.getPixel(x, y, pixel2);
				pixel2[0] &= pixel[0];
				pixel2[1] &= pixel[1];
				pixel2[2] &= pixel[2];
				pixel2[3] &= pixel[3];

				raster.setPixel(x, y, pixel2);
			}
		}

		sourceImage.setData(raster);

		return sourceImage;
	}

	/**
	 * Overlays the overlayImage over the sourceImage
	 *
	 * @param sourceImage
	 * @param overlayImage
	 * @return a modified copy of the sourceImage, overlayed with the
	 *         overlayImage
	 */
	public static BufferedImage overlayImage(BufferedImage sourceImage, BufferedImage overlayImage) {
		int sizeComp = TextureResize.areTexturesSameSize(sourceImage, overlayImage);
		if(sizeComp == 1) {
			overlayImage = TextureResize.resizeTexture(overlayImage, sourceImage);
			sourceImage = copyImage(sourceImage);
		} else if(sizeComp == 2) {
			sourceImage = TextureResize.resizeTexture(sourceImage, overlayImage);
		} else {
			sourceImage = copyImage(sourceImage);
		}

		WritableRaster	raster		= sourceImage.getRaster();
		Raster			maskData	= overlayImage.getRaster();

		int[]			pixel		= new int[4];
		int[]			pixel2		= new int[4];
		int				width		= sourceImage.getWidth(null);
		int				height		= sourceImage.getHeight(null);

		for(int y = 0; y < height; y++ ) {
			for(int x = 0; x < width; x++ ) {
				pixel = maskData.getPixel(x, y, pixel);
				pixel2 = raster.getPixel(x, y, pixel2);
				pixel2[0] = (pixel[0] * pixel[3] / 255) + (pixel2[0] * pixel2[3] * (255 - pixel[3]) / 65025);
				pixel2[1] = (pixel[1] * pixel[3] / 255) + (pixel2[1] * pixel2[3] * (255 - pixel[3]) / 65025);
				pixel2[2] = (pixel[2] * pixel[3] / 255) + (pixel2[2] * pixel2[3] * (255 - pixel[3]) / 65025);
				pixel2[3] = pixel[3] + (pixel2[3] * (255 - pixel[3]) / 255);

				raster.setPixel(x, y, pixel2);
			}
		}
		sourceImage.setData(raster);

		return sourceImage;
	}

	/**
	 * Rotates the sourceImage by the passed in rotationAngle
	 *
	 * @param sourceImage
	 * @param rotationAngle
	 *                      in degrees
	 * @return a modified copy of the sourceImage
	 */
	public static BufferedImage rotateImage(BufferedImage sourceImage, int rotationAngle) {
		double			sin			= Math.abs(Math.sin(Math.toRadians(rotationAngle))), cos = Math.abs(Math.cos(Math.toRadians(rotationAngle)));

		int				width		= sourceImage.getWidth(null), height = sourceImage.getHeight(null);

		int				newWidth	= (int) Math.floor(width * cos + height * sin);
		int				newHeight	= (int) Math.floor(height * cos + width * sin);

		BufferedImage	copy		= new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D		graphics	= copy.createGraphics();

		graphics.translate( (newWidth - width) / 2, (newHeight - height) / 2);
		graphics.rotate(Math.toRadians(rotationAngle), width / 2, height / 2);
		graphics.drawRenderedImage(sourceImage, null);
		graphics.dispose();

		return copy;
	}

	/**
	 * Flips the Source Image.
	 * 
	 * @param sourceImage
	 * @param flipOnX     if true the sourceImage is flipped horizontally,
	 *                    otherwise vertically
	 * @return a modified copy of the sourceImage
	 */
	public static BufferedImage flipImage(BufferedImage sourceImage, boolean flipOnX) {
		BufferedImage	copy		= new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D		graphics	= copy.createGraphics();

		AffineTransform	at			= new AffineTransform();
		if(flipOnX) {
			at.concatenate(AffineTransform.getScaleInstance(1, -1));
			at.concatenate(AffineTransform.getTranslateInstance(0, -sourceImage.getHeight()));
		} else {
			at.concatenate(AffineTransform.getScaleInstance( -1, 1));
			at.concatenate(AffineTransform.getTranslateInstance( -sourceImage.getWidth(), 0));
		}

		graphics.transform(at);
		graphics.drawRenderedImage(sourceImage, null);
		graphics.dispose();

		return sourceImage;
	}

	private static BufferedImage copyImage(BufferedImage source) {
		BufferedImage	b	= new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D		g	= b.createGraphics();
		g.drawImage(source, 0, 0, null);
		g.dispose();
		return b;
	}
}