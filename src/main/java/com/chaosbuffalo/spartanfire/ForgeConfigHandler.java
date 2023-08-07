package com.chaosbuffalo.spartanfire;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = SpartanFire.MODID)
public class ForgeConfigHandler {

    @Config.Comment("General Config Options")
    @Config.Name("General Options")
    public static final GeneralConfig general = new GeneralConfig();

    public static class GeneralConfig {

        @Config.Comment("Registers custom recipe handling for venom throwing weapons")
        @Config.Name("Register Venom Throwing Weapon Recipes")
        @Config.RequiresMcRestart
        public boolean registerVenomThrowingRecipes = true;

        @Config.Comment("Registers custom recipe handling for flamed/iced/shocked throwing weapons")
        @Config.Name("Register Flamed/Iced/Shocked Throwing Weapon Recipes")
        @Config.RequiresMcRestart
        public boolean registerFlamedIcedShockedThrowingRecipes = true;
    }

    @Mod.EventBusSubscriber(modid = SpartanFire.MODID)
    private static class EventHandler {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if(event.getModID().equals(SpartanFire.MODID)) {
                ConfigManager.sync(SpartanFire.MODID, Config.Type.INSTANCE);
            }
        }
    }
}