package net.dark_roleplay.marg.assets.textures.generator;

import com.google.gson.stream.JsonReader;
import net.dark_roleplay.marg.api.materials.Material;
import net.dark_roleplay.marg.assets.IGenerator;
import net.dark_roleplay.marg.assets.textures.TextureCache;
import net.dark_roleplay.marg.handler.LogHelper;
import net.dark_roleplay.marg.handler.MaterialRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.resources.IResource;
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

    private final ResourceLocation[] requiredTextureLocs;
    private BufferedImage[] requiredTextures;
    private final Task[]  tasks;
    private final ResourceLocation generatorLocation;

    public TextureGenerator(ResourceLocation file, JsonReader reader) throws IOException{
        this.generatorLocation = file;

        List<String> requiredTextures = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();

        //Load Texture IGenerator trough Reader
        reader.beginObject();

        while(reader.hasNext()){
            String name = reader.nextName();

            switch(name){
                case "version":
                    this.version = reader.nextInt();
                    break;
                case "type":
                    this.type = reader.nextString();
                    break;
                case "required_textures":
                    reader.beginArray();
                    while(reader.hasNext()) {
                        requiredTextures.add(reader.nextString());
                    }
                    reader.endArray();
                    break;
                case "tasks":
                    reader.beginArray();
                    while(reader.hasNext()) {
                        tasks.add(new Task(reader));
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
    }

    @Override
    public int getVersion() {
        return this.version;
    }

    @Override
    public boolean needsToGenerate(Material material) {
        return Arrays.stream(this.tasks).parallel().map(task -> task.needsToGenerate(material)).reduce((a, b) -> a || b).get();
    }

    @Override
    public void prepareGenerator() {
        this.requiredTextures = Arrays.stream(requiredTextureLocs).parallel().map(loc -> {
            try {
                return Minecraft.getInstance().getResourceManager().getResource(loc);
            } catch (IOException e) {
                LogHelper.error(String.format("There was an error trying to load the required texture '%s' for '%s'", loc.toString(), this.generatorLocation),e);
                e.printStackTrace();
                return null;
            }
        }).map(resource -> {
            if(resource == null) return null; //TODO Replace null with error texture
            try (InputStream input = new BufferedInputStream(resource.getInputStream())){
                return ImageIO.read(input);
            } catch (IOException e) {
                LogHelper.error(String.format("There was an error trying to load the required texture '%s' for '%s'", resource.getLocation().toString(), this.generatorLocation),e);
                e.printStackTrace();
                return null; //TODO Replace null with error texture
            }
        }).toArray(size -> new BufferedImage[size]);

        this.localCache = new TextureCache(globalCache);
    }

    @Override
    public void generate() {
        Set<Material> materials = MaterialRegistry.getMaterialsForType(this.type);
        //LogHelper.info("Running Tasks");
        for(Task task : this.tasks)task.generate(this.requiredTextures, this.localCache, globalCache, materials);
        this.localCache.clear();
    }

    public void printDebug() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("\nTexture Generator: %s\n", this.generatorLocation));
        builder.append(String.format("|- Version: %s\n", this.version));
        builder.append(String.format("|- Type: %s\n", this.type));

        builder.append(String.format("|- Required Textures: \n"));
        for(ResourceLocation loc : this.requiredTextureLocs){
            builder.append(String.format("|  |- %s \n", loc.toString()));
        }

        builder.append(String.format("|- Tasks: \n"));
        for(Task task : this.tasks){
        //    task.printDebug(builder);
        }
        LogHelper.info(builder.toString());
    }
}
