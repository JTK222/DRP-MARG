package net.dark_roleplay.marg.assets.textures.generator;

import com.google.gson.stream.JsonReader;
import net.dark_roleplay.marg.Marg;
import net.dark_roleplay.marg.api.materials.Material;
import net.dark_roleplay.marg.assets.textures.TextureCache;
import net.dark_roleplay.marg.handler.LogHelper;
import net.dark_roleplay.marg.helpers.FileHelpers;
import net.dark_roleplay.marg.objects.other.generators.textures.TextureGeneratorCache;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Task {
    private InputType inputType = InputType.NONE;
    private OutputType outputType = OutputType.FILE;

    private Manipulation[] manipulations = new Manipulation[0];

    private String inputName = "none";
    private int inputID = -1;
    private String outputName = "none";

    public boolean needsToGenerate(Material mat) {
        if (outputType != OutputType.FILE)
            return false;
        return !FileHelpers.doesFileExistClient(mat.getType().getNamed(outputName, mat));
    }

    public Task(JsonReader reader) throws IOException {
        Set<Manipulation> manipulations = new HashSet<Manipulation>();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();

            switch (name) {
                case "input_type":
                    this.inputType = InputType.getByName(reader.nextString());
                    break;
                case "input":
                    if (this.inputType == InputType.CACHE || this.inputType == InputType.MATERIAL) {
                        this.inputName = reader.nextString();
                    } else if (this.inputType == InputType.SUPPLY) {
                        this.inputID = reader.nextInt();
                    }
                    break;
                case "manipulations":
                    reader.beginArray();
                    while (reader.hasNext()) {
                        manipulations.add(new Manipulation(reader));
                    }
                    reader.endArray();
                    break;
                case "output":
                    this.outputName = reader.nextString();
                    break;
                case "output_type":
                    this.outputType = OutputType.getByName(reader.nextString());
                    break;
            }
        }
        reader.endObject();

        this.manipulations = manipulations.toArray(new Manipulation[manipulations.size()]);
    }

    public void generate(BufferedImage[] requiredResources, TextureCache localCache, TextureCache globalCache, Set<Material> materials) {

        //LogHelper.info("Running Task");
        StringBuilder builder = new StringBuilder();
        //this.printDebug(builder);
        //LogHelper.info(builder.toString());

        Set<TexturePair> textures = new HashSet<TexturePair>();

        //TODO Create copies of the Images when required
        switch (this.inputType) {//TODO Implement generation
            case NONE:
                for (Material material : materials)
                    textures.add(new TexturePair(material, new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB)));
                break;
            case MATERIAL:
                for (Material material : materials){
                    if(!material.hasTexture(this.inputName)) LogHelper.error(String.format("Tried to load not existing Texture '%s' for '%s:%s'", this.inputName, material.getType().getName(), material.getName()));
                    textures.add(new TexturePair(material, material.getTexture(this.inputName)));
                }
                break;
            case CACHE:
                for (Material material : materials){
                    BufferedImage tempImage = localCache.getCachedImage(material.getNamed(this.inputName));
                    if(tempImage == null) LogHelper.error(String.format("Tried to load not existing Texture '%s' for '%s:%s' from Cache", this.inputName, material.getType().getName(), material.getName()));
                    textures.add(new TexturePair(material, tempImage));

                }
                break;
            case SUPPLY:
                for (Material material : materials)
                    textures.add(new TexturePair(material, requiredResources[this.inputID]));
                break;
        }

        //LogHelper.info("Loaded Base Textures");

        textures.parallelStream().filter(pair -> pair.getImage() == null).forEach(pair -> {
            //TODO Fill up missing textures with error image
        });

        //LogHelper.info("Filled Missing Textures");

        textures.parallelStream().forEach(pair -> {
            for (Manipulation manipulation : this.manipulations) {
                manipulation.apply(requiredResources, localCache, pair);
            }
        });

        //LogHelper.info("Applied Manipulations");

        switch (this.outputType){
            case FILE:
                LogHelper.info(String.format("Writing File: %s", this.outputName));
                textures.stream().forEach(pair -> {
                    File outputFile = new File(Marg.FOLDER_ASSETS + "/assets/" + pair.getMaterial().getNamed(this.outputName).replaceFirst(":", "/") + ".png");
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
                textures.stream().forEach(pair -> localCache.addImage(pair.getMaterial().getNamed(this.outputName), pair.getImage()));
                break;
            case GLOBAL_CACHE:
                textures.stream().forEach(pair -> globalCache.addImage(pair.getMaterial().getNamed(this.outputName), pair.getImage()));
                break;
        }
    }

    //private InputType inputType = InputType.NONE;
    //private OutputType outputType = OutputType.FILE;

    //private Manipulation[] manipulations = new Manipulation[0];

    //private String inputName = "none";
    //private int inputID = 0;
    //private String outputName = "";

    public void printDebug(StringBuilder builder) {
        builder.append(String.format("|  |- Task\n"));
        builder.append(String.format("|  |  |- Input:\n"));
        builder.append(String.format("|  |  |  |- Type: %s\n", this.inputType.toString()));
        builder.append(String.format("|  |  |  |- Name: %s\n", this.inputName));
        builder.append(String.format("|  |  |  |- ID:   %d\n", this.inputID));
        builder.append(String.format("|  |  |- Output:\n"));
        builder.append(String.format("|  |  |  |- Type: %s\n", this.outputType.toString()));
        builder.append(String.format("|  |  |  |- Name: %s\n", this.outputName));
        builder.append(String.format("|  |  |- Manipulations:\n"));

        for(Manipulation manipulation : this.manipulations){
            //manipulation.printDebug(builder);
        }
    }

    private enum InputType {
        NONE,
        SUPPLY,
        MATERIAL,
        CACHE;

        public static InputType getByName(String value) {
            try {
                return valueOf(value.toUpperCase());
            } catch (IllegalArgumentException e) {
            }
            return NONE;
        }
    }

    private enum OutputType {
        FILE, CACHE, GLOBAL_CACHE;

        public static OutputType getByName(String value) {
            try {
                return valueOf(value.toUpperCase());
            } catch (IllegalArgumentException e) {
            }
            return FILE;
        }
    }
}