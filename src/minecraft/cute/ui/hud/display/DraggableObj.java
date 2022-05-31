package cute.ui.hud.display;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import cute.eventapi.EventManager;
import cute.ui.hud.IRender;
import cute.ui.hud.ScreenPosition;
import cute.util.RenderUtil;
//import cute.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class DraggableObj implements IRender
{

	private boolean isEnabled = true;
	
	protected final Minecraft mc = Minecraft.getMinecraft();
	
	private ScreenPosition pos = ScreenPosition.fromRelative(0.5, 0.5);
	
	private ArrayList<DraggableComponent> components = new ArrayList<DraggableComponent>();
	
	//will essentially act as clickable area to drag
	//means you will be forced to have rectangular hud
	private int height;
	private int width;
	
	private float scaleX;
	private float scaleY;
	
	public DraggableObj()
	{
		this.pos.setRelative(0, 0);
		setEnabled(isEnabled);

		this.width = 0;
		this.height = 0;
		
		this.scaleX = 1;
		this.scaleY = 1;
	}
	public DraggableObj(int x, int y)
	{
		this.pos.setRelative(x, y);
		setEnabled(isEnabled);
		
		this.width = 0;
		this.height = 0;
		
		this.scaleX = 1;
		this.scaleY = 1;
	}
	
	public void setEnabled(boolean isEnabled)
	{
		this.isEnabled = isEnabled;
		
		if (isEnabled)
		{
			EventManager.register(this);
			return;
		}
		EventManager.unregister(this);
	}
	
	public void setScale(float scaleX, float scaleY)
	{
		this.scaleX =  scaleX;
		this.scaleY = scaleY;
	}
	public float getScaleX()
	{
		return this.scaleX;
	}
	public float getScaleY()
	{
		return this.scaleY;
	}
	
	public void calcWidth()
	{
		// check every component and keep track of leftmost and rightmost point
		int left = Integer.MAX_VALUE;
		int right = -Integer.MAX_VALUE;
		
		for (DraggableComponent c : components)
		{
			if (c.getAbsoluteX() < left)
			{
				left = c.getAbsoluteX();
			}
			if (c.getAbsoluteX() + c.getWidth() > right)
			{
				right = c.getAbsoluteX() + c.getWidth();
			}
		}

		this.width = (int)((right - left) * this.scaleX);
	}
	public void updateWidth(DraggableComponent c)
	{
		if (c.getWidth() > this.width)
		{
			this.width = (int)(c.getWidth() * this.scaleX);
		}
	}
	
	// leaving this in in case we ever make it possible to remove components
	public void calcHeight()
	{
		int top = Integer.MAX_VALUE;
		int bot = -Integer.MAX_VALUE;
		
		for (DraggableComponent c : components)
		{
			if (c.getAbsoluteY() < top)
			{
				top = c.getAbsoluteY();
			}
			if (c.getAbsoluteY() + c.getHeight() > bot)
			{
				bot = c.getAbsoluteY() + c.getHeight();
			}
		}
		
		this.height = (int)((bot - top) * scaleY);
	}
	public void updateHeight(DraggableComponent c)
	{
		if (c.getHeight() > this.height)
		{
			this.height = (int)(c.getHeight() * scaleY); // c.getAbsoluteY() + // I'm not sure why this was here
		}
	}
	
	public void render()
	{
		calcWidth();
		calcHeight();

		GL11.glPushMatrix();
		GL11.glScalef((float)scaleX, (float)scaleY, (float)1);
		
		for (DraggableComponent component : components)
		{
//			component.checkPos((int)pos.getAbsoluteX(), (int)pos.getAbsoluteY());
			component.render(pos, this.scaleX, this.scaleY);
		}
		GL11.glPopMatrix();
		RenderUtil.resetColor();
	}
	public void renderDummy(ScreenPosition pos)
	{	
		calcWidth();
		calcHeight();

		GL11.glPushMatrix();
		GL11.glScalef((float)scaleX, (float)scaleY, (float)1);
		
		for (DraggableComponent component : components)
		{
			component.renderDummy(pos, this.scaleX, this.scaleY);
		}
		
		RenderUtil.setColor(0x8d8d8d80);
		RenderUtil.renderRectSingle(
					(int)((pos.getAbsoluteX()) / this.scaleX),
					(int)((pos.getAbsoluteY()) / this.scaleY),
					(int)((pos.getAbsoluteX() + this.width) / this.scaleX),
					(int)((pos.getAbsoluteY() + this.height) / this.scaleY)
				);
		
		GL11.glPopMatrix();
		RenderUtil.resetColor();
	}
	
	public ScreenPosition getPos()
	{
		return this.pos;
	}
	public void setPos(ScreenPosition pos)
	{
		this.pos = pos;
	}
	public void setPos(int x, int y)
	{
//		pos.setRelative(x, y);
//		rx = x;
//		ry = y;
		this.pos.setRelative(x,y
//				absCoordsX(x),
//				absCoordsY(y)
			);
	}
	public int getWidth()
	{
		return this.width;
	}
	public int getHeight()
	{
		return this.height;
	}
	public void setWidth(int width)
	{
		this.width = width;
	}
	public void setHeight(int height)
	{
		this.height = height;
	}
	
	public void addComponent(DraggableComponent component)
	{
		component.setParentXY((int)pos.getAbsoluteX(), (int)pos.getAbsoluteY());
		components.add(component);
//		calcWidth();
		updateWidth(component);
//		calcHeight();
		updateHeight(component);
		
	}
	
}
