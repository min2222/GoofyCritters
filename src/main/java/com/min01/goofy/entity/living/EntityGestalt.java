package com.min01.goofy.entity.living;

import com.min01.goofy.entity.AbstractAnimatableTamableAnimal;
import com.min01.goofy.item.GoofyItems;
import com.min01.goofy.misc.GestaltController;
import com.min01.goofy.util.GoofyUtil;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class EntityGestalt extends AbstractAnimatableTamableAnimal implements Bucketable
{
	public final GestaltController controller;
	
	public EntityGestalt(EntityType<? extends TamableAnimal> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
		this.setNoGravity(true);
		this.controller = new GestaltController(this);
	}
	
    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes()
    			.add(Attributes.MAX_HEALTH, 777.0F)
    			.add(Attributes.ARMOR, 10.0F)
    			.add(Attributes.ARMOR_TOUGHNESS, 10.0F)
    			.add(Attributes.KNOCKBACK_RESISTANCE, 100.0F)
    			.add(Attributes.MOVEMENT_SPEED, 0.0F)
    			.add(Attributes.FOLLOW_RANGE, 100.0F);
    }
    
    @Override
    public void registerDefaultGoals() 
    {
    	
    }
    
    @Override
    public void tick() 
    {
    	super.tick();
    	this.resetFallDistance();
    	this.controller.tick();
    	this.heal(5.0F);
    	
    	if(this.getFirstPassenger() instanceof Player player)
    	{
    		if(player.xxa != 0 || player.zza != 0)
    		{
        		this.addDeltaMovement(new Vec3(0.0F, 0.01F, 0.0F));
    			this.setXRot(GoofyUtil.rotlerp(this.getXRot(), player.getXRot(), 15));
    			this.setYRot(GoofyUtil.rotlerp(this.getYRot(), player.yHeadRot, 15));
    			this.setYBodyRot(GoofyUtil.rotlerp(this.getYRot(), player.yHeadRot, 15));
    			this.setYHeadRot(GoofyUtil.rotlerp(this.getYRot(), player.yHeadRot, 15));
        		Vec3 target = GoofyUtil.getLookPos(new Vec2(player.getXRot(), player.yHeadRot), player.position(), player.xxa * 50, 0, player.zza * 50);
        		this.controller.setTarget(target);
    		}
    	}
    }
    
    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand)
    {
    	ItemStack stack = pPlayer.getItemInHand(pHand);
    	if(!this.isTame() && stack.is(Items.WITHER_SKELETON_SKULL))
    	{
    		this.tame(pPlayer);
    		this.level.broadcastEntityEvent(this, (byte) 7);
    		return InteractionResult.SUCCESS;
    	}
    	if(this.isOwnedBy(pPlayer) && this.isAlive())
    	{
    		if(stack.is(Items.BUCKET))
    		{
    			this.playSound(this.getPickupSound(), 1.0F, 1.0F);
    			ItemStack itemstack1 = this.getBucketItemStack();
    			this.saveToBucketTag(itemstack1);
    			ItemStack itemstack2 = ItemUtils.createFilledResult(stack, pPlayer, itemstack1, false);
    			pPlayer.setItemInHand(pHand, itemstack2);
    			if(!this.level.isClientSide)
    			{
    				CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer)pPlayer, itemstack1);
    			}
    			this.discard();
    			return InteractionResult.sidedSuccess(level.isClientSide);
    		}
    		else
    		{
        		pPlayer.startRiding(this);
    		}
    	}
    	return super.mobInteract(pPlayer, pHand);
    }
    
    @Override
    protected BodyRotationControl createBodyControl() 
    {
    	return new GestaltBodyRotationControl(this);
    }

	@Override
	public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent)
	{
		return null;
	}

	@Override
	public boolean fromBucket() 
	{
		return false;
	}

	@Override
	public void setFromBucket(boolean pFromBucket)
	{
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void saveToBucketTag(ItemStack pStack) 
	{
    	Bucketable.saveDefaultDataToBucketTag(this, pStack);
    	this.addAdditionalSaveData(pStack.getOrCreateTag());
	}

	@SuppressWarnings("deprecation")
	@Override
	public void loadFromBucketTag(CompoundTag pTag)
	{
    	Bucketable.loadDefaultDataFromBucketTag(this, pTag);
    	this.readAdditionalSaveData(pTag);
	}

	@Override
	public ItemStack getBucketItemStack() 
	{
		return GoofyItems.BUCKET_OF_GESTALT.get().getDefaultInstance();
	}

	@Override
	public SoundEvent getPickupSound()
	{
		return SoundEvents.MUD_PLACE;
	}
	
	public static class GestaltBodyRotationControl extends BodyRotationControl
	{
		public GestaltBodyRotationControl(Mob pMob)
		{
			super(pMob);
		}
		
		@Override
		public void clientTick() 
		{
			
		}
	}
}
