package com.chaosbuffalo.spartanfire.proxy;

import com.chaosbuffalo.spartanfire.client.render.RenderDragonBolt;
import com.chaosbuffalo.spartanfire.entity.EntityDragonBolt;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class ClientProxy extends CommonProxy
{
	@Override
	@SideOnly(Side.CLIENT)
	public void init(FMLInitializationEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(
				EntityDragonBolt.class,
				new RenderDragonBolt(
						Minecraft.getMinecraft().getRenderManager(),
						Minecraft.getMinecraft().getRenderItem()
				)
		);
	}
}
