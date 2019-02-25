package net.dark_roleplay.core_modules.maarg.objects.other.generators;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.dark_roleplay.core_modules.maarg.DRPCMMaARG.ClientProxy;
import net.dark_roleplay.core_modules.maarg.References;
import net.dark_roleplay.core_modules.maarg.api.materials.Material;
import net.dark_roleplay.core_modules.maarg.handler.Generator;
import net.dark_roleplay.core_modules.maarg.handler.LogHelper;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

public class JsonGenerator {

	private JsonArray modelArr;
	
	private String type = "none";
	private ResourceLocation file = null;
	
	private File targetFolder = null;
	
	public JsonGenerator(ResourceLocation file, JsonElement elm, File targetFolder){
		this.file = file;
		if(elm.isJsonObject()){
			JsonObject obj = elm.getAsJsonObject();
			
			if(obj.has("type")) {
				this.type = obj.get("type").getAsString();
			}
			
			if(obj.has("jsons")){
				this.modelArr = obj.get("jsons").getAsJsonArray();
			}
		}
		
		this.targetFolder = targetFolder;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void generateJsons(Set<Material> set){
		
		StringBuilder builder = new StringBuilder();
		builder.append("There were some errors trying to generate JSON files from: ");
		builder.append(this.file.toString());
		builder.append("\n");
		
		boolean errored = false;
		
		for(int i = 0; i < modelArr.size(); i++){
			JsonObject obj = modelArr.get(i).getAsJsonObject();
			Charset charset = StandardCharsets.UTF_8;
			String content;
			String destPath = obj.get("output").getAsString().replaceFirst(":", "/");
			
			try {
				boolean needsToCreate = false;
				if(!Generator.FORCE_GENERATE) {
					for(Material mat : set){
						File dest = new File(targetFolder + "/" + mat.getNamed(destPath));
						if(!dest.exists())
							needsToCreate |= true;
					}
					if(!needsToCreate)
						break;
				}
					
				IResource res = ClientProxy.getResource(new ResourceLocation(obj.get("input").getAsString()));
				if(res == null) {
					errored = true;
					builder.append("Couldn't find File: " + obj.get("input").getAsString() + "\n");
					continue;
				}
				InputStream is = res.getInputStream();
				content = IOUtils.toString(is, charset);
				is.close();
				
				for(Material mat : set){
					if(!mat.getType().equals(this.type)) continue;
					
					File dest = new File(targetFolder + "/" + mat.getNamed(destPath));
					if(dest.exists())
						continue;
					dest.getParentFile().mkdirs();
					String base = mat.getNamed(content);
					Files.write(dest.toPath(), base.getBytes(charset));
				}
			} catch (IOException e) {
				e.printStackTrace();
				errored = true;
				builder.append("Couldn't find File: " + obj.get("input").getAsString() + "\n");
				continue;
			}
		}
		
		if(errored) {
			LogHelper.error(builder.toString());
		}
	}
}



