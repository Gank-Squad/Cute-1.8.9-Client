package cute.modules.render;


import java.awt.Color;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL32;

import cute.eventapi.EventTarget;
import cute.events.RenderWorldLastEvent;
import cute.modules.Module;
import cute.modules.bot.AntiBot;
import cute.modules.client.Players;
import cute.modules.enums.Category;
import cute.settings.Checkbox;
import cute.settings.ColorPicker;
import cute.settings.Slider;
import cute.util.EntityUtil;
import cute.util.RenderUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;


public class Tracers extends Module
{
	public Tracers() 
	{
		super("Tracers", Category.RENDER, "Draws lines to entities");
	}
	
	public static Checkbox players = new Checkbox("Players", true);
    public static ColorPicker playerPicker = new ColorPicker(players, "Player Picker", new Color(215, 46, 46));

    public static Checkbox animals = new Checkbox("Animals", true);
    public static ColorPicker animalPicker = new ColorPicker(animals, "Animal Picker", new Color(0, 200, 0));

    public static Checkbox mobs = new Checkbox("Mobs", true);
    public static ColorPicker mobsPicker = new ColorPicker(mobs, "Mob Picker", new Color(131, 19, 199));

    public static Checkbox neutral = new Checkbox("Neutral Creatures", true);
    public static ColorPicker neutralPicker = new ColorPicker(neutral, "Neutral Picker", new Color(255, 255, 255));
    
    public static Checkbox vehicles = new Checkbox("Vehicles", true);
    public static ColorPicker vehiclesPicker = new ColorPicker(vehicles, "Vehicle Picker", new Color(0, 255, 255));
    
    public static Checkbox items = new Checkbox("Items", true);
    public static ColorPicker itemsPicker = new ColorPicker(items, "Item Picker", new Color(199, 196, 19));

    public static Slider lineWidth = new Slider("Line Width", 0.1D, 2.5D, 5.0D, 1);
    public static Slider radius = new Slider("Radius", 1, 100, 300D, 1);
    public static Slider alphaSensitivity = new Slider("Alpha Sensitivity", 0, 1, 2, 1);
    public static Slider firstPersonRenderPoint = new Slider("Y Render Point", 0, 99, 100, 1);
    
    @Override
    public void setup() 
	{
        addSetting(players);
        addSetting(animals);
        addSetting(mobs);
        addSetting(neutral);
        addSetting(vehicles);
        addSetting(items);
        addSetting(lineWidth);
        addSetting(radius);
        addSetting(alphaSensitivity);
        addSetting(firstPersonRenderPoint);
    }
    
	@Override
	public boolean nullCheck() 
	{
		return mc.thePlayer == null ||
	    	   mc.theWorld == null ||
	    	   mc.getRenderManager() == null || 
			   mc.getRenderManager().options == null;
	}

	@EventTarget
	public void onRenderWorld(RenderWorldLastEvent event) 
	{
		if (nullCheck())
			return;

		Vec3 vec = mc.thePlayer.getPositionVector();

		double mx = vec.xCoord;
		double mz = vec.zCoord;
		double my = vec.yCoord + mc.thePlayer.getEyeHeight() - 0.35;

		if (mc.getRenderManager().options.thirdPersonViewSetting == 0) 
		{
			double drawBeforeCameraDist = firstPersonRenderPoint.getValue();
			double pitch = ((mc.thePlayer.rotationPitch + 90) * Math.PI) / 180;
			double yaw = ((mc.thePlayer.rotationYaw + 90) * Math.PI) / 180;
			mx += Math.sin(pitch) * Math.cos(yaw) * drawBeforeCameraDist;
			mz += Math.sin(pitch) * Math.sin(yaw) * drawBeforeCameraDist;
			my += Math.cos(pitch) * drawBeforeCameraDist;
		}


		GL11.glPushMatrix();
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
		GL11.glDepthMask(false);

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL32.GL_DEPTH_CLAMP);
		
