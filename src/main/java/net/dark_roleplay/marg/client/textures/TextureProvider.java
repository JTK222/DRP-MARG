package net.dark_roleplay.marg.client.textures;

import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class TextureProvider {

	Map<String, TextureHolder> textures = new HashMap<>();

	public boolean hasTexture(String key){
		return textures.containsKey(key);
	}

	public ResourceLocation getTextureLocation(String key){
		TextureHolder texture = textures.get(key);
		return texture == null ? null : texture.getTextureLocation();
	}

	public TextureHolder getTexture(String key){
		return textures.get(key);
	}
}
