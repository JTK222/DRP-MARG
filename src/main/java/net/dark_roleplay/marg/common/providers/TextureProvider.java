package net.dark_roleplay.marg.common.providers;

import java.util.Map;

public class TextureProvider {

	private final Map<String, String> textures;

	public TextureProvider(Map<String, String> textures){
		this. textures = textures;
	}

	public Map<String, String> getTextures() {
		return textures;
	}
}