		GL11.glLineWidth((float)lineWidth.getValue());
		
		
		GL11.glTranslated(-mc.thePlayer.posX, -mc.thePlayer.posY, -mc.thePlayer.posZ);
		GL11.glBegin(GL11.GL_LINES);
		
		
		// main draw loop
		for (Entity entity : mc.theWorld.loadedEntityList) 
		{
			if(entity instanceof EntityPlayerSP || (entity instanceof EntityLivingBase) && 
	            	   AntiBot.isBot((EntityLivingBase)entity))
				continue;
			
			switch(entity.getEntityType())
        	{
        		default:
        			continue;
        			
	        	case PLAYER:
	        		if(players.getValue() && !Players.playerNameBlacklist.contains(entity.getName().toLowerCase())) 
	        		{
	        			RenderUtil.renderTracer(mx, my, mz, entity, radius.getValue(), alphaSensitivity.getValue(), playerPicker.getColor());
	        		}
	        		continue;

	        		
	        	case HOSTILE:
	        		if(mobs.getValue()) 
	        		{
	        			RenderUtil.renderTracer(mx, my, mz, entity, radius.getValue(), alphaSensitivity.getValue(), mobsPicker.getColor());
	        		}
	        		continue;
	        		
	        	case NEUTRAL:
	        		
	        		if(neutral.getValue()) 
	        		{
	        			RenderUtil.renderTracer(mx, my, mz, entity, radius.getValue(), alphaSensitivity.getValue(), neutralPicker.getColor());
	        		}
	        		continue;
	        		
	        	case PASSIVE:
	        		
	        		if(animals.getValue()) 
	        		{
	        			RenderUtil.renderTracer(mx, my, mz, entity, radius.getValue(), alphaSensitivity.getValue(), animalPicker.getColor());
	        		}
	        		continue;
	        	
	        	
	        	case VEHICLE:

	        		if(vehicles.getValue()) 
	        		{
	        			RenderUtil.renderTracer(mx, my, mz, entity, radius.getValue(), alphaSensitivity.getValue(), vehiclesPicker.getColor());
	        		}
	        		continue;
	        	
	        	case ITEM:
	        		if(items.getValue()) 
	        		{
	        			RenderUtil.renderTracer(mx, my, mz, entity, radius.getValue(), alphaSensitivity.getValue(), itemsPicker.getColor());
	        		}
	        		continue;
        	}
			
//			if(entity instanceof EntityPlayer) 
//        	{
//        		if(players.getValue() && !Players.playerNameBlacklist.contains(entity.getName().toLowerCase())) 
//        		{
//        			RenderUtil.renderTracer(mx, my, mz, entity, radius.getValue(), alphaSensitivity.getValue(), playerPicker.getColor());
//        		}
//        		continue;
//        	}
        	
//        	if(entity instanceof EntityItem) 
//        	{
//        		if(items.getValue()) 
//        		{
//        			RenderUtil.renderTracer(mx, my, mz, entity, radius.getValue(), alphaSensitivity.getValue(), itemsPicker.getColor());
//        		}
//        		continue;
//        	}
        	
//        	if(EntityUtil.isHostileMob(entity))
//        	{
//        		if(mobs.getValue()) 
//        		{
//        			RenderUtil.renderTracer(mx, my, mz, entity, radius.getValue(), alphaSensitivity.getValue(), mobsPicker.getColor());
//        		}
//        		continue;
//        	}
        	
//        	if(EntityUtil.isPassive(entity)) 
//        	{
//        		if(animals.getValue()) 
//        		{
//        			RenderUtil.renderTracer(mx, my, mz, entity, radius.getValue(), alphaSensitivity.getValue(), animalPicker.getColor());
//        		}
//        		continue;
//        	}
        	
//        	if(EntityUtil.isNeutralMob(entity)) 
//        	{
//        		if(neutral.getValue()) 
//        		{
//        			RenderUtil.renderTracer(mx, my, mz, entity, radius.getValue(), alphaSensitivity.getValue(), neutralPicker.getColor());
//        		}
//        		continue;
//        	}        	
        	
//        	if(EntityUtil.isVehicle(entity)) 
//        	{
//        		if(vehicles.getValue()) 
//        		{
//        			RenderUtil.renderTracer(mx, my, mz, entity, radius.getValue(), alphaSensitivity.getValue(), vehiclesPicker.getColor());
//        		}
//        	}			
		}

		GL11.glEnd();
		GL11.glDisable(GL32.GL_DEPTH_CLAMP);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glDisable(GL11.GL_BLEND);
		
		GL11.glDepthMask(true);
		GL11.glPopMatrix();
		
		// prevents hotbar / hand from being messed up by color changes 
		RenderUtil.resetColor();
	}
}