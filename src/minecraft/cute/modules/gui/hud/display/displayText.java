package cute.modules.gui.hud.display;

import java.awt.Color;

import cute.modules.gui.hud.ScreenPosition;
import cute.util.RenderUtil;

public class displayText extends draggableText
{

	private ScreenPosition pos;
	
	@Override
	public int getWidth()
	{
		return font.getStringWidth("nyah owo uwu");
	}

	@Override
	public int getHeight()
	{
		return font.FONT_HEIGHT;
	}

	@Override
	public void render(ScreenPosition pos)
	{
//		font.drawStringWithShadow("nyah ovo", pos.getAbsoluteX() + 1, pos.getAbsoluteY() + 1, -1);
		font.drawStringWithShadow("nyah ovo", 1, 1, -1);
		
		RenderUtil.setColor(new Color(255,255,255, 122));
		RenderUtil.renderRectSingle(pos.getAbsoluteX(),pos.getAbsoluteY(),pos.getAbsoluteX() + 25,pos.getAbsoluteY() + 25);
		
		RenderUtil.resetColor();
		
	}
	
	@Override
	public void renderDummy(ScreenPosition pos)
	{
		font.drawString("nyah owo uwu", pos.getAbsoluteX(), pos.getAbsoluteY() + 1, 0xFFFF0000);
		RenderUtil.setColor(new Color(255,255,255));
		RenderUtil.renderRectSingle(0,0,2,2);
		
		RenderUtil.resetColor();
	}

	@Override
	public void save(ScreenPosition pos)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public ScreenPosition load()
	{
		return pos;
	}

}
