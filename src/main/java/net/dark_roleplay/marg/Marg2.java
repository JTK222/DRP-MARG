package net.dark_roleplay.marg;

import net.dark_roleplay.marg.common.material.MaterialLoader;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Marg2.MODID)
public class Marg2 {

	public static final String	MODID	= "marg";
	public static final Logger LOGGER = LogManager.getLogger();

	public Marg2() {
		MaterialLoader.loadMaterialFiles();
	}

}
