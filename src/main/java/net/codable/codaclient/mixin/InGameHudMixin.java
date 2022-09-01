package net.codable.codaclient.mixin;

import net.codable.codaclient.config.ModDisplayConfig;
import net.codable.codaclient.Main;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    ModDisplayConfig config = Main.CONFIG;

    @Inject(at = @At("TAIL"), method = "render")
    public void render(MatrixStack matrixStack, float tickDelta, CallbackInfo info) {
        MinecraftClient client = MinecraftClient.getInstance();
        int multiplier = (config.FPS_MULTIPLIER != 0 ? config.FPS_MULTIPLIER : 1);
        int fps = ((MinecraftClientMixin) client).getCurrentFps() * multiplier;

        String displayString = fps + " fps";
     //   client.fpsDebugString = fps + " fps T: inffast B: 0";
        float textPosX = 8;
        float textPosY = 4;

        double guiScale = client.getWindow().getScaleFactor();
        if (guiScale > 0) {
            textPosX /= guiScale;
            textPosY /= guiScale;
        }

        float maxTextPosX = client.getWindow().getScaledWidth() - client.textRenderer.getWidth(displayString);
        float maxTextPosY = client.getWindow().getScaledHeight() - client.textRenderer.fontHeight;
        textPosX = Math.min(textPosX, maxTextPosX);
        textPosY = Math.min(textPosY, maxTextPosY);

        int textColor = 0xFFFFFF;

        if (config.DISPLAY_FPS) {
            this.renderText(matrixStack, client.textRenderer, displayString, textPosX, textPosY, textColor, 1f, false);
        }
    }

    private void renderText(MatrixStack matrixStack, TextRenderer textRenderer, String text, float x, float y, int color, float scale, boolean shadowed) {
        matrixStack.push();
        matrixStack.translate(x, y, 0);
        matrixStack.scale(scale, scale, scale);
        matrixStack.translate(-x, -y, 0);

        if (shadowed) {
            textRenderer.drawWithShadow(matrixStack, text, x, y, color);
        } else {
            textRenderer.draw(matrixStack, text, x, y, color);
        }
        matrixStack.pop();
        

    }
}
