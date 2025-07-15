package com.chaosbuffalo.spartanfire.client.render;

import com.chaosbuffalo.spartanfire.entity.EntityDragonBolt;
import com.chaosbuffalo.spartanfire.init.ItemRegistrySFire;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDragonBolt extends Render<EntityDragonBolt> {
	
	private final RenderItem itemRenderer;

	public RenderDragonBolt(RenderManager render, RenderItem itemRenderer) {
		super(render);
		this.itemRenderer = itemRenderer;
	}

	public void doRender(EntityDragonBolt entity, double x, double y, double z, float yaw, float partialTicks) {
		GlStateManager.pushMatrix();
		double posX = x;
		double posY = y;
		double posZ = z;
		if (entity.isAirBorne) {
			posX = x + entity.motionX * (double)partialTicks;
			posY = y + entity.motionY * (double)partialTicks;
			posZ = z + entity.motionZ * (double)partialTicks;
		}

		GlStateManager.translate((float)posX, (float)posY, (float)posZ);
		GlStateManager.scale(1.5, 1.5, 1.5);
		GlStateManager.enableRescaleNormal();
		this.doRenderTransformations(entity, partialTicks);
		this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		if (this.renderOutlines) {
			GlStateManager.enableColorMaterial();
			GlStateManager.enableOutlineMode(this.getTeamColor(entity));
		}

		Item bolt = getBolt(entity);
		ItemStack stack = new ItemStack(bolt);
		this.itemRenderer.renderItem(stack, TransformType.GROUND);
		GlStateManager.translate(0.1, 0.2, 0.0);
		GlStateManager.rotate(90.0F, 1.0F, 1.0F, 0.0F);
		GlStateManager.translate(-0.1, -0.2, 0.0);
		this.itemRenderer.renderItem(stack, TransformType.GROUND);
		if (this.renderOutlines) {
			GlStateManager.disableOutlineMode();
			GlStateManager.disableColorMaterial();
		}

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, yaw, partialTicks);
	}

	protected void doRenderTransformations(EntityDragonBolt entity, float partialTicks) {
		GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks - 45.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.translate(-0.1, -0.2, 0.0);
	}

	protected ResourceLocation getEntityTexture(EntityDragonBolt arrow) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

	private Item getBolt(EntityDragonBolt entity) {
		switch (entity.getType()) {
			case FIRE:
				return ItemRegistrySFire.dragonbone_bolt_fire;
			case ICE:
				return ItemRegistrySFire.dragonbone_bolt_ice;
			case LIGHTNING:
				return ItemRegistrySFire.dragonbone_bolt_lightning;
			default:
				return ItemRegistrySFire.dragonbone_bolt;
		}
	}
}