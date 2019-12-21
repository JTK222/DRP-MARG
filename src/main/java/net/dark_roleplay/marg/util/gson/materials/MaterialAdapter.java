package net.dark_roleplay.marg.util.gson.materials;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import net.dark_roleplay.marg.api.materials.Material;
import net.dark_roleplay.marg.api.providers.TextureProvider;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class MaterialAdapter extends TypeAdapter<Material> {
    @Override
    public void write(JsonWriter out, Material value) throws IOException {}

    @Override
    public Material read(JsonReader in) throws IOException {


        JsonToken token = in.peek();

        if(token == JsonToken.BEGIN_ARRAY){
            Material lastMaterial = null;
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

    private Material readMaterial(JsonReader in) throws IOException{
        TextureProvider.Builder textures = new TextureProvider.Builder();
        String typeName = "missing";
        String name = "missing";
        in.beginObject();
        while(in.hasNext()){
            switch(in.nextName()){
                case "type":
                    typeName = in.nextString();
                    break;
                case "name":
                    name = in.nextString();
                    break;
                case "textures":
                    in.beginObject();
                    while(in.hasNext())
                        textures.addTexture(in.nextName(), new ResourceLocation(in.nextString()));
                    in.endObject();
                    break;
                case "items":
                    in.skipValue();
                    break;
                case "blocks":
                    in.skipValue();
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }

        in.endObject();
        return new Material(typeName, name, textures.build());
    }
}
