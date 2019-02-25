package net.dark_roleplay.core_modules.maarg.objects.other.loaders;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.dark_roleplay.core_modules.maarg.api.arg.IMaterialLoader;
import net.dark_roleplay.core_modules.maarg.api.materials.Material;
import net.dark_roleplay.core_modules.maarg.handler.LogHelper;
import net.dark_roleplay.core_modules.maarg.handler.MaterialRegistry;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;

public class GeneralMaterialLoader implements IMaterialLoader{

	//TODO add support for tintet materials

	@Override
	public Material loadMaterial(JsonObject obj, ResourceLocation loc) {
		String type = JsonUtils.getString(obj, "type", "none");
		String name = "";
		JsonObject itemsObj = null;
		JsonObject texturesObj = null;

		if(obj.has("name")) {
			name = JsonUtils.getString(obj, "name");

			if(obj.has("required_mod")) {
				if(!Loader.isModLoaded(JsonUtils.getString(obj, "required_mod"))) {
					LogHelper.info("Required mod for Wood Material - " + JsonUtils.getString(obj, "name") + " is missing, Material won't be registered");
					return null;
				}
			}
		}else {
			LogHelper.error("Couldn't find a name for the Material");
			return null;
		}

		itemsObj = JsonUtils.getJsonObject(obj, "items", new JsonObject());
		texturesObj = JsonUtils.getJsonObject(obj, "textures", new JsonObject());

		String langKey = JsonUtils.getString(obj, "lang_key", "none");

		Map<String, ResourceLocation> textures = new HashMap<String, ResourceLocation>();
		for(Entry<String, JsonElement> entry : texturesObj.entrySet()){
			textures.put(entry.getKey(), new ResourceLocation(entry.getValue().getAsString()));
		}

		Material mat = new Material(type, "%" + type + "%", name, langKey, textures, itemsObj);

		MaterialRegistry.register(mat);

		return mat;
	}

}
