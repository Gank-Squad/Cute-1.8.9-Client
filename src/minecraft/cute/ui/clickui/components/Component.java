package cute.ui.clickui.components;

import java.awt.Color;
import java.util.ArrayList;


public class Component 
{
	public ArrayList<Component> subcomponents;
	
	protected boolean hovered;
	protected boolean open;
	
	protected final int height = 12;
	protected final int width  = 88;
	
	public static final float tScale = 1.33333333333333f;
	
	public boolean isOpen()
	{
		return this.open;
	}
	
	public boolean isHovered()
	{
		return this.hovered;
	}
	
	public void renderComponent() 
	{
	}
	
	public void updateComponent(int mouseX, int mouseY) 
	{
		
	}
	
	public void mouseClicked(int mouseX, int mouseY, int button) 
	{
		
	}
	
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) 
	{
	}
	
	public void mouseWheel(int mouseX, int mouseY, int delta) 
	{
	}
	
	public int getParentHeight() 
	{
		return 0;
	}
	
	public void keyTyped(char typedChar, int key) 
	{
		
	}

	public void keyUp(char typedChar, int key) 
	{
		
	}
	
	public void setOff(int newOff) 
	{
		
	}
	
	public int getHeight() 
	{
		return 0;
	}
}





