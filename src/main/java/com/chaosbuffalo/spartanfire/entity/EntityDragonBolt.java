package com.chaosbuffalo.spartanfire.entity;

import com.chaosbuffalo.spartanfire.init.ItemRegistrySFire;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.api.ChainLightningUtils;
import com.github.alexthe666.iceandfire.api.IEntityEffectCapability;
import com.github.alexthe666.iceandfire.api.InFCapabilities;
import com.github.alexthe666.iceandfire.entity.EntityFireDragon;
import com.github.alexthe666.iceandfire.entity.EntityIceDragon;
import com.github.alexthe666.iceandfire.entity.projectile.EntityDragonArrow;
import com.github.alexthe666.iceandfire.enums.EnumParticle;
import com.oblivioussp.spartanweaponry.entity.projectile.EntityBolt;
import com.oblivioussp.spartanweaponry.init.ItemRegistrySW;
import com.oblivioussp.spartanweaponry.item.ItemBolt;
import com.oblivioussp.spartanweaponry.util.EntityDamageSourceIndirectArmorPiercing;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IThrowableEntity;

public class EntityDragonBolt extends EntityBolt
{
    private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityDragonBolt.class, DataSerializers.VARINT);

	public EntityDragonBolt(World worldIn) {
        super(worldIn);
        this.setDamage(8);
    }

    public EntityDragonBolt(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        this.setDamage(8);
    }

    public EntityDragonBolt(World worldIn, EntityLivingBase shooter)
    {
        super(worldIn, shooter);
        this.setDamage(8);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(TYPE, EntityDragonArrow.Type.DEFAULT.ordinal());
    }

    public void setType(EntityDragonArrow.Type type) {
        this.getDataManager().set(TYPE, type.ordinal());
        this.getDataManager().setDirty(TYPE);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        EnumParticle particle = getType().getParticle();
        if (particle != null) {
            if (world.isRemote && !this.inGround) {
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                double d2 = this.rand.nextGaussian() * 0.02D;
                double xRatio = motionX * height;
                double zRatio = motionZ * height;
                IceAndFire.PROXY.spawnParticle(particle, world, this.posX + xRatio + (double) (this.rand.nextFloat() * this.width * 1.0F) - (double) this.width - d0 * 10.0D, this.posY + (double) (this.rand.nextFloat() * this.height) - d1 * 10.0D, this.posZ + zRatio + (double) (this.rand.nextFloat() * this.width * 1.0F) - (double) this.width - d2 * 10.0D, d0, d1, d2);
            }
        }
    }

    public EntityDragonArrow.Type getType() {
        int ordinal = this.getDataManager().get(TYPE);
        return EntityDragonArrow.Type.values()[ordinal];
    }

    @Override
    protected void arrowHit(EntityLivingBase living) {
        switch (getType()) {
            case FIRE:
                if (living instanceof EntityIceDragon) {
                    living.attackEntityFrom(DamageSource.IN_FIRE, 13.5F);
                }
                living.setFire(5);
                break;
            case ICE:
                if (living instanceof EntityFireDragon) {
                    living.attackEntityFrom(DamageSource.DROWN, 13.5F);
                }
                if (!living.world.isRemote) {
                    IEntityEffectCapability capability = InFCapabilities.getEntityEffectCapability(living);
                    if (capability != null) capability.setFrozen(200);
                }
                living.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 2));
                living.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 100, 2));
                break;
            case LIGHTNING:
                if (living instanceof EntityFireDragon || living instanceof EntityIceDragon) {
                    living.attackEntityFrom(DamageSource.LIGHTNING_BOLT, 6.75F);
                }
                ChainLightningUtils.createChainLightningFromTarget(this.world, living, this.shootingEntity);
        }
    }

	@Override
	protected ItemStack getArrowStack()
	{
		return new ItemStack(ItemRegistrySFire.dragonbone_bolt);
	}

	@Override
	public Entity getThrower() 
	{
		return this.shootingEntity;
	}

	@Override
	public void setThrower(Entity entity)
	{
		this.shootingEntity = entity;
	}

    @Override
    public float getRangeMultiplier()
    {
    	return 1.25F;
    }

    @Override
    public float getArmorPiercingFactor()
    {
    	return 0.50F;
    }
}
