package net.dark_roleplay.marg.client.textures;

import com.mojang.datafixers.util.Function3;
import net.dark_roleplay.marg.client.processing.textures.processors.TextureProcessorData;
import net.minecraft.util.ResourceLocation;

public class TextureHolder {

	private ResourceLocation textureLocation;
	private TextureData[] textureData;

	public TextureHolder(ResourceLocation location, TextureData[] textureData) {
		this.textureLocation = location;
		this.textureData = textureData;
	}

	public int size(){
		return textureData.length;
	}

	public void applyProcessor(Function3<TextureData, TextureData, TextureProcessorData, TextureData> processor, TextureHolder input, TextureProcessorData data){
		if(size() == input.size())
			for(int i = 0; i < textureData.length; i++)
				this.textureData[i] = processor.apply(this.textureData[i], input.getTextureData()[i], data);
		else if(size() > input.size())
			for(int i = 0; i < textureData.length; i++)
				this.textureData[i] = processor.apply(this.textureData[i], input.getTextureData()[0], data);
		else if(size() < input.size()){
			TextureData[] newTextureData = new TextureData[input.size()];
			for(int i = 0; i < newTextureData.length; i++)
				newTextureData[i] = processor.apply(this.textureData[0].clone(), input.getTextureData()[i], data);
			this.textureData = newTextureData;
		}
	}

	public TextureData[] getTextureData(){
		return this.textureData;
	}

	public TextureHolder clone(){
		TextureData[] clonedTextureData = new TextureData[textureData.length];
		for(int i = 0; i < textureData.length; i++) clonedTextureData[i] = textureData[i].clone();
		return new TextureHolder(textureLocation, clonedTextureData);
	}

	public ResourceLocation getTextureLocation() {
		return textureLocation;
	}
}
