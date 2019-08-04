package net.dark_roleplay.marg.api;

import com.google.gson.JsonObject;

import net.dark_roleplay.marg.api.materials.Material;
import net.dark_roleplay.marg.handler.MaterialRegistry;
import net.minecraft.util.ResourceLocation;

/**
 * Register Material trough {@link MaterialRegistry}
 * This might be used again in the future.
 */
@Deprecated
public interface IMaterialLoader {

	public Material loadMaterial(JsonObject obj, ResourceLocation loc);

}
