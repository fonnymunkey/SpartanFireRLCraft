package com.chaosbuffalo.spartanfire.mixin.spartanweaponry;

import com.github.alexthe666.iceandfire.item.ItemDragonArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "com/oblivioussp/spartanweaponry/util/QuiverHelper$2")
public abstract class QuiverHelperArrowMixin {
	
	@Inject(
			method = "isAmmo",
			at = @At("HEAD"),
			cancellable = true,
			remap = false
	)
	private void spartanFire_spartanWeaponryQuiverHelperArrow_isAmmo(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(stack.getItem() instanceof ItemArrow || stack.getItem() instanceof ItemDragonArrow);
	}
}