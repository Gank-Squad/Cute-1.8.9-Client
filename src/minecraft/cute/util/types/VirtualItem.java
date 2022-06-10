package cute.util.types;

import net.minecraft.item.ItemStack;

public class VirtualItem 
{
	public final ItemStack stack;
	public final String displayName;
	
	public  VirtualItem(ItemStack stack, String name)
	{
		this.stack = stack;
		this.displayName = name;
		
	}
}
