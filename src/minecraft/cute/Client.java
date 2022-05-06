package cute;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import cute.eventapi.EventManager;
import cute.managers.ModuleManager;
import cute.modules.render.Fullbright;
import cute.util.Cache;
import cute.util.types.BlockInfo;
import cute.util.types.VirtualBlock;
import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ResourceLocation;

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
		Cache.loadCache();
	}
	
	public void init()
	{
		EventManager.register(ModuleManager.INSTANCE);		
	}
	
	public void shutdown()
	{
		System.out.println("Client shutting down...");
	}
	
	
}
