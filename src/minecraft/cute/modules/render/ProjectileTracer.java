package cute.modules.render;



import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
//import org.lwjgl.util.Point;
import org.lwjgl.util.glu.Cylinder;

import cute.eventapi.EventTarget;
import cute.events.RenderWorldLastEvent;
import cute.modules.Module;
import cute.modules.enums.Category;
import cute.settings.Checkbox;
import cute.settings.ColorPicker;
import cute.settings.Slider;
import cute.util.RenderUtil;
import cute.util.Util;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

// most of this is just from https://github.com/ItzMyzo/Ascendit/blob/master/src/main/java/me/ascendit/module/modules/render/Projectiles.java
// thanks random person you're based 

public class ProjectileTracer extends Module
{

	public ProjectileTracer() 
	{
		super("Projectile Tracer", Category.RENDER, "Shows projectile trajectories");
	}
	
	public static Checkbox renderTargetBlock = new Checkbox("Highlight Block", true);
    public static ColorPicker tracerColorPicker = new ColorPicker(renderTargetBlock, "Tracer Color", new Color(255,255,255));
	public static Slider lineWidth = new Slider("Line Width", 0.1D, 2.5D, 5.0D, 1);
	
//	public static Slider projectileSpeed = new Slider("arrow speed", 0.0001D, 3D, 5D, 1);
	
	public static short onTarget = 0;
	
	@Override
    public void setup() 
	{
        addSetting(renderTargetBlock);
        addSetting(lineWidth);
//        addSetting(projectileSpeed);
    }
	
	@Override
	public boolean nullCheck() 
	{
		return mc.theWorld == null ||
			   mc.thePlayer == null ||
			   mc.thePlayer.getHeldItem() == null;
	}

//	public double dotProduct(double x1, double y1,double x2, double y2)
//	{
//		return x1 * x2 + y1 * y2;
//	}
//	public double magnitude(double x, double y)
//	{
//		return Math.sqrt(x*x + y*y);
//	}
//	public double angleBetween(double x1, double y1, double x2, double y2)
//	{
//		return Math.acos(dotProduct(x1,y1,x2,y2) / (magnitude(x1,y1) * magnitude(x2,y2)));
//	}
	public Vec3 futureHitBox2(Entity entity)
	{

		final boolean sprint = entity.isSprinting();
		final double xDelta = (entity.posX - entity.lastTickPosX) * 0.4;
		final double zDelta = (entity.posZ - entity.lastTickPosZ) * 0.4;
		final double dist = mc.thePlayer.getDistanceToEntity(entity);
  
		final double d = dist - dist % 0.8;
  
		double xMulti = d / 0.8 * xDelta * (sprint ? 1.25 : 1.0);
		double zMulti = d / 0.8 * zDelta * (sprint ? 1.25 : 1.0);
		final double x = entity.posX + xMulti - mc.thePlayer.posX;
//		            final double y = mc.thePlayer.posY + mc.thePlayer.getEyeHeight() - (entity.posY + entity.getEyeHeight());
		final double y = mc.thePlayer.posY + mc.thePlayer.getEyeHeight() - (entity.posY + entity.getEyeHeight()) - 0.15D;
		final double z = entity.posZ + zMulti - mc.thePlayer.posZ;
		System.out.println(x + " " + y + " " + z);
	    return new Vec3(x + mc.thePlayer.posX,entity.posY,z + mc.thePlayer.posZ);
	}
	

