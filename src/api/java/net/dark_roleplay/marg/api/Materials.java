package net.dark_roleplay.marg.api;

import net.dark_roleplay.marg.api.materials.IMaterial;

import java.util.HashMap;
import java.util.Map;

public class Materials {

    private static Map<String, IMaterial> materials = new HashMap<>();

    public static IMaterial getMaterial(String name){
        return materials.get(name);
    }

    public static IMaterial register(String name, IMaterial type){
        IMaterial contained = materials.get(name);
        if(contained != null)
            return contained;
        materials.put(name, type);
        return type;
    }
}