package net.dark_roleplay.marg.api.materials;

import net.dark_roleplay.marg.Marg;
import net.dark_roleplay.marg.api.providers.TextureProvider;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * A small helper to easily do operations using Materials
 */
public class MaterialRequirement {

	private final String type;
	private final String[] requiredTextures;
	private Set<Material> gatheredMaterials;

	public MaterialRequirement(String type, String... requiredTextures) {
		this.requiredTextures = requiredTextures;
		this.type = type;
	}

	/**
	 * Collect all materials meeting this requirements.
	 * @return
	 */
	public Set<Material> gatherMaterials(){
		//!Marg.wasInitialized is not stricly required, it's there to avoid race conditions by multithreaded mod loading
		if(gatheredMaterials != null || !Marg.wasInitialized) return gatheredMaterials;

		Set<Material> materials = new HashSet<>();
		MaterialType type = MaterialType.get(this.type);
		for(Material material : type.getMaterials()){
			if(doesFulfillRequirements(material))
				materials.add(material);
		}
		this.gatheredMaterials = materials;
		return materials;
	}

	/**
	 * @param material the {@link Material} to be checked for it's textures.
	 * @return true when the passed in material does have provide all required
	 *         textures.
	 */
	public boolean doesFulfillRequirements(Material material) {
		TextureProvider textures = material.getTextureProvider();
		for(String texture : requiredTextures)
			if(!textures.hasTexture(texture)) return false;
		return true;
	}

	/**
	 * Passed in {@link Consumer} will be executed for all materials that do
	 * fulfill this requirements, and belong to this {@link MaterialType}
	 *
	 * @param consumer
	 */
	public void execute(Consumer<Material> consumer){
		if(gatheredMaterials == null) gatherMaterials();
		gatheredMaterials.stream().forEach(consumer);
	}

}
