package net.dark_roleplay.marg.api.materials;

import net.dark_roleplay.marg.api.MargAPI;
import net.dark_roleplay.marg.api.provider.IGraphicsProvider;
import net.dark_roleplay.marg.api.provider.ITextProvider;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class ItemMaterialCondition implements IMaterialCondition {

	private final String type;
	private final String[] items;
	private Set<IMaterial> gatheredMaterials;

	public ItemMaterialCondition(String type, String... items) {
		this.items = items;
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
		if(!this.type.equals(material.getMaterialTypeName())) return false;
		ITextProvider txtProvider = material.getTextProvider();
		if(items != null && items.length > 0){
			for (String item : items)
				if(!txtProvider.hasKey("item%" + item)) return false;
		}
		return true;
	}

	@Override
	public void forEach(Consumer<IMaterial> consumer){
		if(gatheredMaterials == null) getMaterials();
		gatheredMaterials.stream().forEach(consumer);
	}

}
