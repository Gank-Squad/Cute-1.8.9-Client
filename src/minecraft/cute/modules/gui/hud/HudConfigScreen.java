package cute.modules.gui.hud;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

import org.lwjgl.input.Keyboard;

//import net.java.games.input.Keyboard;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class HudConfigScreen extends GuiScreen 
{
	private final HashMap<IRender, ScreenPosition> renderers = new HashMap<IRender, ScreenPosition>();
	
	private Optional<IRender> selectedRenderer = Optional.empty();
	
	
	private int prevX;
	private int prevY;
	
	public HudConfigScreen(HudManager api)
	{
		Collection<IRender> registeredRenderers = api.getRegisteredRenderers();
		
		for (IRender render : registeredRenderers)
		{
			if (!render.isEnabled())
			{
				continue;
			}
			
			ScreenPosition pos = render.load();
			
			if  (pos == null)
			{
				pos = ScreenPosition.fromRelative(0.5,  0.5);
			}
			
			adjustBounds(render, pos);
			this.renderers.put(render,pos);
		}
	}
	
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		
		
		super.drawDefaultBackground();
		
		final float zBackup = this.zLevel;
		
		// put infront of everything
		this.zLevel = 200;
		
		this.drawHollowRect(0, 0, this.width - 1, this.height - 1, 0xFFFFFFFF);
		
		for (IRender renderer : renderers.keySet())
		{
			ScreenPosition pos = renderers.get(renderer);
			this.drawHollowRect(pos.getAbsoluteX(), pos.getAbsoluteY(), renderer.getWidth(), renderer.getHeight(), 0xFFFFFFFF);
		}
		
		this.zLevel = zBackup;
	}
	
	
	private void drawHollowRect(int x, int y, int w, int h, int c)
	{
		this.drawHorizontalLine(x, x + w, y + h, c);
		this.drawHorizontalLine(x, x + w, y, c);
		
		this.drawVerticalLine(x, y+h, y, c);
		this.drawVerticalLine(x+w, y+h, y, c);
		
	}
	
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		if(keyCode == Keyboard.KEY_ESCAPE)
		{
			renderers.entrySet().forEach((entry)-> 
			{
				entry.getKey().save(entry.getValue());
			});
			this.mc.displayGuiScreen(null);
		}
	}
	
	
	@Override
	protected void mouseClickMove(int x, int y, int btn, long time)
	{
		if (selectedRenderer.isPresent())
		{
			moveSelectedRenderBy(x - prevX, y - prevY);
		}
		
		this.prevX = x;
		this.prevY = y;
	}


	private void moveSelectedRenderBy(int x, int y) {
		IRender renderer = selectedRenderer.get();
		ScreenPosition pos = renderers.get(renderer);
		
		
		pos.setAbsolute(pos.getAbsoluteX() + x, pos.getAbsoluteY() + y);
		
		adjustBounds(renderer, pos);
		
	}
	
	@Override
	public void onGuiClosed()
	{
		for (IRender renderer : renderers.keySet())
		{
			renderer.save(renderers.get(renderer));
		}
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return true;
	}
	
	private void adjustBounds(IRender renderer, ScreenPosition pos)
	{
		ScaledResolution res = new ScaledResolution(mc);
		
		int sw = res.getScaledWidth();
		int sh = res.getScaledHeight();
		
		int ax = Math.max(0,  Math.min(pos.getAbsoluteX(), Math.max(sw-renderer.getWidth(), 0)));
		int ay = Math.max(0,  Math.min(pos.getAbsoluteY(), Math.max(sh-renderer.getHeight(), 0)));
		
		pos.setAbsolute(ax,ay);
	}
	
	
	@Override
	protected void mouseClicked (int x, int y, int btn) throws IOException
	{
		this.prevX = x;
		this.prevY = y;
		
		loadMouseOver(x, y);
	}


	private void loadMouseOver(int x, int y) {
		this.selectedRenderer = renderers.keySet().stream().filter(new MouseOverFinder(x,y)).findFirst();
	}
	private class MouseOverFinder implements Predicate<IRender>
	{
		
		private int x;
		private int y;
		
		public MouseOverFinder(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		@Override
		public boolean test(IRender renderer)
		{
			ScreenPosition pos = renderers.get(renderer);
			
			int absoluteX = pos.getAbsoluteX();
			int absoluteY = pos.getAbsoluteY();
			
			if (x >= absoluteX && x >= absoluteX + renderer.getWidth())
			{
				if (y >= absoluteY && y <= absoluteY + renderer.getHeight())
				{
					return true;
				}
			}
			
			return false;
		}
	}
}
