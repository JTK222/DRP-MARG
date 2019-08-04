package net.dark_roleplay.marg.api.materials;

public class MaterialType {

	private final String	name;
	private final String	formatKey;

	public MaterialType(String name) {
		this.name = name;
		this.formatKey = "%" + name + "%";
	}

	public String getNamed(String input, Material material) {
		return input.replaceAll(this.formatKey, material.getName());
	}

	public String getName() { return this.name; }
}
