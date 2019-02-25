package net.dark_roleplay.core_modules.maarg.configs;

import net.dark_roleplay.core_modules.maarg.References;
import net.minecraftforge.common.config.Config;

@Config(modid = References.MODID, name = "Dark Roleplay Core/Modules/MaARG", category = "server")
public class ServerConfig {
	
	@Config.Name("Command Alias - Maarg Regenerate")
	@Config.Comment("this is a list of all possible alliases for the command")
	public static String[] COMMAND_FORCE_REGEN_ARG = new String[] {"maargregenresources"};
	
}
