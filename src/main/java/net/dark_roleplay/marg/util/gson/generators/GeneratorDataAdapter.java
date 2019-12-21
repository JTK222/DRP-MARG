package net.dark_roleplay.marg.util.gson.generators;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.dark_roleplay.marg.generators.base.GeneratorData;

import java.io.IOException;

public class GeneratorDataAdapter extends TypeAdapter<GeneratorData>{
    @Override
    public void write(JsonWriter out, GeneratorData value) throws IOException {

    }

    @Override
    public GeneratorData read(JsonReader in) throws IOException {
        return null;
    }
}
