package net.dark_roleplay.marg.impl.providers;

import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.marg.api.provider.IMaterialProvider;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MaterialRegistry implements IMaterialProvider {

    private Map<String, IMaterial> materials = new HashMap<>();

    @Override
    public IMaterial getMaterial(String name) {
        return materials.get(name);
    }

    public IMaterial registerMaterial(IMaterial material) {
        IMaterial contained = materials.get(material.getName());
        if (contained != null)
            return contained;
        materials.put(material.getName(), material);

        material.getMaterialType().addMaterial(material);
        return material;
    }

    public Collection<IMaterial> getRegisteredMaterials() {
        return materials.values();
    }
}
