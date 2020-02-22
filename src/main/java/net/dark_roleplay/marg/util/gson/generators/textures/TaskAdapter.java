package net.dark_roleplay.marg.util.gson.generators.textures;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import net.dark_roleplay.marg.Marg;
import net.dark_roleplay.marg.impl.generators.textures.TextureManipulation;
import net.dark_roleplay.marg.impl.generators.textures.util.TextureInputType;
import net.dark_roleplay.marg.impl.generators.textures.util.TextureOutputType;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TaskAdapter extends TypeAdapter<Task>{

    @Override
    public void write(JsonWriter out, Task value) throws IOException {
        out.beginObject();

        out.name("nope").value("nope^nope");

        out.endObject();
    }

    @Override
    public Task read(JsonReader in) throws IOException {
        TextureInputType inputType = TextureInputType.NONE;
        TextureOutputType outputType = TextureOutputType.FILE;
        Set<TextureManipulation> manipulations = new HashSet<>();
        String inputName = "none";
        int inputID = -1;
        String outputName = "none";

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();

            switch (name) {
                case "input_type":
                    inputType = TextureInputType.getByName(in.nextString());
                    break;
                case "input":
                    JsonToken type = in.peek();
                    if(type.equals(JsonToken.STRING))
                        inputName = in.nextString();
                    else
                        inputID = in.nextInt();
                    break;
                case "manipulations":
                    in.beginArray();
                    while (in.hasNext()) {
                        manipulations.add(Marg.MARG_GSON.fromJson(in, TextureManipulation.class));
                    }
                    in.endArray();
                    break;
                case "output":
                    outputName = in.nextString();
                    break;
                case "output_type":
                    outputType = TextureOutputType.getByName(in.nextString());
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        return new Task(inputType, outputType, manipulations.toArray(new TextureManipulation[manipulations.size()]), inputName, inputID, outputName);
    }
}
