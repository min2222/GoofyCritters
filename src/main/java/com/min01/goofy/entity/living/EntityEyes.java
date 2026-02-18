package com.min01.goofy.entity.living;

import com.min01.goofy.effect.GoofyEffects;
import com.min01.goofy.entity.AbstractAnimatableFlyingAnimal;
import com.min01.goofy.entity.AbstractFlyingAnimal;
import com.min01.goofy.misc.SmoothAnimationState;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class EntityEyes extends AbstractAnimatableFlyingAnimal
{
	public final SmoothAnimationState idleAnimationState = new SmoothAnimationState();
	
	public EntityEyes(EntityType<? extends AbstractFlyingAnimal> pEntityType, Level pLevel) 
	{
		super(pEntityType, pLevel);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 30.0F)
    			.add(Attributes.FLYING_SPEED, 0.35F)
    			.add(Attributes.MOVEMENT_SPEED, 0.35F)
    			.add(Attributes.FOLLOW_RANGE, 100.0F);
    }
    
    @Override
    public void tick()
    {
    	super.tick();
    	if(this.level.isClientSide)
    	{
    		this.idleAnimationState.updateWhen(true, this.tickCount);
    	}
    	if(this.getOwner() != null)
    	{
    		this.getOwner().addEffect(new MobEffectInstance(GoofyEffects.GAZE_OF_EYES.get(), 100, 0));
    		if(this.distanceTo(this.getOwner()) >= 6.0F)
    		{
    			this.getNavigation().moveTo(this.getOwner(), 1.25F);
    		}
    	}
    }
    
    @Override
    public float getRelativeSpeed()
    {
    	return 0.1F;
    }
    
    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand)
    {
    	ItemStack stack = pPlayer.getItemInHand(pHand);
    	if(!this.isTame() && stack.is(Items.SPIDER_EYE))
    	{
    		this.tame(pPlayer);
    		this.level.broadcastEntityEvent(this, (byte) 7);
    		return InteractionResult.SUCCESS;
    	}
    	return super.mobInteract(pPlayer, pHand);
    }
    
    @Override
    public boolean canMoveAround() 
    {
    	return super.canMoveAround() && !this.isTame();
    }

	@Override
	public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) 
	{
		return null;
	}
}
