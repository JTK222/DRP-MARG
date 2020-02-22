package net.dark_roleplay.marg.impl.generators.textures.util;

public  enum TextureManipulationType {
    NONE,
    MASK,
    OVERLAY,
    FLIP,
    ROTATE,
    TINT;

    public static TextureManipulationType getByName(String value){
        try{
            return valueOf(value.toUpperCase());
        }catch(IllegalArgumentException e){}
        return NONE;
    }
}