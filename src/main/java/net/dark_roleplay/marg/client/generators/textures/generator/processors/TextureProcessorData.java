package net.dark_roleplay.marg.client.generators.textures.generator.processors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dark_roleplay.marg.client.generators.textures.texture.TextureHolder;
import net.dark_roleplay.marg.client.generators.textures.util.TextureCache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextureProcessorData {
	private static Map<String, Codec<? extends TextureProcessorData>> CODECS = new HashMap<>();

	static{
		CODECS.put("overlay", TextureInputData.CODEC);
		CODECS.put("mask", TextureInputData.CODEC);
	}

	protected int textureDataIndex = 0;

	public void setTexture(Map<String, TextureHolder> textures, TextureCache cache){}

	public TextureHolder getTexture(){
		return null;
	}

	public int getTextureDataIndex() {
		return textureDataIndex;
	}

	public void setTextureDataIndex(int textureDataIndex) {
		this.textureDataIndex = textureDataIndex;
	}

	private static final Codec<TextureProcessorData> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.STRING.optionalFieldOf("type", null).forGetter(t -> null)
	).apply(i, TextureProcessorData::new));

	public static Codec<? extends TextureProcessorData> getCodecForType(String type){
		Codec<? extends TextureProcessorData> codec = CODECS.get(type);
		if(codec == null) return null; //TODO log error
		return codec;
	}

	public TextureProcessorData() {}

	private TextureProcessorData(String t) {}
}
