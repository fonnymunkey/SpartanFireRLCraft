package com.chaosbuffalo.spartanfire.mixin.spartanweaponry;

import com.github.alexthe666.iceandfire.item.ItemDragonArrow;
import com.oblivioussp.spartanweaponry.client.model.ModelArrowQuiverHeavy;
import com.oblivioussp.spartanweaponry.client.model.ModelArrowQuiverLight;
import com.oblivioussp.spartanweaponry.client.model.ModelArrowQuiverMedium;
import com.oblivioussp.spartanweaponry.client.model.ModelQuiverBase;
import com.oblivioussp.spartanweaponry.item.ItemQuiverArrow;
import com.oblivioussp.spartanweaponry.item.ItemQuiverBase;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemQuiverArrow.class)
public class ItemQuiverArrowMixin extends ItemQuiverBase {

    public ItemQuiverArrowMixin(String unlocName, int inventorySize) {
        super(unlocName, inventorySize);
    }

    @Override
    public boolean isAmmoValid(ItemStack ammo, ItemStack quiver) {
        return ammo.getItem() instanceof ItemArrow || ammo.getItem() instanceof ItemDragonArrow;
    }

    @Override
    public ModelQuiverBase initModel() {
        if (arrowSlots == 6){
            return new ModelArrowQuiverMedium();
        } else if(arrowSlots == 9){
            return new ModelArrowQuiverHeavy();
        }
        return new ModelArrowQuiverLight();
    }

    @Override
    public ResourceLocation getTexture() {
        if (arrowSlots == 6){
            return ItemQuiverArrow.TextureQuiverMedium;
        } else if(arrowSlots == 9){
            return ItemQuiverArrow.TextureQuiverHeavy;
        }
        return ItemQuiverArrow.TextureQuiverLight;
    }
}
