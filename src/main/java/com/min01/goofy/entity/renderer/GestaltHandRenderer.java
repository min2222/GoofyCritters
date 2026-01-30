package com.min01.goofy.entity.renderer;

import com.min01.goofy.GoofyCritters;
import com.min01.goofy.entity.living.EntityGestalt;
import com.min01.goofy.entity.living.EntityGestaltHand;
import com.min01.goofy.entity.model.ModelGestaltArm;
import com.min01.goofy.entity.model.ModelGestaltHand;
import com.min01.goofy.util.GoofyClientUtil;
import com.min01.goofy.util.GoofyUtil;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class GestaltHandRenderer extends EntityRenderer<EntityGestaltHand>
{
	public static final ResourceLocation ARM_TEXTURE = ResourceLocation.fromNamespaceAndPath(GoofyCritters.MODID, "textures/entity/gestalt_arm.png");
	public final ModelGestaltHand handModel;
	public final ModelGestaltArm armModel;
	
	public GestaltHandRenderer(Context pContext) 
	{
		super(pContext);
		this.handModel = new ModelGestaltHand(pContext.bakeLayer(ModelGestaltHand.LAYER_LOCATION));
		this.armModel = new ModelGestaltArm(pContext.bakeLayer(ModelGestaltArm.LAYER_LOCATION));
	}
	
	@Override
	public void render(EntityGestaltHand pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) 
	{
		if(pEntity.getOwner() != null)
		{
			Vec3 camPos = GoofyClientUtil.MC.gameRenderer.getMainCamera().getPosition();
			
			EntityGestalt gestalt = pEntity.getOwner();
			
			Vec3 ownerPos = gestalt.getPosition(pPartialTick);
            Vec3 entityPos = pEntity.getPosition(pPartialTick);
            
            Vec3 entityRelative = entityPos.subtract(camPos);
            Vec3 ownerRelative = ownerPos.subtract(camPos);
            
            Vec3 bottom = entityPos.subtract(entityPos);
            Vec3 top = ownerPos.subtract(entityPos);
            
            Vec3 towards = bottom.subtract(top);
            Vec3 current = Vec3.ZERO;

			pPoseStack.pushPose();
			pPoseStack.translate(-entityRelative.x, -entityRelative.y, -entityRelative.z);
			pPoseStack.translate(ownerRelative.x, ownerRelative.y, ownerRelative.z);
            while(current.distanceTo(towards) > 0.5F)
            {
                double remainingDistance = Math.min(current.distanceTo(towards), 1.0F);
                Vec3 linearVec = towards.subtract(current);
                Vec3 powVec = new Vec3(this.modifyVecAngle(linearVec.x), this.modifyVecAngle(linearVec.y), this.modifyVecAngle(linearVec.z));
                Vec3 smoothedVec = remainingDistance < 1.0F ? linearVec : powVec;
                Vec3 next = smoothedVec.normalize().scale(remainingDistance).add(current);
                Vec2 rot = GoofyUtil.lookAt(current, next);
                int light = this.getLightColor(pEntity, bottom.add(current).add(entityPos));
                
    			pPoseStack.pushPose();
    			pPoseStack.scale(-1.0F, -1.0F, 1.0F);
    			pPoseStack.translate(0.0F, -1.5F, 0.0F);
    			pPoseStack.translate(-current.x, -current.y, current.z + 0.5F);
    			this.armModel.setupAnim(pEntity, 0, 0, 0, rot.y, -rot.x);
    	        this.armModel.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityCutoutNoCull(ARM_TEXTURE)), light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    			pPoseStack.popPose();
    			
    			current = next;
            }
			pPoseStack.popPose();

			float rotY = Mth.lerp(pPartialTick, pEntity.yRotO, pEntity.getYRot());
			float rotX = Mth.lerp(pPartialTick, pEntity.xRotO, pEntity.getXRot());
			
			pPoseStack.pushPose();
			pPoseStack.scale(-1.0F, -1.0F, 1.0F);
			pPoseStack.translate(0.0F, -1.5F, 0.0F);
			this.handModel.setupAnim(pEntity, 0, 0, 0, rotY, -rotX);
	        this.handModel.renderToBuffer(pPoseStack, pBuffer.getBuffer(RenderType.entityCutoutNoCull(this.getTextureLocation(pEntity))), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
			pPoseStack.popPose();
		}
	}
    
    private double modifyVecAngle(double dimension) 
    {
        float abs = (float) Math.abs(dimension);
        return Math.signum(dimension) * Mth.clamp(Math.pow(abs, 0.1), 0.01 * abs, abs);
    }
    
    @SuppressWarnings("deprecation")
	private int getLightColor(EntityGestaltHand portal, Vec3 vec3)
    {
        BlockPos blockPos = BlockPos.containing(vec3);
        if(portal.level.hasChunkAt(blockPos))
        {
            int i = LevelRenderer.getLightColor(portal.level, blockPos);
            int j = LevelRenderer.getLightColor(portal.level, blockPos.above());
            int k = i & 255;
            int l = j & 255;
            int i1 = i >> 16 & 255;
            int j1 = j >> 16 & 255;
            return (Math.max(k, l)) | (Math.max(i1, j1)) << 16;
        }
        else
        {
            return 0;
        }
    }

	@Override
	public ResourceLocation getTextureLocation(EntityGestaltHand pEntity)
	{
		return ResourceLocation.fromNamespaceAndPath(GoofyCritters.MODID, "textures/entity/gestalt_hand.png");
	}
}
