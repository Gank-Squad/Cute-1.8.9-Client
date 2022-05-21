package cute.modules.gui.hud;

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.Sets;

import cute.eventapi.EventManager;
import cute.eventapi.EventTarget;
import cute.events.RenderEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiContainer;
//import cute.modules.Module;


public class HudManager 
{
	private HudManager()
	{
		
	}
	
	private static HudManager instance = null;
	
//	private static HudConfigScreen nyah;
	
	public static HudManager getInstance()
	{
		if (instance != null)
		{
			return instance;
		}
		
		instance = new HudManager();
		EventManager.register(instance);
		
		return instance;
		
	}
	
	private Set<IRender> registeredRenderers = Sets.newHashSet();
	
	public void register(IRender... renderers)
	{
		for (IRender render : renderers)
		{
			this.registeredRenderers.add(render);
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
//		this.nyah = new HudConfigScreen(this);
//		Minecraft.getMinecraft().displayGuiScreen(nyah);
		Minecraft.getMinecraft().displayGuiScreen(new HudConfigScreen(this));
	}
	
	@EventTarget
	public void onRender(RenderEvent e)
	{
		if (
				Minecraft.getMinecraft().currentScreen == null || 
				Minecraft.getMinecraft().currentScreen instanceof GuiContainer || 
				Minecraft.getMinecraft().currentScreen instanceof GuiChat
			)
		{
			for(IRender renderer : registeredRenderers)
			{
				callRenderer(renderer);
			}
		}
	}
	
	
	private void callRenderer(IRender renderer)
	{
		if (!renderer.isEnabled())
		{
			return;
		}
		ScreenPosition pos = renderer.load();
		
		if (pos == null)
		{
			pos = ScreenPosition.fromRelative(0.5,0.5);
		}
		
		renderer.render(pos);
	}
	
}
