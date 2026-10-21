package dev.aether.client.module;

import net.minecraft.client.MinecraftClient;
import java.util.ArrayList;
import java.util.List;

public abstract class ClientModule {
    public enum Category { COMBAT, MOVEMENT, RENDER, WORLD, PLAYER, HUD, MISC }
    private final String name, description;
    private final Category category;
    private final List<Setting<?>> settings = new ArrayList<>();
    private boolean enabled;

    protected ClientModule(String name, String description, Category category) {
        this.name = name; this.description = description; this.category = category;
    }
    public String name() { return name; }
    public String description() { return description; }
    public Category category() { return category; }
    public boolean enabled() { return enabled; }
    public List<Setting<?>> settings() { return settings; }
    protected <T extends Setting<?>> T setting(T setting) { settings.add(setting); return setting; }

    public final void setEnabled(boolean enabled, MinecraftClient client) {
        if (this.enabled == enabled) return;
        this.enabled = enabled;
        if (enabled) onEnable(client); else onDisable(client);
    }
    public final void toggle(MinecraftClient client) { setEnabled(!enabled, client); }
    public final void tick(MinecraftClient client) { if (enabled) onTick(client); }
    protected void onEnable(MinecraftClient client) {}
    protected void onDisable(MinecraftClient client) {}
    protected void onTick(MinecraftClient client) {}
}
