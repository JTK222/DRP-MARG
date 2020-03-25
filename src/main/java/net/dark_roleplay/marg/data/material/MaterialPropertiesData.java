package net.dark_roleplay.marg.data.material;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class MaterialPropertiesData {
    private String mapColor = "air";
    private float resistance;
    private float hardness;
    private float slipperiness = 0.6F;
    @SerializedName("lightLevel")
    private int lightValue;
    @SerializedName("harvestLevel")
    private int harvestLevel;
    @SerializedName("harvestTool")
    private String harvestTool = "";

    public String getMapColor() {
        return mapColor;
    }

    public void setMapColor(String mapColor) {
        this.mapColor = mapColor;
    }

    public float getResistance() {
        return resistance;
    }

    public void setResistance(float resistance) {
        this.resistance = resistance;
    }

    public float getHardness() {
        return hardness;
    }

    public void setHardness(float hardness) {
        this.hardness = hardness;
    }

    public float getSlipperiness() {
        return slipperiness;
    }

    public void setSlipperiness(float slipperiness) {
        this.slipperiness = slipperiness;
    }

    public int getLightValue() {
        return lightValue;
    }

    public void setLightValue(int lightValue) {
        this.lightValue = lightValue;
    }

    public int getHarvestLevel() {
        return harvestLevel;
    }

    public void setHarvestLevel(int harvestLevel) {
        this.harvestLevel = harvestLevel;
    }

    public String getHarvestTool() {
        return harvestTool;
    }

    public void setHarvestTool(String harvestTool) {
        this.harvestTool = harvestTool;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaterialPropertiesData that = (MaterialPropertiesData) o;
        return Float.compare(that.resistance, resistance) == 0 &&
                Float.compare(that.hardness, hardness) == 0 &&
                Float.compare(that.slipperiness, slipperiness) == 0 &&
                lightValue == that.lightValue &&
                harvestLevel == that.harvestLevel &&
                Objects.equals(mapColor, that.mapColor) &&
                Objects.equals(harvestTool, that.harvestTool);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mapColor, resistance, hardness, slipperiness, lightValue, harvestLevel, harvestTool);
    }

    @Override
    public String toString() {
        return "MaterialPropertiesData{" +
                "mapColor='" + mapColor + '\'' +
                ", resistance=" + resistance +
                ", hardness=" + hardness +
                ", slipperiness=" + slipperiness +
                ", lightValue=" + lightValue +
                ", harvestLevel=" + harvestLevel +
                ", harvestTool='" + harvestTool + '\'' +
                '}';
    }
}
