package net.dark_roleplay.marg.resource_generators.jsons.generator;

import com.google.gson.stream.JsonReader;
import net.dark_roleplay.marg.api.materials.Material;
import net.dark_roleplay.marg.resource_generators.IGenerator;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class JsonGenerator implements IGenerator {

    private int version = 0;
    private String type = "none";

    private final ResourceLocation generatorLocation;

    public JsonGenerator(ResourceLocation file, JsonReader reader) throws IOException {
        this.generatorLocation = file;

    }


    @Override
    public int getVersion() {
        return this.version;
    }

    @Override
    public boolean needsToGenerate(Material material) {
        return false;
    }

    @Override
    public void prepareGenerator() {

    }

    @Override
    public void generate() {

    }
}
