package net.dark_roleplay.core_modules.maarg.objects.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.dark_roleplay.core_modules.maarg.configs.ServerConfig;
import net.dark_roleplay.core_modules.maarg.handler.Generator;
import net.dark_roleplay.core_modules.maarg.handler.MaterialLoader;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

public class ForceRegen implements ICommand{

	@Override
	public int compareTo(ICommand o) {
		return 0;
	}

	@Override
	public String getName() {
		return "maargforce";
	}

	@Override
	public List<String> getAliases() {
		return Arrays.asList(ServerConfig.COMMAND_FORCE_REGEN_ARG);
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/maargforce";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		Generator.FORCE_GENERATE = true;
		MaterialLoader.loadClientGenerators();
		Generator.generateClientResources();
		
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		return new ArrayList<String>();
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}
}
