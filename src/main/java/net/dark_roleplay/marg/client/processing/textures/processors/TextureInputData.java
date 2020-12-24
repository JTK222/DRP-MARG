package net.dark_roleplay.marg.client.processing.textures.processors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class TextureInputData extends TextureProcessorData {

	public static final Codec<TextureInputData> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.INT.optionalFieldOf("textureID", -1).forGetter(TextureInputData::getGeneratorInputID),
			Codec.STRING.optionalFieldOf("textureName", null).forGetter(TextureInputData::getCacheName)
	).apply(i, TextureInputData::new));

	private int generatorInputID;
	private String cacheName;

	public TextureInputData(int generatorInputID, String cacheName) {
		this.generatorInputID = generatorInputID;
		this.cacheName = cacheName;
	}



	public int getGeneratorInputID() {
		return generatorInputID;
	}

	public String getCacheName() {
		return cacheName;
	}
}
