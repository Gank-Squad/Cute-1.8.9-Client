package cute.util;


import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;

public class Util
{
	private static String playerName = Minecraft.getMinecraft().thePlayer.getName();
	
	public static String playerName() 
	{
		return playerName;
	}
	
	public static boolean nullCheck() 
	{
		return Minecraft.getMinecraft().theWorld == null || 
			   Minecraft.getMinecraft().thePlayer == null;
	}
	
	public static boolean checkRender() 
	{
		return Minecraft.getMinecraft().getRenderManager() == null || 
			   Minecraft.getMinecraft().getRenderManager().options == null;
	}
	
	public static void sendRawClientMessage(String message) 
	{
		Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new ChatComponentText(message), 69);
	}
	
	public static Color getHealthColor(float health) 
    {
        if (health <= 4)
            return new Color(200, 0, 0);
        
        if (health <= 8)
            return new Color(231, 143, 85);
        
        if (health <= 12)
            return new Color(219, 201, 106);
        
        if (health <= 16)
            return new Color(117, 231, 85);
        
        return new Color(44, 186, 19);
    }
	

	public static double roundToPlace(double value, int places) 
	{
        if (places < 0) 
        {
            throw new IllegalArgumentException();
        }
        
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
	
	public static ScaledResolution getScaledRes()
	{
		final ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		return res;
	}
	
	
	public static Vec3 bowPredictionTarget(Entity entity)
	{
		final boolean sprint = entity.isSprinting();
		final double xDelta = (entity.posX - entity.lastTickPosX) * 0.4;
		final double zDelta = (entity.posZ - entity.lastTickPosZ) * 0.4;
		final double dist = Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity);
  
		final double d = dist - dist % 0.8;
  
		double xMulti = d / 0.8 * xDelta * (sprint ? 1.25 : 1.0);
		double zMulti = d / 0.8 * zDelta * (sprint ? 1.25 : 1.0);
		
		final double x = entity.posX + xMulti;
		final double z = entity.posZ + zMulti;
		
	    return new Vec3(x, entity.posY, z);
	}
}



