package com.min01.goofy.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class BasicGoofyEffect extends MobEffect
{
	protected BasicGoofyEffect(MobEffectCategory pCategory, int pColor) 
	{
		super(pCategory, pColor);
	}

	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) 
	{
		return duration > 0;
	}
}
