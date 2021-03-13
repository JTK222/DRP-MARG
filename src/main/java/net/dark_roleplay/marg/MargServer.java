package net.dark_roleplay.marg;

import net.dark_roleplay.marg.common.providers.TextureProvider;

import java.util.Map;

public class MargServer {

	public static TextureProvider createTextureProvider(Map<String, String> textures){
		return new TextureProvider(textures);
	}
}
