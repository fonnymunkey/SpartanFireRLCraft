package com.chaosbuffalo.spartanfire.mixin.spartanweaponry;

import com.chaosbuffalo.spartanfire.items.ItemDragonBolt;
import com.oblivioussp.spartanweaponry.item.ItemBolt;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "com/oblivioussp/spartanweaponry/util/QuiverHelper$1")
public abstract class QuiverHelperBoltMixin {
	
	@Inject(
			method = "isAmmo",
			at = @At("HEAD"),
			cancellable = true,
			remap = false
	)
	private void spartanFire_spartanWeaponryQuiverHelperArrow_isAmmo(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(stack.getItem() instanceof ItemBolt || stack.getItem() instanceof ItemDragonBolt);
	}
}