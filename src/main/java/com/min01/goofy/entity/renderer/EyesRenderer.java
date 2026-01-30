package com.min01.goofy.entity.renderer;

import com.min01.goofy.GoofyCritters;
import com.min01.goofy.entity.living.EntityEyes;
import com.min01.goofy.entity.model.ModelEyes;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class EyesRenderer extends MobRenderer<EntityEyes, ModelEyes>
{
	public EyesRenderer(Context pContext) 
	{
		super(pContext, new ModelEyes(pContext.bakeLayer(ModelEyes.LAYER_LOCATION)), 0.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(EntityEyes pEntity)
	{
		return ResourceLocation.fromNamespaceAndPath(GoofyCritters.MODID, "textures/entity/eyes.png");
	}
}
