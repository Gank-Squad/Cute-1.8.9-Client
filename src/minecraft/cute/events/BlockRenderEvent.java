package cute.events;

import cute.eventapi.events.Cancellable;
import cute.eventapi.events.Event;
import net.minecraft.block.Block;

public class BlockRenderEvent implements Event, Cancellable 
{
	private boolean canceled;
	
	public final Block block; 
	
	public BlockRenderEvent(Block block)
	{
		this.block = block;
		this.canceled = false;
	}
	
	public boolean isCancelled()
    {
    	return this.canceled;
    }

    public void setCancelled(boolean state)
    {
    	this.canceled = state;
    }
}
