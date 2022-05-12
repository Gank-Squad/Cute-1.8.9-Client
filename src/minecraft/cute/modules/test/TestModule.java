package cute.modules.test;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import cute.eventapi.EventTarget;
import cute.events.ClientTickEvent;

import cute.modules.Module;
import cute.modules.enums.Category;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.WorldSettings.GameType;

public class TestModule extends Module 
{
	public TestModule()
	{
		super("Test 1", Category.BOT, "this is for testing events");
	}
	
	
	@EventTarget
	public void onTick(ClientTickEvent event)
	{
		System.out.println("client tick event");
	}
}
