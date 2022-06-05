package cute.modules.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL32;

import cute.eventapi.EventTarget;
import cute.events.RenderWorldLastEvent;
import cute.modules.Module;
import cute.modules.client.Players;
import cute.modules.enums.Category;
import cute.settings.Checkbox;
import cute.settings.ColorPicker;
import cute.settings.Slider;
import cute.util.EntityUtil;
import cute.util.RenderUtil;
import cute.util.Util;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.util.Vec3;

public class PredictTargets extends Module
{
	public PredictTargets()
	{
		super("Predict Targets", Category.RENDER, "");
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

    public static Slider lineWidth = new Slider("Line Width", 0.1D, 2.5D, 5.0D, 1);
    
    @Override
    public void setup() 
	{
        addSetting(players);
        addSetting(animals);
        addSetting(mobs);
        addSetting(neutral);
        addSetting(vehicles);
        addSetting(lineWidth);
    }
	
	@Override
	public boolean nullCheck() 
	{
		return mc.theWorld == null ||
			   mc.thePlayer == null ||
			   mc.thePlayer.getHeldItem() == null ||
			   !(mc.thePlayer.getHeldItem().getItem() instanceof ItemBow);
	}
	
	@EventTarget
	public void worldRenderLastEvent(RenderWorldLastEvent evt)
	{
		if(nullCheck())
			return;

		GL11.glPushMatrix();
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
		GL11.glDepthMask(false);

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_ALPHA_TEST);

		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL32.GL_DEPTH_CLAMP);

		GL11.glLineWidth((float)lineWidth.getValue());
		
		for (Entity entity : this.mc.theWorld.loadedEntityList)
		{
			if(entity instanceof EntityPlayerSP)
				continue;

			if(entity instanceof EntityPlayer) 
        	{
        		if(!players.getValue() || Players.playerNameBlacklist.contains(entity.getName())) 
        			continue;
        		
        		RenderUtil.setColor(playerPicker.getColor());
        	}
			else if(EntityUtil.isHostileMob(entity))
        	{
        		if(!mobs.getValue()) 
        			return;
        		
        		RenderUtil.setColor(mobsPicker.getColor());
        	}
        	else if(EntityUtil.isPassive(entity)) 
        	{
        		if(!animals.getValue()) 
        			return;
        		
        		RenderUtil.setColor(animalPicker.getColor());
        	}
        	else if(EntityUtil.isNeutralMob(entity)) 
        	{
        		if(!neutral.getValue())
        			return;
        		
        		RenderUtil.setColor(neutralPicker.getColor());
        	}        	
        	else if(EntityUtil.isVehicle(entity)) 
        	{
        		if(!vehicles.getValue())
        			continue;
        		
    			RenderUtil.setColor(vehiclesPicker.getColor());
        	}	
        	else 
        	{
        		continue;
        	}
        	Vec3 offset = Util.bowPredictionTarget(entity);
			
			RenderUtil.renderEntityHitboxAbs(entity, offset.xCoord, offset.yCoord, offset.zCoord);
		}
		
		GL11.glDisable(GL32.GL_DEPTH_CLAMP);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glDisable(GL11.GL_BLEND);
		
		GL11.glDepthMask(true);
		GL11.glPopMatrix();	
		
		// prevents hotbar / hand from being messed up by color changes 
		RenderUtil.resetColor();
	}
}
