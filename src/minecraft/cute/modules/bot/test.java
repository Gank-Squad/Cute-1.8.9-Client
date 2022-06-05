package cute.modules.bot;

import cute.modules.Module;
import cute.modules.enums.Category;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class test extends Module 
{
	public test()
	{
		super("UwU", Category.BOT, "Summons a copy of the player at the players position");
	}

	public float[] getBowAngles(final Entity entity) 
	{
	    final boolean sprint = entity.isSprinting();
	    final double xDelta = (entity.posX - entity.lastTickPosX) * 0.4;
	    final double zDelta = (entity.posZ - entity.lastTickPosZ) * 0.4;
	    
	    double d = mc.thePlayer.getDistanceToEntity(entity);
	    d -= d % 0.8;
	    
	    double xMulti = 1.0;
	    double zMulti = 1.0;
	    
	    xMulti = d / 0.8 * xDelta * (sprint ? 1.25 : 1.0);
	    zMulti = d / 0.8 * zDelta * (sprint ? 1.25 : 1.0);
	    
	    final double x = entity.posX + xMulti - mc.thePlayer.posX;
	    final double z = entity.posZ + zMulti - mc.thePlayer.posZ;
	    final double y = mc.thePlayer.posY + mc.thePlayer.getEyeHeight() - (entity.posY + entity.getEyeHeight());
	    final double dist = mc.thePlayer.getDistanceToEntity(entity);
	    final float yaw = (float) Math.toDegrees(Math.atan2(z, x)) - 90.0f;
	    double d1 = MathHelper.sqrt_double(x * x + z * z);
	    final float pitch =  (float) - (Math.atan2(y, d1) * 180.0D / Math.PI) + (float)dist*0.11f;
	    return new float[]{yaw, -pitch};
	}
	@Override
	public void onUpdate()
	{
		double min    = Double.MAX_VALUE;
		Entity target = null;
		for(Entity e : mc.theWorld.getLoadedEntityList())
	        {
//	        	if(e instanceof EntityPlayerSP || !(e instanceof EntityLivingBase))
//	        		continue;
	        	if (e == mc.thePlayer)
	        		continue;
	        	double d = mc.thePlayer.getDistanceToEntity(e);
	        	
	        	if(d < min)
	        	{
	        		target = e;
	        		min = d;
	        	}	
	        }
		
		if(min == Double.MAX_VALUE)
        	return;
		
		Entity entity = target;
		  
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
  
  
		final float yaw = (float) Math.toDegrees(Math.atan2(z, x)) - 90.0f;
		double d1 = MathHelper.sqrt_double(x * x + z * z);
		final float pitch =  (float) - (Math.atan2(y, d1) * 180.0D / Math.PI) + (float)dist * 0.11f;
		mc.thePlayer.rotationYaw = yaw;
		mc.thePlayer.rotationPitch = -pitch;
//        double velocity = mc.thePlayer.getItemInUseDuration() / 20.0F;
//        
//        velocity = ((velocity * velocity + velocity * 2.0F) / 3.0F);
//
//        if (velocity < 0.1D) 
//            return;
//        
//        if (velocity > 1.0F) 
//            velocity = 1.0F;
//        
//        
//        double min    = Double.MAX_VALUE;
//        Entity target = null;
//        
//        for(Entity e : mc.theWorld.getLoadedEntityList())
//        {
////        	if(e instanceof EntityPlayerSP || !(e instanceof EntityLivingBase))
////        		continue;
//        	if (e == mc.thePlayer)
//        		continue;
//        	double d = mc.thePlayer.getDistanceToEntity(e);
//        	
//        	if(d < min)
//        	{
//        		target = e;
//        		min = d;
//        	}	
//        }
//        
//        if(min == Double.MAX_VALUE)
//        	return;
//        Vec3 arrowPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.getEyeHeight() - 0.15D, mc.thePlayer.posZ);
//        Vec3 arrowSpeed = new Vec3(3,3,3);
//        Vec3 targetPos = new Vec3(target.posX, target.posY, target.posZ);
//        Vec3 prevArrowPos = arrowPos;
//        
//        float yaw = 0;
//        float pitch = 0;
//        boolean greater = false;
//        boolean greater2 = false;
//        
//        for (int t = 0; t < 40; t++)
//        {
//            targetPos = new Vec3(
//            		targetPos.xCoord + target.motionX,
//            		targetPos.yCoord + target.motionY,
//            		targetPos.zCoord + target.motionZ
//            		);
//
//        	double posX = targetPos.xCoord - mc.thePlayer.posX;
//            double posY = targetPos.yCoord + target.getEyeHeight() - 0.15D - mc.thePlayer.posY - mc.thePlayer.getEyeHeight();
//            double posZ = targetPos.zCoord - mc.thePlayer.posZ;
//            
//            arrowPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.getEyeHeight() - 0.15D, mc.thePlayer.posZ);
//            
//            yaw = (float) (Math.atan2(posZ, posX) * 180.0D / 3.141592653589793D) - 90.0F;
//            
//            if(Float.isNaN(yaw))
//            	return;
//            
//            double y2 = Math.sqrt(posX * posX + posZ * posZ);
//            float g = 0.006F;
//            float tmp = (float) (velocity * velocity * velocity * velocity - g * (g * (y2 * y2) + 2.0D * posY * (velocity * velocity)));
//            
//            pitch = (float) -Math.toDegrees(Math.atan((velocity * velocity - Math.sqrt(tmp)) / (g * y2)));
//            if(Float.isNaN(pitch))
//            	return;
//            
//            arrowSpeed = new Vec3
//            		(
//            		velocity * Math.sin(yaw) * Math.cos(pitch),
//            		velocity * Math.sin(pitch) - 0.05,
//            		velocity * Math.cos(yaw) * Math.cos(pitch)
//            		);
//            arrowPos = new Vec3
//            		(
//            			arrowPos.xCoord + arrowSpeed.xCoord,
//            			arrowPos.yCoord + arrowSpeed.yCoord,
//            			arrowPos.zCoord + arrowSpeed.zCoord
//    				);
//            
//            
//            if (
//	        		(targetPos.xCoord < arrowPos.xCoord && targetPos.xCoord + target.width > prevArrowPos.xCoord ||
//	        		targetPos.xCoord > arrowPos.xCoord && targetPos.xCoord + target.width < prevArrowPos.xCoord) &&
//	        		(targetPos.yCoord < arrowPos.yCoord && targetPos.yCoord + target.height > prevArrowPos.yCoord ||
//	        		targetPos.yCoord > arrowPos.yCoord && targetPos.yCoord + target.height < prevArrowPos.yCoord) &&
//	        		(targetPos.zCoord < arrowPos.zCoord && targetPos.zCoord + target.width > prevArrowPos.zCoord ||
//	        		targetPos.zCoord > arrowPos.zCoord && targetPos.zCoord + target.width < prevArrowPos.zCoord)
//        		)
//            {
//            	System.out.println("on target " + t);
//            	break;
//            }
//            prevArrowPos = arrowPos;
//            
//        }
//        System.out.println("frick");
	}
	
	
	
	
	
	
	
}