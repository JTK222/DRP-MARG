package net.dark_roleplay.marg.api.materials;

import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;

public interface IMaterialProperties {

    MaterialColor getMaterialColor();
    String getMaterialColorName();
    float getResistance();
    float getHardness();
    float getSlipperiness();
    int getLightValue();
    int getHarvestLevel();
    ToolType getHarvestTool();
}
