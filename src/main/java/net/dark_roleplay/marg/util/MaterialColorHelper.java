package net.dark_roleplay.marg.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.block.material.MaterialColor;
import static net.minecraft.block.material.MaterialColor.*;

public class MaterialColorHelper {

	private static BiMap<String, MaterialColor> COLORS = HashBiMap.create(64);
	private static BiMap<MaterialColor, String> COLORS_INV;

	public static final Codec<MaterialColor> MATERIAL_COLOR = Codec.STRING.comapFlatMap(DataResult.partialGet(MaterialColorHelper::getColor, () -> "Unknown material color"), MaterialColorHelper::getColorName);

	static{
		COLORS.put("air", AIR);
		COLORS.put("grass", GRASS);
		COLORS.put("sand", SAND);
		COLORS.put("wool", WOOL);
		COLORS.put("tnt", TNT);
		COLORS.put("ice", ICE);
		COLORS.put("iron", IRON);
		COLORS.put("foliage", FOLIAGE);
		COLORS.put("snow", SNOW);
		COLORS.put("clay", CLAY);
		COLORS.put("dirt", DIRT);
		COLORS.put("stone", STONE);
		COLORS.put("water", WATER);
		COLORS.put("wood", WOOD);
		COLORS.put("quartz", QUARTZ);
		COLORS.put("adobe", ADOBE);
		COLORS.put("magenta", MAGENTA);
		COLORS.put("light_blue", LIGHT_BLUE);
		COLORS.put("yellow", YELLOW);
		COLORS.put("lime", LIME);
		COLORS.put("pink", PINK);
		COLORS.put("gray", GRAY);
		COLORS.put("light_gray", LIGHT_GRAY);
		COLORS.put("cyan", CYAN);
		COLORS.put("purple", PURPLE);
		COLORS.put("blue", BLUE);
		COLORS.put("brown", BROWN);
		COLORS.put("green", GREEN);
		COLORS.put("red", MaterialColor.RED);
		COLORS.put("black", MaterialColor.BLACK);
		COLORS.put("gold", MaterialColor.GOLD);
		COLORS.put("diamond", MaterialColor.DIAMOND);
		COLORS.put("lapis", MaterialColor.LAPIS);
		COLORS.put("emerald", MaterialColor.EMERALD);
		COLORS.put("obsidian", MaterialColor.OBSIDIAN);
		COLORS.put("netherrack", MaterialColor.NETHERRACK);
		COLORS.put("white_terracotta", MaterialColor.WHITE_TERRACOTTA);
		COLORS.put("orange_terracotta", MaterialColor.ORANGE_TERRACOTTA);
		COLORS.put("magenta_terracotta", MaterialColor.MAGENTA_TERRACOTTA);
		COLORS.put("light_blue_terracotta", MaterialColor.LIGHT_BLUE_TERRACOTTA);
		COLORS.put("yellow_terracotta", MaterialColor.YELLOW_TERRACOTTA);
		COLORS.put("lime_terracotta", MaterialColor.LIME_TERRACOTTA);
		COLORS.put("pink_terracotta", MaterialColor.PINK_TERRACOTTA);
		COLORS.put("gray_terracotta", MaterialColor.GRAY_TERRACOTTA);
		COLORS.put("light_gray_terracotta", MaterialColor.LIGHT_GRAY_TERRACOTTA);
		COLORS.put("cyan_terracotta", MaterialColor.CYAN_TERRACOTTA);
		COLORS.put("purple_terracotta", MaterialColor.PURPLE_TERRACOTTA);
		COLORS.put("blue_terracotta", MaterialColor.BLUE_TERRACOTTA);
		COLORS.put("brown_terracotta", MaterialColor.BROWN_TERRACOTTA);
		COLORS.put("green_terracotta", MaterialColor.GREEN_TERRACOTTA);
		COLORS.put("red_terracotta", MaterialColor.RED_TERRACOTTA);
		COLORS.put("black_terracotta", MaterialColor.BLACK_TERRACOTTA);
		COLORS.put("crimson_nylium", MaterialColor.CRIMSON_NYLIUM);
		COLORS.put("crimson_stem", MaterialColor.CRIMSON_STEM);
		COLORS.put("crimson_hyphae", MaterialColor.CRIMSON_HYPHAE);
		COLORS.put("warped_nylium", MaterialColor.WARPED_NYLIUM);
		COLORS.put("warped_stem", MaterialColor.WARPED_STEM);
		COLORS.put("warped_hyphae", MaterialColor.WARPED_HYPHAE);
		COLORS.put("warped_wart", MaterialColor.WARPED_WART);

		COLORS_INV = COLORS.inverse();
	}

	private static MaterialColor getColor(String name) {
		MaterialColor color = COLORS.get(name);
		return color == null ? AIR : color;
	}

	private static String getColorName(MaterialColor color) {
		String colorName = COLORS_INV.get(color);
		return colorName == null ? "air" : colorName;
	}
}
