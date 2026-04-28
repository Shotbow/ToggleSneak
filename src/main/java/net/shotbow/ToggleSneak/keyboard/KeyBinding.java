package net.shotbow.ToggleSneak.keyboard;

import com.mojang.blaze3d.platform.InputConstants;
import lombok.Getter;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.shotbow.ToggleSneak.ToggleSneak;

@Getter
@OnlyIn(Dist.CLIENT)
public class KeyBinding {

    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath(ToggleSneak.MOD_ID, ToggleSneak.MOD_ID)
    );

    private KeyMapping toggleSneakKey;
    private KeyMapping toggleSprintKey;

    public KeyBinding() {
        RegisterKeyMappingsEvent.BUS.addListener(this::registerMappings);
    }

    private void registerMappings(RegisterKeyMappingsEvent e) {
        e.register(this.toggleSneakKey = getKeyMapping("toggle.sneak", InputConstants.KEY_G));
        e.register(this.toggleSprintKey = getKeyMapping("toggle.sprint", InputConstants.KEY_H));
    }

    private KeyMapping getKeyMapping(String key, int keycode) {
        return new KeyMapping(
                "keybinding." + key,
                KeyConflictContext.IN_GAME,
                KeyModifier.NONE,
                InputConstants.Type.KEYSYM,
                keycode,
                CATEGORY,
                0
        );
    }

}
