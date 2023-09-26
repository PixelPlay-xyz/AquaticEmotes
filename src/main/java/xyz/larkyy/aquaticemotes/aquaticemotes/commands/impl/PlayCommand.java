package xyz.larkyy.aquaticemotes.aquaticemotes.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.larkyy.aquaticemotes.aquaticemotes.AquaticEmotes;
import xyz.larkyy.aquaticemotes.aquaticemotes.commands.ICommand;
import xyz.larkyy.aquaticemotes.aquaticemotes.config.EmoteInfo;

public class PlayCommand implements ICommand {
    @Override
    public void run(CommandSender sender, String[] args) {
        if (!sender.hasPermission("aquaticemotes.play")) {
            sender.sendMessage("You dont have permissions to do that!");
            return;
        }

        if (args.length < 2) {
            sender.sendMessage("Unknown usage");
            return;
        }

        String emoteId = args[1];
        EmoteInfo emoteInfo = AquaticEmotes.getInstance().getEmoteHandler().getEmote(emoteId);
        if (emoteInfo == null) {
            sender.sendMessage("Unknown emote name");
            return;
        }

        if (args.length == 2) {
            if (!(sender instanceof Player player)) {
                return;
            }
            AquaticEmotes.getInstance().getEmoteHandler().spawnEmote(emoteInfo,player.getUniqueId());
            sender.sendMessage("Emote spawned");
        } else {
            if (!sender.hasPermission("aquaticemotes.play.others")) {
                sender.sendMessage("You dont have permissions to do that!");
                return;
            }
            Player target = Bukkit.getPlayer(args[2]);
            if (target == null) {
                sender.sendMessage("Unknown target");
                return;
            }
            AquaticEmotes.getInstance().getEmoteHandler().spawnEmote(emoteInfo,target.getUniqueId());
            sender.sendMessage("Emote spawned");
        }
    }
}
