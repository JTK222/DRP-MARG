package net.dark_roleplay.marg.client.generators.textures.util;

import net.dark_roleplay.marg.client.generators.textures.texture.TextureHolder;
import net.dark_roleplay.marg.common.material.Material;

import java.util.HashMap;
import java.util.Map;

public class TextureCache {
	private static Map<Material, TextureCache> GLOBAL_CACHE = new HashMap<>();

	public static TextureCache getGlobalCacheFor(Material material){
		return GLOBAL_CACHE.computeIfAbsent(material, mat -> new TextureCache());
	}

	public static void clearGlobalCache(){
		GLOBAL_CACHE.values().forEach(val -> val.clear());
		GLOBAL_CACHE.clear();
	}

	private TextureCache parent = null;
	private Map<String, TextureHolder> textureCache = new HashMap<>();

	public TextureCache(){}
	public TextureCache(TextureCache parent){
		this();
		this.parent = parent;
	}

	public TextureHolder getCachedTexture(String key){
		TextureHolder texture = textureCache.get(key);
		if(texture == null && parent != null)
			texture = parent.getCachedTexture(key);
		return texture;
	}

	public void addTexture(String key, TextureHolder texture){
		this.textureCache.put(key, texture);
	}

	public void clear(){
		this.textureCache.clear();
	}
}
