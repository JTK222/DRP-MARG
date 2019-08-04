package net.dark_roleplay.marg.objects.other.generators.client;

import java.util.Set;

import com.google.gson.JsonObject;

import net.dark_roleplay.marg.api.materials.Material;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public abstract class ClientSideGenerator {

	private ResourceLocation	file					= null;
	protected JsonObject		json					= null;
	private int					version					= -1;
	private String				generatorType			= "";
	private int					requiredGeneratorPasses	= 1;

	protected ClientSideGenerator(ResourceLocation file, JsonObject json, int requiredGeneratorPasses) {
		this.file = file;
		this.json = json;
		this.version = JSONUtils.getInt(json, "version", -1);
		this.generatorType = JSONUtils.getString(json, "type", "none");
		this.requiredGeneratorPasses = requiredGeneratorPasses;
	}

	public int getGeneratorPass() { return 0; }

	public final int getVersion() { return this.version; }

	public final ResourceLocation getFile() { return this.file; }

	public final String getGeneratorType() { return this.generatorType; }

	public abstract void prepare();

	/**
	 * This is called after {@link ClientSideGenerator#prepare()}
	 * 
	 * @return true if this generator requires to generate anything.
	 */
	public abstract boolean shouldGenerate(Set<Material> set);

	public abstract void generate(Set<Material> set);
}
