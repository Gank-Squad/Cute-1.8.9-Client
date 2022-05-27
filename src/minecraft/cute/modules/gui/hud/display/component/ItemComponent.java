package cute.modules.gui.hud.display.component;

import org.lwjgl.opengl.GL11;

import cute.modules.gui.hud.ScreenPosition;
import cute.modules.gui.hud.display.DraggableComponent;
import cute.util.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.src.Config;
import net.optifine.shaders.Shaders;

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
	
	
//	public void renderItem(float x, float y)
//	{
//		
//	}
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
		RenderItem renderitem = this.mc.getRenderItem();

		GL11.glPushMatrix();
		GL11.glScalef((float)getScaleX(this.width), (float)getScaleY(this.height), (float)1);
		
		// bless GuiContainer.java, this make the lighting work
		RenderHelper.enableGUIStandardItemLighting();

		renderitem.renderItemForHUD(this.item,
        		(int)((this.x) / getScaleX(this.width)),
        		(int)((this.y) / getScaleY(this.height)), 10);

        GL11.glPopMatrix();
	}
	
	@Override
	public void renderDummy(ScreenPosition pos)
	{
		RenderItem renderitem = this.mc.getRenderItem();
		
		GL11.glPushMatrix();
		GL11.glScalef((float)getScaleX(this.width), (float)getScaleY(this.height), (float)1);
		
		// bless GuiContainer.java, this make the lighting work
		RenderHelper.enableGUIStandardItemLighting();
		renderitem.renderItemForHUD(this.item, 
        		(int)((pos.getAbsoluteX() + (this.rx)) / getScaleX(this.width)), 
        		(int)((pos.getAbsoluteY() + (this.ry)) / getScaleY(this.height)), 10);
        
		
        GL11.glPopMatrix();
    }

}
