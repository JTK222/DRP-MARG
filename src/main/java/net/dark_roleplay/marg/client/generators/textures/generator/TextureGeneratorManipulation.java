package net.dark_roleplay.marg.client.generators.textures.generator;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.KeyDispatchCodec;
import com.mojang.serialization.codecs.PairCodec;
import net.dark_roleplay.marg.client.generators.textures.generator.processors.TextureProcessorData;
import net.dark_roleplay.marg.client.generators.textures.generator.processors.TextureProcessors;
import net.dark_roleplay.marg.client.generators.textures.texture.TextureData;
import net.dark_roleplay.marg.util.EnumDecoder;

import java.util.function.BiFunction;

public class TextureGeneratorManipulation {

	public static final Codec<TextureGeneratorManipulation> CODEC = new PairCodec<>(
			ManipulationType.CODEC.fieldOf("type").codec(),
			KeyDispatchCodec.<String, TextureProcessorData>unsafe(
					"type",
					Codec.STRING,
					data -> DataResult.error("Cannot convert TextureProcessorData Codec back to type"),
					type -> DataResult.success(TextureProcessorData.getCodecForType(type)),
					type -> DataResult.error("Cannot encode TextureProcessorData")
			).codec()
	).xmap(TextureGeneratorManipulation::new, val -> Pair.of(val.getManipulationType(), val.getData()));

	private final ManipulationType manipulationType;
	private final TextureProcessorData data;

	public TextureGeneratorManipulation(Pair<ManipulationType, TextureProcessorData> manData) {
		this.manipulationType = manData.getFirst();
		this.data = manData.getSecond();
	}

	public ManipulationType getManipulationType() {
		return manipulationType;
	}

	public TextureProcessorData getData() {
		return data;
	}

	public enum ManipulationType {
		overlay(TextureProcessors::overlay),
		mask(TextureProcessors::mask);

		private static Codec<ManipulationType> CODEC = Codec.STRING.comapFlatMap(new EnumDecoder<ManipulationType>(ManipulationType.class)::decode, ManipulationType::toString).stable();

		private BiFunction<TextureData, TextureProcessorData, TextureData> processor;

		ManipulationType(BiFunction<TextureData, TextureProcessorData, TextureData> processor){
			this.processor = processor;
		}

		public BiFunction<TextureData, TextureProcessorData, TextureData> getProcessor() {
			return processor;
		}
	}
}
