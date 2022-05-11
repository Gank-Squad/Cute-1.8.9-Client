package cute.modules.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL32;

import cute.eventapi.EventTarget;
import cute.events.RenderLivingEvent;
import cute.events.RenderWorldLastEvent;
import cute.modules.Module;
import cute.modules.enums.Category;
import cute.settings.Checkbox;
import cute.settings.ColorPicker;
import cute.settings.Mode;
import cute.settings.Slider;
import cute.util.EntityUtil;
import cute.util.RenderUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;


public class ESPEntity extends Module
{
	public ESPEntity() 
	{
		super("Entity ESP", Category.RENDER, "Highlights entities");
	}
	
	public static Mode mode = new Mode("Mode", "Hitbox", "Wireframe");

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
    
    @Override
    public void setup() 
	{
        addSetting(mode);
        addSetting(players);
        addSetting(animals);
        addSetting(mobs);
        addSetting(neutral);
        addSetting(vehicles);
        addSetting(items);
        addSetting(lineWidth);
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
    public void onEntityRender(RenderLivingEvent event)
    {
    	if(mode.getValue() != 1)
    		return;
    	
    	Entity entity = event.entity;
    	
    	// pretty sure this is spectators? ?
    	if(entity instanceof EntityPlayerSP || !(entity instanceof EntityLivingBase ))
    		return;

    	if(entity instanceof EntityPlayer) 
    	{
    		if(!players.getValue() || entity.getName() == this.mc.thePlayer.getName()) 
    			return;
    		RenderUtil.setColor(playerPicker.getColor());
    	}
    	else
    	if(EntityUtil.isHostileMob(entity))
    	{
    		if(!mobs.getValue()) 
    			return;
    		RenderUtil.setColor(mobsPicker.getColor());
    	}
    	else
    	if(EntityUtil.isPassive(entity)) 
    	{
    		if(!animals.getValue()) 
    			return;
    		RenderUtil.setColor(animalPicker.getColor());
    	}
    	else
    	if(EntityUtil.isNeutralMob(entity)) 
    	{
    		if(neutral.getValue()) 
    			return;
    		RenderUtil.setColor(neutralPicker.getColor());
    	}        	  	
    	
    	event.setCancelled(true);
    	
    	GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
    	GL11.glLineWidth((float)lineWidth.getValue());
        
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glEnable(GL11.GL_POLYGON_OFFSET_LINE);
        
        GL11.glClear(GL11.GL_FRONT_LEFT);
        GL11.glClearStencil(15);

        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT,  GL11.GL_NICEST);
        
        // render solid color of entity 
        // event.livingBase.renderModelAccessor(event.entity, event.f6, event.f5, event.f8, event.f2, event.f7, 0.0625F);
        
        // render wire outline of entity in white 
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        RenderUtil.resetColor();
        event.livingBase.renderModelAccessor(event.entity, event.f6, event.f5, event.f8, event.f2, event.f7, 0.0625F);

          
        GL11.glDisable(GL11.GL_POLYGON_OFFSET_LINE);
        GL11.glDisable(GL11.GL_STENCIL_TEST);
        GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_BLEND);
        
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glPopAttrib();
        RenderUtil.resetColor();
    }

    @EventTarget
	public void onRenderWorld(RenderWorldLastEvent event) 
	{
		if(nullCheck())
			return;
		
		if(ESPEntity.mode.getValue() != 0)
			return;
		
		GL11.glPushMatrix();
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDepthMask(false);

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);

		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL32.GL_DEPTH_CLAMP);
		
		GL11.glLineWidth((float)lineWidth.getValue());

        
        for(Entity entity : this.mc.theWorld.loadedEntityList) 
		{
        	// pretty sure this is spectators? ?
        	if(entity instanceof EntityPlayerSP || !(entity instanceof EntityLivingBase ))
        		continue;
 
        	if(entity instanceof EntityPlayer) 
        	{
        		if(players.getValue() && entity.getName() != this.mc.thePlayer.getName()) 
        		{
        			RenderUtil.setColor(playerPicker.getColor());
        			RenderUtil.renderEntityHitbox(entity);
        		}
        		continue;
        	}
        	
        	if(entity instanceof EntityItem) 
        	{
        		if(items.getValue()) 
        		{
        			RenderUtil.setColor(itemsPicker.getColor());	
        			RenderUtil.renderEntityHitbox(entity);
        		}
        		continue;
        	}
        	
        	if(EntityUtil.isHostileMob(entity))
        	{
        		if(mobs.getValue()) 
        		{
        			RenderUtil.setColor(mobsPicker.getColor());
        			RenderUtil.renderEntityHitbox(entity);
        		}
        		continue;
        	}
        	
        	if(EntityUtil.isPassive(entity)) 
        	{
        		if(animals.getValue()) 
        		{
        			RenderUtil.setColor(animalPicker.getColor());
        			RenderUtil.renderEntityHitbox(entity);
        		}
        		continue;
        	}
        	
        	if(EntityUtil.isNeutralMob(entity)) 
        	{
        		if(neutral.getValue()) 
        		{
        			RenderUtil.setColor(neutralPicker.getColor());
        			RenderUtil.renderEntityHitbox(entity);
        		}
        		continue;
        	}        	
        	
        	if(EntityUtil.isVehicle(entity)) 
        	{
        		if(vehicles.getValue()) 
        		{
        			RenderUtil.setColor(vehiclesPicker.getColor());
        			RenderUtil.renderEntityHitbox(entity);
        		}
        	}
		}

		GL11.glDisable(GL32.GL_DEPTH_CLAMP);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glDisable(GL11.GL_BLEND);
		
		GL11.glDepthMask(true);
		GL11.glPopMatrix();	
		
		// prevents hotbar / hand from being messed up by color changes 
		RenderUtil.resetColor();
    }
}





