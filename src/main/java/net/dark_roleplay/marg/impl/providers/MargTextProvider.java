package net.dark_roleplay.marg.impl.providers;

import net.dark_roleplay.marg.api.provider.ITextProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MargTextProvider implements ITextProvider {

    private static Pattern regexPattern = Pattern.compile("\\$\\{([a-z]+%?[a-z|_]+)\\}");

    private Map<String, String> replacements = new HashMap<>();

    public MargTextProvider(Map<String, String> replacements){
        this.replacements = replacements;
    }

    public String apply(String source){
        Matcher m = regexPattern.matcher(source);
        StringBuilder out = new StringBuilder();
        int lastEnd = 0;
        while(m.find()){
            String replacement = this.replacements.get(m.group(1));
            out.append(source.subSequence(lastEnd, replacement == null ? m.end(0) : m.start(0)));
            lastEnd = m.end(0);
            if(replacement != null) out.append(replacement);
        }
        out.append(source.subSequence(lastEnd, source.length()));

        return out.toString();
    }
}
