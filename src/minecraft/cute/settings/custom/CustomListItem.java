package cute.settings.custom;

import java.awt.Color;

import cute.settings.ColorPicker;

public class CustomListItem 
{
	protected String displayValue = null;
	protected ColorPicker colorPicker = null;
	
	public boolean hasColorPicker = false;
	public boolean isToggleable = false;
	
	public CustomListItem()
	{
		
	}
	
	public CustomListItem(String display)
	{
		this.displayValue = display;
	}
	
	public void setHasColorPicker(boolean tf)
	{
		if(tf)
		{
			if(this.colorPicker == null)
				this.colorPicker = new ColorPicker(null, "", new Color(0,0,0));
			
			this.hasColorPicker = true;
		}
		else
		{
			this.hasColorPicker = false;
		}
	}
	
	public ColorPicker getColorPicker()
	{
		if(!this.hasColorPicker)
			return null;
		
		return this.colorPicker;
	}
	
	@Override
	public String toString()
	{
		if(this.displayValue == null)
			return super.toString();
		
		return this.displayValue;
	}
	
	
}
