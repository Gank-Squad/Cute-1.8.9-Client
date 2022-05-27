package cute.ui.hud.display;

import java.util.ArrayList;

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
	
//	public final int baseScale = 1000;
//	
//	private double rx;
//	private double ry;
	
	public DraggableObj()
	{
		this.pos.setRelative(0, 0);
		setEnabled(isEnabled);
		
		this.width = 0;
		this.height = 0;
	}
	public DraggableObj(int x, int y)
	{
		this.pos.setRelative(x, y);
		setEnabled(isEnabled);
		this.width = 0;
		this.height = 0;
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
	
//	public int absCoordsX(double relativePosX)
//	{
//		double mod = relativePosX / baseScale;
//		return (int)(res.getScaledWidth() * mod);
//	}
//	public int absCoordsY(double relativePosY)
//	{
//		double mod = relativePosY / baseScale;
//		return (int)(res.getScaledHeight() * mod);
//	}
//	public double relCoordsX(int absolutePosX)
//	{
//		double mod = absolutePosX / res.getScaledWidth();
//		return baseScale * mod;
//	}
//	public double relCoordsY(int absolutePosY)
//	{
//		double mod = absolutePosY / res.getScaledWidth();
//		return baseScale * mod;
//	}
	
	// leaving this in in case we ever make it possible to remove components
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

		this.width = right - left;
	}
	public void updateWidth(DraggableComponent c)
	{
		if (c.getWidth() > this.width)
		{
			this.width = c.getWidth();
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
		
		this.height = bot - top;
	}
	public void updateHeight(DraggableComponent c)
	{
		if (c.getHeight() > this.height)
		{
			this.height = c.getAbsoluteY() + c.getHeight();
		}
	}
	
	public void render()
	{
		for (DraggableComponent component : components)
		{
			component.checkPos((int)pos.getAbsoluteX(), (int)pos.getAbsoluteY());
			component.render();
		}
		
	}
	public void renderDummy(ScreenPosition pos)
	{	
//		ScreenPosition pos2 = pos;
//		pos = new ScreenPosition(
//				(float)absCoordsX(pos.getRelativeX()),
//				(float)absCoordsY(pos.getRelativeY())
//				);
		
		for (DraggableComponent component : components)
		{
			component.renderDummy(pos);
		}
		
		RenderUtil.setColor(0x8d8d8d80);
		RenderUtil.renderRectSingle(
					(int)pos.getAbsoluteX(),
					(int)pos.getAbsoluteY(),
					(int)pos.getAbsoluteX() + this.width,
					(int)pos.getAbsoluteY() + this.height
				);
		RenderUtil.resetColor();
		
//		pos = pos2;
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
