package net.dark_roleplay.marg.util.gson.generators.textures;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.dark_roleplay.marg.impl.builders.generators.textures.TextureManipulationBuilder;
import net.dark_roleplay.marg.impl.generators.textures.util.Axis2D;
import net.dark_roleplay.marg.impl.generators.textures.TextureManipulation;
import net.dark_roleplay.marg.impl.generators.textures.util.TextureManipulationType;

import java.io.IOException;

public class ManipulationAdapter extends TypeAdapter<TextureManipulation>{
    @Override
    public void write(JsonWriter out, TextureManipulation value) throws IOException {
        out.beginObject();

        out.name("nope").value("nope^nope");

        out.endObject();
    }

    @Override
    public TextureManipulation read(JsonReader in) throws IOException {
        String textureName = "";
        TextureManipulationType type = TextureManipulationType.NONE;
        int textureID = 0;
        int angle = 0;
        int tint = 0xFFFFFFFF;
        Axis2D axis = Axis2D.NONE;
        boolean useCache = false;

        in.beginObject();
        while(in.hasNext()) {
            String name = in.nextName();

            switch (name) {
                case "type":
                    type = TextureManipulationType.getByName(in.nextString());
                    break;
                case "cached_texture":
                    textureName = in.nextString();
                    useCache = true;
                    break;
                case "texture":
                    textureID = in.nextInt();
                    break;
                case "angle":
                   angle = in.nextInt();
                    break;
                case "axis":
                    axis = Axis2D.getByName(in.nextString());
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();

        return new TextureManipulationBuilder().setTextureName(textureName).setType(type).setTextureID(textureID).setAngle(angle).setTint(tint).setAxis(axis).setUseCache(useCache).createTextureManipulation();
    }
}