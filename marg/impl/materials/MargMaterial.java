package net.dark_roleplay.marg.impl.materials;

import net.dark_roleplay.marg.api.MargAPI;
import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.marg.api.materials.IMaterialProperties;
import net.dark_roleplay.marg.api.materials.IMaterialType;
import net.dark_roleplay.marg.api.provider.IGraphicsProvider;
import net.dark_roleplay.marg.api.provider.ITextProvider;
import net.dark_roleplay.marg.data.material.MaterialData;
import net.dark_roleplay.marg.impl.providers.TextProvider;
import net.dark_roleplay.marg.impl.providers.TextureProvider;

import java.util.HashMap;

public class MargMaterial implements IMaterial {

    private String materialTypeName;
    private String name;

    private MargMaterialProperties properties;
    private IMaterialType materialType;
    private ITextProvider textProvider;
    private IGraphicsProvider graphicsProvider;

    public MargMaterial(MaterialData data){
        this.materialTypeName = data.getMaterialType();
        this.name = data.getName();
        this.properties = new MargMaterialProperties(data.getProperties());
        this.materialType = MargAPI.getMaterialTypes().getType(data.getMaterialType());
        if(this.materialType == null)
            this.materialType = MargAPI.getMaterialTypes().registerType(new MargMaterialType(data.getMaterialType()));
        this.graphicsProvider = new TextureProvider(data.getTextures());
        TextProvider textProv = new TextProvider();
        textProv.addEntries(null, new HashMap<String, String>(){{put("material", MargMaterial.this.name);}});
        if(data.getTextures() != null)
            textProv.addEntries("texture", data.getTextures());
        if(data.getItems() != null)
            textProv.addEntries("item", data.getItems());
        if(data.getBlocks() != null)
            textProv.addEntries("block", data.getBlocks());
        this.textProvider = textProv;
    }

    @Override
    public String getMaterialTypeName() {
        return materialTypeName;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public IMaterialProperties getProperties() {
        return this.properties;
    }

    @Override
    public IMaterialType getMaterialType() {
        return this.materialType;
    }

    @Override
    public void setMaterialType(IMaterialType materialType) {
        this.materialType = materialType;
    }

    @Override
    public IGraphicsProvider getGraphicsProvider() {
        return this.graphicsProvider;
    }

    @Override
    public ITextProvider getTextProvider() {
        return this.textProvider;
    }
}
