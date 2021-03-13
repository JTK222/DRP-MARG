package net.dark_roleplay.marg;

import net.dark_roleplay.marg.common.listeners.TextProcessorsReloadListener;
import net.dark_roleplay.marg.common.material.MaterialLoader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Marg.MODID)
public class Marg {

	public static final String	MODID	= "marg";
	public static final Logger LOGGER = LogManager.getLogger();

	public Marg() {
		MaterialLoader.loadMaterialFiles();

		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> MargClient::clientConstructor);
		Mod.EventBusSubscriber.Bus.FORGE.bus().get().addListener(this::addReloadListener);
	}

	public void addReloadListener(AddReloadListenerEvent event){
		event.addListener(new TextProcessorsReloadListener(LogicalSide.SERVER));
	}
}
