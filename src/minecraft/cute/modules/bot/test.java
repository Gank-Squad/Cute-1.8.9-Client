package cute.modules.bot;

import cute.modules.Module;
import cute.modules.enums.Category;
import cute.settings.Checkbox;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class test extends Module 
{
	public test()
	{
		super("UwU", Category.BOT, "Summons a copy of the player at the players position");
	}
	
	public static Checkbox predict = new Checkbox("Predict Movement", false);
	
	
	@Override
	public void setup()
	{
		this.addSetting(predict);
	}
	
	@Override
	public void onUpdate()
	{
		// https://github.com/zPeanut/Hydrogen
		// https://github.com/Team-Pepsi/pepsimod
		
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
        
        if(!predict.getValue())
        {
        	final double posX = target.posX - mc.thePlayer.posX;
        	final double posY = target.posY + target.getEyeHeight() - 0.15D - mc.thePlayer.posY - mc.thePlayer.getEyeHeight();
        	final double posZ = target.posZ - mc.thePlayer.posZ;

        	final float yaw = (float) (Math.atan2(posZ, posX) * 180.0D / 3.141592653589793D) - 90.0F;
            
            if(Float.isNaN(yaw))
            	return;
            
            final double y2 = Math.sqrt(posX * posX + posZ * posZ);
            final float g = 0.006F;
            final float tmp = (float) (velocity * velocity * velocity * velocity - g * (g * (y2 * y2) + 2.0D * posY * (velocity * velocity)));
            
            final float pitch = (float) Math.toDegrees(Math.atan((velocity * velocity - Math.sqrt(tmp)) / (g * y2)));
            
            if(Float.isNaN(pitch))
            	return;
            
          	mc.thePlayer.rotationYaw   = yaw;
            mc.thePlayer.rotationPitch = -pitch;  
        }
        else 
        {
        	final boolean sprint = target.isSprinting();
            final double xDelta = (target.posX - target.lastTickPosX) * 0.4;
            final double zDelta = (target.posZ - target.lastTickPosZ) * 0.4;
            final double dist = mc.thePlayer.getDistanceToEntity(target);
            
            final double d = dist - dist % 0.8;
            
            final double xMulti = d / 0.8 * xDelta * (sprint ? 1.25 : 1.0);
            final double zMulti = d / 0.8 * zDelta * (sprint ? 1.25 : 1.0);
          
            final double x = target.posX + xMulti - mc.thePlayer.posX;
            final double y = target.posY + target.getEyeHeight() - 0.15D - mc.thePlayer.posY - mc.thePlayer.getEyeHeight();
            final double z = target.posZ + zMulti - mc.thePlayer.posZ;
            
            
            final float yaw = (float) Math.toDegrees(Math.atan2(z, x)) - 90.0f;
            // this is the calculation from hydrogen, i found the other pitch calc to be better
            // so i swapped it out, 
            // float pitch =  (float) - (Math.atan2(y, MathHelper.sqrt_double(x * x + z * z)) * 180.0D / Math.PI) + (float)dist * 0.11f;
          
            final double y2 = Math.sqrt(x * x + z * z);
            final float g = 0.006F;
            final float tmp = (float) (velocity * velocity * velocity * velocity - g * (g * (y2 * y2) + 2.0D * y * (velocity * velocity)));
            final float pitch = (float) Math.toDegrees(Math.atan((velocity * velocity - Math.sqrt(tmp)) / (g * y2)));

            if(Float.isNaN(pitch))
            	return;
            
            mc.thePlayer.rotationYaw   = yaw;
            mc.thePlayer.rotationPitch = -pitch;
        }   
	}
}