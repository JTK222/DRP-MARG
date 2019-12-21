package net.dark_roleplay.marg.api.providers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProvider {

    private static Pattern regexPattern = Pattern.compile("\\$\\{([a-z]+%?[a-z|_]+)\\}");

    private Map<String, String> replacements = new HashMap<>();

    protected TextProvider(Map<String, String> replacements){
        this.replacements = replacements;
    }

    public String searchAndReplace(String source){
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

    public static class Builder{
        private Map<String, String> replacements = new HashMap<>();

        public Builder(){
            replacements.put("type", "missing");
        }

        public Builder setType(String value){
            replacements.put("type", value);
            return this;
        }

        public Builder setMaterialName(String value){
            replacements.put("material", value);
            return this;
        }

        public Builder addTexture(String key, String value){
            replacements.put("texture%" + key, value);
            return this;
        }

        public Builder addItem(String key, String value){
            replacements.put("item%" + key, value);
            return this;
        }

        public Builder addBlock(String key, String value){
            replacements.put("block%" + key, value);
            return this;
        }

        public TextProvider build(){
            TextProvider provider = new TextProvider(replacements);
            replacements = new HashMap<>();
            replacements.put("material", "missing");
            return provider;
        }
    }

}
