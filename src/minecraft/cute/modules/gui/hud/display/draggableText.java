package cute.modules.gui.hud.display;

import cute.modules.gui.hud.IRender;
import cute.modules.gui.hud.ScreenPosition;

public abstract class draggableText extends renderText implements IRender
{
	public final int getLineOffset(ScreenPosition pos, int lineNum)
	{
		return pos.getAbsoluteY() + getLineOffset(lineNum);
	}
	
	private int getLineOffset(int lineNum)
	{
		return (font.FONT_HEIGHT + 3) * lineNum;
	}
}
