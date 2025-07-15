package com.chaosbuffalo.spartanfire.mixin.spartanweaponry;

import com.chaosbuffalo.spartanfire.entity.EntityDragonBolt;
import com.chaosbuffalo.spartanfire.items.ItemDragonBolt;
import com.github.alexthe666.iceandfire.entity.projectile.EntityDragonArrow;
import com.oblivioussp.spartanweaponry.init.SoundRegistry;
import com.oblivioussp.spartanweaponry.item.ItemCrossbow;
import com.oblivioussp.spartanweaponry.util.ConfigHandler;
import com.oblivioussp.spartanweaponry.util.NBTHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.chaosbuffalo.spartanfire.enums.EnumMaterial.*;
import static com.oblivioussp.spartanweaponry.item.ItemCrossbow.NBT_IS_LOADED;
import static com.oblivioussp.spartanweaponry.item.ItemCrossbow.nbtAmmoStack;

@Mixin(ItemCrossbow.class)
public abstract class ItemCrossbowMixin extends Item {

    @Shadow(remap = false)
    public abstract float getBoltSpeed();

    @Shadow(remap = false)
    public abstract int getAimTicks(ItemStack stack);

    @Inject(
            method = "onItemUseFinish",
            at = @At("HEAD"),
            cancellable = true)
    public void onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving, CallbackInfoReturnable<ItemStack> cir) {
        if (stack.getItem() != DRAGONBONE.crossbow
                && stack.getItem() != FIRE_DRAGONBONE.crossbow
                && stack.getItem() != ICE_DRAGONBONE.crossbow
                && stack.getItem() != LIGHTNING_DRAGONBONE.crossbow){
            return;
        }
        if (!NBTHelper.getBoolean(stack, NBT_IS_LOADED)){
            if(entityLiving instanceof EntityPlayer){
                EntityPlayer player = (EntityPlayer) entityLiving;
                ItemStack bolt = ItemDragonBolt.findDragonBoneBolt(player);
                if (bolt.isEmpty()){
                    return;
                }

                NBTTagCompound nbtBolt = new NBTTagCompound();
                bolt.copy().writeToNBT(nbtBolt);
                NBTHelper.setTagCompound(stack, nbtAmmoStack, nbtBolt);

                if (!player.capabilities.isCreativeMode){
                    bolt.shrink(1);
                    if (bolt.isEmpty()){
                        player.inventory.deleteStack(bolt);
                    }
                }

                worldIn.playSound(null, player.posX, player.posY, player.posZ, SoundRegistry.CROSSBOW_LOAD, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) * 0.5F);
                player.getCooldownTracker().setCooldown(this, ConfigHandler.crossbowTicksCooldown);
                NBTHelper.setBoolean(stack, NBT_IS_LOADED, true);

                cir.setReturnValue(stack);
            }
        }
    }

    @Inject(
            method = "onPlayerStoppedUsing",
            at = @At("HEAD"),
            cancellable = true)
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase living, int timeLeft, CallbackInfo ci) {
        if (stack.getItem() != DRAGONBONE.crossbow
                && stack.getItem() != FIRE_DRAGONBONE.crossbow
                && stack.getItem() != ICE_DRAGONBONE.crossbow
                && stack.getItem() != LIGHTNING_DRAGONBONE.crossbow){
            return;
        }
        if (living instanceof EntityPlayer) {
            NBTTagCompound tag = NBTHelper.getTagCompound(stack, nbtAmmoStack);
            if (tag == null){
                return;
            }
            ItemStack boltStack = new ItemStack(tag);

            EntityPlayer player = (EntityPlayer) living;

            int i = getMaxItemUseDuration(stack) - timeLeft;

            if (i < 0 || !NBTHelper.getBoolean(stack, NBT_IS_LOADED)) return;

            if (ItemDragonBolt.isDragonboneBolt(boltStack)){
                float vel = getBoltSpeed();

                ci.cancel();

                if (!worldIn.isRemote){
                    ItemDragonBolt item = (ItemDragonBolt) boltStack.getItem();

                    int aimTicks = getAimTicks(stack);
                    int inaccuracy = aimTicks - i;
                    float inaccuracyModifier = 0.0f;
                    if (i >= aimTicks){
                        inaccuracy = 0;
                    }

                    if (inaccuracy != 0){
                        inaccuracyModifier = 10.0f * ((float)inaccuracy / aimTicks);
                    }

                    EntityDragonBolt bolt = new EntityDragonBolt(worldIn, player);
                    if (item.getType() != EntityDragonArrow.Type.DEFAULT){
                        bolt.setType(item.getType());
                    } else if (stack.getItem() == FIRE_DRAGONBONE.crossbow){
                        bolt.setType(EntityDragonArrow.Type.FIRE);
                    } else if (stack.getItem() == ICE_DRAGONBONE.crossbow){
                        bolt.setType(EntityDragonArrow.Type.ICE);
                    } else if (stack.getItem() == LIGHTNING_DRAGONBONE.crossbow){
                        bolt.setType(EntityDragonArrow.Type.LIGHTNING);
                    }

                    bolt.setIsCritical(true);
                    Vec3d lookVec = player.getLook(1.0F);
                    Vec3d vector = new Vec3d(lookVec.x, lookVec.y, lookVec.z);

                    bolt.shoot(vector.x, vector.y, vector.z, this.getBoltSpeed() * 3.0F, inaccuracyModifier);

                    int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                    if (j > 0) {
                        bolt.setDamage(bolt.getDamage() + (double)j * 0.5 + 0.5);
                    }

                    int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
                    if (k > 0){
                        bolt.setKnockbackStrength(k);
                    }

                    if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0){
                        bolt.setFire(100);
                    }

                    if (player.capabilities.isCreativeMode){
                        bolt.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
                    }

                    player.world.spawnEntity(bolt);

                    int damage = boltStack.getCount() > 1 ? 3 : 1;
                    stack.damageItem(damage, player);

                    NBTHelper.setBoolean(stack, NBT_IS_LOADED, false);
                    NBTHelper.setTagCompound(stack, nbtAmmoStack, new NBTTagCompound());
                }

                worldIn.playSound(null, player.posX, player.posY, player.posZ, SoundRegistry.CROSSBOW_FIRE, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + vel * 0.5F);

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
        if (stack.getItem() != DRAGONBONE.crossbow
                && stack.getItem() != FIRE_DRAGONBONE.crossbow
                && stack.getItem() != ICE_DRAGONBONE.crossbow
                && stack.getItem() != LIGHTNING_DRAGONBONE.crossbow){
            return;
        }
        if(!ItemDragonBolt.findDragonBoneBolt(playerIn).isEmpty()){
            playerIn.setActiveHand(handIn);
            cir.setReturnValue(new ActionResult<>(EnumActionResult.SUCCESS, stack));
        }
    }
}
