package net.dark_roleplay.marg.objects.other.generators.textures;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TextureResize {

	/**
	 * Compares 2 Images in their width and height
	 * 
	 * @param image1 the first image to be compared
	 * @param image2 the second image to be compared
	 * @return
	 * 		0 - if both mages are the same size<br>
	 *         1 - if image1 is larger<br>
	 *         2 - if image2 is larger
	 */
	public static int areTexturesSameSize(BufferedImage image1, BufferedImage image2) {
		final int	image1W	= image1.getWidth(), image1H = image1.getHeight();
		final int	image2W	= image2.getWidth(), image2H = image2.getHeight();

		if(image1W == image2W && image1H == image2H) return 0;
		if(image1W < image2W)
			return 2;
		else
			return 1;
	}

	/**
	 * Resizes the sourceImage to the same width and height as the targetImage
	 * 
	 * @param sourceImage :BufferedImage Test
	 * @param targetImage :BufferedImage Test2
	 * @return a new BufferedImage with the size of the targetImage and the
	 *         content of the sourceImage
	 */
	public static BufferedImage resizeTexture(BufferedImage sourceImage, BufferedImage targetImage) {
		final int		targetWidth	= targetImage.getWidth(), targetHeight = targetImage.getHeight();

		BufferedImage	b			= new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D		g			= b.createGraphics();
		g.drawImage(sourceImage, 0, 0, targetWidth, targetHeight, null);
		g.dispose();
		return b;
	}
}
