package net.dark_roleplay.marg.client.generators.textures.texture;

import com.mojang.datafixers.util.Function3;
import net.dark_roleplay.marg.client.generators.textures.generator.processors.TextureProcessorData;
import net.minecraft.util.ResourceLocation;

import java.util.function.BiFunction;

public class TextureHolder {

	private ResourceLocation textureLocation;
	private TextureData[] textureData;

	public TextureHolder(ResourceLocation location, TextureData[] textureData) {
		this.textureLocation = location;
		this.textureData = textureData;
	}

	public int frameCount(){
		return textureData.length;
	}

	public void applyProcessor(BiFunction<TextureData, TextureProcessorData, TextureData> processor, TextureProcessorData data){
		data.setTextureDataIndex(0);
		TextureHolder input = data.getTexture();
		if(input == null || frameCount() == input.frameCount())
			for(int i = 0; i < textureData.length; i++){
				data.setTextureDataIndex(i);
				this.textureData[i] = processor.apply(this.textureData[i], data);
			}
		else if(frameCount() > input.frameCount())
			for(int i = 0; i < textureData.length; i++)
				this.textureData[i] = processor.apply(this.textureData[i], data);
		else if(frameCount() < input.frameCount()){
			TextureData[] newTextureData = new TextureData[input.frameCount()];
			for(int i = 0; i < newTextureData.length; i++){
				data.setTextureDataIndex(i);
				newTextureData[i] = processor.apply(this.textureData[0].clone(), data);
			}
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
