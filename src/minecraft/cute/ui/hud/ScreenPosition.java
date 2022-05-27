package cute.ui.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class ScreenPosition
{
	protected double x;
	protected double y;
	protected double baseScale = 1000;
	
	public ScreenPosition(double x, double y)
	{
		setRelative(x,y);
	}
	
	public ScreenPosition(int x, int y)
	{
		setAbsolute(x,y);
	}
	
	public static ScreenPosition fromRelative(double x, double y)
	{
		return new ScreenPosition(x,y);
	}
	
	public static ScreenPosition fromAbsolute(int x, int y)
	{
		return new ScreenPosition(x, y);
	}
	
	
	public int getAbsoluteX()
	{
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		return (int)(x * res.getScaledWidth() / baseScale);
	}
	
	public int getAbsoluteY()
	{
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		return (int)(y * res.getScaledHeight() / baseScale);
	}
	
	public double getRelativeX()
	{
		return x;
	}
	
	public double getRelativeY()
	{
		return y;
	}
	
	
	public void setAbsolute(int x, int y)
	{
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());

		this.x = (double)x / (double)res.getScaledWidth() * (double)baseScale;
		this.y = (double)y / (double)res.getScaledHeight() * (double)baseScale;
	}
	
	public void setRelative(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
}