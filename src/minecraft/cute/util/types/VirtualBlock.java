package cute.util.types;

import java.awt.Color;
import java.util.ArrayList;
import java.util.stream.Stream;

import cute.util.Cache;
import cute.settings.ListSelection;
import cute.modules.render.ESPBlocks;
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
	
	// updates the cache colors / enable and adds the block to the xray list
	public static void updateCacheFromString(String str) 
	{
		String[] info = str.split(" ");
		
		if(info.length != 8)
			return;
		
		try
		{
			int blockId = Integer.parseInt(info[5]); 
			
			Stream<VirtualBlock> s = Cache.BLOCKS.stream().filter(x -> x.blockID == blockId);
			
			s.forEach(x -> 
			{
				try 
				{
					x.r =  (byte) Integer.parseInt(info[0]);
					x.g =  (byte) Integer.parseInt(info[1]);
					x.b =  (byte) Integer.parseInt(info[2]);
					x.a =  (byte) Integer.parseInt(info[3]);
					x.meta    =   Integer.parseInt(info[4]);
					x.displayName = info[6];
					x.enabled = Boolean.parseBoolean(info[7]);
					ESPBlocks.blocks.enableItem(x);
				}
				catch(NumberFormatException e) {}
			});
		}
		catch(NumberFormatException e){}
	}
	
	
	public String toString() 
	{
		return this.r + " " + 
			   this.g + " " + 
			   this.b + " " + 
			   this.a + " " + 
			   this.meta + " " + 
			   this.blockID + " " + 
			   this.displayName + " " + 
			   this.enabled;
	}
}



