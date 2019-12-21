package net.dark_roleplay.marg.generators.text;

import net.dark_roleplay.marg.api.materials.Material;
import net.dark_roleplay.marg.api.materials.MaterialRequirement;
import net.dark_roleplay.marg.generators.textures.IGenerator;
import net.minecraft.util.ResourceLocation;

import java.util.HashSet;
import java.util.Set;

public class TextGenerator implements IGenerator {

    private int version = 0;
    private MaterialRequirement materialRequirements;

    private final Set<String> customKeys;
    private Set<TextTask> tasks;
    private boolean isClient = true;

    public TextGenerator(MaterialRequirement materialRequirements, Set<String> customKeys, Set<TextTask> tasks){
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
    public boolean needsToGenerate(Material material) {
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
            Set<Material> materials = materialRequirements.gatherMaterials();
            for(TextTask task : tasks){
                task.generate(materials, this.customKeys, this.isClient);
            }
        }

    }
}
