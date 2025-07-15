package com.chaosbuffalo.spartanfire.mixin.spartanweaponry;

import com.chaosbuffalo.spartanfire.entity.EntityDragonBolt;
import com.chaosbuffalo.spartanfire.items.ItemDragonBolt;
import com.github.alexthe666.iceandfire.entity.projectile.EntityDragonArrow;
import com.oblivioussp.spartanweaponry.init.EnchantmentRegistrySW;
import com.oblivioussp.spartanweaponry.init.ItemRegistrySW;
import com.oblivioussp.spartanweaponry.init.SoundRegistry;
import com.oblivioussp.spartanweaponry.item.ItemBolt;
import com.oblivioussp.spartanweaponry.item.ItemCrossbow;
import com.oblivioussp.spartanweaponry.item.ItemSW;
import com.oblivioussp.spartanweaponry.util.ConfigHandler;
import com.oblivioussp.spartanweaponry.util.NBTHelper;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Enchantments;
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
public abstract class ItemCrossbowMixin extends ItemSW {
    
    @Shadow(remap = false) protected abstract ItemStack findAmmo(EntityPlayer player);
    
    @Shadow(remap = false) public abstract float getBoltSpeed();
    
    @Shadow(remap = false) public abstract int getAimTicks(ItemStack stack);
    
    @Shadow(remap = false) protected abstract void spawnProjectile(ItemStack crossbow, ItemBolt boltItem, ItemStack boltStack, World world, EntityPlayer player, boolean noPickup, float inaccuracyModifier, float projectileAngle);
    
    public ItemCrossbowMixin(String unlocName) {
        super(unlocName);
    }
    
