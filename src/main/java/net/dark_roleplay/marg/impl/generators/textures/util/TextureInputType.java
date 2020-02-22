package net.dark_roleplay.marg.impl.generators.textures.util;

public enum TextureInputType {
    NONE,
    SUPPLY,
    MATERIAL,
    CACHE;

    public static TextureInputType getByName(String value) {
        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
        }
        return NONE;
    }
}
