package cute.ui.hud.display.components;

import org.lwjgl.opengl.GL11;

import cute.ui.hud.ScreenPosition;
import cute.ui.hud.display.DraggableComponent;
import cute.util.RenderUtil;
import net.minecraft.client.Minecraft;


public class TextComponent extends DraggableComponent
{
	protected String text;
	
	private float scaleX;
	private float scaleY;

//	public TextComponent(int relativeX, int relativeY, String text, int color)
//	{
//		super(relativeX, relativeY, 
//				Minecraft.getMinecraft().fontRendererObj.getStringWidth(text), 
//				Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT, 
//				color);
//		
////		this.scaleX = 1;
////		this.scaleY = 1;
//		this.text = text;
//	}
	public TextComponent(int relativeX, int relativeY, int width, int height, String text, int color)
	{
		super(relativeX, relativeY, width, height, color);
		this.text = text;
//		setScaleX(width);
//		setScaleY(height);
	}
	public TextComponent(int relativeX, int relativeY, float scaleX, float scaleY, String text, int color)
	{
		super(relativeX, relativeY,
				(int)(Minecraft.getMinecraft().fontRendererObj.getStringWidth(text) * scaleX),
				(int)(Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT * scaleY),
				color);
		this.text = text;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}
	
	public void setScaleX(float scaleX)
	{
		this.scaleX = scaleX;
	}
	public void setScaleY(float scaleY)
	{
		this.scaleY = scaleY;
	}
	
	public void setText(String text)
	{
		this.text = text;
		this.width = (int)(this.mc.fontRendererObj.getStringWidth(text));
		this.height = (int)((this.mc.fontRendererObj.FONT_HEIGHT));
	}
	
	public String getText()
	{
		return this.text;
	}
	
	
	@Override
	public void render(ScreenPosition pos, float scaleX, float scaleY)
	{
		GL11.glPushMatrix();
		GL11.glScalef((float)this.scaleX, (float)this.scaleY, (float)1);
		
		this.mc.fontRendererObj.drawStringWithShadow(
				 this.text,
				(int)((pos.getAbsoluteX() / this.scaleX) / scaleX ) + this.rx,
				(int)((pos.getAbsoluteY() / this.scaleY) / scaleY ) + this.ry,
				(int)this.color
				);
		
		GL11.glPopMatrix();
	}
	
	@Override
	public void renderDummy(ScreenPosition pos, float scaleX, float scaleY)
	{
		GL11.glPushMatrix();
		GL11.glScalef((float)this.scaleX, (float)this.scaleY, (float)1);
		
		this.mc.fontRendererObj.drawStringWithShadow(
				 this.text,
				(int)((pos.getAbsoluteX() / this.scaleX) / scaleX ) + this.rx,
				(int)((pos.getAbsoluteY() / this.scaleY) / scaleY ) + this.ry,
				(int)this.color
				);
		
		GL11.glPopMatrix();
	}
}