	@EventTarget
    public void drawTrajectory(RenderWorldLastEvent event) 
    {
		// v = 3 * 0.99^t
		// t = ln(v/3) / ln(0.99)
		
		ProjectileTracer.onTarget = 0;
		
		if(nullCheck())
			return;
		
		EntityPlayerSP thePlayer = mc.thePlayer;
		WorldClient theWorld = mc.theWorld;
		
		ItemStack heldItem = thePlayer.getHeldItem();
		Item item = heldItem.getItem();
		RenderManager renderManager = mc.getRenderManager();
		
		boolean isBow = false;
		float motionFactor = 1.5F;
		float motionSlowdown = 0.99F;
		float gravity = 0.0F;
		float size = 0.0F;
		
		// Check items
		if (item instanceof ItemBow) 
		{
			if (!thePlayer.isUsingItem())
				return;
			
			isBow = true;
			gravity = 0.05F;
			size = 0.3F;
			
			// Calculate power of bow
			float power = thePlayer.getItemInUseDuration() / 20.0F;
			power = (power * power + power * 2.0F) / 3.0F;
			
			if (power < 0.1F)
				return;
			
			if (power > 1.0F)
				power = 1.0F;
			
			motionFactor = power * 3.0F;
		} 
		else if (item instanceof ItemFishingRod) 
		{
			gravity = 0.04F;
			size = 0.25F;
			motionSlowdown = 0.92F;
		} 
		else if (item instanceof ItemPotion && ItemPotion.isSplash(heldItem.getMetadata())) 
		{
			gravity = 0.05F;
			size = 0.25F;
			motionFactor = 0.5F;
		} 
		else 
		{
			if (!(item instanceof ItemSnowball) && !(item instanceof ItemEnderPearl) && !(item instanceof ItemEgg))
				return;
			
			gravity = 0.03F;
			size = 0.25F;
		}
		
		// Yaw and pitch of player
		float yaw = thePlayer.rotationYaw;
		float pitch = thePlayer.rotationPitch;
		
		float yawRadians =   yaw   / 180.0F * (float) Math.PI;
		float pitchRadians = pitch / 180.0F * (float) Math.PI;
		
		// Positions
		double posX = renderManager.viewerPosX - ((float) Math.cos(yawRadians) * 0.16F);
		double posY = renderManager.viewerPosY + thePlayer.getEyeHeight() - 0.10000000149011612D;
		double posZ = renderManager.viewerPosZ - ((float) Math.sin(yawRadians) * 0.16F);
		
		// Motions
		double motionX = (-((float) Math.sin(yawRadians)) * (float) Math.cos(pitchRadians)) * (isBow ? 1.0D : 0.4D);
		double motionY = -((float) Math.sin((pitch + ((item instanceof ItemPotion) ? -20 : 0)) / 180.0F * 3.1415927F)) * (isBow ? 1.0D : 0.4D);
		double motionZ = ((float) Math.cos(yawRadians) * (float) Math.cos(pitchRadians)) * (isBow ? 1.0D : 0.4D);
		double distance = Math.sqrt(motionX * motionX + motionY * motionY + motionZ * motionZ);
		
		motionX /= distance;
		motionY /= distance;
		motionZ /= distance;
		motionX *= motionFactor;
		motionY *= motionFactor;
		motionZ *= motionFactor;
		
		// Landing
		MovingObjectPosition landingPosition = null;
		boolean hasLanded = false;
		short hitEntity = 0;
		Color tracerColor = tracerColorPicker.getColor();
		
		// Drawing
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldRenderer = tessellator.getWorldRenderer();
		
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
		
		RenderUtil.setColor(tracerColor);
		GL11.glLineWidth((float)lineWidth.getValue());
		
		worldRenderer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);

