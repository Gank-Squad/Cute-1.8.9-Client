package cute;

import org.lwjgl.input.Keyboard;

import cute.eventapi.EventManager;
import cute.managers.ModuleManager;
import cute.modules.render.Fullbright;
import cute.util.VirtualBlock;

public class Client 
{
	public static final String VERSION = "0.0.1";
	public static final String NAME    = "Cute";
	
	private static final Client INSTANCE = new Client();
	
	public static final Client getInstance()
	{
		return Client.INSTANCE;
	}
	
	public void preinit()
	{
		System.out.println("Client starting...");
		System.out.println("Cute " + Client.VERSION);
	}
	
	public void init()
	{
		EventManager.register(ModuleManager.INSTANCE);
		ModuleManager.getModules().get(0).setKeyCode(Keyboard.KEY_RSHIFT);
		ModuleManager.getModules().get(1).setKeyCode(Keyboard.KEY_1);
		ModuleManager.getModules().get(2).setKeyCode(Keyboard.KEY_2);
		ModuleManager.getModules().get(3).setKeyCode(Keyboard.KEY_3);
		ModuleManager.getModules().get(4).setKeyCode(Keyboard.KEY_4);
		ModuleManager.getModules().get(5).setKeyCode(Keyboard.KEY_5);
		
		VirtualBlock.setStandardList();
	}
	
	public void shutdown()
	{
		System.out.println("Client shutting down...");
	}
	
	
}
