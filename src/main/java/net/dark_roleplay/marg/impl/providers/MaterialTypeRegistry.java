package net.dark_roleplay.marg.impl.providers;

import net.dark_roleplay.marg.api.materials.IMaterialType;
import net.dark_roleplay.marg.api.provider.IMaterialTypeProvider;

import java.util.HashMap;
import java.util.Map;

public final class MaterialTypeRegistry implements IMaterialTypeProvider {

    private Map<String, IMaterialType> materialTypes = new HashMap<>();

    @Override
    public IMaterialType getType(String name){
        return materialTypes.get(name);
    }

    @Override
    public IMaterialType registerType(IMaterialType materialType) {
        IMaterialType contained = materialTypes.get(materialType);
        if(contained != null)
            return contained;
        else materialTypes.put(materialType.getTypeName(), materialType);
        return materialType;
    }
}
