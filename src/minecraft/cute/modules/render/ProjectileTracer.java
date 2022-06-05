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
import cute.util.point;
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
	
	public static Slider projectileSpeed = new Slider("arrow speed", 0.0001D, 3D, 5D, 1);
	public static Checkbox renderHitTargets = new Checkbox("Aim Assist [BETA]", false);
	
	public static boolean onTarget = false;
	
	@Override
    public void setup() 
	{
        addSetting(renderTargetBlock);
        addSetting(lineWidth);
        addSetting(projectileSpeed);
        addSetting(renderHitTargets);
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
	public Vec3 futureHitBox(Entity e)
	{
//		
//		point ePos = new point(e.posX, e.posZ);
//		point eVel = new point(e.motionX, e.motionZ);
//		point pPos = new point(mc.thePlayer.posX, mc.thePlayer.posZ);
		Vec3 playerPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
//		double pSpeed = 2.713146225;
		double pSpeed = projectileSpeed.getValue();
		pSpeed = 3;
		
		
		//motionX is in blocks/tick
		// so if motionX is 3, and x=5, the following tick, x=8

		//every tick, motion x,y, & z are multiplied by .99, it doesn't do it to net motion
		//so even if net motion is increasing from downwards acceleration, x & z will decrease at same rate
		
		
//		a possible approach:
//		find minimum time to reach target
//		to do this, ignore y component, only use x & z
//		once this is found binary search pitch values until one is found that will result in interception
//		then find out where the target will be after arrow arrives
//		if it wont result in interception any more, adjust yaw and repeat calculations
//		theoretically this will result in an accurate target, however it will become incredibely inefficient at long distances
//		
//		
//		another, likely better approach:
//		work in 3d coordinate system, with a constant speed vector being the target,
//		and a sphere representing where the projectile could be after t ticks
//		problems I'm not sure how to deal with for this appraoch:
//		non constant acceleration, and the constant acceleration in the y-axis
//		the sphere will almost instantly stop being a sphere after just 1 tick
//		and I'm not sure how to account for this.
//		However, if I can figure it out, this method should be able to instantly figure out
//		the interception coordinates, and by extension the yaw & pitch necessary needed by the shooter to hit the target
//		
//		
		Vec3 eFPos = new Vec3(e.posX, e.posY, e.posZ);
		Vec3 pFPos = playerPos;
		
		
		boolean lastPosGreater = false;
		boolean posGreater = lastPosGreater;
		boolean validY = false;
		double yawToTarget;
		double yawToTargetDeg;
		
		double posX = e.posX - mc.thePlayer.posX;
		double posY = e.posY + e.getEyeHeight() - 0.15D - mc.thePlayer.posY - mc.thePlayer.getEyeHeight();
		double posZ = e.posZ - mc.thePlayer.posZ;
		double yaw = (float) (Math.atan2(posZ, posX) * 180.0D / Math.PI) - 90.0F;
		
		
//		yawToTarget = Math.asin((playerPos.xCoord - e.posX) / Math.sqrt(
//				Math.pow((playerPos.xCoord - e.posX),2) +
//				Math.pow((playerPos.zCoord - e.posZ), 2)));
		yawToTarget = yaw;
//		yawToTargetDeg = yawToTarget * 180 / Math.PI;
//		System.out.println(yawToTargetDeg);


		yawToTarget = (Math.atan2(posZ, posX) * 180.0D / 3.141592653589793D) - 90.0F;
		Vec3 snt = null;
		Vec3 slt = snt;
		
		double velocity = mc.thePlayer.getItemInUseDuration() / 20.0F;
        
        velocity = ((velocity * velocity + velocity * 2.0F) / 3.0F);

        if (velocity < 0.1D) 
            return new Vec3(e.posX, e.posY, e.posZ);
        
        if (velocity > 1.0F) 
            velocity = 1.0F;
		
		double y2 = Math.sqrt(posX * posX + posZ * posZ);
        float g = 0.006F;
        float tmp = (float) (velocity * velocity * velocity * velocity - g * (g * (y2 * y2) + 2.0D * posY * (velocity * velocity)));
        
        float pitch = (float) -Math.toDegrees(Math.atan((velocity * velocity - Math.sqrt(tmp)) / (g * y2)));
	        
		
		for (int t = 0; t < 100; t++)
		{
//				(Math.atan2(posZ, posX) * 180.0D / 3.141592653589793D) - 90.0F;
			
			eFPos = new Vec3(
					eFPos.xCoord + e.motionX, 
					eFPos.yCoord + e.motionY,
					eFPos.zCoord + e.motionZ
					);
			
			posX = eFPos.xCoord - mc.thePlayer.posX;
			posY = e.posY + e.getEyeHeight() - 0.15D - mc.thePlayer.posY - mc.thePlayer.getEyeHeight();
			posZ = eFPos.zCoord - mc.thePlayer.posZ;
			yawToTarget = (Math.atan2(posZ, posX) * 180.0D / Math.PI) - 90.0F;
			
			snt = new Vec3(
					pSpeed * Math.sin(yawToTarget) * Math.cos(pitch),
					pSpeed * Math.sin(pitch),
					pSpeed * Math.cos(yawToTarget) * Math.cos(pitch)
					);	
			
			snt = new Vec3(snt.xCoord * 0.99,snt.yCoord * 0.99,snt.zCoord * 0.99);
				//arrows have an acceleration of -0.05b/t^2, v2=v1+a*t
//			snt = new Vec3(snt.xCoord, snt.yCoord - 0.05, snt.zCoord);
//				Vec3 pnt = new Vec3(playerPos.xCoord + snt.xCoord, playerPos.yCoord + snt.yCoord, playerPos.zCoord);
//				Math.sqrt(snt.xCoord*snt.xCoord + snt.yCoord*snt.yCoord + snt.zCoord*snt.zCoord);
			
			pFPos = new Vec3(
					pFPos.xCoord + snt.xCoord,
					pFPos.yCoord + snt.yCoord,
					pFPos.zCoord + snt.zCoord
					);

			
			posGreater = pFPos.xCoord > eFPos.xCoord && pFPos.zCoord > eFPos.zCoord;
			validY = pFPos.yCoord > e.posY && pFPos.yCoord < e.posY + e.height;
			if (posGreater != lastPosGreater)
			{
//				System.out.println("interseption at " + pFPos + " " + yawToTarget * 180 / Math.PI + " " + eFPos);
				return pFPos;
			}
			lastPosGreater = posGreater;
			
		}
			
		

//		double k = eVel.magnitude() / pSpeed;
//		double c = pPos.sub(ePos).magnitude();
//		
//		point bVec = eVel;
//		point cVec = pPos.sub(ePos);
//		
//		double CAB = bVec.angleBetween(cVec);
//		double ABC = Math.asin(Math.sin(CAB) * k);
//		double ACB = (Math.PI) - (CAB + ABC);
//		
//		double j = c / Math.sin(ACB);
//		double a = j * Math.sin(CAB);
//		double b = j * Math.sin(ABC);
//		
//		double t = b / eVel.magnitude();
//		
//		point collisionPos = ePos.add(eVel.mul(t));
//		System.out.println(collisionPos.x + " " + collisionPos.y);

		return new Vec3(0, 0, 0);
	}
	

	@EventTarget
    public void drawTrajectory(RenderWorldLastEvent event) 
    {
		// v = 3 * 0.99^t
		// t = ln(v/3) / ln(0.99)
		
		ProjectileTracer.onTarget = false;
		
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
		boolean hitEntity = false;
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
			List<Entity> collidedEntities = new ArrayList<Entity>();
			
			// This block of code is equivalent to the in-Range function in Kotlin
			int i = chunkMinX;
			int j = chunkMaxX;
			
			if (i <= j)
				while (true) 
				{
					int k = chunkMinZ; 
					int m = chunkMaxZ;
					
					if (k <= m)
						while (true) 
						{
							theWorld.getChunkFromChunkCoords(i, k).getEntitiesWithinAABBForEntity(
									thePlayer,
									arrowBox, 
									collidedEntities, 
									null);
							
							if (k != m) 
							{
								k++;
								continue;
							}
							break;
						}
					
					if (i != j) 
					{
						i++;
						continue;
					}
					break;
				}
			
			// Check all possible entities
			for (Entity possibleEntity : collidedEntities) 
			{
				if (possibleEntity.canBeCollidedWith() && (possibleEntity != thePlayer)) 
				{

					AxisAlignedBB possibleEntityBoundingBox = possibleEntity.getEntityBoundingBox().expand(size, size, size);
					if (possibleEntityBoundingBox.calculateIntercept(posBefore, posAfter) != null) 
					{
						MovingObjectPosition possibleEntityLanding = possibleEntityBoundingBox.calculateIntercept(posBefore, posAfter);
						hitEntity = true;
						hasLanded = true;
						landingPosition = possibleEntityLanding;
						continue;
					}
					possibleEntityBoundingBox.calculateIntercept(posBefore, posAfter);
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
		if(hitEntity)
		{
			// entity has been hit so swap to red color 
			RenderUtil.setColor(new Color(255, 0, 0, 150));
			
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