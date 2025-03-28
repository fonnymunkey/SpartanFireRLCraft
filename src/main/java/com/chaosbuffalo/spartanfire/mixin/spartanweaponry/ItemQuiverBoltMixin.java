package com.chaosbuffalo.spartanfire.mixin.spartanweaponry;

import com.chaosbuffalo.spartanfire.items.ItemDragonBolt;
import com.oblivioussp.spartanweaponry.client.model.ModelBoltQuiverHeavy;
import com.oblivioussp.spartanweaponry.client.model.ModelBoltQuiverLight;
import com.oblivioussp.spartanweaponry.client.model.ModelBoltQuiverMedium;
import com.oblivioussp.spartanweaponry.client.model.ModelQuiverBase;
import com.oblivioussp.spartanweaponry.item.ItemBolt;
import com.oblivioussp.spartanweaponry.item.ItemQuiverBase;
import com.oblivioussp.spartanweaponry.item.ItemQuiverBolt;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ItemQuiverBolt.class)
public class ItemQuiverBoltMixin extends ItemQuiverBase {

    public ItemQuiverBoltMixin(String unlocName, int inventorySize) {
        super(unlocName, inventorySize);
    }

    @Override
    public boolean isAmmoValid(ItemStack ammo, ItemStack quiver) {
        return ammo.getItem() instanceof ItemBolt || ammo.getItem() instanceof ItemDragonBolt;
    }

    @Override
    public ModelQuiverBase initModel() {
        if (arrowSlots == 6){
            return new ModelBoltQuiverMedium();
        } else if(arrowSlots == 9){
            return new ModelBoltQuiverHeavy();
        }
        return new ModelBoltQuiverLight();
    }

    @Override
    public ResourceLocation getTexture() {
        if (arrowSlots == 6){
            return ItemQuiverBolt.TextureBoltQuiverMedium;
        } else if(arrowSlots == 9){
            return ItemQuiverBolt.TextureBoltQuiverHeavy;
        }
        return ItemQuiverBolt.TextureBoltQuiverLight;
    }
}
