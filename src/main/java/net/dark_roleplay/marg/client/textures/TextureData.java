package net.dark_roleplay.marg.client.textures;

import java.util.Arrays;

public class TextureData {

	private final int width;
	private final int height;
	private final int[][] pixels;

	public TextureData(int width, int height){
		this.width = width;
		this.height = height;
		this.pixels = new int[width][height];
	}

	public int getWidth(){
		return this.width;
	}

	public int getHeight(){
		return this.height;
	}

	public int getPixel(int x, int y){
		return pixels[x][y];
	}

	public void getARGB(int x, int y, int[] dest){
		int pixel = pixels[x][y];
		dest[0] = pixel >> 24 & 0xFF;
		dest[1] = pixel >> 16 & 0xFF;
		dest[2] = pixel >> 8 & 0xFF;
		dest[3] = pixel & 0xFF;
	}

	public void setPixel(int x, int y, int argb){
		this.pixels[x][y] = argb;
	}

	public void copyToIntArray(int[] target){
		for(int x = 0; x < this.width; x++)
			for(int y = 0; y < this.height; y++)
				target[x * height + y] = pixels[x][y];
	}

	private TextureData copyPixelData(int[][] source){
		for(int i = 0; i < this.width; i++){
			pixels[i] = Arrays.copyOf(source[i], this.height);
		}
		return this;
	}

	public TextureData clone(){
		return new TextureData(this.width, this.height).copyPixelData(this.pixels);
	}
}
