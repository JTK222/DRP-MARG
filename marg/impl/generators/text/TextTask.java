package net.dark_roleplay.marg.impl.generators.text;

import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.marg.data.text.TextTaskData;
import net.dark_roleplay.marg.impl.materials.MargMaterial;
import net.dark_roleplay.marg.util.FileHelper;
import net.dark_roleplay.marg.util.FileUtil;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.io.*;
import java.util.Set;

public class TextTask{

    private final ResourceLocation input;
    private final String output;
    private boolean hasErrored = false;

    private String inputString = "";

    public TextTask(TextTaskData data) {
        this.input = new ResourceLocation(data.getInput());
        this.output = data.getOutput();
    }

    public boolean needsToGenerate(MargMaterial mat, Set<String> customKeys) {
        return !FileHelper.doesFileExistClient(mat.getTextProvider().apply(output));
    }

    public void prepareTask(IResourceManager resourceManager) {
        IResource inputRes = null;
        try {
            inputRes = resourceManager.getResource(input);
        } catch (IOException e) {
            this.hasErrored = true;
        }

        try {
            if(inputRes != null)
                inputString = FileHelper.quickLoadString(inputRes.getInputStream());
        } catch (IOException e) {
            this.hasErrored = true;
        }
    }

    public void generate(Set<IMaterial> materials, String[] customKeys, boolean isClient) {
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
        outputFile = new File(isClient ? FileUtil.RESOURCE_PACK_FOLDER : FileUtil.DATA_PACK_FOLDER, (isClient ? "assets/" : "data/") + path.replaceFirst(":", "/"));

        try {
            FileHelper.writeFile(outputFile, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ResourceLocation getInput() {
        return input;
    }

    public String getOutput() {
        return output;
    }
}
