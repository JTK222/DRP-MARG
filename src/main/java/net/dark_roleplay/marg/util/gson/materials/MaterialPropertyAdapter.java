package net.dark_roleplay.marg.util.gson.materials;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.dark_roleplay.marg.api.materials.MaterialProperties;
import net.dark_roleplay.marg.api.materials.MaterialPropertiesBuilder;

import java.io.IOException;

public class MaterialPropertyAdapter extends TypeAdapter<MaterialProperties> {

    @Override
    public void write(JsonWriter out, MaterialProperties value) throws IOException {
        out.beginObject();
        out.name("materialColor").value(value.getMaterialColorName());
        out.name("resistance").value(value.getResistance());
        out.name("hardness").value(value.getHardness());
        out.name("slipperiness").value(value.getSlipperiness());
        out.name("lightValue").value(value.getLightValue());
        out.name("harvestLevel").value(value.getHarvestLevel());
        out.name("harvestTool").value(value.getHarvestTool() == null ? null : value.getHarvestTool().getName());
        out.endObject();
    }

    @Override
    public MaterialProperties read(JsonReader in) throws IOException {

        MaterialPropertiesBuilder builder = new MaterialPropertiesBuilder();

        in.beginObject();
        while(in.hasNext()){

        }
        in.endObject();

        return builder.create();
    }
}
