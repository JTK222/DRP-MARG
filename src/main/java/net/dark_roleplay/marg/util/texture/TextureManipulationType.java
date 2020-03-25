package net.dark_roleplay.marg.util.texture;

import com.google.gson.annotations.SerializedName;

public enum TextureManipulationType{
    @SerializedName("none")
    NONE,
    @SerializedName("mask")
    MASK,
    @SerializedName("overlay")
    OVERLAY,
    @SerializedName("flip")
    FLIP,
    @SerializedName("rotate")
    ROTATE,
    @SerializedName("tint")
    TINT;
}