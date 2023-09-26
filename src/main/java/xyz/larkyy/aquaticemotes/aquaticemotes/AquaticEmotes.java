package xyz.larkyy.aquaticemotes.aquaticemotes;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.larkyy.aquaticemotes.aquaticemotes.commands.Commands;
import xyz.larkyy.aquaticemotes.aquaticemotes.config.Loader;
import xyz.larkyy.aquaticemotes.aquaticemotes.emote.Emote;
import xyz.larkyy.aquaticemotes.aquaticemotes.emote.EmoteHandler;
import xyz.larkyy.aquaticemotes.aquaticemotes.emote.EmoteListener;

public final class AquaticEmotes extends JavaPlugin {

    private static AquaticEmotes instance;
    private EmoteHandler emoteHandler;
    private Loader loader;

    @Override
    public void onLoad() {
        instance = this;
        emoteHandler = new EmoteHandler();
        loader = new Loader();

        load();
    }

    private void load() {
        loader.load();
    }

    private void unload() {
        for (Emote value : getEmoteHandler().getSpawnedEmotes().values()) {
            value.destroyEmote();
        }
        emoteHandler.getSpawnedEmotes().clear();
        emoteHandler.getPlayerEmotes().clear();
        emoteHandler.getEmotes().clear();
    }

    public void reload() {
        unload();
        load();
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new EmoteListener(),this);
        getCommand("aquaticemotes").setExecutor(new Commands());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static AquaticEmotes getInstance() {
        return instance;
    }

    public EmoteHandler getEmoteHandler() {
        return emoteHandler;
    }
}
