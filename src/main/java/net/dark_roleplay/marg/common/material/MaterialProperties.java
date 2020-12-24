package net.dark_roleplay.marg.common.material;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.dark_roleplay.marg.util.MaterialColorHelper;
import net.minecraft.block.material.MaterialColor;

import java.util.ArrayList;
import java.util.List;

public class MaterialProperties {

	public static final Codec<MaterialProperties> CODEC= RecordCodecBuilder.create(i -> i.group(
			Codec.FLOAT.optionalFieldOf("resistance", 0F).forGetter(MaterialProperties::getResistance),
			Codec.FLOAT.optionalFieldOf("hardness", 0F).forGetter(MaterialProperties::getHardness),
			Codec.INT.optionalFieldOf("harvestLevel", 0).forGetter(MaterialProperties::getHarvestLevel),
			Codec.FLOAT.optionalFieldOf("slipperiness", 0.6F).forGetter(MaterialProperties::getSlipperiness),
			Codec.INT.optionalFieldOf("lightValue", 0).forGetter(MaterialProperties::getLightValue),
			Codec.STRING.listOf().optionalFieldOf("harvestTools", new ArrayList<>()).forGetter(MaterialProperties::getHarvestTools),
			MaterialColorHelper.MATERIAL_COLOR.optionalFieldOf("mapColor", MaterialColor.AIR).forGetter(MaterialProperties::getMapColor)
	).apply(i, MaterialProperties::new));

	private float resistance;
	private float hardness;
	private int harvestLevel;
	private List<String> harvestTools;
	private MaterialColor mapColor;
	private float slipperiness = 0.6F;
	private int lightValue;

	public MaterialProperties(Float resistance, Float hardness, Integer harvestLevel, float slipperiness, int lightValue, List<String> harvestTools, MaterialColor mapColor) {
		this.resistance = resistance;
		this.hardness = hardness;
		this.harvestLevel = harvestLevel;
		this.harvestTools = harvestTools;
		this.mapColor = mapColor;
		this.slipperiness = slipperiness;
		this.lightValue = lightValue;
	}

	public float getResistance() {
		return resistance;
	}

	public float getHardness() {
		return hardness;
	}

	public int getHarvestLevel() {
		return harvestLevel;
	}

	public List<String> getHarvestTools() {
		return harvestTools;
	}

	public MaterialColor getMapColor() {
		return mapColor;
	}

	public float getSlipperiness() {
		return slipperiness;
	}

	public int getLightValue() {
		return lightValue;
	}
}
