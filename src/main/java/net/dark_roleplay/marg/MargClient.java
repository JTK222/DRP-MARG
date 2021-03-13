package net.dark_roleplay.marg;

import net.dark_roleplay.marg.client.listeners.TextureProcessorsReloadListener;
import net.dark_roleplay.marg.client.providers.ClientTextureProvider;
import net.dark_roleplay.marg.common.listeners.TextProcessorsReloadListener;
import net.dark_roleplay.marg.common.providers.TextureProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraftforge.fml.LogicalSide;

import java.util.Map;

public class MargClient {

	public static void clientConstructor(){
		if (Minecraft.getInstance().getResourceManager() instanceof IReloadableResourceManager) {
			IReloadableResourceManager resourceManager = (IReloadableResourceManager) Minecraft.getInstance().getResourceManager();

			resourceManager.addReloadListener(new TextureProcessorsReloadListener());
			resourceManager.addReloadListener(new TextProcessorsReloadListener(LogicalSide.CLIENT));

//			resourceManager.add
		}
	}

	public static TextureProvider createTextureProvider(Map<String, String> textures){
		return new ClientTextureProvider(textures);
	}
}
