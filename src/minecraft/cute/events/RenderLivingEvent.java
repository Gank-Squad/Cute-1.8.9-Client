package cute.events;

import cute.eventapi.events.Cancellable;
import cute.eventapi.events.Event;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class RenderLivingEvent<T extends EntityLivingBase> implements Cancellable, Event
{
    private boolean canceled;
    
    public final RendererLivingEntity livingBase;
    public final T entity;
    public final float f6;
    public final float f5;
    public final float f8;
    public final float f2;
    public final float f7;
    public final float scale;
    
    public RenderLivingEvent(RendererLivingEntity livingBase, T entity2, float f6, float f5, float f8, float f2, float f7, float scale)
    {
        this.canceled = false;

        this.livingBase = livingBase;
        this.entity = entity2;
        this.f6 = f6;
        this.f5 = f5;
        this.f8 = f8;
        this.f2 = f2;
        this.f7 = f7;
        this.scale = scale;
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
