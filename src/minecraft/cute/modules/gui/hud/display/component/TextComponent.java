package cute.modules.gui.hud.display.component;

import cute.modules.gui.hud.ScreenPosition;
import cute.modules.gui.hud.display.DraggableComponent;
import net.minecraft.client.Minecraft;


public class TextComponent extends DraggableComponent
{
	protected String text;
	
	public TextComponent(int relativeX, int relativeY, int parentX,
			int parentY, String text, int color)
	{
		super(relativeX, relativeY, 
				Minecraft.getMinecraft().fontRendererObj.getStringWidth(text), 
				Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 3, 
				parentX, parentY, color);
		
		
		this.text = text;
	}
	
	public void setText(String text)
	{
		this.text = text;
		this.width = this.mc.fontRendererObj.getStringWidth(text);
		this.height = this.mc.fontRendererObj.FONT_HEIGHT + 3;
	}
	
	public String getText()
	{
		return this.text;
	}
	
	
	@Override
	public void render()
	{
		this.mc.fontRendererObj.drawStringWithShadow(
				this.text,
				(int)this.x,
				(int)this.y,
				(int)this.color
				);
	}
	
	@Override
	public void renderDummy(ScreenPosition pos)
	{
		this.mc.fontRendererObj.drawStringWithShadow(
				 this.text,
				(int)(pos.getRelativeX() + this.rx),
				(int)(pos.getRelativeY() + this.ry),
				(int)this.color
				);
	}
}




