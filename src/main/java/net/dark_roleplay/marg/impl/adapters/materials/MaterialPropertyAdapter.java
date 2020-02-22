package net.dark_roleplay.marg.impl.adapters.materials;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.dark_roleplay.marg.impl.builders.materials.MaterialPropertiesBuilder;
import net.dark_roleplay.marg.impl.materials.MargMaterialProperties;
import net.dark_roleplay.marg.util.gson.GsonWrapper;

import java.io.IOException;

public class MaterialPropertyAdapter extends TypeAdapter<MargMaterialProperties> {

    private GsonWrapper wrapper;

    public MaterialPropertyAdapter(GsonWrapper wrapper){
        this.wrapper = wrapper;
    }

    @Override
    public void write(JsonWriter out, MargMaterialProperties value) throws IOException {
        out.beginObject();

        out.name("materialColor").value(value.getMaterialColorName());
        out.name("resistance").value(value.getResistance());
        out.name("hardness").value(value.getHardness());
        out.name("slipperiness").value(value.getSlipperiness());
        out.name("lightValue").value(value.getLightValue());
        out.name("harvestLevel").value(value.getHarvestLevel());
        out.name("toolType").value(value.getHarvestTool().getName());

        out.endObject();
    }

    @Override
    public MargMaterialProperties read(JsonReader in) throws IOException {
        MaterialPropertiesBuilder builder = new MaterialPropertiesBuilder();

        in.beginObject();
        while(in.hasNext()){
            switch(in.nextName()){
                case "materialColor":
                    builder.setMaterialColor(in.nextString());
                    break;
                case "resistance":
                    builder.setResistance((float) in.nextDouble());
                    break;
                case "hardness":
                    builder.setHardness((float) in.nextDouble());
                    break;
                case "slipperiness":
                    builder.setSlipperiness((float) in.nextDouble());
                    break;
                case "lightValue":
                    builder.setLightValue(in.nextInt());
                    break;
                case "harvestLevel":
                    builder.setHarvestLevel(in.nextInt());
                    break;
                case "toolType":
                    builder.setToolType(in.nextString());
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
