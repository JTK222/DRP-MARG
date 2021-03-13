package net.dark_roleplay.marg.common.providers;

import java.util.HashMap;
import java.util.Map;

public class TextProvider {

	private final Map<String, String> TEXTS = new HashMap<>();

	public TextProvider(String materialName){
		TEXTS.put("material", materialName);
	}

	public void addEntry(String key, String value){
		TEXTS.put(key, value);
	}

	public String apply(String source){
		return source;
	}

	public boolean hasKey(String key){
		return TEXTS.containsKey(key);
	}
}
