package cute.modules.gui.hud;

//import java.awt.Color;
//
//import org.lwjgl.opengl.GL11;
//
//import cute.eventapi.EventTarget;
//import cute.events.RenderHandEvent;
//import cute.events.RenderWorldLastEvent;
//import cute.modules.Module;
//import cute.modules.enums.Category;
//import cute.settings.Checkbox;
//import cute.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class ScreenPosition
{
	private double x;
	private double y;
	
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
		return (int)(x * res.getScaledWidth());
	}
	public int getAbsoluteY()
	{
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		return (int)(y * res.getScaledHeight());
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
		
		this.x = (double)(x / res.getScaledWidth());
		this.y = (double)(y / res.getScaledHeight());
	}
	
	public void setRelative(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
}