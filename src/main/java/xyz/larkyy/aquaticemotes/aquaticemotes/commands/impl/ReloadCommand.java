package xyz.larkyy.aquaticemotes.aquaticemotes.commands.impl;

import org.bukkit.command.CommandSender;
import xyz.larkyy.aquaticemotes.aquaticemotes.AquaticEmotes;
import xyz.larkyy.aquaticemotes.aquaticemotes.commands.ICommand;

public class ReloadCommand implements ICommand {
    @Override
    public void run(CommandSender sender, String[] args) {
        if (!sender.hasPermission("aquaticemotes.reload")) {
            return;
        }
        sender.sendMessage("Plugin has been reloaded");
        AquaticEmotes.getInstance().reload();
    }
}
