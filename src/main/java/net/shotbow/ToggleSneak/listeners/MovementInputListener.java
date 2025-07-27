package net.shotbow.ToggleSneak.listeners;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.ClientInput;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.shotbow.ToggleSneak.ToggleSneak;
import net.shotbow.ToggleSneak.object.ToggleConfig;
import net.shotbow.ToggleSneak.object.ToggleStatus;

import java.time.Duration;
import java.time.Instant;

public class MovementInputListener {

    private final KeyMapping shiftKey = Minecraft.getInstance().options.keyShift;
    private Instant shiftTime = null;
    private boolean isDismounting = false;
    private int dismountingTicks = 0;

    private void setSneaking(ClientInput clientInput) {
        clientInput.keyPresses = new Input(
                clientInput.keyPresses.forward(),
                clientInput.keyPresses.backward(),
                clientInput.keyPresses.left(),
                clientInput.keyPresses.right(),
                clientInput.keyPresses.jump(),
                true,
                clientInput.keyPresses.sprint()
        );
    }

    @SubscribeEvent
    public void moveEvent(MovementInputUpdateEvent e) {
        ToggleConfig config = ToggleConfig.getInstance();
        final boolean isSneakingEnabled = config.getToggleSneak().get();
        final boolean isSprintingEnabled = config.getToggleSprint().get();
        if (!isSneakingEnabled && !isSprintingEnabled) {
            return;
        }
        ToggleStatus settings = ToggleSneak.getToggleSneak().getToggleStatus();
        Player player = e.getEntity();
        if (isSprintingEnabled && !player.isCrouching()) {
            player.setSprinting(true);
        }

        if (player.isPassenger()
                || isDismounting
                || player.getAbilities().flying
                || !isSneakingEnabled
        ) {
            return;
        }
        if (settings.isSneakingToggled()) {
            setSneaking(e.getInput());
        }
        if (shiftKey.isDown()) {
            //Pressing Shift
            if (shiftTime == null) {
                shiftTime = Instant.now();
            }
        } else {
            //Not pressing shift
            if (shiftTime == null) {
                return;
            }
            long diff = Duration.between(shiftTime, Instant.now()).toMillis();
            shiftTime = null;
            if (diff <= config.getShiftReleaseTime().get() && shiftKey.consumeClick()) {
                if (settings.isSneakingToggled()) {
                    settings.setSneakingToggled(false);
                    return;
                }
                settings.setSneakingToggled(true);
                setSneaking(e.getInput());
            }
        }
    }

    @SubscribeEvent
    public void setDismounting(EntityMountEvent e) {
        Player player = ToggleSneak.getToggleSneak().getMinecraft().player;
        if(e.isMounting()
                || player == null
                || e.getEntityMounting() == null
                || !(e.getEntityMounting() instanceof Player playerMounting)
        ) {
            return;
        }
        if(!playerMounting.is(player)) {
            return;
        }
        dismountingTicks = 0;
        isDismounting = true;
    }

    @SubscribeEvent
    public void unsetDismounting(TickEvent.PlayerTickEvent.Post e) {
        if(isDismounting) {
            dismountingTicks += 1;
            if(dismountingTicks >= 15) {
                dismountingTicks = 0;
                isDismounting = false;
            }
        }
    }

}
