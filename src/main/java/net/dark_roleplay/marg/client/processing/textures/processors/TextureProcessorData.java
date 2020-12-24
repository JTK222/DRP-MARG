package net.dark_roleplay.marg.client.processing.textures.processors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.HashMap;
import java.util.Map;

public class TextureProcessorData {
	private static Map<String, Codec<? extends TextureProcessorData>> CODECS = new HashMap<>();

	static{
		CODECS.put("overlay", TextureInputData.CODEC);
		CODECS.put("mask", TextureInputData.CODEC);
	}

	private static final Codec<TextureProcessorData> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.STRING.optionalFieldOf("type", null).forGetter(t -> null)
	).apply(i, TextureProcessorData::new));

	public static Codec<? extends TextureProcessorData> getCodecForType(String type){
		Codec<? extends TextureProcessorData> codec = CODECS.get(type);
		if(codec == null) return CODEC;
		return codec;
	}

	public TextureProcessorData() {}

	private TextureProcessorData(String t) {}
}
