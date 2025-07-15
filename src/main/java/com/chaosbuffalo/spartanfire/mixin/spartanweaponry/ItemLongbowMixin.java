package com.chaosbuffalo.spartanfire.mixin.spartanweaponry;

import com.github.alexthe666.iceandfire.entity.projectile.EntityDragonArrow;
import com.github.alexthe666.iceandfire.item.ItemDragonArrow;
import com.github.alexthe666.iceandfire.item.ItemDragonBow;
import com.oblivioussp.spartanweaponry.item.ItemLongbow;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.chaosbuffalo.spartanfire.enums.EnumMaterial.*;

@Mixin(ItemLongbow.class)
public abstract class ItemLongbowMixin extends ItemBow {

    @Shadow(remap = false)
    public abstract float getArrowSpeed(int charge);

    @Shadow(remap = false)
    public abstract int getDrawTicks();

    @Inject(
            method = "onPlayerStoppedUsing",
            at = @At("HEAD"),
            cancellable = true)
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase living, int timeLeft, CallbackInfo ci) {
        if (stack.getItem() != DRAGONBONE.longbow
                && stack.getItem() != FIRE_DRAGONBONE.longbow
                && stack.getItem() != ICE_DRAGONBONE.longbow
                && stack.getItem() != LIGHTNING_DRAGONBONE.longbow){
            return;
        }
        if (living instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) living;
            ItemStack arrowStack = ItemDragonBow.findDragonBoneArrow(player);
            if (arrowStack.isEmpty()) {
                return;
            }

            ci.cancel();

            int i = this.getMaxItemUseDuration(stack) - timeLeft;
            i = ForgeEventFactory.onArrowLoose(stack, worldIn, player, i, true);
            if (i < 0) {
                return;
            }
            float f = this.getArrowSpeed(i);
            if (f >= 0.1F){
                if (!worldIn.isRemote){
                    ItemDragonArrow item = (ItemDragonArrow) arrowStack.getItem();
                    EntityDragonArrow arrow = new EntityDragonArrow(worldIn, player);
                    if (item.getType() != EntityDragonArrow.Type.DEFAULT){
                      arrow.setType(item.getType());
                    } else if (stack.getItem() == FIRE_DRAGONBONE.longbow){
                        arrow.setType(EntityDragonArrow.Type.FIRE);
                    } else if (stack.getItem() == ICE_DRAGONBONE.longbow){
                        arrow.setType(EntityDragonArrow.Type.ICE);
                    } else if (stack.getItem() == LIGHTNING_DRAGONBONE.longbow){
                        arrow.setType(EntityDragonArrow.Type.LIGHTNING);
                    }
                    arrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, f * 3.0F, 0.5F);
                    if (i >= this.getDrawTicks()) {
                        arrow.setIsCritical(true);
                    }

                    int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                    if (j > 0) {
                        arrow.setDamage(arrow.getDamage() + (double) j * 0.5 + 0.5);
                    }

                    int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
                    if (k > 0) {
                        arrow.setKnockbackStrength(k);
                    }

                    if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
                        arrow.setFire(100);
                    }

                    stack.damageItem(1, player);
                    if (player.capabilities.isCreativeMode){
                        arrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
                    }

                    worldIn.spawnEntity(arrow);
                }
                worldIn.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                if (!player.capabilities.isCreativeMode){
                    arrowStack.shrink(1);
                    if (arrowStack.isEmpty()){
                        player.inventory.deleteStack(arrowStack);
                    }
                }
                player.addStat(StatList.getObjectUseStats(this));
            }
        }
    }


    @Inject(
            method = "onItemRightClick",
            at = @At("HEAD"),
            cancellable = true)
    public void onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn, CallbackInfoReturnable<ActionResult<ItemStack>> cir) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (stack.getItem() != DRAGONBONE.longbow
                && stack.getItem() != FIRE_DRAGONBONE.longbow
                && stack.getItem() != ICE_DRAGONBONE.longbow
                && stack.getItem() != LIGHTNING_DRAGONBONE.longbow){
            return;
        }
        if (!ItemDragonBow.findDragonBoneArrow(playerIn).isEmpty()){
            playerIn.setActiveHand(handIn);
            cir.setReturnValue(new ActionResult<>(EnumActionResult.SUCCESS, stack));
        }
    }
}
