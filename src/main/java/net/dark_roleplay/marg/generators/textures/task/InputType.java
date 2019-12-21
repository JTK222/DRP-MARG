package net.dark_roleplay.marg.generators.textures.task;

public enum InputType {
    NONE,
    SUPPLY,
    MATERIAL,
    CACHE;

    public static InputType getByName(String value) {
        try {
            return valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
        }
        return NONE;
    }
}
