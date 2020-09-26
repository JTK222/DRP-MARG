package net.dark_roleplay.marg.util.texture;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class TextureEditors {

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