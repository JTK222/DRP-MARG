package net.dark_roleplay.marg.api.materials;

public class MaterialPropertiesBuilder {
    private String materialColor = "air";
    private float resistance = 0F;
    private float hardness = 0F;
    private float slipperiness = 0.6F;
    private int lightValue = 0;
    private int harvestLevel = -1;
    private String toolType = null;

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

    public MaterialProperties create() {
        return new MaterialProperties(materialColor, resistance, hardness, slipperiness, lightValue, harvestLevel, toolType);
    }
}