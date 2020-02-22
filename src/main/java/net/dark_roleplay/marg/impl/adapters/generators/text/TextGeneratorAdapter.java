package net.dark_roleplay.marg.impl.adapters.generators.text;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.dark_roleplay.marg.impl.builders.generators.text.TextGeneratorBuilder;
import net.dark_roleplay.marg.impl.generators.text.TextGenerator;
import net.dark_roleplay.marg.impl.generators.text.TextTask;
import net.dark_roleplay.marg.api.materials.BaseMaterialCondition;
import net.dark_roleplay.marg.util.gson.GsonWrapper;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TextGeneratorAdapter extends TypeAdapter<TextGenerator> {

    private GsonWrapper wrapper;

    public TextGeneratorAdapter(GsonWrapper wrapper){
        this.wrapper = wrapper;
    }

    @Override
    public void write(JsonWriter out, TextGenerator value) throws IOException {
        out.beginObject();

        out.name("version").value(value.getVersion());

        out.name("material");
        wrapper.getGson().toJson(value.getMaterialRequirements(), BaseMaterialCondition.class, out);

        out.name("custom_keys").beginArray();
        for(String key : value.getCustomKeys())
            out.value(key);
        out.endArray();

        out.name("tasks").beginArray();
        for(TextTask task : value.getTasks())
            wrapper.getGson().toJson(task, TextTask.class, out);
        out.endArray();

        out.endObject();
    }

    @Override
    public TextGenerator read(JsonReader in) throws IOException {
        TextGeneratorBuilder builder = new TextGeneratorBuilder();

        in.beginObject();
        while(in.hasNext()) {
            switch (in.nextName()) {
                case "version":
                    builder.setVersion(in.nextInt());
                    break;
                case "material":
                    builder.setMaterialRequirements(wrapper.getGson().fromJson(in, BaseMaterialCondition.class));
                    break;
                case "custom_keys":
                    Set<String> keys = new HashSet<>();
                    in.beginArray();
                    while(in.hasNext()){
                        keys.add(in.nextString());
                    }
                    in.endArray();
                    builder.setCustomKeys(keys);
                    break;
                case "tasks":
                    Set<TextTask> tasks = new HashSet<>();
                    in.beginArray();
                    while(in.hasNext()){
                        tasks.add(wrapper.getGson().fromJson(in, TextTask.class));
                    }
                    in.endArray();
                    builder.setTasks(tasks);
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
