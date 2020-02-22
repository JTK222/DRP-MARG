package net.dark_roleplay.marg.impl.generators.text;

import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.marg.api.materials.IMaterialCondition;
import net.dark_roleplay.marg.impl.generators.IGenerator;
import net.dark_roleplay.marg.util.ILoggable;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

public class TextGenerator implements IGenerator<TextGenerator>, ILoggable {

    private int version = 0;
    private IMaterialCondition materialRequirements;

    private final Set<String> customKeys;
    private Set<TextTask> tasks;
    private boolean isClient;

    public TextGenerator(int version, boolean isClient, IMaterialCondition materialRequirements, Set<String> customKeys, Set<TextTask> tasks){
        this.version = version;
        this.isClient = isClient;
        this.customKeys = customKeys;
        this.materialRequirements = materialRequirements;
        this.tasks = tasks;
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
            task.prepareTask();
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

    public Set<String> getCustomKeys() {
        return customKeys;
    }

    public Set<TextTask> getTasks() {
        return tasks;
    }

    public boolean isClient() {
        return isClient;
    }

    @Override
    public void LogToStream(Writer writer, String prefix) throws IOException {
        writer.append(prefix + "TextGenerator:\n");
        writer.append(prefix + "├ Version: " + this.version + "\n");
        writer.append(prefix + "├ Side: " + (this.isClient ? "Client" : "Server") + "\n");
        if(this.customKeys.size() > 0){
            writer.append(prefix + "├ Custom Keys: \n");
            for(String key : this.customKeys)
                writer.append(prefix + "│ ├ '" + key + "'\n");
        }else{
            writer.append(prefix + "├ Custom Keys: <NONE>\n");
        }
        if(this.tasks.size() > 0) {
            writer.append(prefix + "└ Tasks:\n");
            for(TextTask task : this.tasks)
                task.LogToStream(writer, "  │ ");
        }else{
            writer.append(prefix + "└ Tasks: <NONE>\n");
        }
    }
}