		while (!hasLanded && posY > 0.0D) 
		{
			// Set pos before and after
			Vec3 posBefore = new Vec3(posX, posY, posZ);
			Vec3 posAfter = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
			
			// Get landing position
			landingPosition = theWorld.rayTraceBlocks(posBefore, posAfter, false, true, false);
			
			// Set pos before and after
			posBefore = new Vec3(posX, posY, posZ);
			posAfter = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
			
			// Check if arrow is landing
			if (landingPosition != null) 
			{
				hasLanded = true;
				posAfter = new Vec3(landingPosition.hitVec.xCoord, landingPosition.hitVec.yCoord, landingPosition.hitVec.zCoord);
			}
			
			// Set arrow box
			AxisAlignedBB arrowBox = new AxisAlignedBB(
							posX - size, 
							posY - size, 
							posZ - size, 
							posX + size, 
							posY + size, 
							posZ + size).addCoord(motionX, motionY, motionZ).expand(1.0D, 1.0D, 1.0D);
			
			int chunkMinX = (int) Math.floor((arrowBox.minX - 2.0D) / 16.0D);
			int chunkMaxX = (int) Math.floor((arrowBox.maxX + 2.0D) / 16.0D);
			int chunkMinZ = (int) Math.floor((arrowBox.minZ - 2.0D) / 16.0D);
			int chunkMaxZ = (int) Math.floor((arrowBox.maxZ + 2.0D) / 16.0D);
			
			// Check which entities colliding with the arrow
//			List<Entity> collidedEntities = new ArrayList<Entity>();
//			
//			// This block of code is equivalent to the in-Range function in Kotlin
//			int i = chunkMinX;
//			int j = chunkMaxX;
//			
//			if (i <= j)
//				while (true) 
//				{
//					int k = chunkMinZ; 
//					int m = chunkMaxZ;
//					
//					if (k <= m)
//						while (true) 
//						{
//							theWorld.getChunkFromChunkCoords(i, k).getEntitiesWithinAABBForEntity(
//									thePlayer,
//									arrowBox, 
//									collidedEntities, 
//									null);
//							
//							if (k != m) 
//							{
//								k++;
//								continue;
//							}
//							break;
//						}
//					
//					if (i != j) 
//					{
//						i++;
//						continue;
//					}
//					break;
//				}
			
			// Check all possible entities
//			for (Entity possibleEntity : collidedEntities) 
			for (Entity possibleEntity : mc.theWorld.loadedEntityList)
			{
				if (possibleEntity.canBeCollidedWith() && (possibleEntity != thePlayer)) 
				{

					AxisAlignedBB possibleEntityBoundingBox = possibleEntity.getEntityBoundingBox().expand(size, size, size);

					if (possibleEntityBoundingBox.calculateIntercept(posBefore, posAfter) != null) 
					{
						MovingObjectPosition possibleEntityLanding = possibleEntityBoundingBox.calculateIntercept(posBefore, posAfter);
						hitEntity = 1;
						hasLanded = true;
						landingPosition = possibleEntityLanding;
						break;
					}
					AxisAlignedBB possibleEntityFutureBoundingBox = possibleEntity.getEntityBoundingBox().expand(size,size,size);
					Vec3 t = Util.bowPredictionTarget(possibleEntity, 0)[0];
					double difX = t.xCoord - possibleEntity.posX;
					double difZ = t.zCoord - possibleEntity.posZ;
					possibleEntityFutureBoundingBox = new AxisAlignedBB(
							possibleEntityBoundingBox.minX + difX,
							possibleEntityBoundingBox.minY,
							possibleEntityBoundingBox.minZ + difZ,
							possibleEntityBoundingBox.maxX + difX,
							possibleEntityBoundingBox.maxY,
							possibleEntityBoundingBox.maxZ + difZ
							);
							
					if (possibleEntityFutureBoundingBox.calculateIntercept(posBefore, posAfter) != null)
					{
						MovingObjectPosition possibleEntityLanding = possibleEntityFutureBoundingBox.calculateIntercept(posBefore, posAfter);
						hitEntity = 2;
						hasLanded = true;
						landingPosition = possibleEntityLanding;
						
						break;
					}
//					possibleEntityBoundingBox.calculateIntercept(posBefore, posAfter);
//					possibleEntityFutureBoundingBox.calculateIntercept(posBefore,posAfter);
				}
			}
			
			// Affect motions of arrow
			posX += motionX;
			posY += motionY;
			posZ += motionZ;
			
			IBlockState blockState = theWorld.getBlockState(new BlockPos(posX, posY, posZ));
			
			// Check if next position is water
			if (blockState.getBlock().getMaterial() == Material.water) 
			{
				// Update motion
				motionX *= 0.6D;
				motionY *= 0.6D;
				motionZ *= 0.6D;
			} 
			else 
			{ // Update motion
				motionX *= motionSlowdown;
				motionY *= motionSlowdown;
				motionZ *= motionSlowdown;
			}
			
			motionY -= gravity;
			
			// Draw path
			worldRenderer.pos(posX - renderManager.viewerPosX, posY - renderManager.viewerPosY, posZ - renderManager.viewerPosZ).endVertex();
		}
		
		// End the rendering of the path
		tessellator.draw();
		
		GL11.glPushMatrix();
		
		ProjectileTracer.onTarget = hitEntity;
		if(hitEntity == 1)
		{
			// entity has been hit so swap to red color 
			RenderUtil.setColor(0xFF000096);
		}
		else if (hitEntity == 2)
		{
			RenderUtil.setColor(0x00FF0096);
		}
		else if (renderTargetBlock.getValue() && landingPosition != null)
		{
			double doubleX = this.mc.thePlayer.lastTickPosX
	                + (this.mc.thePlayer.posX - this.mc.thePlayer.lastTickPosX)
	                * event.partialTicks;

	        double doubleY = this.mc.thePlayer.lastTickPosY
	                + (this.mc.thePlayer.posY - this.mc.thePlayer.lastTickPosY)
	                * event.partialTicks;

	        double doubleZ = this.mc.thePlayer.lastTickPosZ
	                + (this.mc.thePlayer.posZ - this.mc.thePlayer.lastTickPosZ)
	                * event.partialTicks;

			// translate graphics for the block render
	        GL11.glTranslated(-doubleX, -doubleY, -doubleZ);

			GL11.glBegin(GL11.GL_LINES);
			
	        // landing block position 
	        BlockPos bp = landingPosition.getBlockPos();
	        RenderUtil.renderBlock(bp.getX(), bp.getY(), bp.getZ(), tracerColor);
	        
			GL11.glEnd();
		}
		
		if (landingPosition != null) 
		{
			GL11.glTranslated(posX - renderManager.viewerPosX, posY - renderManager.viewerPosY, posZ - renderManager.viewerPosZ);
			
			// Switch rotation of hit cylinder of the hit axis
			switch (landingPosition.sideHit.getAxis().ordinal()) 
			{
				case 0:
					GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
					break;
				case 2:
					GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
					break;
			}
			
			// Rendering hit cylinder
			GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
			
			Cylinder cylinder = new Cylinder();
			cylinder.setDrawStyle(100011);
			cylinder.draw(0.2F, 0.0F, 0.0F, 60, 1);
		}
			

		// Close Open-GL drawing process
		GL11.glPopMatrix();
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		RenderUtil.resetColor();
    }
}