package net.dark_roleplay.marg.impl.adapters.generators.textures;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import net.dark_roleplay.marg.impl.builders.generators.textures.TextureManipulationBuilder;
import net.dark_roleplay.marg.impl.generators.textures.TextureManipulation;
import net.dark_roleplay.marg.impl.generators.textures.util.Axis2D;
import net.dark_roleplay.marg.impl.generators.textures.util.TextureManipulationType;
import net.dark_roleplay.marg.util.gson.GsonWrapper;

import java.io.IOException;

public class TextureManipulationAdapter extends TypeAdapter<TextureManipulation> {

    private GsonWrapper wrapper;

    public TextureManipulationAdapter(GsonWrapper wrapper){
        this.wrapper = wrapper;
    }

    @Override
    public void write(JsonWriter out, TextureManipulation value) throws IOException {

    }

    @Override
    public TextureManipulation read(JsonReader in) throws IOException {
        TextureManipulationBuilder builder = new TextureManipulationBuilder();

        in.beginObject();

        JsonToken token;
        while(in.hasNext()){
            switch(in.nextName()){
                case "type":
                    builder.setType(TextureManipulationType.getByName(in.nextString()));
                    break;
                case "texture":
                    token = in.peek();
                    if(token == JsonToken.NUMBER){
                        builder.setTextureID(in.nextInt());
                    }else if(token == JsonToken.STRING){
                        builder.setTextureName(in.nextString());
                    }
                    break;
                case "angle":
                    builder.setAngle(in.nextInt());
                    break;
                case "axis":
                    builder.setAxis(Axis2D.getByName(in.nextString()));
                    break;
                case "tint":
                    builder.setTint(in.nextInt());
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
