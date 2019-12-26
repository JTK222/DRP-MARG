package net.dark_roleplay.marg.api;

import net.dark_roleplay.marg.api.materials.IMaterialType;

import java.util.HashMap;
import java.util.Map;

public final class MaterialTypes {

    private static Map<String, IMaterialType> materialTypes = new HashMap<>();

    private MaterialTypes(){}

    public static IMaterialType getType(String name){
        return materialTypes.get(name);
    }

    public static IMaterialType registerType(String name, IMaterialType type){
        IMaterialType contained = materialTypes.get(name);
        if(contained != null)
            return contained;
        materialTypes.put(name, type);
        return type;
    }
}
