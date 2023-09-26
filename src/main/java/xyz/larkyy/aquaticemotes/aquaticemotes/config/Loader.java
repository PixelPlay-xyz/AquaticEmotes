package xyz.larkyy.aquaticemotes.aquaticemotes.config;

import org.bukkit.configuration.file.FileConfiguration;
import xyz.larkyy.aquaticemotes.aquaticemotes.AquaticEmotes;
import xyz.larkyy.itemlibrary.CustomItem;

public class Loader {

    private static final Config CONFIG = new Config(AquaticEmotes.getInstance(),"config.yml");
    public void load() {
        CONFIG.load();
        FileConfiguration cfg = CONFIG.getConfiguration();

        if (cfg.contains("emotes")) {
            for (String id : cfg.getConfigurationSection("emotes").getKeys(false)) {
                EmoteInfo emoteInfo = loadEmote(cfg,"emotes."+id,id);
                if (emoteInfo != null) AquaticEmotes.getInstance().getEmoteHandler().getEmotes().put(id,emoteInfo);
            }
        }
    }

    private EmoteInfo loadEmote(FileConfiguration cfg, String path, String id) {
        String model = cfg.getString(path+".model");
        String preAnimation = cfg.getString(path+".pre-animation",null);
        String animation = cfg.getString(path+".animation","idle");
        String postAnimation = cfg.getString(path+".post-animation",null);
        boolean loop = cfg.getBoolean(path+".loop");
        CustomItem displayItem = CustomItem.loadFromYaml(cfg,path+".display");

        return new EmoteInfo(id,model,preAnimation,animation,postAnimation,loop,displayItem);
    }

}
