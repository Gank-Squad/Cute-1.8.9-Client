package cute.ui.hud.display.components;

import org.lwjgl.opengl.GL11;

import cute.ui.hud.ScreenPosition;
import cute.ui.hud.display.DraggableComponent;
import cute.util.RenderUtil;

public class RectComponent extends DraggableComponent
{

	public RectComponent(int relativeX, int relativeY, int width, int height, int color) 
	{
		super(relativeX, relativeY, width, height, color);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void render(ScreenPosition pos, float scaleX, float scaleY)
	{
		RenderUtil.setColor(this.color);
		RenderUtil.renderRectSingle(
				(pos.getAbsoluteX() + this.rx) / scaleX,
				(pos.getAbsoluteY() + this.ry) / scaleY,
				(pos.getAbsoluteX() + this.rx + this.width) / scaleX,
				(pos.getAbsoluteY() + this.ry + this.height) / scaleY);
		RenderUtil.resetColor();
	}
	
	@Override
	public void renderDummy(ScreenPosition pos, float scaleX, float scaleY)
	{
//		GL11.glPushMatrix();
//		GL11.glScalef((float)this.scaleX, (float)scaleY, (float)1);
		
		RenderUtil.setColor(this.color);
		RenderUtil.renderRectSingle(
				(pos.getAbsoluteX() + this.rx) / scaleX,
				(pos.getAbsoluteY() + this.ry) / scaleY,
				(pos.getAbsoluteX() + this.rx + this.width) / scaleX,
				(pos.getAbsoluteY() + this.ry + this.height) / scaleY);
		RenderUtil.resetColor();
		
//		GL11.glPopMatrix();
	}
}
