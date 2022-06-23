package cute.managers;

import java.util.ArrayList;
import java.util.List;

import cute.eventapi.EventTarget;
import cute.events.ClientTickEvent;
import cute.events.KeyDownEvent;
import cute.events.KeyUpEvent;
import cute.modules.Module;
import cute.modules.bot.AntiBot;
import cute.modules.bot.FakePlayer;
import cute.modules.client.Players;
import cute.modules.combat.BowAimbot;
import cute.modules.enums.Category;
import cute.modules.gui.ClickGUI;
import cute.modules.gui.Hud;
import cute.modules.misc.DetectionBox;
import cute.modules.misc.Sounds;
import cute.modules.misc.Sprint;
import cute.modules.misc.Teams;
import cute.modules.render.DataViewer;
import cute.modules.render.ESPBlocks;
import cute.modules.render.ESPEntity;
import cute.modules.render.ESPItem;
import cute.modules.render.Fullbright;
import cute.modules.render.NameTags;
import cute.modules.render.NoRender;
import cute.modules.render.PredictTargets;
import cute.modules.render.ProjectileTracer;
import cute.modules.render.Tracers;
import cute.modules.render.XRay;
import net.minecraft.client.gui.GuiScreen;

public class ModuleManager extends BaseManager
{
	public static final ModuleManager INSTANCE = new ModuleManager();
	
	public static List<Module> modules = new ArrayList<Module>(); 
//			Arrays.asList
//				(
//					new FakePlayer(),
//					
//					new ClickGUI(),
//					new Hud(),
//					new Players(),
//					
//					new ESPBlocks(),
//					new ESPEntity(),
//					new DataViewer(),
//					new Fullbright(),
//					new Tracers(),
//					new ProjectileTracer(),
//					new NameTags(),
//					new NoRender(),
//					new XRay(),
//					
//					new Sounds()
//				);
//	
	
	public static void initModule()
	{
		modules.add(new FakePlayer());
//		modules.add(new BowAimbot());
		modules.add(new AntiBot());
		
		modules.add(new ClickGUI());
		modules.add(new Hud());
		
		modules.add(new Players());
		modules.add(new ESPBlocks());
		modules.add(new ESPEntity());
		modules.add(new ESPItem());
		modules.add(new DataViewer());
		modules.add(new Fullbright());
		modules.add(new Tracers());
		modules.add(new ProjectileTracer());
		modules.add(new PredictTargets());
		modules.add(new NameTags());
		modules.add(new NoRender());
		modules.add(new XRay());
		
		modules.add(new Sounds());
		modules.add(new Sprint());
		modules.add(new DetectionBox());
		modules.add(new Teams());
	}
	
	public static List<Module> getModules()
	{
		return new ArrayList(modules);
	}
	
	public static List<Module> getModulesInCategory(Category cat)
	{
		List<Module> module = new ArrayList();
		
		for (Module m : modules) 
		{
			if (m.getCategory().equals(cat))
				module.add(m);
		}
		
		return module;
	}
	
	public static Module getModuleByName(String name) 
	{
		return modules.stream().filter(module -> module.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
	}
	
	public static Module getModuleByClass(Class<?> clazz) 
	{
		return modules.stream().filter(module -> module.getClass().equals(clazz)).findFirst().orElse(null);
	}
	
	
	@EventTarget
	public void onTick(ClientTickEvent event) 
	{
		ModuleManager.onUpdate();
	}
	
//	@SideOnly(Side.CLIENT)
//	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
//	public void onFastTick(TickEvent event) 
//	{
//		ModuleManager.onFastUpdate();
//	}

	public static void callDelaySetup()
	{
		for(Module m : modules)
		{
			m.delayedSetup();
		}
	}
	
	public static void onUpdate() 
	{
		for (Module m : modules) 
		{
			if (m.isEnabled())			
				m.onUpdate();
		}
	}
	
	public static void onFastUpdate() 
	{
		for (Module m : modules) 
		{
			if (m.isEnabled())
				m.onFastUpdate();
		}
	}
	
	public static void onServerUpdate() 
	{
		for (Module m : modules) 
		{
			if (m.isEnabled())
				m.onServerUpdate();
		}
	}
	

	@EventTarget
	public void keyDownEvent(KeyDownEvent event)
	{
		if(this.mc.currentScreen instanceof GuiScreen)
			return;
		
		int key = event.getKeyCode();
		
		for (Module m : modules) 
		{
			if(key != m.getKeyCode())
				continue;
			
			m.setKeyDown(true);
			m.toggle();
		}
	}

	@EventTarget
	public void keyUpEvent(KeyUpEvent event)
	{
		if(this.mc.currentScreen instanceof GuiScreen)
			return;
		
		int key = event.getKeyCode();
		
		for (Module m : modules) 
		{
			if(key != m.getKeyCode())
				continue;
			
			m.setKeyDown(false);
		}
	}
}






