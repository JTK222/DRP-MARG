package net.dark_roleplay.marg.generators.textures.task;

import net.dark_roleplay.marg.api.materials.Material;
import net.dark_roleplay.marg.Marg;
import net.dark_roleplay.marg.util.FileHelper;
import net.dark_roleplay.marg.generators.textures.TextureCache;
import net.dark_roleplay.marg.generators.textures.manipulation.Manipulation;
import net.dark_roleplay.marg.util.LogHelper;
import net.minecraftforge.common.util.LazyOptional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Task {

    private final InputType inputType;
    private final OutputType outputType;

    private final Manipulation[] manipulations;

    private final String inputName;
    private final int inputID;
    private final String outputName;

    public Task(InputType inputType, OutputType outputType, Manipulation[] manipulations, String inputName, int inputID, String outputName){
        this.inputType = inputType;
        this.outputType = outputType;
        this.manipulations = manipulations;
        this.inputName = inputName;
        this.inputID = inputID;
        this.outputName = outputName;
    }

    public boolean needsToGenerate(Material mat) {
        if (outputType != OutputType.FILE)
            return false;
        return !FileHelper.doesFileExistClient(mat.getTextProv().searchAndReplace(outputName));
    }

    public void generate(BufferedImage[] requiredResources, TextureCache localCache, TextureCache globalCache, Set<Material> materials) {
        StringBuilder builder = new StringBuilder();

        Set<TexturePair> textures = new HashSet<TexturePair>();

        //TODO Create copies of the Images when required
        switch (this.inputType) {//TODO Implement generation
            case NONE:
                for (Material material : materials)
                    textures.add(new TexturePair(material, new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB)));
                break;
            case MATERIAL:
                for (Material material : materials){
                    LazyOptional<BufferedImage> texture = material.getTextureProvider().getTexture(this.inputName);
                    if(texture == null || !texture.isPresent()) ;//LogHelper.error(String.format("Tried to load not existing Texture '%s' for '%s:%s'", this.inputName, material.getType().getName(), material.getName()));
                    textures.add(new TexturePair(material, texture.orElseThrow(null)));
                }
                break;
            case CACHE:
                for (Material material : materials){
                    BufferedImage tempImage = localCache.getCachedImage(material.getTextProv().searchAndReplace(this.inputName));
                    if(tempImage == null) LogHelper.error(material.getTextProv().searchAndReplace(String.format("Tried to load not existing Texture '%s' for '${type}:${material}' from Cache", this.inputName)));
                    textures.add(new TexturePair(material, tempImage));

                }
                break;
            case SUPPLY:
                for (Material material : materials)
                    textures.add(new TexturePair(material, requiredResources[this.inputID]));
                break;
        }

        textures.parallelStream().filter(pair -> pair.getImage() == null).forEach(pair -> {
            //TODO Fill up missing textures with error image
        });

        textures.parallelStream().forEach(pair -> {
            for (Manipulation manipulation : this.manipulations) {
                manipulation.apply(requiredResources, localCache, pair);
            }
        });

        switch (this.outputType){
            case FILE:
                LogHelper.info(String.format("Writing File: %s", this.outputName));
                textures.stream().forEach(pair -> {
                    File outputFile = new File(Marg.FOLDER_ASSETS + "/assets/" + pair.getMaterial().getTextProv().searchAndReplace(this.outputName).replaceFirst(":", "/") + ".png");
                    try {
                        outputFile.getParentFile().mkdirs();
                        ImageIO.write(pair.getImage(), "png", outputFile);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case CACHE:
                //LogHelper.info(String.format("Adding File '%s' to localCache", this.outputName));
                textures.stream().forEach(pair -> localCache.addImage(pair.getMaterial().getTextProv().searchAndReplace(this.outputName), pair.getImage()));
                break;
            case GLOBAL_CACHE:
                textures.stream().forEach(pair -> globalCache.addImage(pair.getMaterial().getTextProv().searchAndReplace(this.outputName), pair.getImage()));
                break;
        }
    }
}