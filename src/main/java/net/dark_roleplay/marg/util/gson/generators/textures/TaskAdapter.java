package net.dark_roleplay.marg.util.gson.generators.textures;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import net.dark_roleplay.marg.Marg;
import net.dark_roleplay.marg.generators.textures.manipulation.Manipulation;
import net.dark_roleplay.marg.generators.textures.task.InputType;
import net.dark_roleplay.marg.generators.textures.task.OutputType;
import net.dark_roleplay.marg.generators.textures.task.Task;

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
        InputType inputType = InputType.NONE;
        OutputType outputType = OutputType.FILE;
        Set<Manipulation> manipulations = new HashSet<>();
        String inputName = "none";
        int inputID = -1;
        String outputName = "none";

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();

            switch (name) {
                case "input_type":
                    inputType = InputType.getByName(in.nextString());
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
                        manipulations.add(Marg.MARG_GSON.fromJson(in, Manipulation.class));
                    }
                    in.endArray();
                    break;
                case "output":
                    outputName = in.nextString();
                    break;
                case "output_type":
                    outputType = OutputType.getByName(in.nextString());
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        return new Task(inputType, outputType, manipulations.toArray(new Manipulation[manipulations.size()]), inputName, inputID, outputName);
    }
}
