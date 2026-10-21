package dev.aether.client;

import dev.aether.client.module.ModuleManager;
import dev.aether.client.module.modules.CoordinatesModule;
import dev.aether.client.module.modules.FpsModule;
import dev.aether.client.module.modules.FullbrightModule;
import dev.aether.client.module.modules.SprintModule;
import dev.aether.client.ui.AetherClickGui;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class AetherClient implements ClientModInitializer {
    public static final ModuleManager MODULES = new ModuleManager();
    private static KeyBinding clickGuiKey;

    @Override
    public void onInitializeClient() {
        MODULES.register(new FullbrightModule());
        MODULES.register(new SprintModule());
        MODULES.register(new CoordinatesModule());
        MODULES.register(new FpsModule());

        clickGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.aether.clickgui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                KeyBinding.Category.MISC
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (clickGuiKey.wasPressed()) {
                if (client.currentScreen == null) client.setScreen(new AetherClickGui());
            }
            MODULES.tick(client);
        });
    }
}
