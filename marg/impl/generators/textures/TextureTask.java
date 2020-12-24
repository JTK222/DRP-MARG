package net.dark_roleplay.marg.impl.generators.textures;

import net.dark_roleplay.marg.io.TextureDataIO;
import net.dark_roleplay.marg.client.textures.TextureData;
import net.dark_roleplay.marg.client.textures.TextureHolder;
import net.dark_roleplay.marg.util.texture.TextureCache;
import net.dark_roleplay.marg.util.texture.TexturePair;
import net.dark_roleplay.marg.client.textures.TextureInputType;
import net.dark_roleplay.marg.client.textures.TextureOutputType;
import net.dark_roleplay.marg.util.FileHelper;
import net.dark_roleplay.marg.data.texture.TextureTaskData;
import net.dark_roleplay.marg.util.FileUtil;
import net.minecraftforge.common.util.LazyOptional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TextureTask {

    private final TextureInputType inputType;
    private final TextureOutputType outputType;

    private final String inputName;
    private final int inputID;
    private final String outputName;

    private final TextureManipulation[] manipulations;

    public TextureTask(TextureTaskData data){
        this.inputType = data.getInputType();
        this.outputType = data.getOutputType();
        this.inputName = data.getInputName();
        this.inputID = data.getInputID();
        this.outputName = data.getOutputName();
        this.manipulations = new TextureManipulation[data.getManipulations().length];
        for(int i = 0; i < this.manipulations.length; i++){
            this.manipulations[i] = new TextureManipulation(data.getManipulations()[i]);
        }
    }

    public boolean needsToGenerate(IMaterial mat) {
        if (outputType != TextureOutputType.FILE)
            return false;
        return !FileHelper.doesFileExistClient(mat.getTextProvider().apply(outputName));
    }

    public void generate(TextureHolder[] requiredResources, TextureCache localCache, TextureCache globalCache, Set<IMaterial> materials) {
        StringBuilder builder = new StringBuilder();

        Set<TexturePair> textures = new HashSet<>();

        //TODO Create copies of the Images when required
        switch (this.inputType) {//TODO Implement generation
            case NONE:
                for (IMaterial material : materials)
                    textures.add(new TexturePair(material, new TextureHolder(TextureDataIO.loadFromBufferedImage(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB)))));
                break;
            case MATERIAL:
                for (IMaterial material : materials) {
                    LazyOptional<TextureHolder> texture = material.getGraphicsProvider().getTexture(this.inputName);
                    if (texture == null || !texture.isPresent())
                        ;//LogHelper.error(String.format("Tried to load not existing Texture '%s' for '%s:%s'", this.inputName, material.getMaterialTypeName().getName(), material.getName()));
                    textures.add(new TexturePair(material, texture.orElseThrow(null)));
                }
                break;
            case CACHE:
                for (IMaterial material : materials) {
                    TextureHolder tempImage = localCache.getCachedImage(material.getTextProvider().apply(this.inputName));
                    textures.add(new TexturePair(material, tempImage));

                }
                break;
            case SUPPLY:
                for (IMaterial material : materials)
                    textures.add(new TexturePair(material, requiredResources[this.inputID]));
                break;
        }

        textures.parallelStream().filter(pair -> pair.getImage() == null).forEach(pair -> {
            //TODO Fill up missing textures with error image
        });

        textures.parallelStream().forEach(pair -> {
            try {
                for (TextureManipulation manipulation : this.manipulations) {
                    manipulation.apply(requiredResources, localCache, pair);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        });

        switch (this.outputType) {
            case FILE:
                textures.stream().forEach(pair -> {
                    try {
                        File outputFile = new File(FileUtil.RESOURCE_PACK_FOLDER + "/assets/" + pair.getMaterial().getTextProvider().apply(this.outputName).replaceFirst(":", "/") + ".png");
                        outputFile.getParentFile().mkdirs();
                        ImageIO.write(saveTextureData(pair.getImage().getTextureData()), "png", outputFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
        });
                break;
            case CACHE:
                //LogHelper.info(String.format("Adding File '%s' to localCache", this.outputName));
                textures.stream().forEach(pair -> localCache.addImage(pair.getMaterial().getTextProvider().apply(this.outputName), pair.getImage()));
                break;
            case GLOBAL_CACHE:
                textures.stream().forEach(pair -> globalCache.addImage(pair.getMaterial().getTextProvider().apply(this.outputName), pair.getImage()));
                break;
        }
    }

    private static BufferedImage saveTextureData(TextureData texture){
        BufferedImage bi = new BufferedImage(texture.getWidth(), texture.getHeight(), BufferedImage.TYPE_INT_ARGB );
        final int[] a = ( (DataBufferInt) bi.getRaster().getDataBuffer() ).getData();
        texture.copyToIntArray(a);
        return bi;
    }
}
