package net.dark_roleplay.marg.impl.adapters.providers;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.dark_roleplay.marg.impl.builders.providers.TextProviderBuilder;
import net.dark_roleplay.marg.impl.builders.providers.TextureProviderBuilder;
import net.dark_roleplay.marg.impl.builders.providers.TintProviderBuilder;
import net.dark_roleplay.marg.impl.providers.MargTextProvider;
import net.dark_roleplay.marg.impl.providers.MargTextureProvider;
import net.dark_roleplay.marg.impl.providers.MargTintProvider;
import net.dark_roleplay.marg.util.gson.GsonWrapper;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class TextureProviderAdapter extends TypeAdapter<MargTextureProvider> {

    private GsonWrapper wrapper;

    public TextureProviderAdapter(GsonWrapper wrapper){
        this.wrapper = wrapper;
    }

    @Override
    public void write(JsonWriter out, MargTextureProvider value) throws IOException {
        out.beginObject();

        for(String key : value.getTextures()){
            out.name(key).value(value.getTextureLocation(key).toString());
        }

        out.endObject();
    }

    @Override
    public MargTextureProvider read(JsonReader in) throws IOException {
        TextureProviderBuilder builder = new TextureProviderBuilder();

        in.beginObject();

        while(in.hasNext()) {
            String key = in.nextName();
            String loc = in.nextString();
            builder.addTexture(key, new ResourceLocation(loc));
        }

        in.endObject();

        return builder.create();
    }
}
