package net.dark_roleplay.marg.impl.adapters.generators.textures;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import net.dark_roleplay.marg.impl.builders.generators.textures.TextureTaskBuilder;
import net.dark_roleplay.marg.impl.generators.textures.TextureManipulation;
import net.dark_roleplay.marg.impl.generators.textures.TextureTask;
import net.dark_roleplay.marg.impl.generators.textures.util.TextureInputType;
import net.dark_roleplay.marg.impl.generators.textures.util.TextureOutputType;
import net.dark_roleplay.marg.util.gson.GsonWrapper;

import java.io.IOException;

public class TextureTaskAdapter extends TypeAdapter<TextureTask> {

    private GsonWrapper wrapper;

    public TextureTaskAdapter(GsonWrapper wrapper){
        this.wrapper = wrapper;
    }

    @Override
    public void write(JsonWriter out, TextureTask value) throws IOException {

    }

    @Override
    public TextureTask read(JsonReader in) throws IOException {
        TextureTaskBuilder builder = new TextureTaskBuilder();
        in.beginObject();

        JsonToken token;
        while(in.hasNext()){
            switch(in.nextName()){
                case "input_type":
                    builder.setInputType(TextureInputType.getByName(in.nextString()));
                    break;
                case "output_type":
                    builder.setOutputType(TextureOutputType.getByName(in.nextString()));
                    break;
                case "input":
                    token = in.peek();
                    if(token == JsonToken.NUMBER){
                        builder.setInputID(in.nextInt());
                    }else if(token == JsonToken.STRING){
                        builder.setInputName(in.nextString());
                    }
                    break;
                case "output":
                    builder.setOutputName(in.nextString());
                    break;
                case "manipulations":
                    in.beginArray();
                    while(in.hasNext()){
                        builder.addTextureManipulations(wrapper.getGson().fromJson(in, TextureManipulation.class));
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
