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
        ModList.get().getMods().forEach(info -> {
            Map<String, Object> props = info.getModProperties();

            if(props.containsKey("margMaterialFiles") && props.get("margMaterialFiles") instanceof List){
                List files = (List) props.get("margMaterialFiles");

                for(int i = 0; i < files.size(); i++){
                    String file = files.get(i).toString();

                    try(JsonReader reader = new JsonReader(new InputStreamReader(Marg.class.getClassLoader().getResourceAsStream(file)))){
                        MaterialData[] materials = MargGson.NEW_GSON.fromJson(reader, MaterialData[].class);
                        for(MaterialData material : materials){
                            if(ModList.get().isLoaded(material.getRequiredMod()))
                                MargAPI.getMaterials().registerMaterial(new MargMaterial(material));
                        }
                    }catch(IOException e){

                    }
                }
            }
        });
    }


}
