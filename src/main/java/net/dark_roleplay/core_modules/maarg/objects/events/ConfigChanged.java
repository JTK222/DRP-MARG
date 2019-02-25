package net.dark_roleplay.core_modules.maarg.objects.events;

import net.dark_roleplay.core_modules.maarg.References;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = References.MODID)
public class ConfigChanged {

	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if(event.getModID().equals(References.MODID)){
			ConfigManager.sync(References.MODID, Config.Type.INSTANCE);
		}
	}
}
