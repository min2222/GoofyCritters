package com.min01.goofy.entity;

import com.min01.goofy.GoofyCritters;
import com.min01.goofy.entity.living.EntityGestalt;
import com.min01.goofy.entity.living.EntityGestaltHand;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GoofyEntities
{
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, GoofyCritters.MODID);

	public static final RegistryObject<EntityType<EntityCameraShake>> CAMERA_SHAKE = registerEntity("camera_shake", EntityType.Builder.<EntityCameraShake>of(EntityCameraShake::new, MobCategory.MISC).sized(0.0F, 0.0F));
	
	public static final RegistryObject<EntityType<EntityGestalt>> GESTALT = registerEntity("gestalt", createBuilder(EntityGestalt::new, MobCategory.CREATURE).sized(1.0F, 1.0F).fireImmune());
	public static final RegistryObject<EntityType<EntityGestaltHand>> GESTALT_HAND = registerEntity("gestalt_hand", createBuilder(EntityGestaltHand::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(100));

	public static <T extends Entity> EntityType.Builder<T> createBuilder(EntityType.EntityFactory<T> factory, MobCategory category)
	{
		return EntityType.Builder.<T>of(factory, category);
	}
	
	public static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String name, EntityType.Builder<T> builder) 
	{
		return ENTITY_TYPES.register(name, () -> builder.build(ResourceLocation.fromNamespaceAndPath(GoofyCritters.MODID, name).toString()));
	}
}
