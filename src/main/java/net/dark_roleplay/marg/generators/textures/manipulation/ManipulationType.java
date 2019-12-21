package net.dark_roleplay.marg.generators.textures.manipulation;

public  enum ManipulationType{
    NONE,
    MASK,
    OVERLAY,
    FLIP,
    ROTATE,
    TINT;

    public static ManipulationType getByName(String value){
        try{
            return valueOf(value.toUpperCase());
        }catch(IllegalArgumentException e){}
        return NONE;
    }
}