package cute.util.types;

import java.awt.Color;
import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;

public class VirtualBlock extends BlockInfo
{
	public static ArrayList<VirtualBlock> vBlocks = new ArrayList<VirtualBlock>();
	
	public byte r;
	public byte g;
	public byte b;
	public byte a;
	
	// offsets when drawing the block,
	// x1 should be added to the starting x pos and so on... 
	public float x1 = 0f;
	public float y1 = 0f;
	public float z1 = 0f;
	
	public float x2 = 0f;
	public float y2 = 0f;
	public float z2 = 0f;
	
	public int meta;
	
	public boolean enabled;
	

	public VirtualBlock(int id, String displayName, ResourceLocation location, float x1, float y1, float z1, float x2, float y2, float z2)
	{
		super(id, location);
		
		this.displayName = displayName;
		this.meta = -1;
		this.enabled = false;
		this.r = 0;
		this.g = 0;
		this.b = 0;
		this.a = (byte)255;
		
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.z1 = z1;
		this.z2 = z2;
	}
//	
//	public VirtualBlock(ResourceLocation location)
//	{
//		super(location);
//		
//		this.meta = -1;
//		this.enabled = false;
//		this.r = 0;
//		this.g = 0;
//		this.b = 0;
//		this.a = (byte)255;
//	}
//	
//	public VirtualBlock(ResourceLocation loc, boolean enabled, Color c, int meta)
//	{
//		super(loc);
//
//		this.r = (byte)c.getRed();
//		this.g = (byte)c.getGreen();
//		this.b = (byte)c.getBlue();
//		this.a = (byte)c.getAlpha();
//		
//		this.meta = meta;
//		this.enabled = enabled;
//	}
//	
//	public VirtualBlock(ResourceLocation loc, boolean enabled, byte r, byte g, byte b, byte a, byte meta) 
//	{
//		super(loc);
//		
//		this.r = r;
//		this.g = g;
//		this.b = b;
//		this.a = a;
//		
//		this.meta = meta;
//		this.enabled = enabled;
//	}
//	
//	public static VirtualBlock fromRegistryName(String name)
//	{
//		ResourceLocation rl = new ResourceLocation(name);
//		
//		if (!Block.blockRegistry.containsKey(rl)) 
//			return null;
//		
//		return new VirtualBlock(rl);
//	}
//
//	public static VirtualBlock fromRegistryName(String name, boolean enabled, byte r, byte g, byte b, byte a, byte meta)
//	{
//		ResourceLocation rl = new ResourceLocation(name);
//		
//		if (!Block.blockRegistry.containsKey(rl)) 
//			return null;
//		
//		VirtualBlock vb = new VirtualBlock(rl);
//		
//		vb.enabled = enabled;
//		vb.r = r;
//		vb.g = g;
//		vb.b = b;
//		vb.meta = meta;
//		
//		return vb;
//	}
	
	public static void setStandardList() 
	{
//		vBlocks.clear();
//		
//		registerBlock(fromRegistryName("minecraft:lapis_ore", true, 0, 0, 128, 200, -1));
//		registerBlock(fromRegistryName("minecraft:gold_ore", true, 0, 0, 128, 200, -1));
//		registerBlock(fromRegistryName("minecraft:emerald_ore", true, 0, 0, 128, 200, -1));
//		registerBlock(fromRegistryName("minecraft:diamond_ore", true, 0, 0, 128, 200, -1));
//		registerBlock(fromRegistryName("minecraft:coal_ore", true, 0, 0, 128, 200, -1));
//		registerBlock(fromRegistryName("minecraft:iron_ore", true, 0, 0, 128, 200, -1));
//		
//		removeInvalidBlocks();
	}
	

	private static void insertBlock(VirtualBlock vb) 
	{
		for(int i = 0; i < VirtualBlock.vBlocks.size(); i++) 
		{
			if(VirtualBlock.vBlocks.get(i).registryName == vb.registryName) 
			{
				VirtualBlock.vBlocks.set(i, vb);
				return;
			}
		}
		
		VirtualBlock.vBlocks.add(vb);
	}
	
	public static boolean registerBlock(VirtualBlock vb) 
	{
		if (Block.blockRegistry.containsKey(vb.location)) 
		{
			insertBlock(vb);
			return true;
		}
		
		return false;
	}
	
	
	public static void removeInvalidBlocks() 
	{
		for(VirtualBlock b : vBlocks)
		{
			if(b.block == Blocks.air)
				vBlocks.remove(b);
		}
	}
	
//	public static VirtualBlock FromString(String str) 
//	{
//		String[] info = str.split(" ");
//		
//		if(info.length != 7)
//			return null;
//		
//		VirtualBlock result = VirtualBlock.fromRegistryName(info[5]);
//		
//		if(result == null)
//			return null;
//		
//		try
//		{
//			result.r = (byte) Integer.parseInt(info[0]);
//			result.g = (byte) Integer.parseInt(info[1]);
//			result.b = (byte) Integer.parseInt(info[2]);
//			result.a = (byte) Integer.parseInt(info[3]);
//			
//			result.meta = Integer.parseInt(info[4]);
//			
//			result.enabled = Boolean.parseBoolean(info[6]);
//			
//			return result;
//		}
//		catch(NumberFormatException e){}
//		
//		return null;
//	}
//	
//	
	public String toString() 
	{
		return this.r + " " + 
			   this.g + " " + 
			   this.b + " " + 
			   this.a + " " + 
			   this.meta + " " + 
			   this.registryName + " " + 
			   this.enabled;
	}
}




