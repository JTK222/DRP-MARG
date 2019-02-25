package net.dark_roleplay.core_modules.maarg.api.arg;

import com.google.gson.JsonObject;

import net.dark_roleplay.core_modules.maarg.api.materials.Material;
import net.minecraft.util.ResourceLocation;

public interface IMaterialLoader {

	public Material loadMaterial(JsonObject obj, ResourceLocation loc);
	
}
