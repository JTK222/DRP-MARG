package net.dark_roleplay.marg.generators.textures.generator;

import com.google.gson.stream.JsonReader;
import net.dark_roleplay.marg.api.MaterialTypes;
import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.marg.impl.generators.IGenerator;
import net.dark_roleplay.marg.impl.generators.textures.util.TextureCache;
import net.dark_roleplay.marg.Marg;
import net.dark_roleplay.marg.util.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class TextureGenerator implements IGenerator {

    public static final TextureCache globalCache = new TextureCache();
    public TextureCache localCache;


    private int version = 0;
    private String type = "none";


    public TextureGenerator(ResourceLocation file, JsonReader reader){
        try {
            this.generatorLocation = file;

            List<String> requiredTextures = new ArrayList<>();
            List<Task> tasks = new ArrayList<>();

            //Load Texture IGenerator trough Reader
            reader.beginObject();

            while (reader.hasNext()) {
                String name = reader.nextName();

                switch (name) {
                    case "version":
                        this.version = reader.nextInt();
                        break;
                    case "type":
                        this.type = reader.nextString();
                        break;
                    case "required_textures":
                        reader.beginArray();
                        while (reader.hasNext()) {
                            requiredTextures.add(reader.nextString());
                        }
                        reader.endArray();
                        break;
                    case "tasks":
                        reader.beginArray();
                        while (reader.hasNext()) {
                            tasks.add(Marg.MARG_GSON.fromJson(reader, Task.class));
                        }
                        reader.endArray();
                        break;
                    default:
                        reader.skipValue();
                }
            }
            reader.endObject();

            this.requiredTextureLocs = requiredTextures.stream().map(texture -> new ResourceLocation(texture)).toArray(size -> new ResourceLocation[size]);
            this.tasks = tasks.toArray(new Task[tasks.size()]);
        }catch(IOException e){
            this.wasSuccessfull = false;
        }
    }

    @Override
    public int getVersion() {
        return this.version;
    }

    @Override
    public boolean needsToGenerate(IMaterial material) {
        return Arrays.stream(this.tasks).parallel().map(task -> task.needsToGenerate(material)).reduce((a, b) -> a || b).get();
    }

    @Override
    public void prepareGenerator() {
    }

    @Override
    public void generate() {
    }
}
