package cute.ui.clickui.components;


import java.awt.Color;
import java.util.ArrayList;

import cute.Client;
import cute.modules.Module;
import cute.settings.Checkbox;
import cute.settings.ColorPicker;
import cute.settings.ListSelection;
import cute.settings.Mode;
import cute.settings.Setting;
import cute.settings.Slider;
import cute.settings.SubSetting;
import cute.settings.enums.ListInputType;
import cute.ui.clickui.ClickUI;
import cute.ui.clickui.components.sub.CheckboxButton;
import cute.ui.clickui.components.sub.ColorPickerButton;
import cute.ui.clickui.components.sub.DropDownButton;
import cute.ui.clickui.components.sub.KeybindButton;
import cute.ui.clickui.components.sub.ModeButton;
import cute.ui.clickui.components.sub.SearchButton;
import cute.ui.clickui.components.sub.SliderButton;
import cute.ui.clickui.components.sub.TextInputButton;
import cute.util.FontUtil;
import cute.util.RenderUtil;

public class Button extends Component 
{
	public final Module mod;
	public final Frame parent;
	public final ArrayList<Component> subcomponents;

	public int offset;
	
	public Button(Module mod, Frame parent, int offset) 
	{
		this.mod    = mod;
		this.parent = parent;
		this.offset = offset;
		this.subcomponents = new ArrayList<Component>();
		this.open = false;
		
		int opY  = offset + this.height;
		int opY2 = offset + this.height;
		
		Setting hasList = null;
		
		Component last = null;
		
		for(Setting s : mod.getSettings())
		{
			switch(s.getSettingType())
			{
				default:
					last = null;
					continue;
					
				case LIST:
					last =  new DropDownButton((ListSelection)s, this, opY);;
					this.subcomponents.add(last);
					opY += this.height;
					hasList = s; 
					break;
					
				case CHECKBOX:
					last = new CheckboxButton((Checkbox)s, this, opY);
					this.subcomponents.add(last);
					opY += this.height;
					break;
					
				case SLIDER:
					last = new SliderButton((Slider)s, this, opY);
					this.subcomponents.add(last);
					opY += this.height;
					break;
					
				case MODE:
					last = new ModeButton((Mode)s, this, opY);
					this.subcomponents.add(last);
					opY += this.height;
					break;
			}
			
			opY2 = opY;
			
			for(SubSetting ss : s.getSubSettings())
			{
				last.subcomponents.add(new ColorPickerButton((ColorPicker)ss, this, opY2));	
				opY2 += this.height;
			}
			
			if(hasList != null && hasList instanceof ListSelection)
			{
				ListInputType t = ((ListSelection)hasList).listInputType;
				
				switch(t)
				{
					case NONE:
						hasList = null;
						continue;
						
					case TEXT:
						this.subcomponents.add(new TextInputButton(this, opY, (ListSelection)hasList));
						opY += this.height;
						
						hasList = null;
						continue;
						
					case SEARCH:
						this.subcomponents.add(new SearchButton(this, opY, (ListSelection)hasList));
						opY += this.height;
						
						hasList = null;
						continue;
						
					case SEARCH_AND_TEXT:
						this.subcomponents.add(new SearchButton(this, opY, (ListSelection)hasList));
						opY += this.height;
						
						this.subcomponents.add(new TextInputButton(this, opY, (ListSelection)hasList));
						opY += this.height;
						
						hasList = null;
						continue;
				}
			}
		}
		
		
		this.subcomponents.add(new KeybindButton(this, opY));
	}


	@Override
	public int getHeight() 
	{
		if(this.open) 
		{
			int h = this.height;
			for(Component c : this.subcomponents)
			{
				h += c.getHeight();
			}
			return h;
		}
		
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
	public void renderComponent()
	{
		int x = this.parent.getX();
		int y = this.parent.getY() + this.offset;
		int x2 = x + this.parent.getWidth();
		int y2 = y + this.height;
		
		RenderUtil.beginRenderRect();
		RenderUtil.setColor(Client.GlobalColors.backColor);
		RenderUtil.renderRect(x, y, x2, y2);
		RenderUtil.setColor(Client.GlobalColors.sliderColor);
		RenderUtil.renderRect(x, y, x2, y2);
		
		if(this.hovered)
		{
			RenderUtil.renderRect(x, y, x2, y2);
		}
		
		if(this.mod.isEnabled())
		{
			RenderUtil.setColor(new Color(0, 200, 0));
			RenderUtil.renderRect(x, y, x2, y2);
		}

		RenderUtil.endRenderRect();
		
		FontUtil.drawTotalCenteredStringWithShadowMC(
				this.mod.getName(), 
				x + this.parent.getWidth() / 2, 
				y + (int)(this.height / 2) + 1, 
				Client.GlobalColors.textColorInt);

		
		if(!this.open)
			return;
		
		if (this.subcomponents.isEmpty())
			return;
	
		for (Component comp : this.subcomponents) 
		{
			comp.renderComponent();
		}
		
		
		RenderUtil.setColor(ClickUI.color);
		RenderUtil.renderRectSingle(x + 2, y2, x + 3, y + ((this.subcomponents.size() + 1) * this.height));
	}
	
	
	
	@Override
	public void updateComponent(int mouseX, int mouseY) 
	{
		 this.hovered = isMouseOnButton(mouseX, mouseY);
		
		if(this.subcomponents.isEmpty())
			return;
		
		for(Component comp : this.subcomponents) 
		{
			comp.updateComponent(mouseX, mouseY);
		}
	}
	
	@Override
	public void mouseWheel(int mouseX, int mouseY, int delta) 
	{
		for(Component comp : this.subcomponents) 
		{
			comp.mouseWheel(mouseX, mouseY, delta);
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) 
	{
		if(isMouseOnButton(mouseX, mouseY))
		{
			if(button == 0) 
			{
				this.mod.toggle();
			}

			else if(button == 1) 
			{
				this.open = !this.open;
				this.parent.refresh();
			}
		}
		
			
		for(Component comp : this.subcomponents) 
		{
			comp.mouseClicked(mouseX, mouseY, button);
		}
	}
	
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) 
	{
		for(Component comp : this.subcomponents) 
		{
			comp.mouseReleased(mouseX, mouseY, mouseButton);
		}
	}
	
	@Override
	public void keyUp(char typedChar, int key) 
	{
		for(Component comp : this.subcomponents) 
		{
			comp.keyUp(typedChar, key);
		}
	}
	@Override
	public void keyTyped(char typedChar, int key) 
	{
		for(Component comp : this.subcomponents) 
		{
			comp.keyTyped(typedChar, key);
		}
	}
	
	
	public boolean isMouseOnButton(int x, int y) 
	{
		return x > parent.getX() && 
			   x < parent.getX() + parent.getWidth() && 
			   y > this.parent.getY() + this.offset &&
			   y < this.parent.getY() + this.height + this.offset;
	}
}



