package dev.aether.client.ui;

import dev.aether.client.AetherClient;
import dev.aether.client.module.ClientModule;
import dev.aether.client.module.Setting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import java.util.List;

public final class AetherClickGui extends Screen {
    private ClientModule.Category category = ClientModule.Category.COMBAT;
    private ClientModule selected;
    private double animation = 0.0;
    private int scroll = 0;
    private long lastClick = 0;

    public AetherClickGui() { super(Text.literal("Aether Client")); }

    @Override public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        animation = Math.min(1.0, animation + delta * 3.4);
        int w = 520, h = 330;
        int x = (width - w) / 2, y = (height - h) / 2;
        int alpha = (int)(225 * animation);
        context.fill(0, 0, width, height, 0x99060813);
        // Layered translucent panels and accent rails.
        context.fill(x, y, x+w, y+h, (alpha << 24) | 0x11182A);
        context.fill(x, y, x+w, y+42, 0xEE17152A);
        context.fill(x, y, x+3, y+h, 0xFF9B72FF);
        context.drawText(textRenderer, "AETHER", x+18, y+15, 0xFFE5D8FF, false);
        context.drawText(textRenderer, "CLIENT  /  1.21.11", x+90, y+16, 0xFF9B9BB8, false);
        context.drawText(textRenderer, "Right Shift  ·  Modules", x+w-155, y+16, 0xFF9B9BB8, false);

        int cy = y+56;
        for (ClientModule.Category cat : ClientModule.Category.values()) {
            int tw = textRenderer.getWidth(cat.name()) + 18;
            int bg = cat == category ? 0xFF3A2A58 : 0xAA202237;
            context.fill(x+14, cy, x+14+tw, cy+22, bg);
            context.drawText(textRenderer, cat.name(), x+23, cy+7, cat == category ? 0xFFE8D9FF : 0xFFB8B8D0, false);
            cy += 27;
        }
        context.drawText(textRenderer, selected == null ? "MODULES" : selected.name().toUpperCase(),
                x+150, y+57, 0xFFE5D8FF, false);
        context.drawText(textRenderer, "Click a module to toggle · Right-click for settings",
                x+150, y+72, 0xFF9999B5, false);
        List<ClientModule> modules = AetherClient.MODULES.category(category);
        int rowY = y+96 - scroll;
        for (ClientModule module : modules) {
            if (rowY > y+ h-10 || rowY < y+80) { rowY += 34; continue; }
            boolean hover = mouseX >= x+146 && mouseX <= x+w-12 && mouseY >= rowY && mouseY <= rowY+29;
            context.fill(x+146, rowY, x+w-12, rowY+29, hover ? 0xDD292742 : 0xAA1C2033);
            context.drawText(textRenderer, module.name(), x+157, rowY+6, module.enabled()?0xFFBFA0FF:0xFFE3E3F2, false);
            context.drawText(textRenderer, module.description(), x+157, rowY+17, 0xFF9A9AB4, false);
            context.fill(x+w-47, rowY+9, x+w-25, rowY+20, module.enabled()?0xFF9B72FF:0xFF42445A);
            context.fill(x+w-(module.enabled()?34:45), rowY+11, x+w-(module.enabled()?27:38), rowY+18, 0xFFFFFFFF);
            rowY += 34;
        }
        if (selected != null) {
            int sy = y+96;
            context.fill(x+146, y+94, x+w-12, y+h-12, 0xF20F1221);
            context.drawText(textRenderer, selected.name()+" SETTINGS", x+158, sy, 0xFFE5D8FF, false);
            sy += 22;
            for (Setting<?> setting : selected.settings()) {
                String value = String.valueOf(setting.get());
                context.drawText(textRenderer, setting.name()+"  ["+value+"]", x+158, sy, 0xFFFFFFFF, false);
                context.drawText(textRenderer, setting.description(), x+158, sy+11, 0xFF9999B5, false);
                sy += 31;
            }
            context.drawText(textRenderer, "Click setting to change · Esc to close",
                    x+158, y+h-25, 0xFFBBA8E7, false);
        }
        super.render(context, mouseX, mouseY, delta);
    }

    @Override public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int w=520,h=330,x=(width-w)/2,y=(height-h)/2;
        int cy=y+56;
        for (ClientModule.Category cat : ClientModule.Category.values()) {
            int tw=textRenderer.getWidth(cat.name())+18;
            if(mouseX>=x+14 && mouseX<=x+14+tw && mouseY>=cy && mouseY<=cy+22) {
                category=cat; selected=null; scroll=0; return true;
            }
            cy+=27;
        }
        if (selected != null && mouseX > x+146 && mouseY > y+112) {
            int sy=y+118;
            for (Setting<?> s : selected.settings()) {
                if(mouseY>=sy && mouseY<sy+31) {
                    if(s instanceof Setting.Bool b) b.toggle();
                    else if(s instanceof Setting.Number n) n.adjust(button==1?-1:1);
                    else if(s instanceof Setting.Choice c) c.next();
                    return true;
                }
                sy+=31;
            }
        }
        int rowY=y+96-scroll;
        for(ClientModule module : AetherClient.MODULES.category(category)) {
            if(mouseX>=x+146 && mouseX<=x+w-12 && mouseY>=rowY && mouseY<=rowY+29) {
                if(button==1) selected=module;
                else { module.toggle(MinecraftClient.getInstance()); selected=null; }
                return true;
            }
            rowY+=34;
        }
        return super.mouseClicked(mouseX,mouseY,button);
    }

    @Override public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        scroll = Math.max(0, Math.min(500, scroll - (int)(verticalAmount*28)));
        return true;
    }
    @Override public boolean shouldPause() { return false; }
    @Override public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(keyCode==GLFW.GLFW_KEY_ESCAPE) { close(); return true; }
        return super.keyPressed(keyCode,scanCode,modifiers);
    }
}
