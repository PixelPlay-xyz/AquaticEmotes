package xyz.larkyy.aquaticemotes.aquaticemotes.emote;

import com.ticxo.modelengine.api.events.AnimationEndEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import xyz.larkyy.aquaticemotes.aquaticemotes.AquaticEmotes;

import java.util.UUID;

public class EmoteListener implements Listener {

    @EventHandler
    public void onAnimationEnd(AnimationEndEvent e) {
        UUID modelUUID = e.getModel().getModeledEntity().getBase().getUUID();
        Emote emote = AquaticEmotes.getInstance().getEmoteHandler().getSpawnedEmote(modelUUID);
        if (emote == null) return;
        emote.getAnimationHandler().onAnimationEnd(e);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {

        Emote emote = AquaticEmotes.getInstance().getEmoteHandler().getPlayerEmote(e.getPlayer().getUniqueId());
        if (emote == null) return;
        e.getPlayer().setInvisible(false);
        destroyEmote(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        if (!e.isSneaking()) return;
        Emote emote = AquaticEmotes.getInstance().getEmoteHandler().getPlayerEmote(e.getPlayer().getUniqueId());
        if (emote == null)
        {
            return;
        }
        emote.endAnimation();
    }

    private void destroyEmote(UUID playerUUID) {
        Emote emote = AquaticEmotes.getInstance().getEmoteHandler().getPlayerEmote(playerUUID);
        if (emote == null) return;
        emote.destroyEmote();
    }

}
