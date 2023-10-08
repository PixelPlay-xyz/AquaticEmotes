package xyz.larkyy.aquaticemotes.aquaticemotes.emote;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.animation.handler.IPriorityHandler;
import com.ticxo.modelengine.api.animation.handler.IStateMachineHandler;
import com.ticxo.modelengine.api.events.AnimationEndEvent;
import com.ticxo.modelengine.api.model.ModeledEntity;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;

public class AnimationHandler {

    private final Emote emote;

    private AnimationState animationState;

    public AnimationHandler(Emote emote) {
        this.emote = emote;

        if (emote.getEmoteInfo().getPreAnimation() != null) {
            animationState = AnimationState.PRE;
            playAnimation(emote.getEmoteInfo().getPreAnimation());
        } else {
            animationState = AnimationState.ANIMATION;
            playAnimation(emote.getEmoteInfo().getAnimation());
        }
    }

    public void onAnimationEnd(AnimationEndEvent event) {
        Bukkit.broadcast(Component.text("Animation ended"));
        playNext();
    }

    private void playAnimation(String id) {
        ModeledEntity modeledEntity = ModelEngineAPI.getModeledEntity(emote.getModelUUID());
        com.ticxo.modelengine.api.animation.handler.AnimationHandler animationHandler = modeledEntity.getModel(emote.getEmoteInfo().getModel()).get().getAnimationHandler();
        if (animationHandler instanceof IPriorityHandler priorityHandler) {
           priorityHandler.playAnimation(id,1,0,0,false);
        } else if (animationHandler instanceof IStateMachineHandler stateMachineHandler) {
            stateMachineHandler.playAnimation(0,id,1,0,0,false);
        }
    }

    public void endEmote() {
        animationState = AnimationState.ANIMATION;
        playNext();
    }

    private void playNext() {
        switch (animationState) {
            case PRE -> {
                animationState = AnimationState.ANIMATION;
                playAnimation(emote.getEmoteInfo().getAnimation());
            }
            case ANIMATION -> {
                if (emote.getEmoteInfo().getPostAnimation() == null) {
                    animationState = AnimationState.END;
                    onEmoteAnimationComplete();
                    break;
                }
                animationState = AnimationState.POST;
                playAnimation(emote.getEmoteInfo().getPostAnimation());
            }
            case POST -> {
                animationState = AnimationState.END;
                onEmoteAnimationComplete();
            }
        }
    }

    private void onEmoteAnimationComplete() {
        emote.destroyEmote();
    }

    private enum AnimationState {
        PRE,
        ANIMATION,
        POST,
        END
    }

}
