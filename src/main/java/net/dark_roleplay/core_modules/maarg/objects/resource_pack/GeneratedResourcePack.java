package net.dark_roleplay.core_modules.maarg.objects.resource_pack;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.apache.commons.io.filefilter.DirectoryFileFilter;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackFileNotFoundException;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.MetadataSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GeneratedResourcePack implements IResourcePack, IResourceManagerReloadListener {

    protected final File resourcePackFile;

    public GeneratedResourcePack(File file){
    	this.resourcePackFile = file;
    }

	private final Map<ResourceLocation, String> loadedData = new HashMap<>();

	@Override
	public InputStream getInputStream(ResourceLocation location) throws IOException {

		String name = String.format("%s/%s/%s", "assets", location.getNamespace(), location.getPath());

		File file1 = this.getFile(name);

        if (file1 == null){
            throw new ResourcePackFileNotFoundException(this.resourcePackFile, name);
        }else{
            return new BufferedInputStream(new FileInputStream(file1));
        }
	}

	@Nullable
    private File getFile(String path){
        File file1 = new File(this.resourcePackFile, path);

		if (file1.isFile()/* && validatePath(file1, path)*/){
		    return file1;
		};

        return null;
    }

	@Override
	public boolean resourceExists(ResourceLocation location) {
		 return this.getFile(String.format("%s/%s/%s", "assets", location.getNamespace(), location.getPath())) != null;
	}

	@Override
	public Set<String> getResourceDomains() {
		Set<String> set = Sets.<String>newHashSet();
        File file1 = new File(this.resourcePackFile, "assets/");

        if (file1.isDirectory()) {
            for (File file2 : file1.listFiles((FileFilter)DirectoryFileFilter.DIRECTORY)){
                String s = file1.toURI().relativize(file2.toURI()).getPath();

                if (s.equals(s.toLowerCase(java.util.Locale.ROOT))){
                    set.add(s.substring(0, s.length() - 1));
                }else{

                }
            }
        }

        return set;
	}

    @Override
	public <T extends IMetadataSection> T getPackMetadata(MetadataSerializer metadataSerializer, String metadataSectionName) throws IOException {
    	JsonObject o = new JsonParser().parse(
    			"{"
    			+ "\"pack\":{"
    			+ "\"pack_format\": 3,"
    			+ "\"description\": \"Automatic Resource Generation Holder\""
    			+ "	}"
    			+ "}"
    			).getAsJsonObject();

    	return metadataSerializer.parseMetadataSection(metadataSectionName, o);
    }

	@Override
	public BufferedImage getPackImage() throws IOException {
		throw new FileNotFoundException("pack.png");
	}

	@Override
	public String getPackName() {
		return "DRP dummy resource pack";
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
	    this.loadedData.clear();
	}
}