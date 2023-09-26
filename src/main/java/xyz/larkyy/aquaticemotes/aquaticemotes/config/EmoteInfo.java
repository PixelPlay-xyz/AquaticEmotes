package xyz.larkyy.aquaticemotes.aquaticemotes.config;

import xyz.larkyy.itemlibrary.CustomItem;

public class EmoteInfo {

    private final String id;
    private final String model;
    private final String preAnimation;
    private final String animation;
    private final String postAnimation;
    private final boolean loop;
    private final CustomItem display;

    public EmoteInfo(String id, String model, String preAnimation, String animation, String postAnimation, boolean loop, CustomItem display) {
        this.id = id;
        this.model = model;
        this.preAnimation = preAnimation;
        this.animation = animation;
        this.postAnimation = postAnimation;
        this.loop = loop;
        this.display = display;
    }

    public String getId() {
        return id;
    }

    public boolean isLoop() {
        return loop;
    }

    public CustomItem getDisplay() {
        return display;
    }

    public String getAnimation() {
        return animation;
    }

    public String getPostAnimation() {
        return postAnimation;
    }

    public String getPreAnimation() {
        return preAnimation;
    }

    public String getModel() {
        return model;
    }
}
