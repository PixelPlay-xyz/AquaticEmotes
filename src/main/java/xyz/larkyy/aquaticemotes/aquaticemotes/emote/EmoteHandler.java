package xyz.larkyy.aquaticemotes.aquaticemotes.emote;

import xyz.larkyy.aquaticemotes.aquaticemotes.config.EmoteInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EmoteHandler {

    private final Map<String,EmoteInfo> emotes = new HashMap<>();
    private final Map<UUID,Emote> spawnedEmotes = new HashMap<>();
    private final Map<UUID,UUID> playerEmotes = new HashMap<>();

    public Emote spawnEmote(EmoteInfo emoteInfo, UUID playerUUID) {
        Emote playingEmote = getPlayerEmote(playerUUID);
        if (playingEmote != null) {
            playingEmote.destroyEmote();
        }
        Emote emote = new Emote(playerUUID,emoteInfo);
        spawnedEmotes.put(emote.getModelUUID(),emote);
        playerEmotes.put(playerUUID,emote.getModelUUID());
        return emote;
    }

    public Emote spawnEmote(String id, UUID playerUUID) {
        EmoteInfo emote = getEmote(id);
        if (emote == null) return null;
        return spawnEmote(emote,playerUUID);
    }

    public EmoteInfo getEmote(String id) {
        return emotes.get(id);
    }

    public Emote getSpawnedEmote(UUID uuid) {
        return spawnedEmotes.get(uuid);
    }

    public Emote getPlayerEmote(UUID uuid) {
        if (!playerEmotes.containsKey(uuid)) return null;
        UUID emoteUUID = playerEmotes.get(uuid);
        return spawnedEmotes.getOrDefault(emoteUUID,null);
    }

    public Map<String, EmoteInfo> getEmotes() {
        return emotes;
    }

    public Map<UUID, Emote> getSpawnedEmotes() {
        return spawnedEmotes;
    }

    public Map<UUID, UUID> getPlayerEmotes() {
        return playerEmotes;
    }
}
