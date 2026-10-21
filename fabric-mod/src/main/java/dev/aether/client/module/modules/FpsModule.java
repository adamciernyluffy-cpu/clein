package dev.aether.client.module.modules;

import dev.aether.client.module.ClientModule;
import dev.aether.client.module.Setting;

public final class FpsModule extends ClientModule {
    public final Setting.Bool showLabel = setting(new Setting.Bool(
            "Show label", "Show the FPS label before the number.", true));
    public FpsModule() { super("FPS", "Show the current frame rate.", Category.HUD); }
}
