package cute.modules.gui.hud.display;

import cute.eventapi.EventManager;
import cute.modules.gui.hud.IRender;
import cute.modules.gui.hud.ScreenPosition;
import net.minecraft.client.Minecraft;
import cute.modules.gui.hud.display.DraggableComponent;
import cute.util.RenderUtil;
import java.awt.Color;
import java.util.ArrayList;

public class DraggableObj implements IRender
{

	private boolean isEnabled = true;
	
	protected final Minecraft mc;
	
	private ScreenPosition pos = ScreenPosition.fromRelative(0.5, 0.5);
	
	private ArrayList<DraggableComponent> components = new ArrayList<DraggableComponent>();
	
	//will essentially act as clickable area to drag
	//means you will be forced to have rectangular hud
	private int height;
	private int width;
	
	
	public DraggableObj()
	{
		this.pos.setRelative(50, 50);
		
		this.mc = Minecraft.getMinecraft();
		setEnabled(isEnabled);
		DraggableComponent c = new DraggableComponent(
					0, 0, 40, 40,
					(int)pos.getRelativeX(),
					(int)pos.getRelativeY(),
					0x00000080
				);
		addComponent(c);
		DraggableComponent d = new DraggableComponent(
				0, 10,
				(int)pos.getRelativeX(),
				(int)pos.getRelativeY(),
				"nyah owo",
				0xFF00FFFF
			);
		addComponent(d);
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
		int left = 9999999;
		int right = -9999999;
		
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
	public void calcHeight()
	{
		int top = 99999;
		int bot = -99999;
		
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
	
	
	public void render()
	{
		// draw all the components
		for (DraggableComponent component : components)
		{
			component.checkPos((int)pos.getRelativeX(), (int)pos.getRelativeY());
			if (component.isString())
			{
				this.mc.fontRendererObj.drawStringWithShadow(
						component.getText(),
						(int)component.getAbsoluteX(),
						(int)component.getAbsoluteY(),
						(int)component.getColor()
						);
			}
			else
			{
				RenderUtil.setColor(component.getColor());
				RenderUtil.renderRectSingle(
						component.getAbsoluteX(),
						component.getAbsoluteY(),
						component.getAbsoluteX() + component.getWidth(),
						component.getAbsoluteY() + component.getHeight()
						);
				RenderUtil.resetColor();
			}
		}
		
	}
	public void renderDummy(ScreenPosition pos)
	{
		// draw all the components with position given in pos 
		// also draw another rect (this.width, this.height) and make it grey
		// may do an outline in the future
		
		for (DraggableComponent component : components)
		{
			if (component.isString())
			{
				this.mc.fontRendererObj.drawStringWithShadow(
						 component.getText(),
						(int)(pos.getRelativeX() - component.getRelativeX()),
						(int)(pos.getRelativeY() - component.getRelativeY()),
						(int)component.getColor()
						);
			}
			else
			{
				RenderUtil.setColor((int)component.getColor());
				RenderUtil.renderRectSingle(
						pos.getRelativeX() - component.getRelativeX(),
						pos.getRelativeY() - component.getRelativeX(),
						pos.getRelativeX() - component.getRelativeX() + component.getWidth(),
						pos.getRelativeY() - component.getRelativeX() + component.getHeight()
						);
				RenderUtil.resetColor();				
			}
		}
		
//		RenderUtil.setColor(0x8d8d8d80);
//		RenderUtil.renderRectSingle(
//					(int)pos.getRelativeX(),
//					(int)pos.getRelativeY(),
//					(int)pos.getRelativeX() + this.width,
//					(int)pos.getRelativeY() + this.height
//				);
//		RenderUtil.resetColor();
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
		calcWidth();
		calcHeight();
	}
	
}
