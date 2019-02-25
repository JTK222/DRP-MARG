package net.dark_roleplay.core_modules.maarg.objects.other.generators;

import net.minecraft.util.ResourceLocation;

public interface IVersionedGenerator {

	public int getVersion();

	public String getGeneratorType();

	public ResourceLocation getFile();
}
