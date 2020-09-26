package net.dark_roleplay.marg.api.textures.helper;

import net.dark_roleplay.marg.api.textures.processors.IImageProcessor;

public class TextureHolder {

	private TextureData textureData;

	public TextureHolder(TextureData textureData){
		this.textureData = textureData;
	}

	public void applyProcessor(IImageProcessor processor, TextureData input){
		this.textureData = processor.processImages(this.textureData, input);
	}

	public TextureData getTextureData(){
		return this.textureData;
	}

	public TextureHolder clone(){
		return new TextureHolder(textureData.clone());
	}
}
