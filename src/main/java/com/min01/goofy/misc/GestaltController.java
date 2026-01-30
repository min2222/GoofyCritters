package com.min01.goofy.misc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.min01.goofy.entity.GoofyEntities;
import com.min01.goofy.entity.living.EntityGestalt;
import com.min01.goofy.entity.living.EntityGestaltHand;
import com.min01.goofy.util.GoofyUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class GestaltController 
{
	public final EntityGestalt mob;
	protected final List<EntityGestaltHand> hands = new ArrayList<>();
	protected Vec3 target = Vec3.ZERO;
	
	public GestaltController(EntityGestalt mob)
	{
		this.mob = mob;
	}
	
	public void tick()
	{
		this.hands.removeIf(t -> !t.isAlive());
		if(this.hands.size() < 16)
		{
			Vec3 startPos = this.mob.position();
			Vec3 targetPos = this.mob.position();
			float radius = 50;
			if(!this.target.equals(Vec3.ZERO))
			{
				targetPos = this.target;
				radius = 5;
			}
			Vec3 pos = GoofyUtil.getSpreadPosition(this.mob.level, targetPos, radius);
			HitResult hitResult = this.mob.level.clip(new ClipContext(startPos, pos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this.mob));
			if(hitResult instanceof BlockHitResult blockHit)
			{
				Direction direction = blockHit.getDirection();
				Vec3 hitPos = blockHit.getLocation();
				BlockPos blockPos = blockHit.getBlockPos();
				BlockState state = this.mob.level.getBlockState(blockPos);
				if(state.isCollisionShapeFullBlock(this.mob.level, blockPos))
				{
					this.extendArm(hitPos.relative(direction, -1));
				}
			}
		}
		else if(this.hands.size() > 8 && !this.target.equals(Vec3.ZERO))
		{
			Optional<EntityGestaltHand> max = this.hands.stream().filter(t -> t.tickCount >= 40).max(Comparator.comparingDouble(hand -> hand.distanceTo(this.mob)));
			if(max.isPresent())
			{
				max.get().retract(this.mob);
				this.target = Vec3.ZERO;
			}
		}
	}
	
	public void extendArm(Vec3 targetPos)
	{
		Vec2 rot = GoofyUtil.lookAt(targetPos, this.mob.position());
		EntityGestaltHand hand = new EntityGestaltHand(GoofyEntities.GESTALT_HAND.get(), this.mob.level);
		hand.setOwner(this.mob);
		hand.setPos(this.mob.position());
		hand.setYRot(rot.y);
		hand.setXRot(rot.x);
		hand.setDeltaMovement(GoofyUtil.getVelocityTowards(hand.position(), targetPos, 1.5F));
		hand.setMove(true);
		hand.setTargetPos(targetPos);
		this.mob.level.addFreshEntity(hand);
		this.hands.add(hand);
	}
	
	public void addHands(EntityGestaltHand hand)
	{
		if(!this.hands.contains(hand))
		{
			this.hands.add(hand);
		}
	}
	
	public void setTarget(Vec3 target)
	{
		this.target = target;
	}
}
