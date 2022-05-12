package cute.modules.render;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

import cute.eventapi.EventTarget;
import cute.events.RenderNameTagEvent;
import cute.modules.Module;
import cute.modules.enums.Category;
import cute.settings.Checkbox;
import cute.util.RenderUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.optifine.reflect.Reflector;

public class NameTags extends Module 
{
	public NameTags() 
	{
		super("Name Tags", Category.RENDER, "Shows names and hotbar through walls");
	}
	public static final Logger Logger = LogManager.getLogger();
	public static Checkbox names = new Checkbox("Names", true);

    public static Checkbox hotbar = new Checkbox("Hotbar", true);
    
//    public static Checkbox mobs = new Checkbox("Mobs", true);
    
    @Override
    public void setup()
    {
    	this.addSetting(NameTags.names);
    	this.addSetting(NameTags.hotbar);
    }
    
    @EventTarget
    public void onRenderWorld(RenderNameTagEvent event) 
    {
    	event.setCancelled(true);
    	
    	FontRenderer fontrenderer = event.fontRenderer;
    	WorldRenderer worldrenderer = event.worldRenderer;
    	Tessellator tessellator       = event.tessellator;
    	RenderItem renderitem = mc.getRenderItem();
    	
    	String str = event.name;
    	
    	int i = 0;
        int j = fontrenderer.getStringWidth(str) / 2;
        
        
        GlStateManager.disableTexture2D();
        
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos((double)(-j - 1), (double)(-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldrenderer.pos((double)(-j - 1), (double)(8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldrenderer.pos((double)(j + 1), (double)(8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        worldrenderer.pos((double)(j + 1), (double)(-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
        tessellator.draw();

        GlStateManager.enableTexture2D();

        // this is here so that it goes through walls good
        fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, -1);
        
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        // this is here so entities behind the name don't override it 
        fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, -1);
        
        
//        if(event.entity instanceof EntityPlayer)
//    	{
    		EntityPlayer player = mc.thePlayer;//(EntityPlayer)event.entity;
    		
    		int x = -fontrenderer.getStringWidth(str)/2;
    		
    		for (int k = 0; k < 5; ++k) 
    		{
                final ItemStack stack = player.getEquipmentInSlot(k);
                
                if (stack == null) 
                	continue;
                
                GlStateManager.disableDepth();        
                renderitem.renderitemForNameTag(stack, x, -20, 0);
                renderitem.renderItemOverlayIntoGUIForNameTags(fontrenderer, stack, x, -20, null);
                GlStateManager.enableDepth();

                renderitem.renderitemForNameTag(stack, x, -20, 0);
                GlStateManager.disableDepth();
                renderitem.renderItemOverlayIntoGUIForNameTags(fontrenderer, stack, x, -20, null);
                GlStateManager.enableDepth();
                x += 14;
            }	
    		
    		GlStateManager.enableDepth();
    		
    		// if you see enchantment glints on stuff when names are rendered, alpha is 
    		// probably turned off somewhere above, so make sure to enable it 
//    		GlStateManager.enableAlpha();
//    	}
    }
    
    
    
    
    
}














