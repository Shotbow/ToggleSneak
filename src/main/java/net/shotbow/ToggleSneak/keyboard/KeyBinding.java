package net.shotbow.ToggleSneak.keyboard;

import com.mojang.blaze3d.platform.InputConstants;
import lombok.Getter;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.shotbow.ToggleSneak.ToggleSneak;

@Getter
@OnlyIn(Dist.CLIENT)
public class KeyBinding {

    private KeyMapping toggleSneakKey;
    private KeyMapping toggleSprintKey;

    public KeyBinding(){
        FMLJavaModLoadingContext.get().getModEventBus().addListener(
                EventPriority.NORMAL,
                false,
                RegisterKeyMappingsEvent.class, e -> {
                    e.register(this.toggleSneakKey = getKeyMapping("toggle.sneak", InputConstants.KEY_G));
                    e.register(this.toggleSprintKey = getKeyMapping("toggle.sprint", InputConstants.KEY_H));
                }
        );
    }

    private KeyMapping getKeyMapping(String key, int keycode){
        return new KeyMapping(
                "keybinding." + key,
                KeyConflictContext.IN_GAME,
                KeyModifier.NONE,
                InputConstants.Type.KEYSYM,
                keycode,
                ToggleSneak.MOD_TITLE
        );
    }

}
