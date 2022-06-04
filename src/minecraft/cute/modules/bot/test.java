package cute.modules.bot;

import cute.modules.Module;
import cute.modules.enums.Category;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class test extends Module 
{
	public test()
	{
		super("UwU", Category.BOT, "Summons a copy of the player at the players position");
	}
	
	@Override
	public void onUpdate()
	{
        double velocity = mc.thePlayer.getItemInUseDuration() / 20.0F;
        
        velocity = ((velocity * velocity + velocity * 2.0F) / 3.0F);

        if (velocity < 0.1D) 
            return;
        
        if (velocity > 1.0F) 
            velocity = 1.0F;
        
        
        double min    = Double.MAX_VALUE;
        Entity target = null;
        
        for(Entity e : mc.theWorld.getLoadedEntityList())
        {
        	if(e instanceof EntityPlayerSP || !(e instanceof EntityLivingBase))
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
        
        double posX = target.posX - mc.thePlayer.posX;
        double posY = target.posY + target.getEyeHeight() - 0.15D - mc.thePlayer.posY - mc.thePlayer.getEyeHeight();
        double posZ = target.posZ - mc.thePlayer.posZ;

        float yaw = (float) (Math.atan2(posZ, posX) * 180.0D / 3.141592653589793D) - 90.0F;
        
        if(Float.isNaN(yaw))
        	return;
        
        double y2 = Math.sqrt(posX * posX + posZ * posZ);
        float g = 0.006F;
        float tmp = (float) (velocity * velocity * velocity * velocity - g * (g * (y2 * y2) + 2.0D * posY * (velocity * velocity)));
        
        float pitch = (float) -Math.toDegrees(Math.atan((velocity * velocity - Math.sqrt(tmp)) / (g * y2)));
        
        if(Float.isNaN(pitch))
        	return;
        
      	mc.thePlayer.rotationYaw = yaw;
        mc.thePlayer.rotationPitch = pitch;  
	}
	
	
	
	
	
	
	
}