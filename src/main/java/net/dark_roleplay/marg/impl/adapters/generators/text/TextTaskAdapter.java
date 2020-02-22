package net.dark_roleplay.marg.impl.adapters.generators.text;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.dark_roleplay.marg.impl.builders.generators.text.TextTaskBuilder;
import net.dark_roleplay.marg.impl.generators.text.TextTask;
import net.dark_roleplay.marg.util.gson.GsonWrapper;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class TextTaskAdapter extends TypeAdapter<TextTask> {

    private GsonWrapper wrapper;

    public TextTaskAdapter(GsonWrapper wrapper){
        this.wrapper = wrapper;
    }

    @Override
    public void write(JsonWriter out, TextTask value) throws IOException {
        out.beginObject();

        out.name("input").value(value.getInput().toString());
        out.name("output").value(value.getOutput());

        out.endObject();
    }

    @Override
    public TextTask read(JsonReader in) throws IOException {
        TextTaskBuilder builder = new TextTaskBuilder();

        builder.setGeneratorLoc(in.getPath());

        in.beginObject();
        while(in.hasNext()){
            switch(in.nextName()){
                case "input":
                    builder.setInput(new ResourceLocation(in.nextString()));
                    break;
                case "output":
                    builder.setOutput(in.nextString());
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
