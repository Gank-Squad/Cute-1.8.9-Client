package cute;

import java.awt.Color;

import cute.eventapi.EventManager;
import cute.managers.ConfigManager;
import cute.managers.HudManager;
import cute.managers.ModuleManager;
import cute.modules.gui.hud.display.DraggableObj;
import cute.modules.gui.hud.display.component.ItemComponent;
import cute.modules.gui.hud.display.component.RectComponent;
import cute.modules.gui.hud.display.component.TextComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemStack;

public class Client 
{
	public static final String VERSION = "0.3.1";
	public static final String NAME    = "Cute";
	
	private static final Client INSTANCE = new Client();
	
	public static class GlobalColors 
	{
		public static final Color White = new Color(255, 255, 255, 255);

		public static final Color colorEnabled      = new Color(136, 50, 0);
		public static final Color colorDisabled     = new Color(100, 0, 0);
		public static final Color colorEnabledHover = new Color(156, 0, 100);
		public static final Color colorHover        = new Color(153, 0, 0);
		public static final Color textColor         = new Color(255, 255, 255);
		public static final Color textColorDisabled = new Color(128, 128, 128);
		public static final int   textColorInt      = textColor.getRGB();
		public static final int   textColorIntDisabled= textColorDisabled.getRGB();
		
		public static final Color checkboxBackground = new Color(136, 153, 153);
		public static final Color checkboxChecked    = new Color(51, 33, 33);
		
		public static final Color backColor   = new Color(17, 17, 17, 140);
		public static final Color sliderColor = new Color(128, 00, 0, 128);
		
		public static final Color red  = new Color(240, 0, 0);
		public static final Color green = new Color(0, 240, 0);
		public static final Color blue = new Color(0, 0, 240);
		
	}
	
	public static final Client getInstance()
	{
		return Client.INSTANCE;
	}
	
	public void preinit()
	{
	}
	
	public void start()
	{
	}
	
	public void init()
	{
		EventManager.register(ModuleManager.INSTANCE);		
		EventManager.register(HudManager.INSTANCE);
		
		HudManager.INSTANCE.registerDefault();
		
		ConfigManager.loadConfig();
		
		// this is super ugly but this would be how you'd construct complex components
		// just string together individual smaller bois 
		DraggableObj obj =((DraggableObj)HudManager.defaultRenders.get(0)); 
		RectComponent re = new RectComponent(0,0,10,10,
				(int)obj.getPos().getRelativeX(),
				(int)obj.getPos().getRelativeY(), -1);
		
		TextComponent rt = new TextComponent(0,0,
				(int)obj.getPos().getRelativeX(),
				(int)obj.getPos().getRelativeY(),
				"uwu", -1);
		
		ItemComponent ri = new ItemComponent(25,25, 
				new ItemStack(Item.itemRegistry.getObjectById(278)),
				(int)obj.getPos().getRelativeX(),
				(int)obj.getPos().getRelativeY());
		
		
		obj.addComponent(re);
		obj.addComponent(rt);
		obj.addComponent(ri);
		
		// for debug cause eclipse puts it at unlimited for some reason 
//		Minecraft.getMinecraft().gameSettings.limitFramerate = 60;
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable()
		{
			@Override
			public void run()
			{
			    ConfigManager.saveConfig();
			}
		}));
	}
	
	public void shutdown()
	{
		System.out.println("Client shutting down...");
	}
	
	
}
