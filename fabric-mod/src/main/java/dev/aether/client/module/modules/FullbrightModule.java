package dev.aether.client.module.modules;

import dev.aether.client.module.ClientModule;
import dev.aether.client.module.Setting;
import net.minecraft.client.MinecraftClient;

public final class FullbrightModule extends ClientModule {
    private final Setting.Number gamma = setting(new Setting.Number(
            "Brightness", "Brightness multiplier used while enabled.", 16, 1, 16, 1));
    private double previousGamma = 1.0;

    public FullbrightModule() { super("Fullbright", "Increase world brightness locally.", Category.RENDER); }

    @Override protected void onEnable(MinecraftClient client) {
        if (client.options != null) previousGamma = client.options.getGamma().getValue();
    }
    @Override protected void onTick(MinecraftClient client) {
        if (client.options != null) client.options.getGamma().setValue(gamma.get());
    }
    @Override protected void onDisable(MinecraftClient client) {
        if (client.options != null) client.options.getGamma().setValue(previousGamma);
    }
}
