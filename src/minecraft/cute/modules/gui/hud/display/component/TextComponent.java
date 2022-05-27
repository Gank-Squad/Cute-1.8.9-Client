package cute.modules.gui.hud.display.component;

import org.lwjgl.opengl.GL11;

import cute.modules.gui.hud.ScreenPosition;
import cute.modules.gui.hud.display.DraggableComponent;
import net.minecraft.client.Minecraft;


public class TextComponent extends DraggableComponent
{
	protected String text;
	
	private float scaleX;
	private float scaleY;

	public TextComponent(int relativeX, int relativeY, int parentX,
			int parentY, String text, int color)
	{
		super(relativeX, relativeY, 
				Minecraft.getMinecraft().fontRendererObj.getStringWidth(text), 
				Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 
				parentX, parentY, color);
		
//		this.scaleX = 1;
//		this.scaleY = 1;
		this.text = text;
	}
	public TextComponent(int relativeX, int relativeY, int width, int height, int parentX, int parentY, String text, int color)
	{
		super(relativeX, relativeY, width, height, parentX, parentY, color);
		this.text = text;
//		setScaleX(width);
//		setScaleY(height);
	}
	public TextComponent(int relativeX, int relativeY, float scaleX, float scaleY, int parentX, int parentY, String text, int color)
	{
		super(relativeX, relativeY,
				(int)(Minecraft.getMinecraft().fontRendererObj.getStringWidth(text) * scaleX),
				(int)(Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT * scaleY),
				parentX, parentY, color);
		this.text = text;
	}
	
	public void setScaleX(float width)
	{
		this.scaleX = width / Minecraft.getMinecraft().fontRendererObj.getStringWidth(text);
	}
	public void setScaleY(float height)
	{
		this.scaleY = height / Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
	}
	
	public void setText(String text)
	{
		this.text = text;
		this.width = this.mc.fontRendererObj.getStringWidth(text);
		this.height = this.mc.fontRendererObj.FONT_HEIGHT + 3;
	}
	
	public String getText()
	{
		return this.text;
	}
	
	
	@Override
	public void render()
	{
		GL11.glPushMatrix();
		
		setScaleX(this.width);
		setScaleY(this.height);

		GL11.glScalef((float)this.scaleX, (float)this.scaleY, (float)1);
		
		this.mc.fontRendererObj.drawStringWithShadow(
				this.text,
				(int)this.x / this.scaleX,
				(int)this.y / this.scaleY,
				(int)this.color
				);
		
		
		GL11.glPopMatrix();
	}
	
	@Override
	public void renderDummy(ScreenPosition pos)
	{
		GL11.glPushMatrix();
		
		setScaleX(this.width);
		setScaleY(this.height);

		GL11.glScalef((float)this.scaleX, (float)this.scaleY, (float)1);
		
		this.mc.fontRendererObj.drawStringWithShadow(
				 this.text,
				(int)(pos.getRelativeX() + this.rx) / this.scaleX,
				(int)(pos.getRelativeY() + this.ry) / this.scaleY,
				(int)this.color
				);
		
		GL11.glPopMatrix();
	}
}




