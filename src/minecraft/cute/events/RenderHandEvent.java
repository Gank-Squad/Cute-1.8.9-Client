package cute.events;

import cute.eventapi.events.Cancellable;
import cute.eventapi.events.Event;
import net.minecraft.client.renderer.RenderGlobal;

public class RenderHandEvent implements Event, Cancellable
{
	private boolean canceled;
	
	public final RenderGlobal renderGlobal;
	public final float partialTicks;
	public final int pass;
	
	public RenderHandEvent(RenderGlobal renderglobal, float partialTicks, int pass)
	{
		this.renderGlobal = renderglobal;
		this.partialTicks = partialTicks;
		this.pass = pass;
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
