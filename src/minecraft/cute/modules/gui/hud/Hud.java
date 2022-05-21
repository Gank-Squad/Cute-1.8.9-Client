package cute.modules.gui.hud;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import cute.eventapi.EventTarget;
import cute.events.RenderHandEvent;
import cute.events.RenderWorldLastEvent;
import cute.events.SettingChangedEvent;
import cute.modules.Module;
import cute.modules.enums.Category;
import cute.modules.render.Fullbright;
import cute.settings.Checkbox;
//import cute.settings.Checkbox;
import cute.util.RenderUtil;
//import net.minecraft.client.Minecraft;

public class Hud extends Module
{
	public static Checkbox draggable = new Checkbox("Draggable", false);
	private static HudManager hudManager;
	@EventTarget
	public void modeChangedEvent(SettingChangedEvent e)
	{
//		System.out.println("ny");
		if(e.settingName != draggable.getName())
			return;
		
		if (e.args.length == 0)
		{
			return;
		}
		
		if (!(boolean)e.args[0])
		{
			return;
		}
		
//		System.out.println("nyah:3");
		hudManager = HudManager.getInstance();
		mc.displayGuiScreen(null);
		hudManager.openConfigScreen();
//		enable(Fullbright.Mode.getValue());
	}
	
	public Hud() 
	{
		super("Hud", Category.CLIENT, "a in game hud");
		
	}

	
	@Override
    public void setup() 
	{
		this.addSetting(draggable);
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
		
//		RenderUtil.setColor(new Color(255,255,255));
//		GL11.glPushMatrix();
//		GL11.glDisable(GL11.GL_DEPTH_TEST);
//		RenderUtil.renderBlock((int)mc.thePlayer.posX, (int)mc.thePlayer.posY, (int)mc.thePlayer.posZ, new Color(255,255,255));
//		GL11.glPopMatrix();
//		RenderUtil.renderRectSingle(0, 0, 100, 100);
	}
}
