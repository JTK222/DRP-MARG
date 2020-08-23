package net.dark_roleplay.assetier.helper;

import net.dark_roleplay.assetier.processors.IImageProcessor;

public class TextureHolder {

	private TextureData textureData;

	public TextureHolder(TextureData textureData){
		this.textureData = textureData;
	}

	public void applyProcessor(IImageProcessor processor, TextureData input){
		this.textureData = processor.processImages(this.textureData, input);
	}
}
