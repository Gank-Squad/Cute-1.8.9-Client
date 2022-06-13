package cute.ui.clickui.components.sub;

import java.util.List;
import java.util.stream.Stream;

import org.lwjgl.opengl.GL11;

import cute.Client;
import cute.settings.ListSelection;
import cute.settings.enums.ListType;
import cute.ui.clickui.components.Button;
import cute.ui.clickui.components.Component;
import cute.util.Cache;
import cute.util.EntityUtil;
import cute.util.FontUtil;
import cute.util.RenderUtil;
import cute.util.types.BlockInfo;
import cute.util.types.VirtualBlock;

public class SearchButton extends TextButtonBase  
{
	private final Button parent;
	private final ListType type;
	private final ListSelection setting;
	
	private final int listCap = 10;
	
	private int scrollButtonSize = this.height - 3;
	private int scrollIndex = 0;
	
	private Object[] foundSearchTerms = new Object[0];
	
	public SearchButton(Button button, int offset, ListSelection setting)
	{
		super(button, offset);
		
		this.parent = button;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
		this.type = setting.getListType();
		this.setting = setting;
	}
	
	@Override
	public int getHeight() 
	{
		return this.height;
	}

	
	public int getListHeight()
	{
		return this.height * Math.min(this.listCap, this.foundSearchTerms.length) + this.scrollButtonSize * 2;
	}
	
	public int getListY()
	{
		return this.y - this.scrollButtonSize;
	}
	
	public int getListX()
	{
		return this.x + this.width;
	}
	
	@Override
	public void setOff(int newOff) 
	{
		this.offset = newOff;
	}
	
	@Override
	public void renderComponent() 
	{
		super.renderComponent();
		
		// if the block list isn't open we're done here
		if(!this.binding)
			return;
		
		int lx = this.getListX();
		int ly = this.getListY();
		int range = Math.min(this.foundSearchTerms.length, scrollIndex + this.listCap);
		
		// render background for the list of blocks 
		RenderUtil.setColor(Client.GlobalColors.backColor);
		RenderUtil.renderRectSingle(lx + 2, ly, lx + width, ly + this.getListHeight());
		
		GL11.glPushMatrix();
		GL11.glScalef(0.75f,0.75f, 0.75f);
		
		// render up and down arrows for the scroll buttons 
		FontUtil.drawStringWithShadow(
				"/\\     " + String.valueOf(scrollIndex) + "-" + String.valueOf(range) + "/" + String.valueOf(this.foundSearchTerms.length), 
				lx * Component.tScale + 4, 
				ly * Component.tScale + 4, 
				Client.GlobalColors.textColorInt);
		
		FontUtil.drawStringWithShadow(
				"\\/", 
				lx * Component.tScale + 4, 
				(ly + this.getListHeight() - this.scrollButtonSize) * Component.tScale + 2, 
				Client.GlobalColors.textColorInt);
		
		
		// render all the blocks 
		ly += this.scrollButtonSize;
		
		for(int i = scrollIndex; i < Math.min(this.foundSearchTerms.length, scrollIndex + this.listCap); i++)
		{
			String display;
			
			switch(this.type)
			{
			default:
				display = "NULL";
				break;
			case BLOCK:
				display = ((BlockInfo)this.foundSearchTerms[i]).displayName;
				break;
				
			case PLAYERNAME:
				display = (String)this.foundSearchTerms[i];
				break;
			}
			
			
			FontUtil.drawStringWithShadow(
					display + " ", 
					lx * Component.tScale + 4, 
					ly * Component.tScale + 4, 
					Client.GlobalColors.textColorInt);

			ly += this.height;
		}
		
		GL11.glPopMatrix();
	}
	
	@Override
	public void mouseWheel(int mouseX, int mouseY, int delta)
	{
		if(!this.isMouseOnListAndScrolls(mouseX, mouseY))
			return;
			
		if(delta > 0) // wheel up
		{
			this.scrollIndex = Math.max(0, this.scrollIndex - 1);
		}
		else if (delta < 0) // wheel down
		{
			if(this.foundSearchTerms.length <= this.listCap)
				return;
			
			this.scrollIndex = Math.min(this.foundSearchTerms.length - this.listCap, this.scrollIndex + 1);
		}
	}
	
