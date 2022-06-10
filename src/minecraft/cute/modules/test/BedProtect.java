package cute.modules.test;

import java.awt.Color;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL32;

import cute.eventapi.EventTarget;
import cute.events.RenderWorldLastEvent;
import cute.modules.Module;
import cute.modules.enums.Category;
import cute.settings.Checkbox;
import cute.settings.ColorPicker;
import cute.settings.Mode;
import cute.settings.Slider;
import cute.util.RenderUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;

public class BedProtect extends Module {

	// 		mc.gameSettings.keyBindDrop.setPressed(true);  -> Place
	
	public BedProtect()
	{
		super("Bed Protect", Category.BOT, "Protects your bed.");
	}
	
	int[] bedPosition = new int[]{ -1698 , 76 , -96 };
	int width = 5;
	int length = 5;
	
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
			mc.gameSettings.keyBindRight.setPressed(true); //Move back
			mc.gameSettings.keyBindDrop.setPressed(true); //Place blocks
		}
		
		if(relPos[2] > midWidth+1)
		{
			mc.gameSettings.keyBindDrop.setPressed(false); //STOP Place blocks
			mc.gameSettings.keyBindRight.setPressed(false); //STOP Move back
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
