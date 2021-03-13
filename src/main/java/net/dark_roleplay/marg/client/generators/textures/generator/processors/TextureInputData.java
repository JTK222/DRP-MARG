package net.dark_roleplay.marg.client.generators.textures.generator.processors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dark_roleplay.marg.client.generators.textures.texture.TextureHolder;
import net.dark_roleplay.marg.client.generators.textures.util.TextureCache;

import java.util.List;
import java.util.Map;

public class TextureInputData extends TextureProcessorData {

	public static final Codec<TextureInputData> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.STRING.optionalFieldOf("cachedTexture", "").forGetter(TextureInputData::getCachedTextureName),
			Codec.STRING.optionalFieldOf("texture", "").forGetter(TextureInputData::getTextureName)
	).apply(i, TextureInputData::new));

	private String cachedTextureName;
	private String textureName;
	private TextureHolder texture;

	public TextureInputData(String cachedTextureName, String textureName) {
		this.cachedTextureName = cachedTextureName;
		this.textureName = textureName;
	}

	@Override
	public void setTexture(Map<String, TextureHolder> textures, TextureCache cache){
		if(!getCachedTextureName().isEmpty())
			this.texture = cache.getCachedTexture(cachedTextureName);
		if(!getTextureName().isEmpty())
			this.texture = textures.get(textureName);
	}

	@Override
	public TextureHolder getTexture(){
		return this.texture;
	}

	public String getCachedTextureName() {
		return cachedTextureName;
	}
	public String getTextureName() {
		return textureName;
	}
}
