package com.min01.goofy.entity.living;

import com.min01.goofy.entity.AbstractOwnableEntity;
import com.min01.goofy.misc.GoofyEntityDataSerializers;
import com.min01.goofy.util.GoofyUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityGestaltHand extends AbstractOwnableEntity<EntityGestalt>
{
	public static final EntityDataAccessor<Boolean> IS_MOVE = SynchedEntityData.defineId(EntityGestaltHand.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Boolean> IS_DISCARD = SynchedEntityData.defineId(EntityGestaltHand.class, EntityDataSerializers.BOOLEAN);
	public static final EntityDataAccessor<Vec3> TARGET_POS = SynchedEntityData.defineId(EntityGestaltHand.class, GoofyEntityDataSerializers.VEC3.get());
	
	public EntityGestaltHand(EntityType<?> pEntityType, Level pLevel)
	{
		super(pEntityType, pLevel);
		this.noCulling = true;
	}
	
	@Override
	protected void defineSynchedData()
	{
		super.defineSynchedData();
		this.entityData.define(IS_MOVE, false);
		this.entityData.define(IS_DISCARD, false);
		this.entityData.define(TARGET_POS, Vec3.ZERO);
	}
	
	@Override
	public void tick() 
	{
		super.tick();
		if(this.getOwner() != null)
		{
			EntityGestalt owner = this.getOwner();
			owner.controller.addHands(this);
			Vec3 targetPos = this.getTargetPos();
			BlockPos blockPos = BlockPos.containing(targetPos);
			if(!targetPos.equals(Vec3.ZERO))
			{
				if(this.isMove())
				{
					this.move(MoverType.SELF, this.getDeltaMovement());
					if(targetPos.subtract(this.position()).length() <= 1.0F || this.verticalCollision || this.horizontalCollision)
					{
						this.setMove(false);
						this.setDeltaMovement(Vec3.ZERO);
						if(this.isDiscard())
						{
							this.discard();
						}
					}
				}
				else if(!this.level.getBlockState(blockPos).isCollisionShapeFullBlock(this.level, blockPos))
				{
					this.retract(owner);
				}
			}
			if(this.distanceTo(owner) >= 6.0F)
			{
	            Vec3 vec3 = this.position().subtract(owner.position());
	            float dist = this.distanceTo(owner);
	            double length = vec3.length();
	            if(length > dist) 
	            {
	            	double scale = (length / dist) * 0.05D;
	            	owner.addDeltaMovement(vec3.scale(1.0D / length).scale(scale));
	            }
			}
			else
			{
	            Vec3 vec3 = owner.position().subtract(this.position());
	            float dist = this.distanceTo(owner);
	            double length = vec3.length();
	            if(length > dist)
	            {
	            	double scale = (length / dist) * 0.01D;
	            	owner.addDeltaMovement(vec3.scale(1.0D / length).scale(scale));
	            }
			}
		}
		else
		{
			this.discard();
		}
	}
	
	@Override
	public boolean displayFireAnimation() 
	{
		return false;
	}
	
	public void retract(EntityGestalt owner)
	{
		this.setMove(true);
		this.setDiscard(true);
		this.setTargetPos(owner.position());
		this.setDeltaMovement(GoofyUtil.getVelocityTowards(this.position(), owner.position(), 1.5F));
	}
	
	@Override
	public boolean shouldRenderAtSqrDistance(double pDistance) 
	{
		return true;
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag pCompound) 
	{
		super.addAdditionalSaveData(pCompound);
		pCompound.putBoolean("isMove", this.isMove());
		pCompound.putBoolean("isDiscard", this.isDiscard());
		CompoundTag tag = new CompoundTag();
		tag.putDouble("X", this.getTargetPos().x);
		tag.putDouble("Y", this.getTargetPos().y);
		tag.putDouble("Z", this.getTargetPos().z);
		pCompound.put("TargetPos", tag);
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag pCompound)
	{
		super.readAdditionalSaveData(pCompound);
		this.setMove(pCompound.getBoolean("isMove"));
		this.setDiscard(pCompound.getBoolean("isDiscard"));
		CompoundTag tag = pCompound.getCompound("TargetPos");
		this.setTargetPos(new Vec3(tag.getDouble("X"), tag.getDouble("Y"), tag.getDouble("Z")));
	}
	
	public void setMove(boolean value)
	{
		this.entityData.set(IS_MOVE, value);
	}
	
	public boolean isMove()
	{
		return this.entityData.get(IS_MOVE);
	}
	
	public void setDiscard(boolean value)
	{
		this.entityData.set(IS_DISCARD, value);
	}
	
	public boolean isDiscard()
	{
		return this.entityData.get(IS_DISCARD);
	}
	
	public Vec3 getTargetPos()
	{
		return this.entityData.get(TARGET_POS);
	}
	
	public void setTargetPos(Vec3 pos)
	{
		this.entityData.set(TARGET_POS, pos);
	}
}
