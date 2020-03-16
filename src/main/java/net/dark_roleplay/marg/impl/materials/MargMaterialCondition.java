package net.dark_roleplay.marg.impl.materials;

import net.dark_roleplay.marg.api.MargAPI;
import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.marg.api.materials.IMaterialCondition;
import net.dark_roleplay.marg.api.materials.IMaterialType;
import net.dark_roleplay.marg.api.provider.ITextProvider;
import net.dark_roleplay.marg.data.MaterialRequirementData;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class MargMaterialCondition implements IMaterialCondition {

    private final String type;
    private final String[] textures;
    private final String[] items;
    private final String[] blocks;
    private Set<IMaterial> gatheredMaterials;

    public MargMaterialCondition(MaterialRequirementData data){
        this.type = data.getType();
        this.textures = data.getTextures();
        this.items = data.getItems();
        this.blocks = data.getBlocks();
    }

    @Override
    public Set<IMaterial> getMaterials() {
        if(gatheredMaterials != null) return gatheredMaterials;

        Set<IMaterial> materials = new HashSet<>();
        IMaterialType type = MargAPI.getMaterialTypes().getType(this.type);
        for(IMaterial material : type.getMaterials()){
            if(doesAccept(material))
                materials.add(material);
        }
        this.gatheredMaterials = materials;
        return materials;
    }

    @Override
    public boolean doesAccept(IMaterial material) {
        if(!this.type.equals(material.getMaterialTypeName())) return false;
        ITextProvider txtProvider = material.getTextProvider();

        if(textures != null && textures.length > 0) {
            for (String texture : textures)
                if(!txtProvider.hasKey("texture%" + texture)) return false;
        }
        if(items != null && items.length > 0){
            for (String item : items)
                if(!txtProvider.hasKey("item%" + item)) return false;
        }
        if(blocks != null && blocks.length > 0){
            for (String block : blocks)
                if(!txtProvider.hasKey("block%" + block)) return false;
        }
        return true;
    }

    @Override
    public void forEach(Consumer<IMaterial> consumer) {
        if(gatheredMaterials == null) getMaterials();
        gatheredMaterials.stream().forEach(consumer);
    }
}
