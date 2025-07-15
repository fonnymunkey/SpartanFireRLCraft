package com.chaosbuffalo.spartanfire.mixin.spartanweaponry;

import com.github.alexthe666.iceandfire.entity.projectile.EntityDragonArrow;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.ItemDragonArrow;
import com.github.alexthe666.iceandfire.item.ItemDragonBow;
import com.oblivioussp.spartanweaponry.item.ItemLongbow;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArrow;
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
    
    @Shadow(remap = false) public abstract float getArrowSpeed(int charge);
    
    @Shadow(remap = false) public abstract int getDrawTicks();
    
    @Inject(
            method = "onPlayerStoppedUsing",
            at = @At("HEAD"),
            cancellable = true
    )
    private void spartanFire_spartanWeaponryItemLongbow_onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase living, int timeLeft, CallbackInfo ci) {
        if(living instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)living;
            boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
            boolean dragonbone = stack.getItem() == DRAGONBONE.longbow
                    || stack.getItem() == FIRE_DRAGONBONE.longbow
                    || stack.getItem() == ICE_DRAGONBONE.longbow
                    || stack.getItem() == LIGHTNING_DRAGONBONE.longbow;
            ItemStack arrowStack = ItemStack.EMPTY;
            if(dragonbone) arrowStack = ItemDragonBow.findDragonBoneArrow(player);
            if(arrowStack.isEmpty()) arrowStack = this.findAmmo(player);
            int i = this.getMaxItemUseDuration(stack) - timeLeft;
            i = ForgeEventFactory.onArrowLoose(stack, worldIn, player, i, !arrowStack.isEmpty() || flag);
            if(i < 0) {
                ci.cancel();
                return;
            }
            
            if(!arrowStack.isEmpty() || flag) {
                if(arrowStack.isEmpty()) arrowStack = new ItemStack(Items.ARROW);
                
                float f = this.getArrowSpeed(i);
                if(f >= 0.1F) {
                    boolean flag1 = player.capabilities.isCreativeMode || arrowStack.getItem() instanceof ItemArrow && ((ItemArrow)arrowStack.getItem()).isInfinite(arrowStack, stack, player);
                    if(!worldIn.isRemote) {
                        EntityArrow entityArrow;
                        if(arrowStack.getItem() == IafItemRegistry.dragonbone_arrow
                                || arrowStack.getItem() == IafItemRegistry.dragonbone_arrow_fire
                                || arrowStack.getItem() == IafItemRegistry.dragonbone_arrow_ice
                                || arrowStack.getItem() == IafItemRegistry.dragonbone_arrow_lightning) {
                            ItemDragonArrow item = (ItemDragonArrow)arrowStack.getItem();
                            entityArrow = new EntityDragonArrow(worldIn, player);
                            if(item.getType() != EntityDragonArrow.Type.DEFAULT) {
                                ((EntityDragonArrow)entityArrow).setType(item.getType());
                            }
                            else if(stack.getItem() == FIRE_DRAGONBONE.longbow) {
                                ((EntityDragonArrow)entityArrow).setType(EntityDragonArrow.Type.FIRE);
                            }
                            else if(stack.getItem() == ICE_DRAGONBONE.longbow) {
                                ((EntityDragonArrow)entityArrow).setType(EntityDragonArrow.Type.ICE);
                            }
                            else if(stack.getItem() == LIGHTNING_DRAGONBONE.longbow) {
                                ((EntityDragonArrow)entityArrow).setType(EntityDragonArrow.Type.LIGHTNING);
                            }
                        }
                        else {
                            ItemArrow itemarrow = (ItemArrow)((ItemArrow)(arrowStack.getItem() instanceof ItemArrow ? arrowStack.getItem() : Items.ARROW));
                            entityArrow = itemarrow.createArrow(worldIn, arrowStack, player);
                        }
                        entityArrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, f * 3.0F, 0.5F);
                        if(i >= this.getDrawTicks()) {
                            entityArrow.setIsCritical(true);
                        }
                        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                        if(j > 0) {
                            entityArrow.setDamage(entityArrow.getDamage() + (double)j * 0.5 + 0.5);
                        }
                        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
                        if(k > 0) {
                            entityArrow.setKnockbackStrength(k);
                        }
                        if(EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
                            entityArrow.setFire(100);
                        }
                        stack.damageItem(1, player);
                        if(flag1) {
                            entityArrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
                        }
                        worldIn.spawnEntity(entityArrow);
                    }
                    worldIn.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if(!flag1) {
                        arrowStack.shrink(1);
                        if(arrowStack.isEmpty()) {
                            player.inventory.deleteStack(arrowStack);
                        }
                    }
                    player.addStat(StatList.getObjectUseStats(this));
                }
            }
        }
        ci.cancel();
    }


    @Inject(
            method = "onItemRightClick",
            at = @At("HEAD"),
            cancellable = true
    )
    private void spartanFire_spartanWeaponryItemLongbow_onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn, CallbackInfoReturnable<ActionResult<ItemStack>> cir) {
        ItemStack bow = playerIn.getHeldItem(handIn);
        boolean dragonbone = bow.getItem() == DRAGONBONE.longbow
                || bow.getItem() == FIRE_DRAGONBONE.longbow
                || bow.getItem() == ICE_DRAGONBONE.longbow
                || bow.getItem() == LIGHTNING_DRAGONBONE.longbow;
        ItemStack arrow = ItemStack.EMPTY;
        if(dragonbone) arrow = ItemDragonBow.findDragonBoneArrow(playerIn);
        if(arrow.isEmpty()) arrow = this.findAmmo(playerIn);
        boolean flag = !arrow.isEmpty() || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, bow) > 0;
        
        ActionResult<ItemStack> ret = ForgeEventFactory.onArrowNock(bow, worldIn, playerIn, handIn, flag);
        if(ret != null) cir.setReturnValue(ret);
        else if(flag || playerIn.capabilities.isCreativeMode) {
            playerIn.setActiveHand(handIn);
            cir.setReturnValue(new ActionResult<>(EnumActionResult.SUCCESS, bow));
        }
        else cir.setReturnValue(new ActionResult<>(EnumActionResult.FAIL, bow));
    }
}