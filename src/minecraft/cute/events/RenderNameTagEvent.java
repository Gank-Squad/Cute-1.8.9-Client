package cute.events;

import cute.eventapi.events.Cancellable;
import cute.eventapi.events.Event;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;

public class RenderNameTagEvent<T extends Entity> implements Event, Cancellable 
{
	private boolean canceled;

	public final Tessellator tessellator;
	public final WorldRenderer worldRenderer;
	public final FontRenderer fontRenderer;
	public final T entity;
	public final String name;
	public final double x;
	public final double y;
	public final double z;
	
	
    public RenderNameTagEvent(Tessellator tess, WorldRenderer wr, FontRenderer fontr, T entityIn, String str, double x, double y, double z)
    {
        this.canceled = false;
        this.tessellator = tess;
        this.worldRenderer = wr;
        this.fontRenderer = fontr;
        this.entity = entityIn;
        this.name = str;
        this.x = x;
        this.y = y;
        this.z = z;
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
