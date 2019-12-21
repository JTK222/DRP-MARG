package net.dark_roleplay.marg.api.materials;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MaterialType {

    private static Map<String, MaterialType> materialTypes = new HashMap<>();

    private final Set<Material> materials = new HashSet<>();
    private final String name;

    private MaterialType(String name){
        this.name = name;
        materialTypes.put(name, this);
    }

    public static MaterialType get(String typeName){
        MaterialType type = materialTypes.get(typeName);
        if(type == null){
            type = new MaterialType(typeName);
        }
        return type;
    }

    public Set<Material> getMaterials(){
        return this.materials;
    }

    public void addMaterial(Material material){
        this.materials.add(material);
    }

    public String getName(){
        return this.name;
    }

}
