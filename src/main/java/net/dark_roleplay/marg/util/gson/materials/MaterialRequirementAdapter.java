package net.dark_roleplay.marg.util.gson.materials;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.dark_roleplay.marg.api.materials.MaterialRequirement;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MaterialRequirementAdapter extends TypeAdapter<MaterialRequirement> {
    @Override
    public void write(JsonWriter out, MaterialRequirement value) throws IOException {

    }

    @Override
    public MaterialRequirement read(JsonReader in) throws IOException {
        String type = "missing";
        Set<String> requiredTextures = new HashSet<>();

        in.beginObject();

        while(in.hasNext()){
            switch(in.nextName()){
                case "type":
                    type = in.nextString();
                    break;
                case "required_textures":
                    in.beginArray();
                    while(in.hasNext())
                        requiredTextures.add(in.nextString());
                    in.endArray();
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        return new MaterialRequirement(type, requiredTextures.toArray(new String[requiredTextures.size()]));
    }
}
