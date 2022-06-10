package cute.ui.hud.display.components;

import org.lwjgl.opengl.GL11;

import cute.ui.hud.ScreenPosition;
import cute.ui.hud.display.DraggableComponent;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;

public class ItemComponent extends DraggableComponent
{
	protected ItemStack item;
	
	public ItemComponent(int relativeX, int relativeY, ItemStack item) 
	{
		super(relativeX, relativeY, 16, 16, -1);
		
		this.item = item;
	}
	public ItemComponent(int relativeX, int relativeY, int width, int height,  ItemStack item) 
	{
		super(relativeX, relativeY, width, height, -1);
		System.out.println(width);
		
		this.item = item;
	}
	
	public ItemStack getItem()
	{
		return this.item;
	}
	
	public void setItem(ItemStack item)
	{
		this.item = item;
	}
	
	public float getScaleX(int x)
	{
		return x / 16;	
	}
	public float getScaleY(int y)
	{
		return y / 16;
	}
	
	@Override
	public void render(ScreenPosition pos, float scaleX, float scaleY)
	{
		if(this.item == null)
			return;
		
		RenderItem renderitem = this.mc.getRenderItem();
		
		GL11.glPushMatrix();
		GL11.glScalef((float)getScaleX(this.width), (float)getScaleY(this.height), (float)1);
		
		// bless GuiContainer.java, this make the lighting work
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glEnable(GL11.GL_BLEND);
		
		renderitem.renderItemForHUD(this.item, 
        		(int)((pos.getAbsoluteX() / getScaleX(this.width)) / scaleX) + this.rx, 
        		(int)((pos.getAbsoluteY() / getScaleY(this.height)) / scaleY) + this.ry, 10);
        
		renderitem.renderItemOverlayIntoGUI(
				this.mc.fontRendererObj, 
				this.item, 
				(int)((pos.getAbsoluteX() / getScaleX(this.width)) / scaleX) + this.rx, 
				(int)((pos.getAbsoluteY() / getScaleY(this.height)) / scaleY) + this.ry, null);
		
		GL11.glDisable(GL11.GL_BLEND);
		RenderHelper.disableStandardItemLighting();
		GL11.glPopMatrix();
	}
	
	@Override
	public void renderDummy(ScreenPosition pos, float scaleX, float scaleY)
	{
		if(this.item == null)
			return;
		
		RenderItem renderitem = this.mc.getRenderItem();
		
		GL11.glPushMatrix();
		GL11.glScalef((float)getScaleX(this.width), (float)getScaleY(this.height), (float)1);
		
		// bless GuiContainer.java, this make the lighting work
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glEnable(GL11.GL_BLEND);
		
		renderitem.renderItemForHUD(this.item, 
        		(int)((pos.getAbsoluteX() / getScaleX(this.width)) / scaleX) + this.rx, 
        		(int)((pos.getAbsoluteY() / getScaleY(this.height)) / scaleY) + this.ry, 10);
        
		renderitem.renderItemOverlayIntoGUI(
				this.mc.fontRendererObj, 
				this.item, 
				(int)((pos.getAbsoluteX() / getScaleX(this.width)) / scaleX) + this.rx, 
				(int)((pos.getAbsoluteY() / getScaleY(this.height)) / scaleY) + this.ry, null);
		
		GL11.glDisable(GL11.GL_BLEND);
		RenderHelper.disableStandardItemLighting();
        GL11.glPopMatrix();
    }

}