	public int getListHoverIndex(int x, int y)
	{
		return this.scrollIndex + (int)((y - this.y) / this.height);
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) 
	{
		if(!this.parent.isOpen())
			return;
		
		if(button == 1)
		{
			super.setBinding(false);
			return;
		}

		if(button == 0)
		{
			if(this.isMouseOnSearch(mouseX, mouseY))
			{
				this.setBinding(true);
				return;
			}
			
			if(!this.binding)
				return;
			
			if(this.isMouseOnList(mouseX, mouseY))
			{
				int index = this.getListHoverIndex(mouseX, mouseY);
				
				if(index >= 0 && index < this.foundSearchTerms.length)
				{
//					this.setBinding(false);
					
					switch(this.type)
					{
						case BLOCK:
							this.setting.enableItem((VirtualBlock)this.foundSearchTerms[index]);
							break;
							
						case PLAYERNAME:
							this.setting.enableItem((String)this.foundSearchTerms[index]);
							break;
					}
				}
				return;
			}

			if(this.isMouseOnScrollUp(mouseX, mouseY))
			{
				this.scrollIndex = Math.max(0, this.scrollIndex - 1);
				return;
			}
			
			if(this.isMouseOnScrollDown(mouseX, mouseY))
			{	
				if(this.foundSearchTerms.length <= this.listCap)
					return;
				
				this.scrollIndex = Math.min(this.foundSearchTerms.length - this.listCap, this.scrollIndex + 1);
				return;
			}
			
			this.setBinding(false);
		}		
	}	
	

	@Override
	public boolean isOpen()
	{
		return this.foundSearchTerms.length != 0;
	}
	
	@Override 
	public void onEnter(String search)
	{
		String lowerSearch = search.toLowerCase();
		
		switch(this.type)
		{
		case PLAYERNAME:
			
			this.scrollIndex = 0;
			
			List<String> names = EntityUtil.getTabMenuPlayerNames();
			
			Stream<String> s = names.stream().
					filter(x -> x.toLowerCase().contains(lowerSearch));

			this.foundSearchTerms = s.toArray();
			break;
			
		case BLOCK:
			this.scrollIndex = 0;
			this.foundSearchTerms = Cache.searchForBlock(lowerSearch);
			break;
		}
		
	}	

	public boolean isMouseOnScrollUp(int x, int y)
	{
		if(!this.isOpen())
			return false;
		
		int lx = this.getListX();
		int ly = this.getListY();
		
		return x > lx && 
			   x < lx + this.width && 
			   y > ly &&
			   y < ly + this.scrollButtonSize;
	}
	
	public boolean isMouseOnScrollDown(int x, int y)
	{
		if(!this.isOpen())
			return false;
		
		int lx = this.getListX();
		int ly = this.getListY() + this.getListHeight() - this.scrollButtonSize;
		
		return x > lx && 
			   x < lx + this.width && 
			   y > ly &&
			   y < ly + this.scrollButtonSize;
	}
	
	
	public boolean isMouseOnList(int x, int y)
	{
		if(!this.isOpen())
			return false;
		
		int lx = this.getListX();
		int ly = this.y;
		int height = this.getListHeight() - this.scrollButtonSize*2;
		
		return x > lx && 
			   x < lx + this.width && 
			   y > ly &&
			   y < ly + height;
	}
	
	public boolean isMouseOnButton(int x, int y) 
	{
		return x > this.x && 
			   x < this.x + this.width && 
			   y > this.y &&
			   y < this.y + this.height;
	}
	
	public boolean isMouseOnListAndScrolls(int x, int y)
	{
		if(!this.isOpen())
			return false;
		
		int lx = this.getListX();
		int ly = this.getListY();
		int height = this.getListHeight();
		
		return x > lx && 
			   x < lx + this.width && 
			   y > ly &&
			   y < ly + height;
	}
}




