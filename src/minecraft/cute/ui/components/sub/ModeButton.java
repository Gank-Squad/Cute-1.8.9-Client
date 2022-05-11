package cute.ui.components.sub;


import org.lwjgl.opengl.GL11;

import cute.ui.components.Button;
import cute.ui.components.Component;
import cute.settings.Mode;
import cute.util.FontUtil;
import cute.util.RenderUtil;

public class ModeButton extends Component {

	private boolean hovered;
	private final Button parent;
	private final Mode setting;
	private int offset;
	private int x;
	private int y;

	private int modeIndex;
	
	public ModeButton(Mode set, Button button, int offset) 
	{
		this.setting = set;
		this.parent = button;
		this.x = button.parent.getX() + button.parent.getWidth();
		this.y = button.parent.getY() + button.offset;
		this.offset = offset;
		this.modeIndex = 0;
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
		RenderUtil.setColor(this.backColor);
		RenderUtil.renderRect(x, y, x + width, y + this.height);
		RenderUtil.renderRect(x, y, x + 2    , y + 12         );
		RenderUtil.endRenderRect();
		
		// scale the text 
		GL11.glPushMatrix();
		GL11.glScalef(0.75f,0.75f, 0.75f);

		
		// render the setting name
		String modeText = this.setting.getName();

		FontUtil.drawStringWithShadow(
				modeText, 
				(this.x + 3) * Component.tScale + 4, 
				(this.y + 2) * Component.tScale + 2,
				this.textColorInt);
		
		// render the setting value
		modeText = this.setting.getMode();
		
		FontUtil.drawStringWithShadow(
				modeText, 
				(this.x + this.width) * Component.tScale - FontUtil.getStringWidth(modeText), 
				(this.y + 2         ) * Component.tScale + 2,
				this.textColorInt);
		
		
		GL11.glPopMatrix();
	}


	
	@Override
	public void updateComponent(int mouseX, int mouseY) 
	{
		this.hovered = isMouseOnButton(mouseX, mouseY);
		this.y = parent.parent.getY() + offset;
		this.x = parent.parent.getX();
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) 
	{
		if(button != 0 || !this.parent.isOpen() || !isMouseOnButton(mouseX, mouseY))
			return;
		
		this.setting.nextMode();
	}
	
	public boolean isMouseOnButton(int x, int y) 
	{
		return x > this.x && 
			   x < this.x + 88 && 
			   y > this.y && 
			   y < this.y + this.height;
	}
}




