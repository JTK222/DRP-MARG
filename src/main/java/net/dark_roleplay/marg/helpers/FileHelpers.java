package net.dark_roleplay.marg.helpers;

import java.io.File;

import net.dark_roleplay.marg.Marg;
import net.minecraft.util.ResourceLocation;

public class FileHelpers {

	public static boolean doesFileExistClient(String string) {
		ResourceLocation	loc		= new ResourceLocation(string);

		File				file	= new File(Marg.FOLDER_ASSETS, String.format("assets/%s/%s", loc.getNamespace(), loc.getPath()));

		return file.exists();
	}

	public static File getFileClient(String string) {
		ResourceLocation	loc		= new ResourceLocation(string);

		File				file	= new File(Marg.FOLDER_ASSETS, String.format("assets/%s/%s", loc.getNamespace(), loc.getPath()));

		return file;
	}

}
