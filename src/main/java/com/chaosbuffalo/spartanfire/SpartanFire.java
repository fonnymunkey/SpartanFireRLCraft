package com.chaosbuffalo.spartanfire;

import com.chaosbuffalo.spartanfire.integrations.CompatLoadUtil;
import com.chaosbuffalo.spartanfire.integrations.RLCombatCompat;
import com.chaosbuffalo.spartanfire.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = SpartanFire.MODID, name = SpartanFire.NAME, version = SpartanFire.VERSION,
        dependencies="required-after:iceandfire;required-after:spartanweaponry@[1.6.0,);required-after:llibrary@[1.7.19,)")
public class SpartanFire {
    public static final String MODID = "spartanfire";
    public static final String NAME = "Spartan Fire";
    public static final String VERSION = "1.3.3";

    @Mod.Instance(MODID)
    public static SpartanFire INSTANCE;

    @SidedProxy(clientSide = "com.chaosbuffalo.spartanfire.proxy.ClientProxy", serverSide = "com.chaosbuffalo.spartanfire.proxy.CommonProxy")
    public static CommonProxy PROXY;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if (CompatLoadUtil.isRLCombatLoaded()) {
            MinecraftForge.EVENT_BUS.register(RLCombatCompat.class);
        }
        MinecraftForge.EVENT_BUS.register(PROXY);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        PROXY.init(event);
    }
}
