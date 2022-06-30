package cute.modules.test;

import cute.eventapi.EventTarget;
import cute.events.ClientTickEvent;
import cute.modules.Module;
import cute.modules.enums.Category;

public class SpeedBridge extends Module {
	
	public SpeedBridge()
	{
		super("Auto Bridge", Category.BOT, "this is for testing events");
	}
	
	@EventTarget
	public void onTick(ClientTickEvent event)
	{
		//Relative positions from bed [ X , Y , Z ]
		float[] playerPos = new float[] 
		{
				(float) (mc.thePlayer.posX),
				(float) (mc.thePlayer.posY),
				(float) (mc.thePlayer.posZ)
		};
		
		
		System.out.println("client tick event");
	}
}
