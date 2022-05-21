package cute.modules.gui.hud;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
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
		
//		System.out.println("test2");
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
//		super.drawDefaultBackground();
	    drawRect(0, 0, this.width, this.height, 0x66101010);
//	    System.out.println("test");
		final float zBackup = this.zLevel;
		
		// put infront of everything
		this.zLevel = 200;
		
		this.drawHollowRect(0, 0, this.width - 1, this.height - 1, 0xFFFFFFFF);
		
//		renderers.forEach((renderer, position) -> renderer.renderDummy(position));
		
		for (IRender renderer : renderers.keySet())
		{
			ScreenPosition pos = renderers.get(renderer);
			
			renderer.renderDummy(pos);
			
			this.drawHollowRect(pos.getAbsoluteX(), pos.getAbsoluteY(), renderer.getWidth(), renderer.getHeight(), 0xFFFFFFFF);
			
		}
		
		this.zLevel = zBackup;
	}
	
	
	private void drawHollowRect(int x, int y, int w, int h, int c)
	{
//		this.drawHorizontalLine(x, x + w, y + h, c);
//		this.drawHorizontalLine(x, x + w, y, c);
//		
//		this.drawVerticalLine(x, y+h, y, c);
//		this.drawVerticalLine(x+w, y+h, y, c);
//		System.out.println("abc");
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
			Minecraft.getMinecraft().displayGuiScreen(null);
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

	@Override
	protected void mouseClicked (int x, int y, int btn) // throws IOException
	{
		IRender renderer = selectedRenderer.get();
		ScreenPosition pos = renderers.get(renderer);
		
		System.out.println(pos.getAbsoluteX());
		
		this.prevX = x;
		this.prevY = y;
		
		loadMouseOver(x, y);
		
	}

	private void moveSelectedRenderBy(int offsetX, int offsetY) {
		IRender renderer = selectedRenderer.get();
		ScreenPosition pos = renderers.get(renderer);
		
		pos.setAbsolute(
					pos.getAbsoluteX() + offsetX,
					pos.getAbsoluteY() + offsetY
				);
		
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
		return false;
	}
	
	private void adjustBounds(IRender renderer, ScreenPosition pos)
	{
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		
		int sw = res.getScaledWidth();
		int sh = res.getScaledHeight();
		
		int ax = Math.max(0, Math.min(
					pos.getAbsoluteX(),
					Math.max(sw - renderer.getWidth(), 0)
				));
		int ay = Math.max(0, Math.min(pos.getAbsoluteY(), Math.max(sh - renderer.getHeight(), 0)));
		
		pos.setAbsolute(ax,ay);
	}


	private void loadMouseOver(int x, int y) {
		this.selectedRenderer = renderers.keySet().stream()
				.filter(new MouseOverFinder(x,y))
				.findFirst();
	}
	
	
	private class MouseOverFinder implements Predicate<IRender>
	{
		
		private int mouseX;
		private int mouseY;
		
		public MouseOverFinder(int x, int y)
		{
			this.mouseX = x;
			this.mouseY = y;
		}
		
		@Override
		public boolean test(IRender renderer)
		{
			ScreenPosition pos = renderers.get(renderer);
			
			int absoluteX = pos.getAbsoluteX();
			int absoluteY = pos.getAbsoluteY();
			
			if (mouseX >= absoluteX && mouseX <= absoluteX + renderer.getWidth())
			{
				if (mouseY >= absoluteY && mouseY <= absoluteY + renderer.getHeight())
				{
					return true;
				}
			}
			
			return false;
		}
	}
}
