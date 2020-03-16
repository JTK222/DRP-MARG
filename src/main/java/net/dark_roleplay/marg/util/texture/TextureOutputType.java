package net.dark_roleplay.marg.util.texture;

import com.google.gson.annotations.SerializedName;

public enum TextureOutputType {
    @SerializedName("file")
    FILE,
    @SerializedName("cache")
    CACHE,
    @SerializedName("global_cache")
    GLOBAL_CACHE;
}
