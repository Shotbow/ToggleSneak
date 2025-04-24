package net.shotbow.ToggleSneak;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.shotbow.ToggleSneak.gui.ConfigScreen;
import net.shotbow.ToggleSneak.gui.StatusDisplay;
import net.shotbow.ToggleSneak.keyboard.KeyBinding;
import net.shotbow.ToggleSneak.listeners.KeyboardListener;
import net.shotbow.ToggleSneak.listeners.MovementInputListener;
import net.shotbow.ToggleSneak.object.ToggleConfig;
import net.shotbow.ToggleSneak.object.ToggleStatus;

@Mod(ToggleSneak.MOD_ID)
public class ToggleSneak {

    public static final String MOD_ID = "togglesneak";
    public static final String MOD_TITLE = "Toggle Sneak";
    private @Getter static ToggleSneak toggleSneak;

    private @Getter final Minecraft minecraft = Minecraft.getInstance();
    private @Getter final KeyBinding keyBinding;
    private @Getter final ToggleStatus toggleStatus;

    public ToggleSneak(FMLJavaModLoadingContext context) {
        toggleSneak = this;
        loadConfig();
        this.keyBinding = new KeyBinding(context);
        this.toggleStatus = new ToggleStatus();
        context.registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> new ConfigScreen())
        );
        this.registerListeners();
    }

    private void loadConfig(){
        ToggleConfig.loadConfigFile(FMLPaths.CONFIGDIR.get().resolve(MOD_ID + ".toml"));
    }

    private void registerListeners() {
        MinecraftForge.EVENT_BUS.register(new StatusDisplay());
        MinecraftForge.EVENT_BUS.register(new KeyboardListener());
        MinecraftForge.EVENT_BUS.register(new MovementInputListener());
    }
}
