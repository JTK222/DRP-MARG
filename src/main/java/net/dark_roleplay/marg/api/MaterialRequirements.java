package net.dark_roleplay.marg.api;

import java.util.function.Consumer;

import net.dark_roleplay.marg.api.materials.Material;
import net.dark_roleplay.marg.api.materials.MaterialType;
import net.dark_roleplay.marg.handler.MaterialRegistry;

/**
 * A small helper to easily do operations using Materials
 */
public class MaterialRequirements {

	private final MaterialType	type;
	private final String[]		requiredTextures;

	public MaterialRequirements(MaterialType type, String... requiredTextures) {
		this.requiredTextures = requiredTextures;
		this.type = type;
	}

	/**
	 * @param material the {@link Material} to be checked for it's textures.
	 * @return true when the passed in material does have provide all required
	 *         textures.
	 */
	public boolean doesFulfillRequirements(Material material) {
		for(String texture : requiredTextures)
			if( !material.hasTexture(texture)) return false;
		return true;
	}

	/**
	 * Passed in {@link Consumer} will be executed for all materials that do
	 * fulfill this requirements.
	 * And are registered in the {@link MaterialRegistry}
	 * 
	 * @param consumer
	 */
	public void execute(Consumer<Material> consumer) {
		MaterialRegistry.getMaterialsForType(type.getName()).stream().filter((material) -> {
			return doesFulfillRequirements(material);
		}).forEach(consumer);
	}

}
