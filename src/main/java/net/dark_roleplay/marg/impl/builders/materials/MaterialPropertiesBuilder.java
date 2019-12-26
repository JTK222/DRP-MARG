package net.dark_roleplay.marg.impl.builders.materials;

import net.dark_roleplay.marg.impl.materials.MargMaterialProperties;

public class MaterialPropertiesBuilder {
    private String materialColor = "air";
    private float resistance;
    private float hardness;
    private float slipperiness = 0.6F;
    private int lightValue;
    private int harvestLevel = -1;
    private String toolType;

    public MaterialPropertiesBuilder setMaterialColor(String materialColor) {
        this.materialColor = materialColor;
        return this;
    }

    public MaterialPropertiesBuilder setResistance(float resistance) {
        this.resistance = resistance;
        return this;
    }

    public MaterialPropertiesBuilder setHardness(float hardness) {
        this.hardness = hardness;
        return this;
    }

    public MaterialPropertiesBuilder setSlipperiness(float slipperiness) {
        this.slipperiness = slipperiness;
        return this;
    }

    public MaterialPropertiesBuilder setLightValue(int lightValue) {
        this.lightValue = lightValue;
        return this;
    }

    public MaterialPropertiesBuilder setHarvestLevel(int harvestLevel) {
        this.harvestLevel = harvestLevel;
        return this;
    }

    public MaterialPropertiesBuilder setToolType(String toolType) {
        this.toolType = toolType;
        return this;
    }

    public MargMaterialProperties create() {
        return new MargMaterialProperties(materialColor, resistance, hardness, slipperiness, lightValue, harvestLevel, toolType);
    }
}