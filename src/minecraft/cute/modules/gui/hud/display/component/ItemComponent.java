package cute.modules.gui.hud.display.component;

import cute.modules.gui.hud.ScreenPosition;
import cute.modules.gui.hud.display.DraggableComponent;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;

public class ItemComponent extends DraggableComponent
{
	protected ItemStack item;
	
	public ItemComponent(int relativeX, int relativeY, ItemStack item, int parentX, int parentY) 
	{
		super(relativeX, relativeY, 16, 16, parentX, parentY, -1);
		
		this.item = item;
	}
	
	@Override
	public void render()
	{
		RenderItem renderitem = this.mc.getRenderItem();

        renderitem.renderitemForNameTag(this.item, this.x, this.y, 10);
        renderitem.renderItemOverlayIntoGUIForNameTags(this.mc.fontRendererObj, this.item, this.x, this.y, null);
	}
	
	@Override
	public void renderDummy(ScreenPosition pos)
	{
		RenderItem renderitem = this.mc.getRenderItem();

		renderitem.renderitemForNameTag(this.item, 
        		(int)(pos.getRelativeX()), 
        		(int)(pos.getRelativeY()), 10);
        
        renderitem.renderItemOverlayIntoGUIForNameTags(
        		this.mc.fontRendererObj, this.item, 
        		(int)(pos.getRelativeX() - this.rx), 
        		(int)(pos.getRelativeY() - this.ry), null);		
	}

}
