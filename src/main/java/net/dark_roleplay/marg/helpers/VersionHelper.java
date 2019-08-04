package net.dark_roleplay.marg.helpers;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.apache.maven.artifact.versioning.ComparableVersion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.minecraft.util.ResourceLocation;

public final class VersionHelper {

	private static Map<String, Integer> currentVersions = new HashMap<String, Integer>();

	public static void addVersionEntry(ResourceLocation location, Integer version) {
		currentVersions.put(location.toString(), version);
	}

	public static void updateVersionFile() {
		// VersionChecker
		try(Writer writer = new FileWriter("./mod_data/marg/versions.json")) {
			Gson gson = new GsonBuilder().create();
			gson.toJson(currentVersions, writer);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static void loadVersionFile() {
		try(Reader reader = new FileReader("./mod_data/marg/versions.json")) {
			Gson	gson	= new GsonBuilder().create();
			Type	type	= new TypeToken<Map<String, Integer>>() {
							}.getType();
			currentVersions = gson.fromJson(reader, type);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean requiresUpdate(ResourceLocation location, Integer version) {
		return !currentVersions.containsKey(location.toString()) || version > currentVersions.get(location.toString());
	}

}
