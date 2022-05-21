package cute.modules.gui.hud.display;

import java.awt.Color;

import cute.modules.gui.hud.ScreenPosition;
import cute.util.RenderUtil;
import net.minecraft.client.Minecraft;

public class displayText extends draggableText
{

	private ScreenPosition pos;
	
	@Override
	public int getWidth()
	{
		return font.getStringWidth("nyah uwu sex");
	}

	@Override
	public int getHeight()
	{
		return font.FONT_HEIGHT;
	}

	@Override
	public void render(ScreenPosition pos)
	{
		font.drawStringWithShadow("Nyah ovo", pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
//		font.drawStringWithShadow("nyah ovo", 1, 1, -1);
		
//		RenderUtil.setColor(new Color(255,255,255, 122));
//		RenderUtil.renderRectSingle(pos.getAbsoluteX(),pos.getAbsoluteY(),pos.getAbsoluteX() + 25,pos.getAbsoluteY() + 25);
//		
//		RenderUtil.resetColor();
		
	}
	
	@Override
	public void renderDummy(ScreenPosition pos)
	{
//		String text = "nyah uwu";
//		Minecraft.getMinecraft().fontRendererObj.drawString(text, pos.getAbsoluteX(), pos.getAbsoluteY(), 0xFFFFFF);
		font.drawString("nyah owo uwu", pos.getAbsoluteX(), pos.getAbsoluteY(), 0xFFFF0000);
//		RenderUtil.setColor(new Color(255,255,255));
//		RenderUtil.renderRectSingle(0,0,2,2);
//		
//		RenderUtil.resetColor();
	}

	@Override
	public void save(ScreenPosition pos)
	{
		this.pos = pos;
	}

	@Override
	public ScreenPosition load()
	{
		return pos;
	}

}
