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
	public void render(float scaleX, float scaleY)
	{
		if(this.item == null)
			return;
		
		GL11.glScalef((float)scaleX, (float)scaleY, (float)1);
		
		RenderItem renderitem = this.mc.getRenderItem();

		GL11.glPushMatrix();
		GL11.glScalef((float)getScaleX(this.width), (float)getScaleY(this.height), (float)1);
		
		// bless GuiContainer.java, this make the lighting work
		RenderHelper.enableGUIStandardItemLighting();

		renderitem.renderItemForHUD(this.item,
        		(int)((this.x) / getScaleX(this.width)),
        		(int)((this.y) / getScaleY(this.height)), 10);

		
		renderitem.renderItemOverlayIntoGUI(
				this.mc.fontRendererObj, 
				this.item, 
				(int)((this.x) / getScaleX(this.width)), 
				(int)((this.y) / getScaleY(this.height)), null);
		
        GL11.glPopMatrix();
        
        GL11.glScalef((float)(1 / scaleX), (float)(1 /scaleY), (float)1);
	}
	
	@Override
	public void renderDummy(ScreenPosition pos, float scaleX, float scaleY)
	{
		if(this.item == null)
			return;
		
		GL11.glScalef((float)scaleX, (float)scaleY, (float)1);
		
		RenderItem renderitem = this.mc.getRenderItem();
		
		GL11.glPushMatrix();
		GL11.glScalef((float)getScaleX(this.width), (float)getScaleY(this.height), (float)1);
		
		// bless GuiContainer.java, this make the lighting work
		RenderHelper.enableGUIStandardItemLighting();
		renderitem.renderItemForHUD(this.item, 
        		(int)((pos.getAbsoluteX() + (this.rx)) / getScaleX(this.width)), 
        		(int)((pos.getAbsoluteY() + (this.ry)) / getScaleY(this.height)), 10);
        
		renderitem.renderItemOverlayIntoGUI(
				this.mc.fontRendererObj, 
				this.item, 
				(int)((pos.getAbsoluteX() + (this.rx)) / getScaleX(this.width)), 
				(int)((pos.getAbsoluteY() + (this.ry)) / getScaleY(this.height)), null);
        GL11.glPopMatrix();
        
        GL11.glScalef((float)(1 / scaleX), (float)(1 /scaleY), (float)1);
    }

}
