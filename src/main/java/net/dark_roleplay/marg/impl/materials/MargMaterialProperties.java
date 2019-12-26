package net.dark_roleplay.marg.impl.materials;

import net.dark_roleplay.marg.api.materials.IMaterialProperties;
import net.dark_roleplay.marg.util.MaterialColorHelper;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;

public class MargMaterialProperties implements IMaterialProperties {

    private final MaterialColor materialColor;
    private final String materialColorName;
    private final float resistance;
    private final float hardness;
    private final float slipperiness;
    private final int lightValue;
    private final int harvestLevel;
    private final ToolType harvestTool;

    public MargMaterialProperties(String materialColor, float resistance, float hardness, float slipperiness, int lightValue, int harvestLevel, String toolType){
        this.materialColor = MaterialColorHelper.getColor(materialColor);
        this.materialColorName = materialColor;
        this.resistance = resistance;
        this.hardness = hardness;
        this.slipperiness = slipperiness;
        this.lightValue = lightValue;
        this.harvestLevel = harvestLevel;
        this.harvestTool = ToolType.get(toolType);
    }

    @Override
    public MaterialColor getMaterialColor() {
        return materialColor;
    }

    @Override
    public String getMaterialColorName() {
        return materialColorName;
    }

    @Override
    public float getResistance() {
        return resistance;
    }

    @Override
    public float getHardness() {
        return hardness;
    }

    @Override
    public float getSlipperiness() {
        return slipperiness;
    }

    @Override
    public int getLightValue() {
        return lightValue;
    }

    @Override
    public int getHarvestLevel() {
        return harvestLevel;
    }

    @Override
    public ToolType getHarvestTool() {
        return harvestTool;
    }
}
