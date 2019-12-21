package net.dark_roleplay.marg.generators.textures.manipulation;

public enum Axis2D {
    X,
    Y,
    NONE;

    public static Axis2D getByName(String value){
        try{
            return valueOf(value.toUpperCase());
        }catch(IllegalArgumentException e){}
        return X;
    }
}