    @Inject(
            method = "onItemUseFinish",
            at = @At("HEAD"),
            cancellable = true
    )
    private void spartanFire_spartanWeaponryItemCrossbow_onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving, CallbackInfoReturnable<ItemStack> cir) {
        if(!NBTHelper.getBoolean(stack, NBT_IS_LOADED) && entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entityLiving;
            boolean dragonbone = stack.getItem() == DRAGONBONE.crossbow
                    || stack.getItem() == FIRE_DRAGONBONE.crossbow
                    || stack.getItem() == ICE_DRAGONBONE.crossbow
                    || stack.getItem() == LIGHTNING_DRAGONBONE.crossbow;
            
            ItemStack bolt = ItemStack.EMPTY;
            if(dragonbone) bolt = ItemDragonBolt.findDragonBoneBolt(player);
            if(bolt.isEmpty()) bolt = this.findAmmo(player);
            if(bolt.isEmpty()) bolt = new ItemStack(ItemRegistrySW.bolt);
            
            boolean isCreativeOrArrowInfinite = player.capabilities.isCreativeMode || bolt.getItem() instanceof ItemBolt && ((ItemBolt)bolt.getItem()).isInfinite(bolt, stack, player);
            int count = !dragonbone && EnchantmentHelper.getEnchantmentLevel(EnchantmentRegistrySW.CROSSBOW_SPREADSHOT, stack) > 0 ? 3 : 1;
            
            ItemStack boltToStore = bolt.copy();
            boltToStore.setCount(count);
            NBTTagCompound nbtBolt = new NBTTagCompound();
            boltToStore.writeToNBT(nbtBolt);
            NBTHelper.setTagCompound(stack, nbtAmmoStack, nbtBolt);
            if(!isCreativeOrArrowInfinite) {
                bolt.shrink(1);
                if(bolt.isEmpty()) {
                    player.inventory.deleteStack(bolt);
                }
            }
            worldIn.playSound(null, player.posX, player.posY, player.posZ, SoundRegistry.CROSSBOW_LOAD, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) * 0.5F);
            player.getCooldownTracker().setCooldown(this, ConfigHandler.crossbowTicksCooldown);
            NBTHelper.setBoolean(stack, NBT_IS_LOADED, true);
        }
        cir.setReturnValue(stack);
    }

    @Inject(
            method = "onPlayerStoppedUsing",
            at = @At("HEAD"),
            cancellable = true
    )
    private void spartanFire_spartanWeaponryItemCrossbow_onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase living, int timeLeft, CallbackInfo ci) {
        if(stack.getItem() != DRAGONBONE.crossbow
                && stack.getItem() != FIRE_DRAGONBONE.crossbow
                && stack.getItem() != ICE_DRAGONBONE.crossbow
                && stack.getItem() != LIGHTNING_DRAGONBONE.crossbow) {
            return;
        }
        if(living instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)living;
            boolean flag = player.capabilities.isCreativeMode;
            ItemStack boltStack = ItemStack.EMPTY;
            NBTTagCompound tag = NBTHelper.getTagCompound(stack, nbtAmmoStack);
            if(tag != null) boltStack = new ItemStack(tag);

            int i = getMaxItemUseDuration(stack) - timeLeft;
            if(i < 0 || !NBTHelper.getBoolean(stack, NBT_IS_LOADED)) {
                ci.cancel();
                return;
            }
            
            if(!boltStack.isEmpty() || flag) {
                if(boltStack.isEmpty()) boltStack = new ItemStack(ItemRegistrySW.bolt);
                float vel = this.getBoltSpeed();
                boolean flag1 = player.capabilities.isCreativeMode || boltStack.getItem() instanceof ItemBolt && ((ItemBolt)boltStack.getItem()).isInfinite(boltStack, stack, player);
                if(!worldIn.isRemote) {
                    int aimTicks = this.getAimTicks(stack);
                    int inaccuracy = aimTicks - i;
                    float inaccuracyModifier = 0.0f;
                    if(i >= aimTicks) {
                        inaccuracy = 0;
                    }
                    
                    if(inaccuracy != 0) {
                        inaccuracyModifier = 10.0f * ((float)inaccuracy / aimTicks);
                    }
                    
                    if(ItemDragonBolt.isDragonboneBolt(boltStack)) {
                        ItemDragonBolt item = (ItemDragonBolt)boltStack.getItem();
                        EntityDragonBolt bolt = new EntityDragonBolt(worldIn, player);
                        if(item.getType() != EntityDragonArrow.Type.DEFAULT) {
                            bolt.setType(item.getType());
                        }
                        else if(stack.getItem() == FIRE_DRAGONBONE.crossbow) {
                            bolt.setType(EntityDragonArrow.Type.FIRE);
                        }
                        else if(stack.getItem() == ICE_DRAGONBONE.crossbow) {
                            bolt.setType(EntityDragonArrow.Type.ICE);
                        }
                        else if(stack.getItem() == LIGHTNING_DRAGONBONE.crossbow) {
                            bolt.setType(EntityDragonArrow.Type.LIGHTNING);
                        }
                        bolt.setIsCritical(true);
                        Vec3d lookVec = player.getLook(1.0F);
                        Vec3d vector = new Vec3d(lookVec.x, lookVec.y, lookVec.z);
                        bolt.shoot(vector.x, vector.y, vector.z, this.getBoltSpeed() * 3.0F, inaccuracyModifier);
                        int j = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
                        if(j > 0) {
                            bolt.setDamage(bolt.getDamage() + (double)j * 0.5 + 0.5);
                        }
                        
                        int k = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
                        if(k > 0) {
                            bolt.setKnockbackStrength(k);
                        }
                        
                        if(EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
                            bolt.setFire(100);
                        }
                        
                        if(player.capabilities.isCreativeMode) {
                            bolt.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY;
                        }
                        
                        player.world.spawnEntity(bolt);
                    }
                    else {
                        ItemBolt itemBolt = (ItemBolt)(boltStack.getItem() instanceof ItemBolt ? boltStack.getItem() : ItemRegistrySW.bolt);
                        this.spawnProjectile(stack, itemBolt, boltStack, worldIn, player, flag1, inaccuracyModifier, 0.0F);
                        if(boltStack.getCount() > 1) {
                            this.spawnProjectile(stack, itemBolt, boltStack, worldIn, player, true, inaccuracyModifier, -10.0F);
                            this.spawnProjectile(stack, itemBolt, boltStack, worldIn, player, true, inaccuracyModifier, 10.0F);
                        }
                    }
                    int damage = boltStack.getCount() > 1 ? 3 : 1;
                    stack.damageItem(damage, player);
                    NBTHelper.setBoolean(stack, NBT_IS_LOADED, false);
                    NBTHelper.setTagCompound(stack, nbtAmmoStack, new NBTTagCompound());
                }
                worldIn.playSound(null, player.posX, player.posY, player.posZ, SoundRegistry.CROSSBOW_FIRE, SoundCategory.NEUTRAL, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + vel * 0.5F);
                player.addStat(StatList.getObjectUseStats(this));
            }
        }
        ci.cancel();
    }

    @Inject(
            method = "onItemRightClick",
            at = @At("HEAD"),
            cancellable = true
    )
    private void spartanFire_spartanWeaponryItemCrossbow_onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn, CallbackInfoReturnable<ActionResult<ItemStack>> cir) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        boolean dragonbone = stack.getItem() == DRAGONBONE.crossbow
                || stack.getItem() == FIRE_DRAGONBONE.crossbow
                || stack.getItem() == ICE_DRAGONBONE.crossbow
                || stack.getItem() == LIGHTNING_DRAGONBONE.crossbow;
        ItemStack bolt = ItemStack.EMPTY;
        if(dragonbone) bolt = ItemDragonBolt.findDragonBoneBolt(playerIn);
        if(bolt.isEmpty()) bolt = this.findAmmo(playerIn);
        boolean flag = !bolt.isEmpty();
        if(flag || playerIn.capabilities.isCreativeMode || NBTHelper.getBoolean(stack, "isLoaded") || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0) {
            playerIn.setActiveHand(handIn);
            cir.setReturnValue(new ActionResult<>(EnumActionResult.SUCCESS, stack));
        }
        else cir.setReturnValue(new ActionResult<>(EnumActionResult.FAIL, stack));
    }
}