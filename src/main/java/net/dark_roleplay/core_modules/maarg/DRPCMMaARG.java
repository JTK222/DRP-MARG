package net.dark_roleplay.core_modules.maarg;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import net.dark_roleplay.core_modules.maarg.api.materials.Material;
import net.dark_roleplay.core_modules.maarg.handler.Generator;
import net.dark_roleplay.core_modules.maarg.handler.MaterialLoader;
import net.dark_roleplay.core_modules.maarg.handler.MaterialRegistry;
import net.dark_roleplay.core_modules.maarg.objects.commands.ForceRegen;
import net.dark_roleplay.core_modules.maarg.objects.resource_pack.GeneratedResourcePack;
import net.dark_roleplay.library.sides.IProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.ProgressManager.ProgressBar;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = References.MODID, name = References.NAME, version = References.VERSION, dependencies = References.DEPENDECIES, certificateFingerprint = "893c317856cf6819b3a8381c5664e4b06df7d1cc")
public class DRPCMMaARG{

	@SidedProxy(modId = References.MODID)
	public static IProxy proxy;

	@EventHandler
    public void preInit(FMLPreInitializationEvent event){
    	References.init(event);

//		VanillaMaterials.registerVanillaMaterials();

		MaterialLoader.loadCommonGenerators();
		Generator.generateCommonResources();

    	proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event){

    	proxy.init(event);

    	Set<Material> materials = MaterialRegistry.getMaterials();
        ProgressBar bar = ProgressManager.push("Initializing Material Items", materials.size());
        MaterialRegistry.getMaterials().forEach(mat -> {
        	bar.step(mat.getType() + ":" + mat.getName());
        	mat.init();
        });
        ProgressManager.pop(bar);

        MaterialRegistry.getMaterials().forEach(mat -> {
        	mat.printDebug();
        });
    }

	public static class ServerProxy implements IProxy{}

	@SideOnly(Side.CLIENT)
	public static class ClientProxy implements IProxy{

		@Override
		public void preInit(FMLPreInitializationEvent event) {
			MaterialLoader.loadClientGenerators();
			Generator.generateClientResources();
		}

		@Override
		public void init(FMLInitializationEvent event) {
	    	ClientCommandHandler.instance.registerCommand(new ForceRegen());
		}

		@SuppressWarnings("unchecked")
		public ClientProxy() {
			File resourcesFile = new File(Minecraft.getMinecraft().gameDir.getPath() + "/dark roleplay/argh/");
			File resourcesFolder = new File(Minecraft.getMinecraft().gameDir.getPath() + "/dark roleplay/argh/assets/");
			resourcesFolder.mkdirs();
			((List<IResourcePack>)ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "field_110449_ao", "defaultResourcePacks")).add(new GeneratedResourcePack(resourcesFile));
		}

		public static IResource getResource(ResourceLocation location){
	        IResourceManager manager = Minecraft.getMinecraft().getResourceManager();
	        IResource res = null;
			try {
				res = manager.getResource(location);
			} catch (IOException e) {
				e.printStackTrace();
			}
	        return res;
	    }

		public static List<IResource> getResources(ResourceLocation location){
			List<IResource> resources = new ArrayList<IResource>();

			File folder;
			try {
				folder = new File(ClientProxy.class.getClassLoader().getResource("/assets/" + location.getNamespace() + "/" + location.getPath()).toURI());

				for (final File file : folder.listFiles()) {
			        if (file.isDirectory()) {
			        } else {
			        	resources.add(getResource(new ResourceLocation(location.getNamespace(), location.getPath() + file.getName())));
			        }
			    }
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}

			return resources;
		}

		public static JsonElement getResourceAsJson(ResourceLocation location){
			IResource res = getResource(location);
			Gson gson = new Gson();
			BufferedReader reader = new BufferedReader(new InputStreamReader(res.getInputStream()));
			JsonElement je = gson.fromJson(reader, JsonElement.class);
			return je;
		}
	}
}
