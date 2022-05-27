package cute.ui.clickui.components.sub;


import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import cute.Client;
import cute.settings.Slider;
import cute.ui.clickui.components.Button;
import cute.ui.clickui.components.Component;
import cute.util.FontUtil;
import cute.util.RenderUtil;
import cute.util.Util;

public class SliderButton extends Component 
{
	private final Button parent;
	private final Slider setting;

	private boolean hovered;
	private boolean dragging = false;
	
	private int offset;
	private int x;
	private int y;

	private double renderWidth;
	
	public SliderButton(Slider value, Button button, int offset) 
	{
		this.subcomponents = new ArrayList<Component>();
		this.setting = value;
		this.parent = button;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
		
		double min = this.setting.getMinValue();
		double max = this.setting.getMaxValue();
		this.renderWidth = (this.parent.parent.width) * (this.setting.getValue() - min) / (max - min);
	}
	
	@Override
	public int getHeight() 
	{
		if(this.open) 
		{
			return (this.height * (this.subcomponents.size() + 1));
		}
		
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
		// render the background 
		RenderUtil.beginRenderRect();
		RenderUtil.setColor(Client.GlobalColors.backColor);
		RenderUtil.renderRect(x + 2, y, x + width, y + this.height);
		RenderUtil.renderRect(x, y, x + 2, y + this.height);
		
		// render the slider 
		RenderUtil.setColor(Client.GlobalColors.sliderColor);
		RenderUtil.renderRect(x + 2, y, x + (int)this.renderWidth, y + this.height);
		
		// render the slider again if hovered (alpha adds up so it's brighter)
		if(this.hovered) 
		{
			RenderUtil.renderRect(x + 2, y, x + (int)this.renderWidth, y + this.height);
		}
		
		RenderUtil.endRenderRect();
		
		// scale the text
		GL11.glPushMatrix();
		GL11.glScalef(0.75f,0.75f, 0.75f);
		
		// render the name
		String displayValue = this.setting.getName() + " ";
		
		FontUtil.drawStringWithShadow(
				displayValue, 
				(this.x + 3) * Component.tScale + 4, 
				(this.y + 2) * Component.tScale + 2, 
				Client.GlobalColors.textColorInt);
		
		// render the value
		displayValue = String.valueOf(Util.roundToPlace(this.setting.getValue(), 2));
		
		FontUtil.drawStringWithShadow(
				displayValue, 
				(this.x + this.width) * Component.tScale - FontUtil.getStringWidth(displayValue), 
				(this.y + 2)          * Component.tScale + 2,
				Client.GlobalColors.textColorInt);
		

		GL11.glPopMatrix();
	}

	
	@Override
	public void updateComponent(int mouseX, int mouseY) 
	{
		this.hovered = isMouseOnButton(mouseX, mouseY);
		this.y = parent.parent.getY() + offset;
		this.x = parent.parent.getX();
		
		if (!dragging)
			return;
		
		double diff = Math.min(width, Math.max(0, mouseX - this.x));

		double min = this.setting.getMinValue();
		double max = this.setting.getMaxValue();
		
		this.renderWidth = (this.parent.parent.width) * (this.setting.getValue() - min) / (max - min);
		
		
		if (diff == 0) 
		{
			this.setting.setValue(this.setting.getMinValue());
		}
		else 
		{
			double newValue = (diff / width) * (max - min) + min;
			this.setting.setValue(newValue);
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) 
	{
		if(!this.parent.isOpen() || button != 0 || !isMouseOnButton(mouseX, mouseY))
			return;
		
		dragging = true;	
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) 
	{
		dragging = false;
	}
	
	public boolean isMouseOnButton(int x, int y) 
	{
		return x > this.x && 
			   x < this.x + this.width  &&
			   y > this.y && 
			   y < this.y + this.height;
	}
	
//	public boolean isMouseOnButtonI(int x, int y) 
//	{
//		return x > this.x + this.width / 2 && 
//			   x < this.x + this.width &&
//			   y > this.y && 
//			   y < this.y + this.height;
//	}
}









