package xyz.larkyy.aquaticemotes.aquaticemotes.emote;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.entity.data.BukkitEntityData;
import com.ticxo.modelengine.api.generator.blueprint.ModelBlueprint;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import com.ticxo.modelengine.api.model.bone.BoneBehaviorTypes;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.larkyy.aquaticemotes.aquaticemotes.AquaticEmotes;
import xyz.larkyy.aquaticemotes.aquaticemotes.config.EmoteInfo;

import java.util.UUID;

public class Emote {

    private final UUID playerUUID;
    private final EmoteInfo emoteInfo;
    private final UUID modelUUID;
    private boolean spawned;
    private final AnimationHandler animationHandler;

    public Emote(UUID playerUUID, EmoteInfo emoteInfo) {
        this.playerUUID = playerUUID;
        this.emoteInfo = emoteInfo;
        this.modelUUID = spawnModel();
        animationHandler = new AnimationHandler(this);
    }

    private UUID spawnModel() {
        Player player = Bukkit.getPlayer(playerUUID);
        ModelBlueprint blueprint = ModelEngineAPI.getBlueprint(emoteInfo.getModel());
        ActiveModel activeModel = ModelEngineAPI.createActiveModel(blueprint);
        ModeledEntity modeledEntity = ModelEngineAPI.createModeledEntity(player);
        modeledEntity.addModel(activeModel, false);
        spawned = true;

        if(modeledEntity.getBase().getData() instanceof BukkitEntityData data) {
            data.getTracked().addForcedPairing(player);
        }

        activeModel.getBones().forEach((s, modelBone) -> {
            modelBone.getBoneBehavior(BoneBehaviorTypes.PLAYER_LIMB).ifPresent(playerLimb -> {
                playerLimb.setTexture(player);
            });
        });

        Bukkit.broadcastMessage("Spawning emote");
        return modeledEntity.getBase().getUUID();
    }

    public void destroyEmote() {
        if (!spawned) return;
        ModelEngineAPI.getModeledEntity(modelUUID).destroy();
        spawned = false;

        getEmoteHandler().getSpawnedEmotes().remove(modelUUID);
        if (getEmoteHandler().getPlayerEmotes().containsKey(playerUUID) &&
                getEmoteHandler().getPlayerEmote(playerUUID).equals(modelUUID)) {
            getEmoteHandler().getPlayerEmotes().remove(playerUUID);
        }
    }

    public EmoteInfo getEmoteInfo() {
        return emoteInfo;
    }

    public boolean isSpawned() {
        return spawned;
    }

    public UUID getModelUUID() {
        return modelUUID;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public AnimationHandler getAnimationHandler() {
        return animationHandler;
    }

    private EmoteHandler getEmoteHandler() {
        return AquaticEmotes.getInstance().getEmoteHandler();
    }
}
