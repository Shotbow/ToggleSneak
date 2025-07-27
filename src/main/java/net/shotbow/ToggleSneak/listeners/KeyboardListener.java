package net.shotbow.ToggleSneak.listeners;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.shotbow.ToggleSneak.ToggleSneak;
import net.shotbow.ToggleSneak.keyboard.KeyBinding;
import net.shotbow.ToggleSneak.object.ToggleConfig;

public class KeyboardListener {

    public KeyboardListener() {
        TickEvent.PlayerTickEvent.Post.BUS.addListener(this::keyPress);
    }

    @SubscribeEvent
    public void keyPress(TickEvent.PlayerTickEvent.Post e) {
        //Handle toggling of options
        KeyBinding keyBinding = ToggleSneak.getToggleSneak().getKeyBinding();
        ToggleConfig config = ToggleConfig.getInstance();
        if(keyBinding.getToggleSneakKey() != null && isPressed(keyBinding.getToggleSneakKey())){
            //Toggle Sneak pressed
            final boolean setTo = !config.getToggleSneak().get();
            ToggleConfig.getInstance().setToggleSneak(setTo);
        }
        if(keyBinding.getToggleSprintKey() != null && isPressed(keyBinding.getToggleSprintKey())){
            //Toggle Sprint pressed
            final boolean setTo = !config.getToggleSprint().get();
            ToggleConfig.getInstance().getToggleSprint().set(setTo);
        }
    }

    private boolean isPressed(KeyMapping mapping) {
        return mapping.isDown()
                && mapping.getKeyConflictContext().isActive()
                && mapping.consumeClick();
    }

}
