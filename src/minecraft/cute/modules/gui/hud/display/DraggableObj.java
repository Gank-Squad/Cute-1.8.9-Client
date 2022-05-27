package cute.modules.gui.hud.display;

import java.util.ArrayList;

import cute.eventapi.EventManager;
import cute.modules.gui.hud.IRender;
import cute.modules.gui.hud.ScreenPosition;
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
	
	public DraggableObj()
	{
		this.pos.setRelative(0, 0);
		setEnabled(isEnabled);
	}
	public DraggableObj(int x, int y)
	{
		this.pos.setRelative(x, y);
		setEnabled(isEnabled);
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
		int left = (int)pos.getRelativeX();
		int right = (int)pos.getRelativeX() + this.width;
		
		if (c.getAbsoluteX() < left)
		{
			left = c.getAbsoluteX();
		}
		if (c.getAbsoluteX() + c.getWidth() > right)
		{
			right = c.getAbsoluteX() + c.getWidth();
		}
		this.width = right - left;
	}
	
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
		int top = (int)pos.getRelativeY();
		int bot = (int)pos.getRelativeY() + this.height;
		
		if (c.getAbsoluteY() < top)
		{
			top = c.getAbsoluteY();
		}
		if (c.getAbsoluteY() + c.getHeight() > bot)
		{
			bot = c.getAbsoluteY() + c.getHeight();
		}
		this.height = bot - top;
	}
	
	public void render()
	{
		// draw all the components
		for (DraggableComponent component : components)
		{
			component.checkPos((int)pos.getRelativeX(), (int)pos.getRelativeY());
			component.render();
		}
		
	}
	public void renderDummy(ScreenPosition pos)
	{
		// draw all the components with position given in pos 
		// also draw another rect (this.width, this.height) and make it grey
		// may do an outline in the future
		
		for (DraggableComponent component : components)
		{
			component.renderDummy(pos);
		}
		
		RenderUtil.setColor(0x8d8d8d80);
		RenderUtil.renderRectSingle(
					(int)pos.getRelativeX(),
					(int)pos.getRelativeY(),
					(int)pos.getRelativeX() + this.width,
					(int)pos.getRelativeY() + this.height
				);
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
		this.pos.setRelative(x,y);
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
		components.add(component);
//		calcWidth();
		updateWidth(component);
//		calcHeight();
		updateHeight(component);
	}
	
}
