package net.dark_roleplay.core_modules.maarg.objects.other.generators;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.dark_roleplay.core_modules.maarg.api.materials.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class LanguageGenerator {

	protected static Map<String, String> languageFiles = new HashMap<String, String>();

	private JsonArray translations;

	private String type = "none";
	private ResourceLocation file = null;

	public LanguageGenerator(ResourceLocation file, JsonElement elm){
		this.file = file;
		if(elm.isJsonObject()){
			JsonObject obj = elm.getAsJsonObject();

			if(obj.has("type")) {
				this.type = obj.get("type").getAsString();
			}

			if(obj.has("translations")){
				this.translations = obj.get("translations").getAsJsonArray();
			}
		}
	}

	public String getType() {
		return this.type;
	}

	public static Map<String, String> getLanguageFiles(){
		return languageFiles;
	}

	public static void clearLanguageFiles() {
		languageFiles = new HashMap<String, String>();
	}

	public void generateLanguages(Set<Material> materials){
		String langName = Minecraft.getMinecraft().gameSettings.language.toLowerCase();

		for(int i = 0; i < this.translations.size(); i++){
			JsonObject obj = this.translations.get(i).getAsJsonObject();

			String outputKey = obj.get("outputKey").getAsString();
			String inputKey = obj.get("inputKey").getAsString();


			String file = languageFiles.containsKey(langName) ? languageFiles.get(langName) : "";
			for(Material mat : materials) {
				file += outputKey.replace("%wood%", mat.getName()) + "=" + I18n.format(inputKey, mat.getTranslation()) + "\n";
			}
			languageFiles.put(langName, file);
		}
	}
}
