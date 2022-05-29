package cute.ui.clickui;


import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import cute.managers.ModuleManager;
import cute.modules.enums.Category;
import cute.ui.clickui.components.Component;
import cute.ui.clickui.components.Frame;
import net.minecraft.client.gui.GuiScreen;


public class ClickUI extends GuiScreen 
{
	public static boolean keyboardInUses = false;
	
	public static final Color color = new Color(153, 207, 220);
	
	public static ArrayList<Frame> frames;

	public int closeOnKey = 1;

	public ClickUI() 
	{
		this.frames = new ArrayList<Frame>();
		
		int frameX = 5;
		
		for(Category category : Category.values()) 
		{
			if(ModuleManager.getModulesInCategory(category).size() == 0)
				continue;
			
			Frame frame = new Frame(category);
			
			frame.setX(frameX);
			
			frames.add(frame);
			
			frameX += frame.getWidth() + 1;
		}		
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) 
	{
		drawRect(0, 0, this.width, this.height, 0x66101010);
		
		for (Frame frame : frames) 
		{
			frame.renderFrame(this.fontRendererObj);
			frame.updatePosition(mouseX, mouseY);
			
			for (Component comp : frame.getComponents()) 
			{
				comp.updateComponent(mouseX, mouseY);
			}
		}
	}

	@Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException 
	{
		for(Frame frame : frames) 
		{
			if (frame.isWithinHeader(mouseX, mouseY)) 
			{
				if(mouseButton == 0) 
				{
					frame.setDrag(true);
					frame.dragX = mouseX - frame.getX();
					frame.dragY = mouseY - frame.getY();
				}
				
				if(mouseButton == 1) 
				{
					frame.toggleOpen();
				}
			}
						
			if(!frame.isOpen())
				continue;
			
			if(frame.getComponents().isEmpty())
				continue;
			
			for(Component component : frame.getComponents()) 
			{
				component.mouseClicked(mouseX, mouseY, mouseButton);
			}
		}
	}
	
	@Override 
	protected void mouseWheel(int mouseX, int mouseY, int delta)
	{
		for(Frame frame : frames) 
		{
			if(!frame.isOpen())
				continue;
			
			if(frame.getComponents().isEmpty())
				continue;
			
			for(Component component : frame.getComponents()) 
			{
				component.mouseWheel(mouseX, mouseY, delta);
			}
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) 
	{
		for(Frame frame : frames) 
		{
			if(!frame.isOpen())
				continue;
			
			if(frame.getComponents().isEmpty())
				continue;
			
			for(Component component : frame.getComponents()) 
			{
				component.keyTyped(typedChar, keyCode);
			}
		}
		
		if(ClickUI.keyboardInUses)
			return;

		if (keyCode == Keyboard.KEY_ESCAPE || keyCode == this.closeOnKey) 
		{
			ClickUI.keyboardInUses = false;
            this.mc.displayGuiScreen(null);
        }
	}

	@Override
	protected void keyUp(char typedChar, int keyCode)
	{
		for(Frame frame : frames) 
		{
			if(!frame.isOpen())
				continue;
			
			if(frame.getComponents().isEmpty())
				continue;
			
			for(Component component : frame.getComponents()) 
			{
				component.keyUp(typedChar, keyCode);
			}
		}
	}
	
//	@Override
//	public void onGuiClosed() 
//	{
////		if(!Hydrogen.getClient().panic) 
////		{
////			ClickGuiConfig clickGuiConfig = new ClickGuiConfig();
////			clickGuiConfig.saveConfig();
////		}
//
//		super.onGuiClosed();
//	}

	
	@Override
    protected void mouseReleased(int mouseX, int mouseY, int state) 
	{
		for(Frame frame : frames) 
		{
			frame.setDrag(false);
		}
		
		for(Frame frame : frames) 
		{
			if(!frame.isOpen()) 
				continue;
			
			if(frame.getComponents().isEmpty()) 
				continue;
			
			for(Component component : frame.getComponents()) 
			{
				component.mouseReleased(mouseX, mouseY, state);
			}
		}
	}

	
	@Override
	public boolean doesGuiPauseGame() 
	{
		return false;
	}
}