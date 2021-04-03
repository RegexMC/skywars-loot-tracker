package me.regexmc.skywarsloottracker.commands;

import me.regexmc.skywarsloottracker.SkywarsLootTracker;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.List;

public class SkywarsLootTrackerCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "skywarsloottracker";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("toggle")) {
                SkywarsLootTracker.configManager.enabled = !SkywarsLootTracker.configManager.enabled;
                SkywarsLootTracker.configManager.saveConfig();
                sender.addChatMessage(
                        new ChatComponentText(
                                (SkywarsLootTracker.configManager.enabled ?
                                        EnumChatFormatting.GREEN + "Enabled " :
                                        EnumChatFormatting.RED + "Disabled "
                                ) + "Skywars Loot Tracker"
                        )
                );
            } else if (args[0].equalsIgnoreCase("export")) {
                sender.addChatMessage(new ChatComponentText(SkywarsLootTracker.dataManager.export()));
            } else {
                sender.addChatMessage(new ChatComponentText("Invalid Command"));
            }
        }
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        List<String> x = new ArrayList<>();
        x.add("toggle");
        x.add("export");
        return x;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return -1;
    }
}
