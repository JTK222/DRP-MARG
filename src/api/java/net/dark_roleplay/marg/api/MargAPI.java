package net.dark_roleplay.marg.api;

import net.dark_roleplay.marg.api.provider.IMaterialProvider;
import net.dark_roleplay.marg.api.provider.IMaterialTypeProvider;

public class MargAPI {

    private static IMaterialProvider MATERIALS = null;
    private static IMaterialTypeProvider MATERIALS_TYPES = null;

    public static IMaterialProvider getMaterials(){
        return MATERIALS;
    }
    public static IMaterialTypeProvider getMaterialTypes(){
        return MATERIALS_TYPES;
    }

    public static void setMaterialProvider(IMaterialProvider provider){
        if(MATERIALS == null)
        MATERIALS = provider;
    }

    public static void setMaterialTypeProvider(IMaterialTypeProvider provider){
        if(MATERIALS_TYPES == null)
            MATERIALS_TYPES = provider;
    }
}
