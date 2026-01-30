package com.min01.goofy.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class GazeOfEyesEffect extends BasicGoofyEffect
{
	public GazeOfEyesEffect() 
	{
		super(MobEffectCategory.BENEFICIAL, 6751493);
	}

	@Override
	public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier)
	{
		pLivingEntity.resetFallDistance();
	}
}
