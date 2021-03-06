package cute.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;

public class EntityUtil
{
	public static Minecraft mc = Minecraft.getMinecraft();
	
	public static List<Class<?>> getPassives() 
	{
        return new ArrayList(Arrays.asList(EntityPigZombie.class, EntitySquid.class, EntityIronGolem.class, EntityWolf.class, EntityEnderman.class, EntityChicken.class, EntityCow.class, EntitySheep.class, EntityRabbit.class, EntityPig.class, EntityBat.class, EntityHorse.class, EntitySnowman.class));
    }

    public static List<Class<?>> getHostiles() 
    {
        return new ArrayList(Arrays.asList(EntitySpider.class, EntitySkeleton.class, EntityZombie.class, EntityBlaze.class, EntityCreeper.class, EntityCaveSpider.class, EntityBlaze.class, EntityGhast.class, EntityWitch.class, EntitySlime.class, EntityWither.class));
    }

    public static List<Class<?>> getVehicles() 
    {

        return new ArrayList(Arrays.asList(EntityBoat.class, EntityMinecart.class));
    }
    
    public static List<String> getTabMenuPlayerNames()
	{
		NetHandlerPlayClient nethandlerplayclient = mc.thePlayer.sendQueue;
        List<NetworkPlayerInfo> list = GuiPlayerTabOverlay.getOrdering().<NetworkPlayerInfo>sortedCopy(nethandlerplayclient.getPlayerInfoMap());

        ArrayList<String> names = new ArrayList<String>();
        for (NetworkPlayerInfo ni : list)
        {
            String playerName = ni.getDisplayName() != null ? ni.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName(ni.getPlayerTeam(), ni.getGameProfile().getName());

            names.add(playerName);
        }
        
        return names;
	}

    public static boolean isPassive(Entity entity) 
    {
        if (entity instanceof EntityWolf && ((EntityWolf) entity).isAngry())
            return false;

        if (entity instanceof EntityAgeable || 
        	entity instanceof EntityAmbientCreature || 
        	entity instanceof EntitySquid)
            return true;

        return entity instanceof EntityIronGolem && ((EntityIronGolem) entity).isPlayerCreated();
    }

    public static boolean isVehicle(Entity entity) 
    {
        return entity instanceof EntityBoat || entity instanceof EntityMinecart;
    }

    public static boolean isHostileMob(Entity entity) 
    {
        return (entity.isCreatureType(EnumCreatureType.MONSTER, false) && !EntityUtil.isNeutralMob(entity)) || 
        		entity instanceof EntitySpider;
    }

    public static boolean isNeutralMob(Entity entity) 
    {
    	return  entity instanceof EntityPigZombie || 
    			entity instanceof EntityEnderman ||
    			entity instanceof EntityWolf || 
    			entity instanceof EntityIronGolem;
    }
    
    public static boolean isProjectile(Entity entity)
    {
    	return entity instanceof IProjectile;
    	
//    	return 	entity instanceof EntityArrow 	||
//    			entity instanceof EntityEgg		||
//    			entity instanceof EntityFireball ||
//    			entity instanceof EntityFishHook ||
//    			entity instanceof EntityLargeFireball ||
//    			entity instanceof EntityPotion ||
//    			entity instanceof EntitySnowball ||
//    			entity instanceof EntityThrowable ||
//    			entity instanceof EntityWitherSkull;
    }

    public static boolean isCurrentPlayer(Entity entity) 
    {
    	if(entity == null)
    		return false;
    	return entity.getName() == mc.thePlayer.getName();
    }
    
    public static boolean isLiving(Entity entity) 
    {
        return entity instanceof EntityLivingBase;
    }
//    
       public boolean isValidType(Entity entity) 
       { 
    	   if (entity instanceof EntityPlayerSP ) 
    		   return false;  
    	   
    	   if (entity instanceof EntityPlayer) 
    		   return true;
    		   // return ((Boolean)this.player.getValue()).booleanValue();  
    	   
    	   if (entity instanceof EntityAmbientCreature || 
    		   entity instanceof EntityAgeable         || 
    		   entity instanceof EntityWaterMob)
    		   return true;
    		   // return ((Boolean)this.animals.getValue()).booleanValue();  
    	   
    	   if (entity instanceof EntityLiving && 
			 !(entity instanceof net.minecraft.entity.item.EntityArmorStand))
    		   return true;
    		   // return ((Boolean)this.mobs.getValue()).booleanValue();  
    	   
    	   if (entity instanceof EntityItem)
    		   return true;
    		   // return ((Boolean)this.items.getValue()).booleanValue();  
    	   
    	   if (entity instanceof EntityFallingBlock) 
    		   return true;  
    	   
    	   return false;
    	   // return ((Boolean)this.other.getValue()).booleanValue(); 
	   } 
    
    
   public int getPlayerTeamColor(EntityLivingBase entity)
   {
	   int i = 16777215;

       if (entity instanceof EntityPlayer)
       {
           ScorePlayerTeam scoreplayerteam = (ScorePlayerTeam)entity.getTeam();

           if (scoreplayerteam != null)
           {
               String s = FontRenderer.getFormatFromString(scoreplayerteam.getColorPrefix());

               if (s.length() >= 2)
               {
                   i = mc.getRenderManager().getFontRenderer().getColorCode(s.charAt(1));
               }
           }
       }

//       float f1 = (float)(i >> 16 & 255) / 255.0F;
//       float f2 = (float)(i >> 8 & 255) / 255.0F;
//       float f = (float)(i & 255) / 255.0F;
       
       return i;
   }

   /**
    * Returns the name that should be renderd for the player supplied
    */
   public static String getPlayerTabMenuName(NetworkPlayerInfo networkPlayerInfoIn)
   {
       return networkPlayerInfoIn.getDisplayName() != null ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName(networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
   }

   
   public static boolean isInTab(AbstractClientPlayer entity) 
	{
	   return mc.getNetHandler().getPlayerInfo(entity.getUniqueID()) != null;
//		for (NetworkPlayerInfo p : mc.getNetHandler().getPlayerInfoMap()) 
//		{
//			if (p.equals(entity.getPlayerInfo()))
//				return true; 
//		}  
//		return false;
	}
}








