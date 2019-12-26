package net.dark_roleplay.marg.impl.adapters.providers;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.dark_roleplay.marg.impl.builders.providers.TintProviderBuilder;
import net.dark_roleplay.marg.impl.providers.MargTintProvider;
import net.dark_roleplay.marg.util.gson.GsonWrapper;

import java.io.IOException;

public class TintProviderAdapter extends TypeAdapter<MargTintProvider> {

    private GsonWrapper wrapper;

    public TintProviderAdapter(GsonWrapper wrapper){
        this.wrapper = wrapper;
    }

    @Override
    public void write(JsonWriter out, MargTintProvider value) throws IOException {
        out.beginObject();

        for(String key : value.getTints()){
            out.name(key).value(value.getTint(key));
        }

        out.endObject();
    }

    @Override
    public MargTintProvider read(JsonReader in) throws IOException {
        TintProviderBuilder builder = new TintProviderBuilder();

        in.beginObject();

        while(in.hasNext()) {
            String key = in.nextName();
            int value = in.nextInt();
            builder.addTint(key, value);
        }

        in.endObject();

        return builder.create();
    }
}
