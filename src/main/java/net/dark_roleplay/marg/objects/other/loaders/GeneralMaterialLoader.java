package net.dark_roleplay.marg.objects.other.loaders;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.dark_roleplay.marg.api.IMaterialLoader;
import net.dark_roleplay.marg.api.materials.Material;
import net.dark_roleplay.marg.api.materials.MaterialType;
import net.dark_roleplay.marg.handler.LogHelper;
import net.dark_roleplay.marg.handler.MaterialRegistry;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;

@Deprecated
public class GeneralMaterialLoader implements IMaterialLoader {

	// TODO add support for tintet materials

	@Override
	public Material loadMaterial(JsonObject obj, ResourceLocation loc) {
		String		type		= JSONUtils.getString(obj, "type", "none");
		String		name		= "";
		JsonObject	itemsObj	= null;
		JsonObject	texturesObj	= null;

		if(obj.has("name")) {
			name = JSONUtils.getString(obj, "name");

			if(obj.has("required_mod")) {
				if( !ModList.get().isLoaded(JSONUtils.getString(obj, "required_mod"))) {
					LogHelper.info("Required mod for Wood Material - " + JSONUtils.getString(obj, "name") + " is missing, Material won't be registered");
					return null;
				}
			}
		} else {
			LogHelper.error("Couldn't find a name for the Material");
			return null;
		}

		itemsObj = JSONUtils.getJsonObject(obj, "items", new JsonObject());
		texturesObj = JSONUtils.getJsonObject(obj, "textures", new JsonObject());

		String							langKey		= JSONUtils.getString(obj, "lang_key", "none");

		Map<String, ResourceLocation>	textures	= new HashMap<String, ResourceLocation>();
		for(Entry<String, JsonElement> entry : texturesObj.entrySet()) {
			textures.put(entry.getKey(), new ResourceLocation(entry.getValue().getAsString()));
		}

		// FIXME readd textures and items, in case this will every again be
		// used.

		Material mat = new Material(new MaterialType(type), name, langKey);

		MaterialRegistry.register(mat);

		return mat;
	}

}
