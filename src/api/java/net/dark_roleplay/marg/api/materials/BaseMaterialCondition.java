package net.dark_roleplay.marg.api.materials;

import net.dark_roleplay.marg.api.MargAPI;
import net.dark_roleplay.marg.api.provider.IGraphicsProvider;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class BaseMaterialCondition implements IMaterialCondition {

	private final String type;
	private final String[] requiredTextures;
	private Set<IMaterial> gatheredMaterials;

	public BaseMaterialCondition(String type, String[] requiredTextures) {
		this.requiredTextures = requiredTextures;
		this.type = type;
	}

	@Override
	public Set<IMaterial> getMaterials(){
		if(gatheredMaterials != null) return gatheredMaterials;

		Set<IMaterial> materials = new HashSet<>();
		IMaterialType type = MargAPI.getMaterialTypes().getType(this.type);
		for(IMaterial material : type.getMaterials()){
			if(doesAccept(material))
				materials.add(material);
		}
		this.gatheredMaterials = materials;
		return materials;
	}

	@Override
	public boolean doesAccept(IMaterial material) {
		if(requiredTextures != null && requiredTextures.length > 0) {
			IGraphicsProvider gfxProvider = material.getGraphicsProvider();
			for (String texture : requiredTextures)
				if (!gfxProvider.hasTexture(texture)) return false;
		}
		return true;
	}

	@Override
	public void forEach(Consumer<IMaterial> consumer){
		if(gatheredMaterials == null) getMaterials();
		gatheredMaterials.stream().forEach(consumer);
	}

}
