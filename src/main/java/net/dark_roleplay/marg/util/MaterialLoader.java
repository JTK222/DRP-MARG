package net.dark_roleplay.marg.util;

import com.google.gson.stream.JsonReader;
import net.dark_roleplay.marg.api.MargAPI;
import net.dark_roleplay.marg.Marg;
import net.dark_roleplay.marg.data.material.MaterialData;
import net.dark_roleplay.marg.impl.materials.MargMaterial;
import net.minecraftforge.fml.ModList;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class MaterialLoader {

	public static void loadMaterialFiles() {
		//ModList.get().getModFiles()
		ModList.get().getModFiles().forEach(info -> {
			Map<String, Object> props = info.getFileProperties();

			//if(props.containsKey("margMaterialFiles") && props.get("margMaterialFiles") instanceof List){
			//List files = (List) props.get("margMaterialFiles");

			String files[] = new String[]{"data/marg/marg_materials/oak.json",
					"data/marg/marg_materials/spruce.json",
					"data/marg/marg_materials/birch.json",
					"data/marg/marg_materials/jungle.json",
					"data/marg/marg_materials/dark_oak.json",
					"data/marg/marg_materials/acacia.json",
					"data/marg/marg_materials/crimson.json",
					"data/marg/marg_materials/warped.json"};
			for (int i = 0; i < files.length; i++) {
				String file = files[i];

				try (JsonReader reader = new JsonReader(new InputStreamReader(Marg.class.getClassLoader().getResourceAsStream(file)))) {
					MaterialData[] materials = MargGson.NEW_GSON.fromJson(reader, MaterialData[].class);
					for (MaterialData material : materials) {
						if (ModList.get().isLoaded(material.getRequiredMod()))
							MargAPI.getMaterials().registerMaterial(new MargMaterial(material));
					}
				} catch (IOException e) {

				}
			}
			//}
		});
	}


}
