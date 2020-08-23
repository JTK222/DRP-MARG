package net.dark_roleplay.marg.client;

import java.io.File;
import java.util.Map;
import java.util.function.Consumer;

import net.minecraft.resources.FolderPack;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.IPackNameDecorator;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackInfo.IFactory;
import net.minecraft.resources.data.PackMetadataSection;

public class MargResourcePackFinder implements IPackFinder {

	private File	folder;

	public MargResourcePackFinder(File file) {
		this.folder = file;
	}

	@Override
	public void func_230230_a_(Consumer<ResourcePackInfo> consumer, IFactory iFactory) {
		if(folder.exists() && folder.isDirectory()) {
			ResourcePackInfo info = ResourcePackInfo.createResourcePack("generated/MARG", true, () -> new FolderPack(folder), iFactory, ResourcePackInfo.Priority.TOP, IPackNameDecorator.BUILTIN);
			consumer.accept(info);
		}
	}
}