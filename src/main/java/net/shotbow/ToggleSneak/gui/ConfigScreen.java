package net.shotbow.ToggleSneak.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.widget.ForgeSlider;
import net.shotbow.ToggleSneak.object.ToggleConfig;
import org.jetbrains.annotations.NotNull;

public class ConfigScreen extends Screen {
    private static final int TITLE_HEIGHT = 8;
    private static final int BUTTON_WIDTH = 150;
    private static final int LONG_BUTTON_WIDTH = BUTTON_WIDTH * 2;
    private static final int BUTTON_HEIGHT = 20;
    private ForgeSlider releaseTimeSlider;

    public ConfigScreen() {
        super(Component.translatable("configGui.title"));
    }

    @Override
    protected void init() {
        ToggleConfig toggleConfig = ToggleConfig.getInstance();
        int screenWidth = this.width;
        int screenHeight = this.height;
        int buttonGap = 5;
        int buttonX = (screenWidth - BUTTON_WIDTH * 2 - buttonGap) / 2; // center the two buttons horizontally
        int buttonY = TITLE_HEIGHT + 30; // position the two buttons below the title
        int sliderX = (screenWidth - BUTTON_WIDTH * 2) / 2; // center the slider horizontally
        int sliderY = buttonY + BUTTON_HEIGHT + 10; // position the slider below the buttons
        int doneButtonX = (screenWidth - LONG_BUTTON_WIDTH) / 2; // center the done button horizontally
        int doneButtonY = screenHeight - 30; // position the done button near the bottom of the screen

        this.addRenderableWidget(CycleButton.onOffBuilder(toggleConfig.getToggleSneak().get()).create(
                buttonX,
                buttonY,
                BUTTON_WIDTH,
                BUTTON_HEIGHT,
                Component.translatable("configGui.option.toggleSneak"), (widget, value) -> toggleConfig.setToggleSneak(value))
        );

        this.addRenderableWidget(CycleButton.onOffBuilder(toggleConfig.getToggleSprint().get()).create(
                buttonX + BUTTON_WIDTH + buttonGap,
                buttonY,
                BUTTON_WIDTH,
                BUTTON_HEIGHT,
                Component.translatable("configGui.option.toggleSprint"), (widget, value) -> toggleConfig.getToggleSprint().set(value))
        );

        this.addRenderableWidget(this.releaseTimeSlider = new ForgeSlider(
                sliderX,
                sliderY,
                LONG_BUTTON_WIDTH,
                BUTTON_HEIGHT,
                Component.translatable("configGui.option.shiftReleaseTime").append(": "),
                Component.empty(),
                toggleConfig.getShiftReleaseMinimum(),
                toggleConfig.getShiftReleaseMaximum(),
                toggleConfig.getShiftReleaseTime().get(),
                1,
                0,
                true
        ));

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, btn -> onClose())
                .bounds(doneButtonX, doneButtonY, LONG_BUTTON_WIDTH, BUTTON_HEIGHT)
                .build());
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float ticks) {
        this.renderMenuBackground(guiGraphics);
        guiGraphics.drawCenteredString(this.font, this.title,
                this.width / 2, TITLE_HEIGHT, 0xFFFFFF);
        super.render(guiGraphics, mouseX, mouseY, ticks);
    }

    @Override
    public void onClose() {
        ToggleConfig.getInstance().getShiftReleaseTime().set(this.releaseTimeSlider.getValue());
        ToggleConfig.save();
        super.onClose();
    }
}
