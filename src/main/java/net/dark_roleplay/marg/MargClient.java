package net.dark_roleplay.marg;

import net.dark_roleplay.marg.generators.text.TextGenReloadListener;
import net.dark_roleplay.marg.generators.textures.TextureGenReloadListener;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.ResourcePackList;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class MargClient {

    public static void run(){
        //FMLEnvironment.Keys
        if(Minecraft.getInstance() == null) return; //Running on DataGen, nop out
        Minecraft.getInstance().getResourcePackList().addPackFinder(new MargResourcePackFinder(Marg.FOLDER_ASSETS, "Generated Asset Holder"));

        if(Minecraft.getInstance().getResourceManager() instanceof IReloadableResourceManager) {
            ((IReloadableResourceManager) Minecraft.getInstance().getResourceManager()).addReloadListener(new TextureGenReloadListener());
            ((IReloadableResourceManager) Minecraft.getInstance().getResourceManager()).addReloadListener(new TextGenReloadListener());
        }
    }

}
