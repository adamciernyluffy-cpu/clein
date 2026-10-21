package dev.aether.client.module;

import net.minecraft.client.MinecraftClient;
import java.util.ArrayList;
import java.util.List;

public final class ModuleManager {
    private final List<ClientModule> modules = new ArrayList<>();
    public void register(ClientModule module) { modules.add(module); }
    public List<ClientModule> all() { return List.copyOf(modules); }
    public List<ClientModule> category(ClientModule.Category category) {
        return modules.stream().filter(m -> m.category() == category).toList();
    }
    public ClientModule find(String name) {
        return modules.stream().filter(m -> m.name().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
    public void tick(MinecraftClient client) { for (ClientModule module : modules) module.tick(client); }
}
