package cute.modules.gui.hud.display.component;

import org.lwjgl.opengl.GL11;

import cute.modules.gui.hud.ScreenPosition;
import cute.modules.gui.hud.display.DraggableComponent;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;

public class ItemComponent extends DraggableComponent
{
	protected ItemStack item;
	
	public ItemComponent(int relativeX, int relativeY, ItemStack item, int parentX, int parentY) 
	{
		super(relativeX, relativeY, 13, 13, parentX, parentY, -1);
		
		this.item = item;
	}
	public ItemComponent(int relativeX, int relativeY, int width, int height,  ItemStack item, int parentX, int parentY) 
	{
		super(relativeX, relativeY, width, height, parentX, parentY, -1);
		System.out.println(width);
		
		this.item = item;
	}
	
	
	public void renderItem(float x, float y)
	{
		
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
	public void render()
	{
		GL11.glPushMatrix();
		GlStateManager.disableLighting();
		RenderItem renderitem = this.mc.getRenderItem();
		
		GL11.glScalef((float)getScaleX(this.width), (float)getScaleY(this.height), (float)1);

//        renderitem.renderitemForNameTag(this.item,
//        		(int)((this.x - 2 * getScaleX(this.width)) / getScaleX(this.width)),
//        		(int)((this.y - 2 * getScaleY(this.height)) / getScaleY(this.height)), 10);
		
		renderitem.renderItemIntoGUI(this.item,
        		(int)((this.x) / getScaleX(this.width)),
        		(int)((this.y) / getScaleY(this.height)));
//        renderitem.renderItemOverlayIntoGUIForNameTags(this.mc.fontRendererObj, this.item, this.x, this.y, null);
        
		GlStateManager.enableLighting();
        GL11.glPopMatrix();
	}
	
	@Override
	public void renderDummy(ScreenPosition pos)
	{
		GL11.glPushMatrix();
		GlStateManager.disableDepth();
		
		RenderItem renderitem = this.mc.getRenderItem();
		
		GL11.glScalef((float)getScaleX(this.width), (float)getScaleY(this.height), (float)1);
		
		renderitem.renderitemForNameTag(this.item, 
        		(int)((pos.getRelativeX() + (this.rx)) / getScaleX(this.width)), 
        		(int)((pos.getRelativeY() + (this.ry)) / getScaleY(this.height)), 10);
        
		GlStateManager.enableDepth();
        GL11.glPopMatrix();
    }

}
