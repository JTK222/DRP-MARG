package net.dark_roleplay.marg.impl.generators.textures;

import net.dark_roleplay.marg.api.MaterialTypes;
import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.marg.api.materials.IMaterialCondition;
import net.dark_roleplay.marg.impl.generators.textures.util.TextureCache;
import net.dark_roleplay.marg.impl.generators.IGenerator;
import net.dark_roleplay.marg.util.ILoggable;
import net.dark_roleplay.marg.util.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Arrays;
import java.util.Set;

public class TextureGenerator implements IGenerator<TextureGenerator>, ILoggable {

    public static final TextureCache globalCache = new TextureCache();

    private int version = 0;
    private IMaterialCondition materialRequirements;

    private  TextureCache localCache;

    private ResourceLocation[] requiredTextureLocs;
    private BufferedImage[] requiredTextures;
    private TextureTask[]  tasks;
    private ResourceLocation generatorLocation;

    private boolean wasSuccessfull = true;

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
        return this;
    }

    @Override
    public void generate() {
        if(!this.wasSuccessfull) return;
        Set<IMaterial> materials = materialRequirements.getMaterials();
        for(TextureTask task : this.tasks)task.generate(this.requiredTextures, this.localCache, globalCache, materials);
        this.localCache.clear();
    }

    @Override
    public void LogToStream(Writer writer, String prefix) throws IOException {

    }
}
