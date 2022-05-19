package cute.ui.components.sub;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cute.ui.ClickUI;
import cute.ui.components.Button;
import cute.ui.components.Component;
import cute.util.FontUtil;
import cute.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class TextButton extends Component   
{
	private final Button parent;
	
	protected boolean binding;
	
	protected int offset;
	protected int x;
	protected int y;
	
	protected String searchTerm = "";
	
	private boolean backSpaceDown = false;
	private Long backSpaceDelay = Minecraft.getSystemTime(); 
	
	public TextButton(Button button, int offset)
	{
		this.parent = button;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
	}
	
	@Override
	public int getHeight() 
	{
		return this.height;
	}

	
	@Override
	public void setOff(int newOff) 
	{
		this.offset = newOff;
	}
	
	@Override
	public void renderComponent() 
	{
		RenderUtil.beginRenderRect();
		
		// background 
		RenderUtil.setColor(this.backColor);
		RenderUtil.renderRect(x + 2, y, x + width, y + this.getHeight());
		RenderUtil.renderRect(x    , y, x + 2    , y + this.getHeight());
		RenderUtil.endRenderRect();
		
		// draw the text for the setting 
		GL11.glPushMatrix();
		GL11.glScalef(0.75f,0.75f, 0.75f);
		
		String text = this.binding ? this.searchTerm : "Search";
		
		FontUtil.drawStringWithShadow(
				text, 
				(this.x + 3) * Component.tScale + 4, 
				(this.y + 2) * Component.tScale + 2,
				this.textColorInt);
		
		GL11.glPopMatrix();
	}
	
	@Override
	public void updateComponent(int mouseX, int mouseY) 
	{
		this.y = parent.parent.getY() + offset;
		this.x = parent.parent.getX();
		
		if(this.backSpaceDown)
		{
			if(this.searchTerm.length() == 0)
				return;
			
			Long time = Minecraft.getSystemTime();
			
			if(time - 100 > this.backSpaceDelay)
			{
				this.searchTerm = this.searchTerm.substring(0, this.searchTerm.length() - 1);
				this.backSpaceDelay = time;
			}
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) 
	{
		if(!this.parent.isOpen())
			return;
		
		if(button == 1)
		{
			this.setBinding(false);
			return;
		}
		
		if(button == 0)
		{
			if(this.isMouseOnSearch(mouseX, mouseY))
			{
				this.setBinding(true);
				return;
			}
			
			this.setBinding(false);	
		}
		
		System.out.println("sex");
	}	
	
	public void setBinding(boolean state)
	{
		this.binding = state;
		ClickUI.keyboardInUses = state;
	}
	
	@Override
	public void keyUp(char typedChar, int key) 
	{
		if(key == Keyboard.KEY_BACK)
		{
			this.backSpaceDown = false;
		}
	}
	
	
	public void onEnter(String search)
	{
		
	}
	
	@Override
	public void keyTyped(char typedChar, int key) 
	{
		if(!this.binding) 
			return;
		
		this.backSpaceDown = false;
		switch(key)
		{
			case Keyboard.KEY_ESCAPE:
				
				if(this.searchTerm.length() > 0) 
				{
					this.searchTerm = "";
					return;
				}
					
				this.setBinding(false);
				
				return;
				
			case Keyboard.KEY_BACK:
				if(this.searchTerm.length() == 0)
					return;
				
				if(GuiScreen.isCtrlKeyDown())
				{
					int index = this.searchTerm.lastIndexOf(' ');
					
					if(index == -1) 
					{
						this.searchTerm = "";
						return;	
					}
					
					this.searchTerm = this.searchTerm.substring(0, index);
					return;
				}
				
				this.backSpaceDown = true;
				this.searchTerm = this.searchTerm.substring(0, this.searchTerm.length() - 1);
				this.backSpaceDelay = Minecraft.getSystemTime();
				return;
				
			case Keyboard.KEY_RETURN:
				this.onEnter(this.searchTerm.toLowerCase());
				return;
				
			default:
				if(typedChar >= 'a' && typedChar <= 'z' ||
				   typedChar >= 'A' && typedChar <= 'Z' || 
				   typedChar >= '0' && typedChar <= '9' ||
				   typedChar >= ' ' && typedChar <= '?')
				{
					this.searchTerm += typedChar;
				}
				return;
		}
	}
	

	public boolean isMouseOnSearch(int x, int y) 
	{
		return x > this.x && 
			   x < this.x + this.width && 
			   y > this.y &&
			   y < this.y + this.height;
	}
}
