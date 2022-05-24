package cute.modules.gui.hud.display;

import cute.modules.gui.hud.ScreenPosition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class DraggableComponent
{
	
	// just assume that if its not a string, its a rect
	private String text;
	boolean str;
	
	private int color;
	
	private int x;
	private int y;
	
	private int rx;
	private int ry;
	
	private int parentX;
	private int parentY;
	
	private int width;
	private int height;
	
	protected final Minecraft mc;
	
	public DraggableComponent(int relativeX, int relativeY, int width, int height, 
			int parentX, int parentY, int color)
	{
		mc = Minecraft.getMinecraft();
		
		this.rx = relativeX;
		this.ry = relativeY;
		
		this.x = rx + parentX;
		this.y = ry + parentY;
		this.width = width;
		this.height = height;
		
		this.parentX = parentX;
		this.parentY = parentY;
		
		this.color = color;
		this.str = false;
		
	}
	public DraggableComponent(int relativeX, int relativeY, int parentX,
			int parentY, String text, int color)
	{
		mc = Minecraft.getMinecraft();
		
		this.x = relativeX + parentX;
		this.y = relativeY + parentY;
		
		this.parentX = parentX;
		this.parentY = parentY;
		
		this.text = text;
		this.str = true;
		
		this.width = this.mc.fontRendererObj.getStringWidth(text);
		this.height = this.mc.fontRendererObj.FONT_HEIGHT; // + 3;
		
		this.color = color;
	}
	
	// unlike whatever ScreenPosition does
	// this should give position relative to parent
	public int getAbsoluteX()
	{
		return x;
	}
	public int getAbsoluteY()
	{
		return y;
	}
	public int getRelativeX()
	{
		return rx;
	}
	public int getRelativeY()
	{
		return ry;
	}
	
	public void checkPos(int parentX, int parentY)
	{
		this.parentX = parentX;
		this.parentY = parentY;
		this.x = this.parentX + rx;
		this.y = this.parentY + ry;
	}
	
	public void setRelativeXY(int x, int y)
	{
		this.rx = x;
		this.ry = y;
		checkPos(this.parentX, this.parentY);
	}
	
	public void setColor(byte color)
	{
		this.color = color;
	}
	
	public void setText(String text)
	{
		if (text.length() == 0)
		{
			this.str = false;
			this.text = "";
			return;
		}
		this.text = text;
		this.width = this.mc.fontRendererObj.getStringWidth(text);
		this.height = this.mc.fontRendererObj.FONT_HEIGHT + 3;
	}
	
	public boolean isString()
	{
		return str;
	}
	
	public void setWidthHeight(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	
	public String getText()
	{
		return this.text;
	}
	public int getColor()
	{
		return this.color;
	}
	public int getWidth()
	{
		return this.width;
	}
	public int getHeight()
	{
		return this.height;
	}
}
