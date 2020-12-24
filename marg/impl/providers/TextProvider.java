package net.dark_roleplay.marg.impl.providers;

import net.dark_roleplay.marg.api.provider.ITextProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProvider implements ITextProvider {

    private static Pattern regexPattern = Pattern.compile("\\$\\{([a-z]+%?[a-z|_]+)\\}");

    private Map<String, String> texts = new HashMap<>();

    public void addEntries(String prefix, Map<String, String> entries) {
        if (prefix == null)
            texts.putAll(entries);
        else
            for (Map.Entry<String, String> entry : entries.entrySet())
                texts.put(prefix + "%" + entry.getKey(), entry.getValue());
    }

    @Override
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

    @Override
    public boolean hasKey(String key) {
        return texts.containsKey(key);
    }
}
