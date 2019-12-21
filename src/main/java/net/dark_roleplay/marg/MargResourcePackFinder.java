package net.dark_roleplay.marg;

import java.io.File;
import java.util.Map;

import net.minecraft.resources.FolderPack;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackInfo.IFactory;

public class MargResourcePackFinder implements IPackFinder {

	private File	folder;
	private String	name;

	public MargResourcePackFinder(File file, String name) {
		this.folder = file;
		this.name = name;

	}

	@Override
	public <T extends ResourcePackInfo> void addPackInfosToMap(Map<String, T> nameToPackMap, IFactory<T> packInfoFactory) {
		T t = ResourcePackInfo.createResourcePack(name.toLowerCase(), true, () -> {
			return new FolderPack(this.folder) {

				@Override
				public String getName() { return name; }
			};
		}, packInfoFactory, ResourcePackInfo.Priority.TOP);
		if(t != null) {
			nameToPackMap.put(name.toLowerCase(), t);
		}
	}
}