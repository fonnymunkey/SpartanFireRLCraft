package com.chaosbuffalo.spartanfire.mixin.spartanweaponry;

import com.chaosbuffalo.spartanfire.items.ItemDragonBolt;
import com.oblivioussp.spartanweaponry.item.ItemBolt;
import com.oblivioussp.spartanweaponry.item.ItemQuiverBolt;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemQuiverBolt.class)
public abstract class ItemQuiverBoltMixin {
    
    @Inject(
            method = "isAmmoValid",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void spartanFire_spartanWeaponryItemQuiverBolt_isAmmoValid(ItemStack ammo, ItemStack quiver, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(ammo.getItem() instanceof ItemBolt || ammo.getItem() instanceof ItemDragonBolt);
    }
}