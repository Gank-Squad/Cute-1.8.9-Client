package cute.modules.misc;

import cute.util.EntityUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class DetectionBoxes 
{
	private AxisAlignedBB box;
	
	// if this is false, corner1/2 will have the absolute positions
	// if this is true, corner1/2 will have the position relative to the player
	private boolean anchorPlayer;
	
	public boolean players;
	public boolean hostile;
	public boolean passive;
	public boolean neutral;
	public boolean vehicle;
	public boolean projectile;
	public boolean item;
	
	public String label;
	
	Minecraft mc = Minecraft.getMinecraft();
	
	DetectionBoxes()
	{
		
	}
	DetectionBoxes(
			boolean players,
			boolean hostile,
			boolean passive,
			boolean neutral,
			boolean vehicle,
			boolean projectile,
			boolean item,
			boolean anchorPlayer,
			AxisAlignedBB box,
			String label
			)
	{
		this.players = players;
		this.hostile = hostile;
		this.passive = passive;
		this.neutral = neutral;
		this.vehicle = vehicle;
		this.projectile = projectile;
		this.item = item;
		this.box = box;
		this.anchorPlayer = anchorPlayer;
		if (!anchorPlayer)
		{
			this.box = new AxisAlignedBB(
					mc.thePlayer.posX - box.minX,
					mc.thePlayer.posY - box.minY,
					mc.thePlayer.posZ - box.minZ,
					
					mc.thePlayer.posX - box.maxX,
					mc.thePlayer.posY - box.maxY,
					mc.thePlayer.posZ - box.maxZ
					);
		}
		this.label = label;
	}
	
	AxisAlignedBB getBoundsBB()
	{
		if (anchorPlayer)
		{
			return new AxisAlignedBB(
					mc.thePlayer.posX - box.minX,
					mc.thePlayer.posY - box.minY,
					mc.thePlayer.posZ - box.minZ,
					
					mc.thePlayer.posX - box.maxX,
					mc.thePlayer.posY - box.maxY,
					mc.thePlayer.posZ - box.maxZ
					);
		}
		return box;
	}
	
	public boolean trigger(Entity e)
	{
//		return box.intersectsWith(e.getCollisionBox(e));
		AxisAlignedBB boundsBB = getBoundsBB();
		if (e == mc.thePlayer)
			return false;
		if (e.posX < boundsBB.minX || e.posX > boundsBB.maxX)
			return false;
		if (e.posY < boundsBB.minY || e.posY > boundsBB.maxY)
			return false;
		if (e.posZ < boundsBB.minZ || e.posZ > boundsBB.maxZ)
			return false;
		
		if(e instanceof EntityPlayer 	&& !players)
			return false;
		if(EntityUtil.isHostileMob(e) 	&& !hostile)
			return false;
		if(EntityUtil.isPassive(e) 		&& !passive)
			return false;
		if(EntityUtil.isNeutralMob(e)	&& !neutral)
			return false;
		if(EntityUtil.isVehicle(e)		&& !vehicle)
			return false;
		if(EntityUtil.isProjectile(e) 	&& !projectile)
			return false;
		if(e instanceof EntityItem		&& !item)
			return false;
		return true;
	}
}
