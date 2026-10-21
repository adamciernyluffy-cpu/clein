package dev.aether.client.module.modules;

import dev.aether.client.module.ClientModule;
import dev.aether.client.module.Setting;

public final class CoordinatesModule extends ClientModule {
    public final Setting.Bool showDimension = setting(new Setting.Bool(
            "Dimension", "Include the current dimension in the coordinates line.", true));
    public CoordinatesModule() { super("Coordinates", "Show coordinates in the HUD.", Category.HUD); }
}
