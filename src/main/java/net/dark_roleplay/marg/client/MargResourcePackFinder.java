package net.dark_roleplay.marg.client;

import java.io.File;
import java.util.Map;

import net.minecraft.resources.FolderPack;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackInfo.IFactory;

public class MargResourcePackFinder implements IPackFinder {

	private File	folder;

	public MargResourcePackFinder(File file) {
		this.folder = file;
	}

	@Override
	public <T extends ResourcePackInfo> void addPackInfosToMap(Map<String, T> nameToPackMap, IFactory<T> packInfoFactory) {
		if(folder.exists() && folder.isDirectory()) {
			T t = ResourcePackInfo.createResourcePack("Marg Generated Assets/Data", true, () -> new FolderPack(folder), packInfoFactory, ResourcePackInfo.Priority.TOP);
			if (t == null) return;
			nameToPackMap.put("marg:generated", t);
		}
	}
}