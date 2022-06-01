package cute.ui.clickui.components.sub;

import org.lwjgl.opengl.GL11;

import cute.Client;
import cute.settings.ListSelection;
import cute.settings.enums.ListType;
import cute.ui.clickui.components.Button;
import cute.ui.clickui.components.Component;
import cute.util.FontUtil;
import cute.util.RenderUtil;
import cute.util.types.BlockInfo;

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
		if(input == null || input.length() == 0 || this.type != ListType.PLAYERNAME)
			return;
		
		this.setting.enableItem(input.trim());
		
		this.searchTerm = "";
		super.setBinding(false);	
	}
}






