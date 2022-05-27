package cute.ui.hud.display;

import cute.eventapi.EventManager;
import cute.ui.hud.IRender;
import cute.ui.hud.ScreenPosition;
import net.minecraft.client.Minecraft;

public class DraggableText implements IRender
{
	private boolean isEnabled = true;
	
	protected final Minecraft mc;
	
	private ScreenPosition pos = ScreenPosition.fromRelative(0.5, 0.5);
	
	public DraggableText()
	{
		this.mc = Minecraft.getMinecraft();
		setEnabled(isEnabled);
	}
	
	public void setEnabled(boolean isEnabled)
	{
		this.isEnabled = isEnabled;
		
		if (isEnabled)
		{
			EventManager.register(this);
		}
		else
		{
			EventManager.unregister(this);
		}
	}
	
	public final int getLineOffset(ScreenPosition pos, int lineNum)
	{
		return pos.getAbsoluteY() + getLineOffset(lineNum);
	}
	
	private int getLineOffset(int lineNum)
	{
		return (this.mc.fontRendererObj.FONT_HEIGHT + 3) * lineNum;
	}
	
	public ScreenPosition getPos()
	{
		return this.pos;
	}
	
	public void setPos(ScreenPosition pos)
	{
		this.pos = pos;
	}
	
	public int getWidth()
	{
		return this.mc.fontRendererObj.getStringWidth("nyah uwu sex");
	}

	public int getHeight()
	{
		return this.mc.fontRendererObj.FONT_HEIGHT;
	}

	public void render()
	{
		this.mc.fontRendererObj.drawStringWithShadow("Nyah ovo", (int)pos.getRelativeX(), (int)pos.getRelativeY(), -1);
	}
	

	public void renderDummy(ScreenPosition pos)
	{
		this.mc.fontRendererObj.drawString("nyah owo uwu", (int)pos.getRelativeX(), (int)pos.getRelativeY(), 0xFFFF0000);
	}
}
