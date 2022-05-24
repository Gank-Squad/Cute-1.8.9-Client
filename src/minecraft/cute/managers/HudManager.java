package cute.managers;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

import cute.eventapi.EventTarget;
import cute.events.RenderEvent;
import cute.modules.gui.hud.HudConfigScreen;
import cute.modules.gui.hud.IRender;
import cute.modules.gui.hud.display.DraggableText;
import cute.modules.gui.hud.display.DraggableObj;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiContainer;


public class HudManager extends BaseManager
{
	public static final HudManager INSTANCE = new HudManager();
	
	private HudConfigScreen hudScreen;
	
	private Set<IRender> registeredRenderers = Sets.newHashSet();
	
	public static List<IRender> defaultRenders = Arrays.asList
			(
				//new DraggableText()
				new DraggableObj()
			);

	public void registerDefault()
	{
		for(IRender r : HudManager.defaultRenders)
		{
			this.register(r);
		}
	}

	public void register(IRender... renderers)
	{
		for (IRender render : renderers)
		{
			this.registeredRenderers.add(render);
		}
	}
	
	public void unregisterDefault()
	{
		for(IRender r : HudManager.defaultRenders)
		{
			this.unregister(r);
		}
	}
	
	public void unregister(IRender... renderers)
	{
		for (IRender render : renderers)
		{
			this.registeredRenderers.remove(render);
		}
	}
	
	public Collection<IRender> getRegisteredRenderers()
	{
		return Sets.newHashSet(registeredRenderers);
	}
	
	public void openConfigScreen()
	{
		if(this.hudScreen == null)
			this.hudScreen = new HudConfigScreen(this);

		this.mc.displayGuiScreen(this.hudScreen);
	}
	
	@EventTarget
	public void onRender(RenderEvent e)
	{
		if (
				this.mc.currentScreen == null || 
				this.mc.currentScreen instanceof GuiContainer || 
				this.mc.currentScreen instanceof GuiChat
			)
		{
			for(IRender renderer : registeredRenderers)
			{
				if(!renderer.isEnabled())
					continue;
				
				renderer.render();
			}
		}
	}
}
