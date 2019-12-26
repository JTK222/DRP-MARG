package net.dark_roleplay.marg.generators.text;

import net.dark_roleplay.marg.Marg;
import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.marg.impl.materials.MargMaterial;
import net.dark_roleplay.marg.util.FileHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResource;
import net.minecraft.util.ResourceLocation;

import java.io.*;
import java.util.Set;

public class TextTask {

    private final ResourceLocation input;
    private final String output;
    private final String generatorLoc;
    private boolean hasErrored = false;

    private String inputString = "";

    public TextTask(String generatorLoc, ResourceLocation input, String output) {
        this.generatorLoc = generatorLoc;
        this.input = input;
        this.output = output;
    }

    public boolean needsToGenerate(MargMaterial mat, Set<String> customKeys) {
        return !FileHelper.doesFileExistClient(mat.getTextProvider().apply(output));
    }

    public void prepareTask() {
        IResource inputRes = null;
        try {
            inputRes = Minecraft.getInstance().getResourceManager().getResource(input);
        } catch (IOException e) {
            Marg.LOGGER.error("Text Generator '{}' has an Invalid input '{}'", generatorLoc.toString(), input.toString());
            this.hasErrored = true;
        }

        try {
            if(inputRes != null)
                inputString = FileHelper.quickLoadString(inputRes.getInputStream());
        } catch (IOException e) {
            Marg.LOGGER.error("An error occurred trying to read '{}' referenced by '{}'", input.toString(), generatorLoc.toString());
            this.hasErrored = true;
        }
    }

    public void generate(Set<IMaterial> materials, Set<String> customKeys, boolean isClient) {
        if(this.hasErrored) return;
        if(materials == null && customKeys != null){
            for(String custom : customKeys){
                writeToFile(isClient, output.replace("${custom}", custom), inputString.replaceAll("\\$\\{custom}", custom));
            }
        }else if(materials != null){
            for(IMaterial mat : materials){
                String matOut = mat.getTextProvider().apply(output);
                String matInput = mat.getTextProvider().apply(inputString);
                if(matOut.contains("${custom}")){
                    for(String custom : customKeys){
                        writeToFile(isClient, matOut.replace("${custom}", custom), matInput.replaceAll("\\$\\{custom}", custom));
                    }
                }else{
                    writeToFile(isClient, matOut, matInput);
                }
            }
        }
    }

    private void writeToFile(boolean isClient, String path, String content){
        File outputFile = null;
        outputFile = new File(isClient ? Marg.FOLDER_ASSETS : Marg.FOLDER_DATA, (isClient ? "assets/" : "data/") + path.replaceFirst(":", "/"));

        try {
            FileHelper.writeFile(outputFile, content);
        } catch (IOException e) {
            Marg.LOGGER.error("An error occurred trying to generate outputFile '{}' referenced in '{}'", outputFile.getPath(), generatorLoc);
            e.printStackTrace();
        }
    }
}
