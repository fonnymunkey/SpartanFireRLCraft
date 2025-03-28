package com.chaosbuffalo.spartanfire.mixin.spartanweaponry;

import com.github.alexthe666.iceandfire.item.ItemDragonArrow;
import com.oblivioussp.spartanweaponry.inventory.SlotArrow;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SlotArrow.class)
public class SlotArrowMixin extends SlotItemHandler {

    public SlotArrowMixin(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        if (stack.getItem() instanceof ItemArrow || stack.getItem() instanceof ItemDragonArrow) {
            return super.isItemValid(stack);
        }
        return false;
    }
}
