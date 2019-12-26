package net.dark_roleplay.marg.api.materials;

import net.dark_roleplay.marg.api.providers.TextProvider;
import net.dark_roleplay.marg.api.providers.TextureProvider;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.ResourceLocation;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Material {

    private static Set<Material> materials = new HashSet<Material>();

    private final MaterialType type;
    private final String name;
    private final TextProvider textProvider;
    private final TextureProvider textureProvider;

    public Material(String typeName, String name, TextureProvider textureProvider){
        this.type = MaterialType.get(typeName);
        this.type.addMaterial(this);
        this.name = name;
        this.textureProvider = textureProvider;

        TextProvider.Builder textBuilder = new TextProvider.Builder();
        textBuilder.setType(typeName);
        textBuilder.setMaterialName(name);

        for(Map.Entry<String, ResourceLocation> entry : this.textureProvider.getTexturePaths()){
            textBuilder.addTexture(entry.getKey(), entry.getValue().toString());
        }

        this.textProvider = textBuilder.build();

        materials.add(this);
    }

    public TextureProvider getTextureProvider(){
        return this.textureProvider;
    }

    public TextProvider getTextProv(){
        return this.textProvider;
    }

    public String getName(){ return this.name; }

    public MaterialType getType(){ return this.type; }

    /*-- Registry Like Stuff --*/

    public static Set<Material> getMaterials(){
        return materials;
    }

    public static Set<Material> getMaterialsForType(String type) {
        return materials.parallelStream().filter(material -> type.equals(material.type.getName())).collect(Collectors.toSet());
    }

    public static Material getMaterialForName(String name){
        for(Material mat : materials){
            if(name.equals(mat.getName()))
                return mat;
        }
        return null;
    }
}
