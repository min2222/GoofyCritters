package com.min01.goofy.effect;

import com.min01.goofy.GoofyCritters;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GoofyEffects
{
	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, GoofyCritters.MODID);
	
	public static final RegistryObject<MobEffect> GAZE_OF_EYES = EFFECTS.register("gaze_of_eyes", () -> new GazeOfEyesEffect());
}
