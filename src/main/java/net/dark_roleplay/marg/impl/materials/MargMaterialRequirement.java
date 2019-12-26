package net.dark_roleplay.marg.impl.materials;

import net.dark_roleplay.marg.Marg;
import net.dark_roleplay.marg.api.MaterialTypes;
import net.dark_roleplay.marg.api.materials.IMaterial;
import net.dark_roleplay.marg.api.materials.IMaterialCondition;
import net.dark_roleplay.marg.api.materials.IMaterialType;
import net.dark_roleplay.marg.api.provider.IGraphicsProvider;
import net.dark_roleplay.marg.impl.providers.MargTextureProvider;
import net.dark_roleplay.marg.impl.materials.MargMaterial;
import net.dark_roleplay.marg.impl.materials.MargMaterialType;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class MargMaterialRequirement implements IMaterialCondition {

	private final String type;
	private final String[] requiredTextures;
	private Set<IMaterial> gatheredMaterials;

	public MargMaterialRequirement(String type, String... requiredTextures) {
		this.requiredTextures = requiredTextures;
		this.type = type;
	}

	@Override
	public Set<IMaterial> getMaterials(){
		//!Marg.wasInitialized is not stricly required, it's there to avoid race conditions by multithreaded mod loading
		if(gatheredMaterials != null || !Marg.wasInitialized) return gatheredMaterials;

		Set<IMaterial> materials = new HashSet<>();
		IMaterialType type = MaterialTypes.getType(this.type);
		for(IMaterial material : type.getMaterials()){
			if(doesAccept(material))
				materials.add(material);
		}
		this.gatheredMaterials = materials;
		return materials;
	}

	@Override
	public boolean doesAccept(IMaterial material) {
		IGraphicsProvider gfxProvider = material.getGraphicsProvider();
		for(String texture : requiredTextures)
			if(!gfxProvider.hasTexture(texture)) return false;
		return true;
	}

	@Override
	public void forEach(Consumer<IMaterial> consumer){
		if(gatheredMaterials == null) getMaterials();
		gatheredMaterials.stream().forEach(consumer);
	}

}
