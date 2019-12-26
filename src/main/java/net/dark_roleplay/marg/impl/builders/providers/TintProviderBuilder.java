package net.dark_roleplay.marg.impl.builders.providers;

import net.dark_roleplay.marg.impl.providers.MargTintProvider;

import java.util.HashMap;
import java.util.Map;

public class TintProviderBuilder {
    private Map<String, Integer> tints = new HashMap<>();

    public TintProviderBuilder addTint(String key, int value) {
        this.tints.put(key, value);
        return this;
    }

    public MargTintProvider create() {
        return new MargTintProvider(this.tints);
    }
}
