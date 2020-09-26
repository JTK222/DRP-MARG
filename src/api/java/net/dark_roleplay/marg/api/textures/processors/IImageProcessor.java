package net.dark_roleplay.marg.api.textures.processors;


import net.dark_roleplay.marg.api.textures.helper.TextureData;

@FunctionalInterface
public interface IImageProcessor {
	TextureData processImages(TextureData source, TextureData input);
}
