package net.dark_roleplay.marg.util.gson.generators.textures;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.dark_roleplay.marg.generators.textures.generator.TextureGenerator;

import java.io.IOException;

public class TextureGeneratorAdapter extends TypeAdapter<TextureGenerator>{
    @Override
    public void write(JsonWriter out, TextureGenerator value) throws IOException {

    }

    @Override
    public TextureGenerator read(JsonReader in) throws IOException {
        return null;
    }
}
