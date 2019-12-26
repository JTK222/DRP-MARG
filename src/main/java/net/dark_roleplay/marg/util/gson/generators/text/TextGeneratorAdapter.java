package net.dark_roleplay.marg.util.gson.generators.text;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.dark_roleplay.marg.Marg;
import net.dark_roleplay.marg.impl.materials.MargMaterialRequirement;
import net.dark_roleplay.marg.generators.text.TextGenerator;
import net.dark_roleplay.marg.generators.text.TextTask;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TextGeneratorAdapter  extends TypeAdapter<TextGenerator> {

    @Override
    public void write(JsonWriter out, TextGenerator value) throws IOException {

    }

    @Override
    public TextGenerator read(JsonReader in) throws IOException {
        MargMaterialRequirement requirements = null;
        Set<String> customKeys = null;
        Set<TextTask> tasks = new HashSet<>();

        in.beginObject();

        while(in.hasNext()){
            switch(in.nextName()){
                case "material":
                    requirements = Marg.MARG_GSON.fromJson(in, MargMaterialRequirement.class);
                    break;
                case "custom_keys":
                    customKeys = new HashSet<>();
                    in.beginArray();
                    while(in.hasNext())
                        customKeys.add(in.nextString());
                    in.endArray();
                    break;
                case "tasks":
                    in.beginArray();
                    while(in.hasNext()){
                        String input = "", output = "";
                        in.beginObject();
                        while(in.hasNext()){
                            switch(in.nextName()){
                                case "input":
                                    input = in.nextString();
                                    break;
                                case "output":
                                    output = in.nextString();
                                    break;
                                default:
                                    in.skipValue();
                                    break;
                            }
                        }
                        in.endObject();
                        tasks.add(new TextTask(in.getPath(), new ResourceLocation(input), output));
                    }
                    in.endArray();
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        return new TextGenerator(requirements, customKeys, tasks);
    }
}
