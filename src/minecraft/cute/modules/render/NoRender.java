package cute.modules.render;

import cute.eventapi.EventTarget;
import cute.events.EntityViewRenderEvent;
import cute.modules.Module;
import cute.modules.enums.Category;
import cute.settings.Checkbox;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.src.Config;

public class NoRender extends Module
{
	public NoRender() 
	{
		super("No Render", Category.RENDER, "Prevent certain things from being rendered");
	}
	
	public static Checkbox fog = new Checkbox("All Fog", true);
	public static Checkbox blindnessFog = new Checkbox("Blindness Fog", true);
	public static Checkbox water = new Checkbox("Water", false);
	public static Checkbox blocks = new Checkbox("Blocks", false);
	
	@Override
    public void setup() 
	{
        addSetting(fog);
        addSetting(blindnessFog);
        addSetting(water);
        addSetting(blocks);
    }
	
	
    @EventTarget
    public void onFogRender(EntityViewRenderEvent.RenderFogEvent event) 
    {
    	if(!fog.getValue()) 
    		return;
    	
		event.setCancelled(true);
		
		GlStateManager.setFogDensity(0);
    	
		// set the normal fog start / end 
    	float f3 = event.farPlaneDistance;
    	
    	if (event.fogMode == -1)
        {
            GlStateManager.setFogStart(0.0F);
            GlStateManager.setFogEnd(f3);
        }
        else
        {
            GlStateManager.setFogStart(f3 * Config.getFogStart());
            GlStateManager.setFogEnd(f3);
        }
    }
    
    
    @EventTarget
    public void onBlindnessFogRender(EntityViewRenderEvent.RenderBlindnessFogEvent event) 
    {
    	if(!blindnessFog.getValue())
    		return;
    	
    	// prevent the blindness fog from being rendered
    	// see net.minecraft.client.renderer.EntityRenderer.setupFog
    	event.setCancelled(true); 
    	
    	GlStateManager.setFogDensity(0);
    	
    	// set the normal fog start / end 
    	float f3 = event.farPlaneDistance;
    	
    	if (event.fogMode == -1)
        {
            GlStateManager.setFogStart(0.0F);
            GlStateManager.setFogEnd(f3);
        }
        else
        {
            GlStateManager.setFogStart(f3 * Config.getFogStart());
            GlStateManager.setFogEnd(f3);
        }
    }

    @EventTarget
    public void onWaterRender(EntityViewRenderEvent.RenderWater event) 
    {
    	if(!water.getValue())
    		return;
    	
    	event.setCancelled(true); 
    }

    
    @EventTarget
    public void onBlocksRender(EntityViewRenderEvent.RenderSolid event) 
    {
    	if(!blocks.getValue())
    		return;
    	
    	event.setCancelled(true); 
    }
}







