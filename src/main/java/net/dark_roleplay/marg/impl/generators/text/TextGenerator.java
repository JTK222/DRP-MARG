package net.dark_roleplay.marg.impl.generators.text;

import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.marg.api.materials.IMaterialCondition;
import net.dark_roleplay.marg.impl.generators.IGenerator;
import net.dark_roleplay.marg.data.text.TextGeneratorData;
import net.dark_roleplay.marg.data.text.TextTaskData;
import net.dark_roleplay.marg.impl.materials.MargMaterialCondition;
import net.minecraft.resources.IResourceManager;

import java.util.HashSet;
import java.util.Set;

public class TextGenerator implements IGenerator<TextGenerator> {

    private int version = 0;
    private IMaterialCondition materialRequirements;

    private final String[] customKeys;
    private Set<TextTask> tasks;
    private boolean isClient;
    private IResourceManager resourceManager;

    //TODO Implement Side Check
    public TextGenerator(TextGeneratorData data, IResourceManager resourceManager, boolean isClient){
        this.version = data.getGeneratorVersion();
        this.isClient = isClient;
        this.customKeys = data.getCustomKeys();
        this.materialRequirements = new MargMaterialCondition(data.getMaterial());
        this.tasks = new HashSet<>();
        for(TextTaskData taskData : data.getTasks()){
            tasks.add(new TextTask(taskData));
        }
        this.resourceManager = resourceManager;
    }

    @Override
    public int getVersion() {
        return this.version;
    }

    @Override
    public boolean needsToGenerate(IMaterial material) {
        return true;
    }

    @Override
    public TextGenerator prepareGenerator() {
        for(TextTask task : tasks){
            task.prepareTask(this.resourceManager);
        }
        return this;
    }

    @Override
    public void generate() {
        if(materialRequirements == null && customKeys != null){
            for(TextTask task : tasks){
                task.generate(null, this.customKeys, this.isClient);
            }
        }else if(materialRequirements != null){
            try{
                Set<IMaterial> materials = materialRequirements.getMaterials();
                for(TextTask task : tasks){
                    task.generate(materials, this.customKeys, this.isClient);
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public IMaterialCondition getMaterialRequirements() {
        return materialRequirements;
    }

    public String[] getCustomKeys() {
        return customKeys;
    }

    public Set<TextTask> getTasks() {
        return tasks;
    }

    public boolean isClient() {
        return isClient;
    }
}
