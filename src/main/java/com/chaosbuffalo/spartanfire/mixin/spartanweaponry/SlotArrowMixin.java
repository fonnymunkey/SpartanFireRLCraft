package com.chaosbuffalo.spartanfire.mixin.spartanweaponry;

import com.github.alexthe666.iceandfire.item.ItemDragonArrow;
import com.oblivioussp.spartanweaponry.inventory.SlotArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SlotArrow.class)
public abstract class SlotArrowMixin extends SlotItemHandler {
    
    public SlotArrowMixin(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }
    
    @Inject(
            method = "isItemValid",
            at = @At("HEAD"),
            cancellable = true
    )
    private void spartanFire_spartanWeaponrySlotArrow_isItemValid(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue((stack.getItem() instanceof ItemArrow || stack.getItem() instanceof ItemDragonArrow) && super.isItemValid(stack));
    }
}