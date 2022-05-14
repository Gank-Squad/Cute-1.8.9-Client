package cute;

import java.awt.Color;

import cute.eventapi.EventManager;
import cute.managers.ConfigManager;
import cute.managers.ModuleManager;

public class Client 
{
	public static final String VERSION = "0.2.0";
	public static final String NAME    = "Cute";
	
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
	
	public void init()
	{
		EventManager.register(ModuleManager.INSTANCE);		
		
		ConfigManager.loadConfig();
		
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
