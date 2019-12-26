package net.dark_roleplay.marg.generators.text;

import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.marg.impl.materials.MargMaterial;
import net.dark_roleplay.marg.impl.materials.MargMaterialRequirement;
import net.dark_roleplay.marg.generators.textures.IGenerator;

import java.util.Set;

public class TextGenerator implements IGenerator {

    private int version = 0;
    private MargMaterialRequirement materialRequirements;

    private final Set<String> customKeys;
    private Set<TextTask> tasks;
    private boolean isClient = true;

    public TextGenerator(MargMaterialRequirement materialRequirements, Set<String> customKeys, Set<TextTask> tasks){
        this.customKeys = customKeys;
        this.materialRequirements = materialRequirements;
        this.tasks = tasks;
    }

    public TextGenerator setServer(){
        this.isClient = false;
        return this;
    }

    @Override
    public int getVersion() {
        return this.version;
    }

    @Override
    public boolean needsToGenerate(MargMaterial material) {
        return true;
    }

    @Override
    public void prepareGenerator() {
        for(TextTask task : tasks){
            task.prepareTask();
        }
    }

    @Override
    public void generate() {
        if(materialRequirements == null && customKeys != null){
            for(TextTask task : tasks){
                task.generate(null, this.customKeys, this.isClient);
            }
        }else if(materialRequirements != null){
            Set<IMaterial> materials = materialRequirements.getMaterials();
            for(TextTask task : tasks){
                task.generate(materials, this.customKeys, this.isClient);
            }
        }

    }
}
