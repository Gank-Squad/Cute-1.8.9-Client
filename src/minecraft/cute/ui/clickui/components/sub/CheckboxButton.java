package cute.ui.clickui.components.sub;


import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import cute.Client;
import cute.settings.Checkbox;
import cute.ui.clickui.components.Button;
import cute.ui.clickui.components.Component;
import cute.util.FontUtil;
import cute.util.RenderUtil;

public class CheckboxButton extends Component 
{
	private final Checkbox setting;
	private final Button parent;
	
	private int offset;
	private int x;
	private int y;
	
	public CheckboxButton(Checkbox ss, Button button, int offset) 
	{
		this.subcomponents = new ArrayList<Component>();
		this.setting = ss;
		this.parent = button;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
	}

	@Override
	public void renderComponent() 
	{		
		// background 
		RenderUtil.beginRenderRect();
		RenderUtil.setColor(Client.GlobalColors.backColor);
		RenderUtil.renderRect(x + 2, y, x + width, y + this.getHeight());
		RenderUtil.renderRect(x    , y, x + 2    , y + this.getHeight());

		// background of the checkbox 
		RenderUtil.setColor(Client.GlobalColors.backColor);
		RenderUtil.renderRect(x + width - 8, y + 3, x + width - 2, y + 9);
		
		// the check of the checkbox 
		if(this.setting.getValue()) 
		{
			RenderUtil.setColor(Client.GlobalColors.textColor);
			RenderUtil.renderRect(x + width - 7, y + 4, x + width - 3, y + 8);
		}
		
		RenderUtil.endRenderRect();
		
		
		// scale the text
		GL11.glPushMatrix();
		GL11.glScalef(0.75f,0.75f, 0.75f);

		// render the text
		FontUtil.drawStringWithShadow(
				this.setting.getName(), 
				(x + 3) * Component.tScale + 4, 
				(y + 2) * Component.tScale + 2,
				Client.GlobalColors.textColorInt);
		
		GL11.glPopMatrix();
		
		// this is where the color picker controls get their render event 
		if(!this.open || this.subcomponents.isEmpty())
			return;
	
		for (Component comp : this.subcomponents) 
		{
			comp.renderComponent();
		}
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

		int opY = newOff + this.height;
		
		for(Component comp : this.subcomponents) 
		{
			comp.setOff(opY);
			opY += comp.getHeight();
		}
	}

	@Override
	public void updateComponent(int mouseX, int mouseY) 
	{
		this.hovered = isMouseOnButton(mouseX, mouseY);
		this.y = parent.parent.getY() + offset;
		this.x = parent.parent.getX();
		
		if(!this.isOpen() || this.subcomponents.isEmpty())
			return;
		
		for(Component comp : this.subcomponents) 
		{
			comp.updateComponent(mouseX, mouseY);
		}
	}
	
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) 
	{
		if(!this.isOpen() || this.subcomponents.isEmpty())
			return;
		
		for(Component comp : this.subcomponents) 
		{
			comp.mouseReleased(mouseX, mouseY, mouseButton);
		}
	}
	
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) 
	{
		if(!this.parent.isOpen())
			return;
		
		if(isMouseOnButton(mouseX, mouseY))
		{
			if(button == 0) 
			{
				this.setting.toggleValue();
			}
			else if(button == 1)
			{
				this.open = !this.open;
				this.parent.parent.refresh();
			}
			return;
		}
		
		if(!this.isOpen() || this.subcomponents.isEmpty())
			return;
		
		boolean close = true;
		
		for(Component comp : this.subcomponents) 
		{
			if(comp instanceof ColorPickerButton)
			{
				ColorPickerButton btn = (ColorPickerButton)comp;
				
				if(btn.isMouseOnButton(mouseX, mouseY))
				{
					comp.mouseClicked(mouseX, mouseY, button);
					close = close && false;
				}
				else 
				{
					close = close && true;
				}
			}
		}
		
		this.open = !close;
		this.parent.parent.refresh();
	}
	
	public boolean isMouseOnButton(int x, int y) 
	{
		return x > this.x && 
			   x < this.x + this.width && 
			   y > this.y && 
			   y < this.y + this.height;
	}
}




