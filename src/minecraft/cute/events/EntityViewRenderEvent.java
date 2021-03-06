package cute.events;

import cute.eventapi.events.Cancellable;
import cute.eventapi.events.Event;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;

public class EntityViewRenderEvent implements Event 
{
	public final EntityRenderer renderer;
    public final Entity entity;
    public final Block block;
    public final double renderPartialTicks;

    public EntityViewRenderEvent(EntityRenderer renderer, Entity entity, Block block, double renderPartialTicks)
    {
        this.renderer = renderer;
        this.entity = entity;
        this.block = block;
        this.renderPartialTicks = renderPartialTicks;
    }
	
    public static class RenderSolid extends EntityViewRenderEvent implements Cancellable
    {
        private boolean canceled;

        public RenderSolid(EntityRenderer renderer, Entity entity, Block block, double renderPartialTicks)
        {
            super(renderer, entity, block, renderPartialTicks);
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
    
    public static class RenderWater extends EntityViewRenderEvent implements Cancellable
    {
        private boolean canceled;

        public RenderWater(EntityRenderer renderer, Entity entity, Block block, double renderPartialTicks)
        {
            super(renderer, entity, block, renderPartialTicks);
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
	
    
    
    public static class RenderFogEvent extends EntityViewRenderEvent implements Cancellable
    {
        public final int fogMode;
        public final float farPlaneDistance;
        private boolean canceled;

        public RenderFogEvent(EntityRenderer renderer, Entity entity, Block block, double renderPartialTicks, int fogMode, float farPlaneDistance)
        {
            super(renderer, entity, block, renderPartialTicks);
            this.fogMode = fogMode;
            this.farPlaneDistance = farPlaneDistance;
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
    
    
    public static class RenderBlindnessFogEvent extends EntityViewRenderEvent implements Cancellable
    {
        public final int fogMode;
        public final float farPlaneDistance;
        private boolean canceled;

        public RenderBlindnessFogEvent(EntityRenderer renderer, Entity entity, Block block, double renderPartialTicks, int fogMode, float farPlaneDistance)
        {
            super(renderer, entity, block, renderPartialTicks);
            this.fogMode = fogMode;
            this.farPlaneDistance = farPlaneDistance;
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
}
