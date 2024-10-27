package com.chaosbuffalo.spartanfire.integrations;

import net.minecraftforge.fml.common.Loader;

public abstract class CompatLoadUtil {

    private static final String RLCOMBAT_MODID = "bettercombatmod";
    private static Boolean rlcombatLoaded;

    public static boolean isRLCombatLoaded() {
        if(rlcombatLoaded == null) rlcombatLoaded = Loader.isModLoaded(RLCOMBAT_MODID) && isRLCombatCorrectVersion();
        return rlcombatLoaded;
    }

    //RLCombat is 2.x.x, BetterCombat is 1.x.x
    private static boolean isRLCombatCorrectVersion() {
        String[] arrOfStr = Loader.instance().getIndexedModList().get(RLCOMBAT_MODID).getVersion().split("\\.");
        try {
            int i = Integer.parseInt(String.valueOf(arrOfStr[0]));
            if(i == 2) return true;
        }
        catch(Exception ignored) { }
        return false;
    }
}
