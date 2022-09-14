package com.chaosbuffalo.spartanfire.init;

import com.chaosbuffalo.spartanfire.SpartanFire;
import com.oblivioussp.spartanweaponry.api.SpartanWeaponryAPI;
import net.minecraft.item.Item;


public class ModelRenderRegistrySFire {


    public static void addItemToRegistry(Item item, String modelLoc) {
        SpartanWeaponryAPI.addItemModelToRegistry(
                item, SpartanFire.MODID,
                modelLoc);
    }

}


