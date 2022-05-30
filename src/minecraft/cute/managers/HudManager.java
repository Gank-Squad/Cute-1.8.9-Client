package cute.managers;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import com.google.common.collect.Sets;

import cute.eventapi.EventTarget;
import cute.events.RenderEvent;
import cute.ui.hud.HudConfigScreen;
import cute.ui.hud.IRender;
import cute.ui.hud.display.DraggableObj;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;


public class HudManager extends BaseManager
{
	public static final HudManager INSTANCE = new HudManager();
	
	private HudConfigScreen hudScreen;
	
	private Set<IRender> registeredRenderers = Sets.newHashSet();
	
	private Stack guiStack = new Stack();
	
	public static List<IRender> defaultRenders = Arrays.asList
			(
				//new DraggableText()
//				new DraggableObj(0,0)
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

		this.guiStack.add(this.mc.currentScreen);
		
		this.mc.displayGuiScreen(this.hudScreen);
	}
	
	public void restoreOldScreen()
	{
		if(this.guiStack.size() < 1)
			return;
		
		GuiScreen screen = (GuiScreen)this.guiStack.pop();
		
		if (screen == null)
			return;
		
		this.mc.displayGuiScreen(screen);
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
