package cute.modules.gui.hud;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

import org.lwjgl.input.Keyboard;

import cute.managers.HudManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

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

			renderers.put(render, render.getPos());
		}
	}
	

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{	
	    drawRect(0, 0, this.width, this.height, 0x66101010);

		final float zBackup = this.zLevel;
		
		// put infront of everything
		this.zLevel = 200;
		
		// this updates the position while dragging,
		// you could put the same code in the mouseDragClick event, 
		// but the dragging is way smoother when it's here in the render function
		if (selectedRenderer.isPresent())
		{
			renderers.get(selectedRenderer.get()).setRelative(mouseX - this.prevX, mouseY - this.prevY);
		}
		
		for (IRender renderer : renderers.keySet())
		{
			if(!renderer.isEnabled())
				continue;
			
			ScreenPosition pos = renderers.get(renderer);

			renderer.renderDummy(pos);
			
			//this.drawHollowRect(pos.getAbsoluteX(), pos.getAbsoluteY(), renderer.getWidth(), renderer.getHeight(), 0xFFFFFFFF);
			
		}
		
		this.zLevel = zBackup;
	}
	

	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		if(keyCode != Keyboard.KEY_ESCAPE)
			return;
		
		renderers.entrySet().forEach((entry)-> 
		{
			entry.getKey().setPos(entry.getValue());
		});
		
		Minecraft.getMinecraft().displayGuiScreen(null);
	}
	

	@Override
	protected void mouseReleased(int x, int y, int btn)
	{
		if(selectedRenderer.isPresent())
		{
			IRender render = selectedRenderer.get();
			
			ScreenPosition pos = renderers.get(render);
			
			// save the position 
			render.setPos(pos);
		}
		
		selectedRenderer = Optional.empty();
		this.prevX = 0;
		this.prevY = 0;
	}
	
	@Override
	protected void mouseClicked (int x, int y, int btn)
	{
		if(btn == 0) 
		{
			this.loadMouseOver(x, y); // set the selected renderer 
		}	
		
		this.prevX = x;
		this.prevY = y;
		
		// if the renderer was selected, adjust the prevXY so it's not 0
		if (selectedRenderer.isPresent()) 
		{
			ScreenPosition pos = renderers.get(selectedRenderer.get());
			
			this.prevX -= pos.getRelativeX();
			this.prevY -= pos.getRelativeY();
		}
	}

	
	@Override
	public void onGuiClosed()
	{
		for (IRender renderer : renderers.keySet())
		{
			renderer.setPos(renderers.get(renderer));
		}
	}
	
	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
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
			
			double absoluteX = pos.getRelativeX();
			double absoluteY = pos.getRelativeY();
		
			System.out.println("finding renderer");
			
			return mouseX >= absoluteX && mouseX <= absoluteX + renderer.getWidth() &&
				   mouseY >= absoluteY && mouseY <= absoluteY + renderer.getHeight();
		}
	}
}
