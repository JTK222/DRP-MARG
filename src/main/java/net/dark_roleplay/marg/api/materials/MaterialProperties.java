package net.dark_roleplay.marg.api.materials;

import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;

public class MaterialProperties {

    private MaterialColor materialColor;
    private String materialColorName;
    private float resistance = 0F;
    private float hardness = 0F;
    private float slipperiness = 0.6F;
    private int lightValue  = 0;
    private int harvestLevel = -1;
    private ToolType harvestTool = null;

    public MaterialProperties(String materialColor, float resistance, float hardness, float slipperiness, int lightValue, int harvestLevel, String toolType){

    }

    public MaterialColor getMaterialColor() {
        return materialColor;
    }

    public String getMaterialColorName() {
        return materialColorName;
    }

    public float getResistance() {
        return resistance;
    }

    public float getHardness() {
        return hardness;
    }

    public float getSlipperiness() {
        return slipperiness;
    }

    public int getLightValue() {
        return lightValue;
    }

    public int getHarvestLevel() {
        return harvestLevel;
    }

    public ToolType getHarvestTool() {
        return harvestTool;
    }

//    public void setMaterialColor(String materialColor) {
//        this.materialColor = MaterialColorHelper.getColor(materialColor);
//        this.materialColorName = materialColor;
//    }

}
