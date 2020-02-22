package net.dark_roleplay.marg.impl.adapters.materials;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.dark_roleplay.marg.api.materials.BaseMaterialCondition;
import net.dark_roleplay.marg.impl.builders.materials.MaterialConditionBuilder;
import net.dark_roleplay.marg.util.gson.GsonWrapper;

import java.io.IOException;

public class MaterialConditionAdapter extends TypeAdapter<BaseMaterialCondition> {

    private GsonWrapper wrapper;

    public MaterialConditionAdapter(GsonWrapper wrapper){
        this.wrapper = wrapper;
    }

    @Override
    public void write(JsonWriter out, BaseMaterialCondition value) throws IOException {

    }

    @Override
    public BaseMaterialCondition read(JsonReader in) throws IOException {
        MaterialConditionBuilder builder = new MaterialConditionBuilder();

        in.beginObject();
        while(in.hasNext()){
            switch(in.nextName()){
                case "type":
                    builder.setType(in.nextString());
                    break;
                case "required_textures":
                    in.beginArray();
                    while(in.hasNext()){
                        builder.addTexture(in.nextString());
                    }
                    in.endArray();
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        return builder.create();
    }
}
