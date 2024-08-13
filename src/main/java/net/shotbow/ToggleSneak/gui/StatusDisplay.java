package net.shotbow.ToggleSneak.gui;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.shotbow.ToggleSneak.ToggleSneak;
import net.shotbow.ToggleSneak.object.ToggleConfig;
import net.shotbow.ToggleSneak.object.ToggleStatus;

public class StatusDisplay {

    @SubscribeEvent
    public void render(CustomizeGuiOverlayEvent e) {
        ToggleConfig config = ToggleConfig.getInstance();
        Minecraft minecraft = ToggleSneak.getToggleSneak().getMinecraft();
        if(minecraft.player == null
                || !config.getToggleDisplay().get()) {
            return;
        }
        ToggleStatus status = ToggleSneak.getToggleSneak().getToggleStatus();
        int midPoint = e.getWindow().getGuiScaledHeight() / 2;
        if(config.getToggleSneak().get()) {
            // Render sneaking display
            MutableComponent sneakingDisplayText = Component.translatable("displayGui.sneaking")
                    .withStyle(ChatFormatting.WHITE);
            if(status.isSneakingToggled()) {
                sneakingDisplayText.withStyle(ChatFormatting.GOLD);
            }
            e.getGuiGraphics().drawString(
                    minecraft.font,
                    sneakingDisplayText,
                    1,
                    midPoint,
                    0
            );
        }
        if(config.getToggleSprint().get()) {
            // Render sprinting display
            MutableComponent sprintingDisplayText = Component.translatable("displayGui.sprinting")
                    .withStyle(ChatFormatting.GOLD);
            int yHeight = midPoint;
            if(config.getToggleSneak().get()) {
                yHeight += 10;
            }
            e.getGuiGraphics().drawString(
                    minecraft.font,
                    sprintingDisplayText,
                    1,
                    yHeight,
                    0
            );
        }
    }

}
