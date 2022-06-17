package cute.ui.clickui.components.sub;

import cute.settings.ListSelection;
import cute.settings.custom.CustomListItem;
import cute.settings.enums.ListType;
import cute.ui.clickui.components.Button;

public class TextInputButton extends TextButtonBase
{
	private final Button parent;
	private final ListType type;
	private final ListSelection setting;
	
	public TextInputButton(Button button, int offset, ListSelection setting) 
	{
		super(button, offset);
		
		this.parent = button;
		
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
		this.type = setting.getListType();
		this.setting = setting;
		
		this.idleTextDisplay = "[ Input ]";
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
	public void onEnter(String input)
	{
		if(input == null || input.length() == 0)
			return;
		
		this.setting.term = this.searchTerm;
		
		switch(this.type)
		{
			case PLAYERNAME:
				this.setting.enableItem(input.trim().toLowerCase());
				
				this.searchTerm = "";
				break;
				
			case CUSTOM:
				
				CustomListItem cli = new CustomListItem(input);
				
				this.setting.sendUpdate(cli);
				
				break;
				
			default:
				break;
		}
	}
}






