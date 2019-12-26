package net.dark_roleplay.marg.impl.builders.materials;

import net.dark_roleplay.marg.api.MaterialTypes;
import net.dark_roleplay.marg.api.materials.IMaterialProperties;
import net.dark_roleplay.marg.api.materials.IMaterialType;
import net.dark_roleplay.marg.api.provider.IGraphicsProvider;
import net.dark_roleplay.marg.impl.materials.MargMaterial;
import net.dark_roleplay.marg.impl.materials.MargMaterialType;

public class MaterialBuilder {
    private IMaterialType type;
    private String name;
    private IGraphicsProvider graphicsProvider;
    private IMaterialProperties properties;

    public MaterialBuilder setType(String type) {
        this.type = MaterialTypes.registerType(type, new MargMaterialType(type));
        return this;
    }

    public MaterialBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public MaterialBuilder setGraphicsProvider(IGraphicsProvider graphicsProvider) {
        this.graphicsProvider = graphicsProvider;
        return this;
    }

    public MaterialBuilder setProeprties(IMaterialProperties properties) {
        this.properties = properties;
        return this;
    }

    public MargMaterial create() {
        return new MargMaterial(type, name, graphicsProvider, properties);
    }
}