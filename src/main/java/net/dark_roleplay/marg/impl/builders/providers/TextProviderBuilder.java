package net.dark_roleplay.marg.impl.builders.providers;

import net.dark_roleplay.marg.impl.providers.MargTextProvider;

import java.util.HashMap;
import java.util.Map;

public class TextProviderBuilder {
    private Map<String, String> replacements = new HashMap<>();

    public TextProviderBuilder(){
        replacements.put("type", "missing");
        replacements.put("material", "missing");
    }

    public TextProviderBuilder setType(String value){
        replacements.put("type", value);
        return this;
    }

    public TextProviderBuilder setMaterialName(String value){
        replacements.put("material", value);
        return this;
    }

    public TextProviderBuilder addTexture(String key, String value){
        replacements.put("texture%" + key, value);
        return this;
    }

    public TextProviderBuilder addItem(String key, String value){
        replacements.put("item%" + key, value);
        return this;
    }

    public TextProviderBuilder addBlock(String key, String value){
        replacements.put("block%" + key, value);
        return this;
    }

    public MargTextProvider create(){
        MargTextProvider provider = new MargTextProvider(replacements);
        replacements = new HashMap<>();
        replacements.put("material", "missing");
        return provider;
    }

}
