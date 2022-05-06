package cute.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

import cute.util.types.BlockInfo;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;

public class Cache 
{

	public static ArrayList<String> POTION_NAMES = new ArrayList<String>();
	public static ArrayList<BlockInfo> BLOCKS = new ArrayList<BlockInfo>();
	
	public static void loadCache()
	{
		POTION_NAMES.clear();
		BLOCKS.clear();
		
		Set<ResourceLocation> pots = Potion.getPotionLocations();
		
		for(ResourceLocation rl : pots)
		{
//			System.out.println(rl.toString());
			POTION_NAMES.add(rl.toString());
//			potionCache.add(Potion.getPotionFromResourceLocation(rl.toString()));
		}
		
		
		
		
		// this is all stupid, but who cares, this function should only
		// be called once at startup
		Set<ResourceLocation> bloc = Block.blockRegistry.getKeys();
		
		// using this to sort by in game name
		Set<String> blocs = new TreeSet<String>();
		
		for(ResourceLocation rl : bloc)
		{
			Block b = Block.blockRegistry.getObject(rl);
			
			if(b == Blocks.air)
				continue;
			
			// adding the resource location at the end so i can use it later
			blocs.add(b.getLocalizedName() + "|" + rl.toString());
		}
		
		// now that we have the sorted block names, fill the list
		Iterator<String> iterator = blocs.iterator();
        while (iterator.hasNext())
        {
        	// this is just really good 
        	String n = iterator.next();
            String[] name_id = n.split("[|]");
            
            BLOCKS.add(new BlockInfo(new ResourceLocation(name_id[1])));
        }
	}
	
	
	
	
	
	
	
	public static BlockInfo[] searchForBlock(String lookup)
	{
		Stream<BlockInfo> s = BLOCKS.stream().
				filter(x -> x.displayName.toLowerCase().contains(lookup));
		
		
		return (BlockInfo[]) s.toArray();
		
	}
}








