package net.dark_roleplay.marg.common.material;

import com.google.gson.JsonParser;
import net.dark_roleplay.marg.Marg2;
import net.dark_roleplay.marg.util.CodecUtil;
import net.minecraftforge.fml.ModList;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class MaterialLoader {
	private static JsonParser parser = new JsonParser();

	public static void loadMaterialFiles() {
		ModList.get().getMods().forEach(info -> {
			Map<String, Object> props = info.getModProperties();

			if (props.containsKey("margMaterialFiles") && props.get("margMaterialFiles") instanceof List) {
				List<String> files = (List) props.get("margMaterialFiles");

				files.parallelStream()
						.map(fileName -> Marg2.class.getClassLoader().getResourceAsStream(fileName))
						.map(inputStream -> new InputStreamReader(inputStream))
						.map(reader -> parser.parse(reader))
						.map(json -> CodecUtil.decodeCodec(Material.CODEC, json))
						.filter(material -> material != null && ModList.get().isLoaded(material.getRequiredMods()))
				.forEach(material -> System.out.println(material.getMaterialName()));
				//TODO Collect and register
				System.out.println("Test");
			}
		});
	}
}
