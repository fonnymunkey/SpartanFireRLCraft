package com.chaosbuffalo.spartanfire.mixin.spartanweaponry;

import com.chaosbuffalo.spartanfire.items.ItemDragonBolt;
import com.oblivioussp.spartanweaponry.inventory.SlotBolt;
import com.oblivioussp.spartanweaponry.item.ItemBolt;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SlotBolt.class)
public class SlotBoltMixin extends SlotItemHandler {

    public SlotBoltMixin(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if (stack.getItem() instanceof ItemBolt || stack.getItem() instanceof ItemDragonBolt) {
            return super.isItemValid(stack);
        }
        return false;
    }
}
