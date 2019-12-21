package net.dark_roleplay.marg.listeners;

import net.dark_roleplay.marg.Marg;
import net.dark_roleplay.marg.MargResourcePackFinder;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

@EventBusSubscriber(bus = Bus.FORGE)
public class ServerStarting {

	@SubscribeEvent
	public static void serverStarting(FMLServerAboutToStartEvent event) {
		((ResourcePackList<?>) ObfuscationReflectionHelper.getPrivateValue(MinecraftServer.class, event.getServer(), "field_195577_ad")).addPackFinder(new MargResourcePackFinder(Marg.FOLDER_DATA, "Generated Date Holder"));
	}

}
