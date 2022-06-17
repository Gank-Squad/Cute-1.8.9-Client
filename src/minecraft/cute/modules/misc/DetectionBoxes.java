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
	private Vec3 corner1;
	private Vec3 corner2;
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
	DetectionBoxes(
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
		this.corner1 = corner1;
		this.corner2 = corner2;
		this.anchorPlayer = anchorPlayer;
		
		if (!anchorPlayer)
		{
			this.corner1 = new Vec3(
					mc.thePlayer.posX - corner1.xCoord,
					mc.thePlayer.posY - corner1.yCoord,
					mc.thePlayer.posZ - corner1.zCoord
					);
			this.corner2 = new Vec3(
					mc.thePlayer.posX + corner1.xCoord,
					mc.thePlayer.posY + corner1.yCoord,
					mc.thePlayer.posZ + corner1.zCoord
					);
		}
		
		
		this.label = label;
	}
	
	Vec3[] getBounds()
	{
		Vec3[] arr = new Vec3[2];
		
		if (anchorPlayer)
		{
			arr[0] = new Vec3(
					mc.thePlayer.posX - corner1.xCoord,
					mc.thePlayer.posY - corner1.yCoord,
					mc.thePlayer.posZ - corner1.zCoord
					);
			arr[1] = new Vec3(
					mc.thePlayer.posX + corner1.xCoord,
					mc.thePlayer.posY + corner1.yCoord,
					mc.thePlayer.posZ + corner1.zCoord
					);
			return arr;
		}
		arr[0] = corner1;
		arr[1] = corner2;
		return arr;
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
	
	public void unAnchor()
	{
		if (!anchorPlayer)
			return;
		
		anchorPlayer = false;
		corner1 = new Vec3(
				corner1.xCoord + mc.thePlayer.posX,
				corner1.yCoord + mc.thePlayer.posY,
				corner1.zCoord + mc.thePlayer.posZ
				);
		corner2 = new Vec3(
				corner2.xCoord + mc.thePlayer.posX,
				corner2.yCoord + mc.thePlayer.posY,
				corner2.zCoord + mc.thePlayer.posZ
				);
	}
	public void anchor()
	{
		if (anchorPlayer)
			return;
		
		anchorPlayer = true;
		corner1 = new Vec3(
				corner1.xCoord - mc.thePlayer.posX,
				corner1.yCoord - mc.thePlayer.posY,
				corner1.zCoord - mc.thePlayer.posZ
				);
		corner2 = new Vec3(
				corner2.xCoord - mc.thePlayer.posX,
				corner2.yCoord - mc.thePlayer.posY,
				corner2.zCoord - mc.thePlayer.posZ
				);
	}
	
	public boolean trigger(Entity e)
	{
		Vec3[] bounds = getBounds();
		if (e == mc.thePlayer)
			return false;
		if (e.posX > bounds[0].xCoord || e.posX < bounds[1].xCoord)
			return false;
		if (e.posY > bounds[0].yCoord || e.posY < bounds[1].yCoord)
			return false;
		if (e.posZ > bounds[0].zCoord || e.posZ < bounds[1].zCoord)
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
