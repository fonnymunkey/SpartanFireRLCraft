package com.chaosbuffalo.spartanfire.mixin.spartanweaponry;

import com.github.alexthe666.iceandfire.item.ItemDragonArrow;
import com.oblivioussp.spartanweaponry.item.ItemQuiverArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemQuiverArrow.class)
public abstract class ItemQuiverArrowMixin {

    @Inject(
            method = "isAmmoValid",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private void spartanFire_spartanWeaponryItemQuiverArrow_isAmmoValid(ItemStack ammo, ItemStack quiver, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(ammo.getItem() instanceof ItemArrow || ammo.getItem() instanceof ItemDragonArrow);
    }
}