package net.dark_roleplay.marg.common.providers;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProvider {

	private static Pattern regexPattern = Pattern.compile("\\$\\{([a-z]+%?[a-z|_]+)\\}");

	private final Map<String, String> texts = new HashMap<>();

	public TextProvider(String materialName){
		texts.put("material", materialName);
	}

	public void addEntry(String key, String value){
		texts.put(key, value);
	}

	public String apply(String source){
		Matcher m = regexPattern.matcher(source);
		StringBuilder out = new StringBuilder();
		int lastEnd = 0;
		while(m.find()){
			String replacement = this.texts.get(m.group(1));
			out.append(source.subSequence(lastEnd, replacement == null ? m.end(0) : m.start(0)));
			lastEnd = m.end(0);
			if(replacement != null) out.append(replacement);
		}
		out.append(source.subSequence(lastEnd, source.length()));

		return out.toString();
	}

	public boolean hasKey(String key){
		return texts.containsKey(key);
	}
}
