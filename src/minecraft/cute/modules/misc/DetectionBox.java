package cute.modules.misc;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL32;

import cute.eventapi.EventTarget;
import cute.events.RenderWorldLastEvent;
import cute.events.SettingChangedEvent;
import cute.modules.Module;
import cute.modules.enums.Category;
import cute.modules.render.ESPBlocks;
import cute.settings.Checkbox;
import cute.settings.ListSelection;
import cute.settings.Slider;
import cute.settings.custom.CustomListItem;
import cute.settings.enums.ListInputType;
import cute.settings.enums.ListType;
import cute.util.EntityUtil;
import cute.util.RenderUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class DetectionBox extends Module
{
	public static String label;
	public static int alerts = 0;
	
	public ArrayList<DetectionBoxes> list = new ArrayList<DetectionBoxes>();
	
	public static Checkbox players = 	new Checkbox("Players"		, 	true);
	public static Checkbox hostile = 	new Checkbox("Hostile Mobs"	,	true);
	public static Checkbox passive = 	new Checkbox("Passive Mobs"	, 	true);
	public static Checkbox neutral = 	new Checkbox("Neutral Mobs"	, 	true);
	public static Checkbox vehicle = 	new Checkbox("Vehicles"		, 	true);
	public static Checkbox projectile = new Checkbox("Projectiles"	, 	true);
	public static Checkbox item = 		new Checkbox("Items"		, 	true);
	public static Checkbox show = 		new Checkbox("Show Boxes"	,	false);
	public static Checkbox anchor = 	new Checkbox("Anchor on Player", true);
	public static Checkbox add = 	new Checkbox("Create new Box"	,	false);
	
	public static Slider width = 		new Slider("Width", 1, 5, 160, 1);
	public static Slider height = 		new Slider("Height", 1, 5, 512, 1);
	
	public static ListSelection inputLabel = new ListSelection<String>("Box List", new ArrayList<String>(), ListType.CUSTOM, ListInputType.TEXT);
	public DetectionBox() 
	{
		super("Detection Box", Category.MISC, "a box that sends an alert when an entity enters it");
	}
	
	@Override
	public void setup()
	{
		addSetting(inputLabel);
		addSetting(players);
		addSetting(hostile);
		addSetting(passive);
		addSetting(neutral);
		addSetting(vehicle);
		addSetting(projectile);
		addSetting(item);
		addSetting(anchor);
		addSetting(width);
		addSetting(height);
		addSetting(add);
		addSetting(show);
	}
	
	public void addBox(
			boolean players,
			boolean hostile,
			boolean passive,
			boolean neutral,
			boolean vehicle,
			boolean projectile,
			boolean item,
			boolean anchorPlayer,
			AxisAlignedBB box,
			String label)
	{
		list.add(new DetectionBoxes(
				players,
				hostile,
				passive,
				neutral,
				vehicle,
				projectile,
				item,
				anchorPlayer,
				box,
				label
				));
		
	}
	
	public boolean removeBox(String label)
	{
		for (DetectionBoxes i : list)
		{
			if (label == i.label)
				return list.remove(i);
		}
		return false;
	}
	
	@EventTarget
	public void settingChanged(SettingChangedEvent e)
	{
		inputLabel.term = inputLabel.term == null ? "no name" : inputLabel.term;
		if(e.settingID == inputLabel.getID())
		{
			for (Object i : e.args)
			{
				if (i instanceof CustomListItem)
				{
					removeBox(((CustomListItem)i).toString());
				}
			}
			return;
		}
		
		if(e.settingID != add.getID())
			return;
		
		if (!add.getValue())
			return;
		
		for (DetectionBoxes i : list)
		{
			if (inputLabel.term == i.label)
				return;
		}
		
		CustomListItem custom = new CustomListItem(inputLabel.term);
		inputLabel.enableItem(custom);
		
		addBox(
				players.getValue(), hostile.getValue()   , passive.getValue(), neutral.getValue(),
				vehicle.getValue(), projectile.getValue(), item.getValue()   , anchor.getValue(),
				new AxisAlignedBB(-width.getValue() / 2, 
						 -height.getValue() / 2, 
						 -width.getValue() / 2,
						 width.getValue() / 2, 
						 height.getValue() / 2, 
						 width.getValue() / 2),
				inputLabel.term
				);
	}
	
	// couldn't think of a better event, but there probably is one
    @EventTarget
	public void onRenderWorld(RenderWorldLastEvent event) 
	{
    	// will probably want some way to display simultaneous alerts
    	// and I dont want to have a separate hud element for each one
    	alerts = 0;
    	label = "";
    	
    	if(show.getValue())
		{
	    	GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
			GL11.glDepthMask(false);
	
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDisable(GL11.GL_ALPHA_TEST);
	
			GL11.glEnable(GL11.GL_LINE_SMOOTH);
			
			double doubleX = this.mc.thePlayer.lastTickPosX
	                + (this.mc.thePlayer.posX - this.mc.thePlayer.lastTickPosX)
	                * event.partialTicks;
	
	        double doubleY = this.mc.thePlayer.lastTickPosY
	                + (this.mc.thePlayer.posY - this.mc.thePlayer.lastTickPosY)
	                * event.partialTicks;
	
	        double doubleZ = this.mc.thePlayer.lastTickPosZ
	                + (this.mc.thePlayer.posZ - this.mc.thePlayer.lastTickPosZ)
	                * event.partialTicks;
	
	        
	        GL11.glTranslated(-doubleX, -doubleY, -doubleZ);
		}
    	
		for(Entity e : this.mc.theWorld.loadedEntityList) 
		{
			for (DetectionBoxes l : list)
			{
				if (l.trigger(e))
				{
					label = l.label;
					alerts++;
				}
				
				if(show.getValue())
				{
					RenderUtil.renderBoundingBox(l.getBoundsBB());	
				}
			}
		}
		
		if(show.getValue())
		{
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
}
