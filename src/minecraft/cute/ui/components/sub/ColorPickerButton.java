package cute.ui.components.sub;


import org.lwjgl.opengl.GL11;

import cute.ui.components.Button;
import cute.ui.components.Component;
import cute.Client;
import cute.settings.ColorPicker;
import cute.util.FontUtil;
import cute.util.RenderUtil;

public class ColorPickerButton extends Component 
{
	private final ColorPicker setting;
	private final Button parent;
	
	private final int min = 0;
	private final int max = 255;
	
	private boolean hovered;
	
	private int redWidth;
	private int greenWidth;
	private int blueWidth;
	
	private int previewColorHeight = (int)(this.height / 2);
//	distnace between colors 
	private int dist = 2;
	
//	0 for none
//	1 for red
//	2 for green
//	3 for blue 
	private int dragging = 0;
	
	private int offset;
	private int x;
	private int y;
	
	public ColorPickerButton(ColorPicker set, Button button, int offset) 
	{
		this.setting = set;
		this.parent = button;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
		
		this.redWidth   = this.width * this.setting.getRed()   / this.max;
		this.greenWidth = this.width * this.setting.getGreen() / this.max;
		this.blueWidth  = this.width * this.setting.getBlue()  / this.max;
	}

	@Override
	public int getHeight() 
	{
		return this.height * 3 + this.previewColorHeight;
	}

	
	@Override
	public void setOff(int newOff) 
	{
		this.offset = newOff;
	}


	

	@Override
	public void renderComponent() 
	{
		// the offset off the top where the preview color is rendered 
		int previewOffset = this.previewColorHeight + dist;
		
		// background
		RenderUtil.beginRenderRect();
		RenderUtil.setColor(Client.GlobalColors.backColor);
		RenderUtil.renderRect(x + 2, y, x + width, y + this.getHeight());
		
		// preview color 
		RenderUtil.setColor(this.setting.getColor());
		RenderUtil.renderRect(x + 2, y, x + this.width, y + this.previewColorHeight);
		
		// red slider 
		RenderUtil.setColor(Client.GlobalColors.red);
		RenderUtil.renderRect(x + 2, y + previewOffset, x + (int)this.redWidth, y + this.height + this.previewColorHeight );
		
		// green slider 
		RenderUtil.setColor(Client.GlobalColors.green);
		RenderUtil.renderRect(x + 2, y + previewOffset + this.height, x + (int)this.greenWidth, y + this.height * 2 + this.previewColorHeight);
		
		// blue slider 
		RenderUtil.setColor(Client.GlobalColors.blue);
		RenderUtil.renderRect(x + 2, y + previewOffset + this.height * 2, x + (int)this.blueWidth, y + this.height * 3 + this.previewColorHeight);
		RenderUtil.endRenderRect();
		
		
		// scale the text
		GL11.glPushMatrix();
		GL11.glScalef(0.75f,0.75f, 0.75f);
		
		// red value
		String displaValue = String.valueOf(this.setting.getRed());
		
		FontUtil.drawStringWithShadow(
				displaValue + " ", 
				(this.x                ) * Component.tScale + 4, 
				(this.y + previewOffset) * Component.tScale + 3, 
				Client.GlobalColors.textColorInt);
		
		// green value
		displaValue =  String.valueOf(this.setting.getGreen());
		
		FontUtil.drawStringWithShadow(
				displaValue + " ", 
				(this.x                              ) * Component.tScale + 4, 
				(this.y + previewOffset + this.height) * Component.tScale + 3, 
				Client.GlobalColors.textColorInt);
		
		// blue value 
		displaValue =  String.valueOf(this.setting.getBlue());
		
		FontUtil.drawStringWithShadow(
				displaValue + " ", 
				(this.x                                  ) * Component.tScale + 4, 
				(this.y + previewOffset + this.height * 2) * Component.tScale + 3, 
				Client.GlobalColors.textColorInt);
		
		GL11.glPopMatrix();
	}
	

	@Override
	public void updateComponent(int mouseX, int mouseY) 
	{
		this.y = parent.parent.getY() + offset - this.height;
		this.x = parent.parent.getX() + this.width;
		
		if(this.dragging == 0)
			return;
		
		double diff = Math.min(this.width, Math.max(0, mouseX - this.x));
		
		int newVal;
		
		if(diff == 0)
		{
			newVal = 0;
		}
		else 
		{
			newVal = (int)((diff / this.width) * max);
		}
		
		switch(this.dragging)
		{
			case 1:
				
				this.redWidth = this.width * this.setting.getRed() / this.max;
				this.setting.setRed(newVal);
				return;
				
			case 2:
				
				this.greenWidth = this.width * this.setting.getGreen() / this.max;
				this.setting.setGreen(newVal);
				return;
				
			case 3:
				
				this.blueWidth = this.width * this.setting.getBlue() / this.max;
				this.setting.setBlue(newVal);
				return;
		}
	}
	
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) 
	{
		if(!this.parent.isOpen() || button != 0)
			return;
		
		if(this.isMouseOnButtonRed(mouseX, mouseY))
		{
			this.dragging = 1;
			return;
		}
		
		if(this.isMouseOnButtonGreen(mouseX, mouseY))
		{
			this.dragging = 2;
			return;
		}
		
		if(this.isMouseOnButtonBlue(mouseX, mouseY))
		{
			this.dragging = 3;
			return;
		}
		
		this.dragging = 0;
	}	
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) 
	{
		this.dragging = 0;
	}
	
	
	
	public boolean isMouseOnButtonRed(int x, int y)
	{
		return x > this.x  && 
			   x < this.x + this.width && 
			   y > this.y + this.previewColorHeight&& 
			   y < this.y + this.height + this.previewColorHeight;
	}
	
	public boolean isMouseOnButtonGreen(int x, int y)
	{
		return x > this.x && 
			   x < this.x + this.width  && 
			   y > this.y + this.height + this.previewColorHeight&& 
			   y < this.y + this.height * 2 + this.previewColorHeight;
	}
	
	public boolean isMouseOnButtonBlue(int x, int y)
	{
		return x > this.x && 
				   x < this.x + this.width  && 
				   y > this.y + this.height + this.previewColorHeight&& 
				   y < this.y + this.height * 3 + this.previewColorHeight;
	}
}






