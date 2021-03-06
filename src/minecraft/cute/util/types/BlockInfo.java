package cute.util.types;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

public class BlockInfo 
{
	// the creative menu block name
	public String displayName;
	
	// the registry id / name (minecraft:block_name) 
	public final String registryName;
	
	public final int blockID;
	
	public final Block block;
	
	public final ResourceLocation location;
	
	public BlockInfo(int id,  ResourceLocation loc)
	{
		this.blockID = id;
		
		this.location = loc;
		
		this.registryName = loc.toString();
		
		this.block = Block.blockRegistry.getObject(loc);

		this.displayName = this.block.getLocalizedName();
	}
}
