package net.dark_roleplay.marg.util;

import net.minecraft.block.material.MaterialColor;

public class MaterialColorHelper {

    public static MaterialColor getColor(String name) {
        switch(name){
            case "air": return MaterialColor.AIR;
            case "grass": return MaterialColor.GRASS;
            case "sand": return MaterialColor.SAND;
            case "wool": return MaterialColor.WOOL;
            case "tnt": return MaterialColor.TNT;
            case "ice": return MaterialColor.ICE;
            case "iron": return MaterialColor.IRON;
            case "foliage": return MaterialColor.FOLIAGE;
            case "snow": return MaterialColor.SNOW;
            case "clay": return MaterialColor.CLAY;
            case "dirt": return MaterialColor.DIRT;
            case "stone": return MaterialColor.STONE;
            case "water": return MaterialColor.WATER;
            case "wood": return MaterialColor.WOOD;
            case "quartz": return MaterialColor.QUARTZ;
            case "adobe": return MaterialColor.ADOBE;
            case "magenta": return MaterialColor.MAGENTA;
            case "light_blue": return MaterialColor.LIGHT_BLUE;
            case "yellow": return MaterialColor.YELLOW;
            case "lime": return MaterialColor.LIME;
            case "pink": return MaterialColor.PINK;
            case "gray": return MaterialColor.GRAY;
            case "light_gray": return MaterialColor.LIGHT_GRAY;
            case "cyan": return MaterialColor.CYAN;
            case "purple": return MaterialColor.PURPLE;
            case "blue": return MaterialColor.BLUE;
            case "brown": return MaterialColor.BROWN;
            case "green": return MaterialColor.GREEN;
            case "red": return MaterialColor.RED;
            case "black": return MaterialColor.BLACK;
            case "gold": return MaterialColor.GOLD;
            case "diamond": return MaterialColor.DIAMOND;
            case "lapis": return MaterialColor.LAPIS;
            case "emerald": return MaterialColor.EMERALD;
            case "obsidian": return MaterialColor.OBSIDIAN;
            case "netherrack": return MaterialColor.NETHERRACK;
            case "white_terracotta": return MaterialColor.WHITE_TERRACOTTA;
            case "orange_terracotta": return MaterialColor.ORANGE_TERRACOTTA;
            case "magenta_terracotta": return MaterialColor.MAGENTA_TERRACOTTA;
            case "light_blue_terracotta": return MaterialColor.LIGHT_BLUE_TERRACOTTA;
            case "yellow_terracotta": return MaterialColor.YELLOW_TERRACOTTA;
            case "lime_terracotta": return MaterialColor.LIME_TERRACOTTA;
            case "pink_terracotta": return MaterialColor.PINK_TERRACOTTA;
            case "gray_terracotta": return MaterialColor.GRAY_TERRACOTTA;
            case "light_gray_terracotta": return MaterialColor.LIGHT_GRAY_TERRACOTTA;
            case "cyan_terracotta": return MaterialColor.CYAN_TERRACOTTA;
            case "purple_terracotta": return MaterialColor.PURPLE_TERRACOTTA;
            case "blue_terracotta": return MaterialColor.BLUE_TERRACOTTA;
            case "brown_terracotta": return MaterialColor.BROWN_TERRACOTTA;
            case "green_terracotta": return MaterialColor.GREEN_TERRACOTTA;
            case "red_terracotta": return MaterialColor.RED_TERRACOTTA;
            case "black_terracotta": return MaterialColor.BLACK_TERRACOTTA;

            default: return null;
        }
    }
}
