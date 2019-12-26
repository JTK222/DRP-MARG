package net.dark_roleplay.marg.impl.adapters.materials;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import net.dark_roleplay.marg.api.provider.IGraphicsProvider;
import net.dark_roleplay.marg.impl.builders.materials.MaterialBuilder;
import net.dark_roleplay.marg.impl.materials.MargMaterial;
import net.dark_roleplay.marg.impl.materials.MargMaterialProperties;
import net.dark_roleplay.marg.impl.providers.MargTextureProvider;
import net.dark_roleplay.marg.impl.providers.MargTintProvider;
import net.dark_roleplay.marg.util.gson.GsonWrapper;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class MaterialAdapter extends TypeAdapter<MargMaterial> {

    private GsonWrapper wrapper;

    public MaterialAdapter(GsonWrapper wrapper){
        this.wrapper = wrapper;
    }

    @Override
    public void write(JsonWriter out, MargMaterial value) throws IOException {
        out.beginObject();

        out.name("type").value(value.getType().getTypeName());
        out.name("name").value(value.getName());

        IGraphicsProvider gfxProvider = value.getGraphicsProvider();
        if(gfxProvider instanceof MargTextureProvider){
            out.name("textures");
            wrapper.getGson().toJson(value.getGraphicsProvider(), MargTextureProvider.class, out);
        }else if(gfxProvider instanceof MargTintProvider){
            out.name("tints");
            wrapper.getGson().toJson(value.getGraphicsProvider(), MargTintProvider.class, out);
        }

        out.name("properties");
        wrapper.getGson().toJson(value.getProperties(), MargMaterialProperties.class, out);

        out.endObject();
    }

    @Override
    public MargMaterial read(JsonReader in) throws IOException {
        JsonToken token = in.peek();
        if(token == JsonToken.BEGIN_ARRAY){
            MargMaterial lastMaterial = null;
            in.beginArray();
            while(in.hasNext()){
                lastMaterial = readMaterial(in);
            }
            in.endArray();
            return lastMaterial;
        }else{
            return readMaterial(in);
        }
    }

    private MargMaterial readMaterial(JsonReader in) throws IOException{
        MaterialBuilder builder = new MaterialBuilder();

        in.beginObject();
        while(in.hasNext()){
            switch(in.nextName()){
                case "type":
                    builder.setType(in.nextString());
                    break;
                case "name":
                    builder.setName(in.nextString());
                    break;
                case "textures":
                    builder.setGraphicsProvider(this.wrapper.getGson().fromJson(in, MargTextureProvider.class));
                    break;
                case "tints":
                    builder.setGraphicsProvider(this.wrapper.getGson().fromJson(in, MargTintProvider.class));
                    break;
                case "items":
                    in.skipValue();
                    break;
                case "blocks":
                    in.skipValue();
                    break;
                case "properties":
                    builder.setProeprties(this.wrapper.getGson().fromJson(in, MargMaterialProperties.class));
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
