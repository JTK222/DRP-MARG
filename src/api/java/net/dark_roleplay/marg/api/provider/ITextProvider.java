package net.dark_roleplay.marg.api.provider;

public interface ITextProvider {

    String apply(String source);
    boolean hasKey(String key);
}
