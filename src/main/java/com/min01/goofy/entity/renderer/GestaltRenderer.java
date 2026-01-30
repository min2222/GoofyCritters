package com.min01.goofy.entity.renderer;

import com.min01.goofy.GoofyCritters;
import com.min01.goofy.entity.living.EntityGestalt;
import com.min01.goofy.entity.model.ModelGestalt;
import com.min01.goofy.entity.renderer.layer.GestaltRiderLayer;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GestaltRenderer extends MobRenderer<EntityGestalt, ModelGestalt>
{
	public GestaltRenderer(Context pContext)
	{
		super(pContext, new ModelGestalt(pContext.bakeLayer(ModelGestalt.LAYER_LOCATION)), 0.5F);
		this.addLayer(new GestaltRiderLayer(this));
	}
	
	@Override
	protected float getFlipDegrees(EntityGestalt pLivingEntity)
	{
		return 0;
	}

	@Override
	public ResourceLocation getTextureLocation(EntityGestalt pEntity)
	{
		return ResourceLocation.fromNamespaceAndPath(GoofyCritters.MODID, "textures/entity/gestalt.png");
	}
}
