package cute.modules.gui.hud.display.component;

import cute.modules.gui.hud.ScreenPosition;
import cute.modules.gui.hud.display.DraggableComponent;
import cute.util.RenderUtil;

public class RectComponent extends DraggableComponent
{

	public RectComponent(int relativeX, int relativeY, int width, int height, int parentX, int parentY, int color) 
	{
		super(relativeX, relativeY, width, height, parentX, parentY, color);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void render()
	{
		RenderUtil.setColor(this.color);
		RenderUtil.renderRectSingle(this.x, this.y, this.x + this.width, this.y + this.height);
		RenderUtil.resetColor();
	}
	
	@Override
	public void renderDummy(ScreenPosition pos)
	{
		RenderUtil.setColor(this.color);
		RenderUtil.renderRectSingle(
				pos.getRelativeX() - this.rx,
				pos.getRelativeY() - this.ry,
				pos.getRelativeX() - this.rx + this.width,
				pos.getRelativeY() - this.ry + this.height);
		RenderUtil.resetColor();
	}
}
