package net.dark_roleplay.marg.impl.generators.textures.util;

public enum TextureOutputType {
    FILE,
    CACHE,
    GLOBAL_CACHE;

    public static TextureOutputType getByName(String value) {
        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
        }
        return FILE;
    }
}
