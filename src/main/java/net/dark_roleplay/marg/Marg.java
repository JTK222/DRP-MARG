package net.dark_roleplay.marg;

import net.dark_roleplay.marg.api.MargAPI;
import net.dark_roleplay.marg.client.MargClient;
import net.dark_roleplay.marg.impl.providers.MaterialRegistry;
import net.dark_roleplay.marg.impl.providers.MaterialTypeRegistry;
import net.dark_roleplay.marg.util.FileUtil;
import net.dark_roleplay.marg.util.MaterialLoader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Marg.MODID)
public class Marg {

	public static final Logger LOGGER = LogManager.getLogger();

	static{
		MargAPI.setMaterialProvider(new MaterialRegistry());
		MargAPI.setMaterialTypeProvider(new MaterialTypeRegistry());
	}

	public static final String	MODID	= "marg";

	public Marg() {
		MaterialLoader.loadMaterialFiles();
		FileUtil.setupFolders();

		DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> MargClient::run);
	}
}
