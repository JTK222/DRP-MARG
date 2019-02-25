package net.dark_roleplay.core_modules.maarg.objects.other.generators.textures;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class TextureGeneratorCache {

	private static Map<String, BufferedImage> globalCache = new HashMap<String, BufferedImage>();

	private static Map<String, BufferedImage> temporaryCache = new HashMap<String, BufferedImage>();

	public static void addToGlobalCache(String key, BufferedImage image) {
		globalCache.put(key, image);
	}

	public static void addToTemporaryCache(String key, BufferedImage image) {
		temporaryCache.put(key, image);
	}

	public static void clearGlobalCache() {
		globalCache.clear();
	}

	public static void clearTemporaryCache() {
		temporaryCache.clear();
	}

	public static BufferedImage getFromCache(String key) {
		if(temporaryCache.containsKey(key)) {
			return temporaryCache.get(key);
		}else if(globalCache.containsKey(key)) {
			return globalCache.get(key);
		}else {
			//TODO Replace with missingTexture
			return null;
		}
	}

}
