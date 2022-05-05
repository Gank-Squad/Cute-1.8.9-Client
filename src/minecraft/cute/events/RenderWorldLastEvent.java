package cute.events;

import cute.eventapi.events.Event;
import net.minecraft.client.renderer.RenderGlobal;

public class RenderWorldLastEvent implements Event 
{
	public final RenderGlobal context;
	public final float partialTicks;
	
	public RenderWorldLastEvent(RenderGlobal context, float partialTicks) 
	{
		this.context = context;
		this.partialTicks = partialTicks;
	}
	
	
	public float getPartialTicks()
	{
		return this.partialTicks;
	}
	
	public RenderGlobal getContext()
	{
		return this.context;
	}
}
