package net.dark_roleplay.marg.client.providers;

import net.dark_roleplay.marg.client.generators.textures.texture.TextureHolder;
import net.dark_roleplay.marg.common.providers.TextureProvider;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ClientTextureProvider extends TextureProvider {

	private final Map<String, TextureHolder> textureHolders = new HashMap<>();

	public ClientTextureProvider(Map<String, String> textures) {
		super(textures);
	}

	public void setTexture(String key, TextureHolder texture){
		this.textureHolders.put(key, texture);
	}

	public void wipeHolders(){
		this.textureHolders.clear();
	}

	public boolean hasTexture(String key){
		return textureHolders.containsKey(key);
	}

	public ResourceLocation getTextureLocation(String key){
		TextureHolder texture = textureHolders.get(key);
		return texture == null ? null : texture.getTextureLocation();
	}

	public TextureHolder getTexture(String key){
		return textureHolders.get(key);
	}
}
