package net.dark_roleplay.marg.impl.builders.materials;

import net.dark_roleplay.marg.api.materials.BaseMaterialCondition;

import java.util.ArrayList;
import java.util.List;

public class MaterialConditionBuilder {
    private String type;
    private List<String> requiredTextures = new ArrayList<>();

    public MaterialConditionBuilder setType(String type){
        this.type = type;
        return this;
    }

    public MaterialConditionBuilder addTexture(String texture){
        this.requiredTextures.add(texture);
        return this;
    }

    public BaseMaterialCondition create(){
        return new BaseMaterialCondition(type, requiredTextures.toArray(new String[requiredTextures.size()]));
    }
}
