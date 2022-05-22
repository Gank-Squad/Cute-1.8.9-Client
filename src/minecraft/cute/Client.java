package cute;

import java.awt.Color;

import cute.eventapi.EventManager;
import cute.managers.ConfigManager;
import cute.managers.ModuleManager;
import cute.modules.gui.hud.HudManager;
import cute.modules.gui.hud.display.instances;

public class Client 
{
	public static final String VERSION = "0.3.0";
	public static final String NAME    = "Cute";
	
//	public static final Logger LOGGER = LogManager.getLogger("Cute");
	
	private static final Client INSTANCE = new Client();
	
	public static class GlobalColors 
	{
		public static Color White = new Color(255, 255, 255, 255);
	}
	
	public static final Client getInstance()
	{
		return Client.INSTANCE;
	}
	
	public void preinit()
	{
	}
	
	private HudManager hudManager;
	public void start()
	{
		hudManager = HudManager.getInstance();
		instances.register(hudManager);
	}
	
	public void init()
	{
		EventManager.register(ModuleManager.INSTANCE);		
		
		ConfigManager.loadConfig();
		
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
