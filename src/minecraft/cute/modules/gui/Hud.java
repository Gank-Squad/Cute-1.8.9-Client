package cute.modules.gui;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import cute.eventapi.EventTarget;
import cute.events.RenderHandEvent;
import cute.events.RenderWorldLastEvent;
import cute.modules.Module;
import cute.modules.enums.Category;
import cute.settings.Checkbox;
import cute.util.RenderUtil;

public class Hud extends Module
{
	
	public Hud() 
	{
		super("Hud", Category.CLIENT, "a in game hud");
	}

	
	@Override
    public void setup() 
	{
	
    }

	@EventTarget
	public void onRenderWorld(RenderWorldLastEvent e)
	{
//		RenderUtil.setColor(new Color(255,255,255));
//		RenderUtil.renderRectSingle(0, 0, 100, 100);
	}
	
	@EventTarget
	public void onRenderHand(RenderHandEvent e)
	{
		
		RenderUtil.setColor(new Color(255,255,255));
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		RenderUtil.renderBlock((int)mc.thePlayer.posX, (int)mc.thePlayer.posY, (int)mc.thePlayer.posZ, new Color(255,255,255));
		GL11.glPopMatrix();
		RenderUtil.renderRectSingle(0, 0, 100, 100);
	}
}
