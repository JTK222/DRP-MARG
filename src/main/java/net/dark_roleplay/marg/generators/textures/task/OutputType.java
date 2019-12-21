package net.dark_roleplay.marg.generators.textures.task;

public enum OutputType {
    FILE,
    CACHE,
    GLOBAL_CACHE;

    public static OutputType getByName(String value) {
        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
        }
        return FILE;
    }
}
