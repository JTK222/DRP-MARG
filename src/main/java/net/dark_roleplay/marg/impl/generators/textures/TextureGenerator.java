package net.dark_roleplay.marg.impl.generators.textures;

import net.dark_roleplay.marg.api.textures.helper.TextureData;
import net.dark_roleplay.marg.io.TextureDataIO;
import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.marg.api.materials.IMaterialCondition;
import net.dark_roleplay.marg.api.textures.helper.TextureHolder;
import net.dark_roleplay.marg.util.texture.TextureCache;
import net.dark_roleplay.marg.impl.generators.IGenerator;
import net.dark_roleplay.marg.data.texture.TextureGeneratorData;
import net.dark_roleplay.marg.impl.materials.MargMaterialCondition;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Set;

public class TextureGenerator implements IGenerator<TextureGenerator> {

    public static final TextureCache globalCache = new TextureCache();

    private int version = 0;
    private IMaterialCondition materialRequirements;

    private  TextureCache localCache;

    private ResourceLocation[] requiredTextureLocs;
    private TextureHolder[] requiredTextures;
    private TextureTask[]  tasks;

    private boolean wasSuccessfull = true;

    public TextureGenerator(TextureGeneratorData data){
        this.materialRequirements = new MargMaterialCondition(data.getMaterial());
        this.version = data.getGeneratorVersion();
        this.requiredTextureLocs = new ResourceLocation[data.getRequiredTextures().length];
        for(int i = 0; i < this.requiredTextureLocs.length; i++){
            this.requiredTextureLocs[i] = new ResourceLocation(data.getRequiredTextures()[i]);
        }
        this.tasks = new TextureTask[data.getTasks().length];
        for(int i = 0; i < this.tasks.length; i++){
            this.tasks[i] = new TextureTask(data.getTasks()[i]);
        }
    }

    @Override
    public int getVersion() { return this.version; }

    @Override
    public boolean needsToGenerate(IMaterial material) { return true; }

    @Override
    public TextureGenerator prepareGenerator() {
        if(!this.wasSuccessfull) return this;
        this.requiredTextures = Arrays.stream(requiredTextureLocs).parallel().map(loc -> {
            try {
                return Minecraft.getInstance().getResourceManager().getResource(loc);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).map(resource -> {
            if(resource == null) return null; //TODO Replace null with error texture
            TextureData data = TextureDataIO.loadDataFromResources(resource);
            if(data == null) return null;
            else return new TextureHolder(data);
        }).toArray(size -> new TextureHolder[size]);

        this.localCache = new TextureCache(globalCache);
        return this;
    }

    @Override
    public void generate() {
        if(!this.wasSuccessfull) return;
        Set<IMaterial> materials = materialRequirements.getMaterials();
        for(TextureTask task : this.tasks)task.generate(this.requiredTextures, this.localCache, globalCache, materials);
        this.localCache.clear();
    }
}
