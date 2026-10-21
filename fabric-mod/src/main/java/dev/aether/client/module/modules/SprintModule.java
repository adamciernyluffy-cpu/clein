package dev.aether.client.module.modules;

import dev.aether.client.module.ClientModule;
import dev.aether.client.module.Setting;
import net.minecraft.client.MinecraftClient;

public final class SprintModule extends ClientModule {
    private final Setting.Bool onlyForward = setting(new Setting.Bool(
            "Forward only", "Only sprint while moving forward.", true));

    public SprintModule() { super("Sprint", "Automatically sprint while moving.", Category.MOVEMENT); }

    @Override protected void onTick(MinecraftClient client) {
        if (client.player == null || client.options == null) return;
        boolean moving = onlyForward.get()
                ? client.options.forwardKey.isPressed()
                : (client.options.forwardKey.isPressed() || client.options.backKey.isPressed()
                   || client.options.leftKey.isPressed() || client.options.rightKey.isPressed());
        if (moving && !client.player.isSneaking() && client.player.getHungerManager().getFoodLevel() > 6) {
            client.player.setSprinting(true);
        }
    }
}
