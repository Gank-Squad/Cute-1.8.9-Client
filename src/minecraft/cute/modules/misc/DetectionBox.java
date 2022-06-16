package cute.modules.misc;

import java.util.ArrayList;

import cute.eventapi.EventTarget;
import cute.events.RenderWorldLastEvent;
import cute.events.SettingChangedEvent;
import cute.modules.Module;
import cute.modules.enums.Category;
import cute.settings.Checkbox;
import cute.settings.Slider;
import cute.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
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
	public static Checkbox anchor = 	new Checkbox("Anchor on Player", true);
	public static Checkbox add = 	new Checkbox("Create new Box"	,	true);
	
	public static Slider width = 		new Slider("Width", 1, 5, 160, 1);
	public static Slider height = 		new Slider("Height", 1, 5, 512, 1);
	public DetectionBox() 
	{
		super("Detection Box", Category.MISC, "a box that sends an alert when an entity enters it");
	}
	
	@Override
	public void setup()
	{
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
			Vec3 corner1,
			Vec3 corner2,
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
				corner1,
				corner2,
				label
				));
		
	}
	
	@EventTarget
	public void settingChanged(SettingChangedEvent e)
	{
		if(e.settingID == add.getId())
			return;
		if (!add.getValue())
			return;
		addBox(
				players.getValue(),hostile.getValue(),passive.getValue(),neutral.getValue(),
				vehicle.getValue(), projectile.getValue(), item.getValue(), anchor.getValue(),
				new Vec3(-width.getValue() / 2, 
						 -height.getValue() / 2, 
						 -width.getValue() / 2),
				new Vec3(width.getValue() / 2, 
						 height.getValue() / 2, 
						 width.getValue() / 2),
				"label;3c"
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
		for(Entity e : this.mc.theWorld.loadedEntityList) 
		{
			for (DetectionBoxes l : list)
			{
				if (l.trigger(e))
				{
					label = l.label;
					alerts++;
				}
			}
		}
	}
}