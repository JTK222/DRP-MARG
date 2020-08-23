package net.dark_roleplay.assetier.processors;


import net.dark_roleplay.assetier.helper.TextureData;

@FunctionalInterface
public interface IImageProcessor {
	TextureData processImages(TextureData source, TextureData input);
}
