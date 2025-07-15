package com.chaosbuffalo.spartanfire.mixin.spartanweaponry;

import com.chaosbuffalo.spartanfire.items.ItemDragonBolt;
import com.oblivioussp.spartanweaponry.inventory.SlotBolt;
import com.oblivioussp.spartanweaponry.item.ItemBolt;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SlotBolt.class)
public abstract class SlotBoltMixin extends SlotItemHandler {
    
    public SlotBoltMixin(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }
    
    @Inject(
            method = "isItemValid",
            at = @At("HEAD"),
            cancellable = true
    )
    private void spartanFire_spartanWeaponrySlotBolt_isItemValid(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue((stack.getItem() instanceof ItemBolt || stack.getItem() instanceof ItemDragonBolt) && super.isItemValid(stack));
    }
}