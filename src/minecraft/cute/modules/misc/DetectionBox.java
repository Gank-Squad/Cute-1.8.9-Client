package cute.modules.misc;

import cute.eventapi.EventTarget;
import cute.events.RenderWorldLastEvent;
import cute.modules.Module;
import cute.modules.enums.Category;
import cute.settings.Checkbox;
import cute.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

public class DetectionBox extends Module
{
	public static String label;
	public static boolean inBox = false;
	int color;
	
	private Vec3 corner1;
	private Vec3 corner2;
	
	public static Checkbox players = 	new Checkbox("Players"		, 	true);
	public static Checkbox hostile = 	new Checkbox("Hostile Mobs"	,	true);
	public static Checkbox passive = 	new Checkbox("Passive Mobs"	, 	true);
	public static Checkbox neutral = 	new Checkbox("Neutral Mobs"	, 	true);
	public static Checkbox vehicle = 	new Checkbox("Vehicles"		, 	true);
	public static Checkbox projectile = new Checkbox("Projectiles"	, 	true);
	public static Checkbox item = 		new Checkbox("Items"		, 	true);
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
	}
	
	// couldn't think of a better event, but there probably is one
    @EventTarget
	public void onRenderWorld(RenderWorldLastEvent event) 
	{
    	corner1 = new Vec3(mc.thePlayer.posX - 5, mc.thePlayer.posY - 5, mc.thePlayer.posZ - 5);
		corner2 = new Vec3(mc.thePlayer.posX + 5, mc.thePlayer.posY + 5, mc.thePlayer.posZ + 5);
		
		inBox = false;
		for(Entity e : this.mc.theWorld.loadedEntityList) 
		{
			if (e == mc.thePlayer)
				continue;
			if (e.posX < corner1.xCoord || e.posX > corner2.xCoord)
				continue;
			if (e.posY < corner1.yCoord || e.posY > corner2.yCoord)
				continue;
			if (e.posZ < corner1.zCoord || e.posZ > corner2.zCoord)
				continue;
			
			if(e instanceof EntityPlayer 	&& !players.getValue())
				continue;
			if(EntityUtil.isHostileMob(e) 	&& !hostile.getValue())
				continue;
			if(EntityUtil.isPassive(e) 		&& !passive.getValue())
				continue;
			if(EntityUtil.isNeutralMob(e)	&& !neutral.getValue())
				continue;
			if(EntityUtil.isVehicle(e)		&& !vehicle.getValue())
				continue;
			if(EntityUtil.isProjectile(e) 	&& !projectile.getValue())
				continue;
			if(e instanceof EntityItem		&& !item.getValue())
				continue;
			
			inBox = true;
		}
	}
}
