package net.dark_roleplay.marg.impl.materials;

import net.dark_roleplay.marg.api.materials.IMaterialProperties;
import net.dark_roleplay.marg.util.MaterialColorHelper;
import net.dark_roleplay.marg.data.material.MaterialPropertiesData;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;

public class MargMaterialProperties implements IMaterialProperties {

    private MaterialColor mapColor;
    private ToolType harvestTool;
    private float resistance;
    private float hardness;
    private float slipperiness = 0.6F;
    private int lightValue;
    private int harvestLevel;

    public MargMaterialProperties(MaterialPropertiesData data){
        if(data.getMapColor() != null)
            this.mapColor = MaterialColorHelper.getColor(data.getMapColor());
        if(data.getHarvestTool() != null)
            this.harvestTool = ToolType.get(data.getHarvestTool());
        this.resistance = data.getResistance();
        this.hardness = data.getHardness();
        this.slipperiness = data.getSlipperiness();
        this.lightValue = data.getLightValue();
        this.harvestLevel = data.getHarvestLevel();
    }

    @Override
    public MaterialColor getMaterialColor() {
        return mapColor;
    }

    @Override
    public float getResistance() {
        return this.resistance;
    }

    @Override
    public float getHardness() {
        return this.hardness;
    }

    @Override
    public float getSlipperiness() {
        return this.slipperiness;
    }

    @Override
    public int getLightValue() {
        return this.lightValue;
    }

    @Override
    public int getHarvestLevel() {
        return this.harvestLevel;
    }

    @Override
    public ToolType getHarvestToolType() {
        return harvestTool;
    }
}
