package cute.modules.bot;

import java.util.HashMap;
import java.util.Map;

import cute.eventapi.EventManager;
import cute.eventapi.EventTarget;
import cute.events.PacketReceivedEvent;
import cute.modules.Module;
import cute.modules.enums.Category;
import cute.modules.render.XRay;
import cute.settings.Checkbox;
import cute.util.EntityUtil;
import cute.util.types.VirtualBlock;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S14PacketEntity;

public class AntiBot extends Module 
{
	public AntiBot()
	{
		super("Anti Bot", Category.BOT, "tries to detect bots");
		EventManager.register(this);
	}
	
	public static Checkbox entityId = new Checkbox("Entity ID", true);
	public static Checkbox tablist  = new Checkbox("Tab List", true);
	public static Checkbox invisible= new Checkbox("Invisible", false);
	
	public static Checkbox ground   = new Checkbox("Ground", true);
	public static Checkbox swing    = new Checkbox("Swing", true);
	public static Checkbox velocity = new Checkbox("Velocity", true);
	
	private static boolean globalEnabled = false;

	public static Map<Integer, PlayerInfo> infos = new HashMap<>();
	  
	public static class PlayerInfo 
	{
		public boolean ground   = false;
		public boolean swing    = false;
		public boolean velocity = false;
	}
	
	
	@Override
	public void setup()
	{
		this.addSetting(entityId);
		this.addSetting(tablist);
		this.addSetting(invisible);
		this.addSetting(ground);
		this.addSetting(swing);
		this.addSetting(velocity);
	}
	
	@Override
	public void onEnable()
	{
		this._enabled = true;
		globalEnabled = true;
	}
	
	@Override 
	public void onDisable()
	{
		this._enabled = false;
		globalEnabled = false;
	}
	
	public static boolean isBot(EntityLivingBase entity) 
	{ 
		if(!globalEnabled)
			return false;
		
		if (entityId.getValue())
			return entity.getEntityId() > 10; 
		
		if (tablist.getValue())
			return entity instanceof AbstractClientPlayer && 
				   !EntityUtil.isInTab((AbstractClientPlayer)entity);
		
		if (ground.getValue())
			return infos.containsKey(entity.getEntityId()) && 
					!infos.get(entity.getEntityId()).ground; 
		
		if (swing.getValue())
			return infos.containsKey(entity.getEntityId()) && 
					!infos.get(entity.getEntityId()).swing; 

		if ((velocity.getValue()))
			return  infos.containsKey(entity.getEntityId()) && 
					!infos.get(entity.getEntityId()).velocity; 
		
		if (invisible.getValue())
			return entity.isInvisible(); 
		
		return false;
	}
  
	
	@EventTarget
	public void packetRecieve(PacketReceivedEvent event)
	{
		try 
		{
		    if (event.packet instanceof S0CPacketSpawnPlayer) 
		    {
		        infos.put(((S0CPacketSpawnPlayer) event.packet).getEntityID(), new PlayerInfo());
		        return;
		    } 
		    
		    if (event.packet instanceof S13PacketDestroyEntities) 
		    {
		    	S13PacketDestroyEntities p = (S13PacketDestroyEntities) event.packet;
		        
		        int[] arrayOfInt = p.getEntityIDs();
		        
		        for (byte b = 0; b < arrayOfInt.length; b++) 
		        {
		            infos.remove(arrayOfInt[b]);
		        }
		        return;
		    } 
		    
		    if (event.packet instanceof net.minecraft.network.play.server.S01PacketJoinGame) 
		    {
		        infos.clear();
		        return;
		    } 
		    
		    if (event.packet instanceof S0BPacketAnimation) 
		    {
		    	S0BPacketAnimation p = (S0BPacketAnimation)event.packet;
		    	
		        if (infos.containsKey(p.getEntityID())) 
		        {
		            infos.get(p.getEntityID()).swing = true;
		        }
		        return;
		    } 
		    
		    if (event.packet instanceof S14PacketEntity) 
		    {
		    	S14PacketEntity p = (S14PacketEntity)event.packet;
		    	
		    	if(infos.containsKey(p.getEntityID()))
		        {
		            infos.get(p.getEntityID()).ground = p.getOnGround();
		        }
		    	return;
		    } 
		    
		    if (event.packet instanceof S12PacketEntityVelocity) 
		    {
		    	S12PacketEntityVelocity p = (S12PacketEntityVelocity)event.packet;
		    	
		    	if(infos.containsKey(p.getEntityID()))
    			{
		    		infos.get(p.getEntityID()).ground = true;
		    	}
		    }
		} 
		catch (Exception e) 
		{
		    e.printStackTrace();
		}
	}
}
