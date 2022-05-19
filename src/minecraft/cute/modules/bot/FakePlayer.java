package cute.modules.bot;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

import cute.modules.Module;
import cute.modules.enums.Category;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.EntityPlayer;

public class FakePlayer extends Module 
{
	public FakePlayer()
	{
		super("Fake Player", Category.BOT, "Summons a copy of the player at the players position");
	}
	
	public static String generateString() 
	{
        String uuid = UUID.randomUUID().toString();
        return uuid.split("[-]")[0];
    }
	
	@Override
	public void onEnable()
	{
		super.onEnable();
		
		if(nullCheck())
			return;
		
		EntityPlayer ww = new EntityOtherPlayerMP(mc.theWorld, new GameProfile(
				UUID.fromString("3f66a67c-db6c-36b7-b762-d7a40c17883b"), generateString()));
		ww.inventory.copyInventory(mc.thePlayer.inventory);
		ww.setPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
		mc.theWorld.spawnEntityInWorld(ww);
	}
}
