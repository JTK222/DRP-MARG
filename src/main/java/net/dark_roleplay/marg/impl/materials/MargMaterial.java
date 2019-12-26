package net.dark_roleplay.marg.impl.materials;

import net.dark_roleplay.marg.api.Materials;
import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.marg.api.materials.IMaterialProperties;
import net.dark_roleplay.marg.api.materials.IMaterialType;
import net.dark_roleplay.marg.api.provider.IGraphicsProvider;
import net.dark_roleplay.marg.api.provider.ITextProvider;
import net.dark_roleplay.marg.impl.builders.providers.TextProviderBuilder;

public class MargMaterial implements IMaterial {


    private final IMaterialType type;
    private final String name;
    private final ITextProvider textProvider;
    private final IGraphicsProvider graphicsProvider;
    private final IMaterialProperties properties;

    public MargMaterial(IMaterialType type, String name, IGraphicsProvider graphicsProvider, IMaterialProperties properties){
        this.type = type;
        this.name = name;
        this.graphicsProvider = graphicsProvider;
        this.properties = properties;

        TextProviderBuilder textBuilder = new TextProviderBuilder();
        textBuilder.setType(type.getTypeName());
        textBuilder.setMaterialName(name);

        for(String key : this.graphicsProvider.getTextures()){
            textBuilder.addTexture(key, this.graphicsProvider.getTextureLocation(key).toString());
        }

        this.textProvider = textBuilder.create();

        Materials.register(this.name, this);
    }

    @Override
    public IGraphicsProvider getGraphicsProvider(){
        return this.graphicsProvider;
    }

    @Override
    public ITextProvider getTextProvider() {
        return  this.textProvider;
    }

    @Override
    public String getName(){ return this.name; }

    @Override
    public IMaterialType getType(){ return this.type; }

    @Override
    public IMaterialProperties getProperties() {
        return null;
    }
}
