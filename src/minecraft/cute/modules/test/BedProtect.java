package cute.modules.test;

import cute.eventapi.EventTarget;
import cute.events.RenderWorldLastEvent;
import cute.modules.Module;
import cute.modules.enums.Category;
import net.minecraft.client.settings.KeyBinding;

public class BedProtect extends Module {

	// 		mc.gameSettings.keyBindDrop.setPressed(true);  -> Place
	
	public BedProtect()
	{
		super("Bed Protect", Category.BOT, "Protects your bed.");
	}
	
	int[] bedPosition = new int[]{ -1698 , 76 , -96 };
	int width = 5;
	int length = 5;
	
	//Re references since decompiled definitions are wonky:
	//private KeyBinding forward = mc.gameSettings.keyBindLeft;
	///private KeyBinding backward = mc.gameSettings.keyBindRight;
	//private KeyBinding left = mc.gameSettings.keyBindBack;
	//private KeyBinding right = mc.gameSettings.keyBindJump;
	
	//private KeyBinding place = mc.gameSettings.keyBindDrop;
	
	
	private void keyWrap(String action, Boolean toggle ) 
	{	
		switch(action)
		{
		case "forward":
			mc.gameSettings.keyBindLeft.setPressed(toggle);
			break;
			
		case "backward":
			mc.gameSettings.keyBindRight.setPressed(toggle);
			break;
			
		case "left":
			mc.gameSettings.keyBindBack.setPressed(toggle);
			break;	
			
		case "right":
			mc.gameSettings.keyBindJump.setPressed(toggle);
			break;
		
		case "place":
			mc.gameSettings.keyBindDrop.setPressed(toggle);
			break;	
			
		case "jump":
			mc.gameSettings.keyBindSneak.setPressed(toggle);
			break;	
		}	
	}
	
	private static int _cooldownTicks = 0; 
	
	@EventTarget
	public void render(RenderWorldLastEvent e) 
	{
		
		//Relative positions from bed [ X , Y , Z ]
		int[] relPos = new int[] {
				(int) (bedPosition[0]-mc.thePlayer.posX),
				(int) (bedPosition[1]-mc.thePlayer.posY),
				(int) (bedPosition[2]-mc.thePlayer.posZ)
		};
		
		int midWidth = (width-1) / 2;
		
		
		if( relPos[2] < ((midWidth-2)*-1) ) 
		{
			keyWrap("backward",true);
			keyWrap("place",true);
		}
		
		if(relPos[2] > midWidth+1)
		{
			keyWrap("place",false); //STOP Place blocks
			keyWrap("backward",false); //STOP Move back
		}
		

		//System.out.println(mc.thePlayer.getHeldItem());
		//mc.gameSettings.keyBindLeft.setPressed(true);
		//mc.gameSettings.keyBindDrop.setPressed(true); 
		
		mc.thePlayer.rotationYaw = 0F;
		mc.thePlayer.rotationPitch = 45F;
		System.out.println("X:");
		System.out.println(relPos[0]); 
		System.out.println("Z:");
		System.out.println(relPos[2]); 
	}
	
}
