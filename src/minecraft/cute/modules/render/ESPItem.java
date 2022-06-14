package cute.modules.render;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import cute.eventapi.EventTarget;
import cute.events.RenderWorldLastEvent;
import cute.modules.Module;
import cute.modules.enums.Category;
import cute.settings.Checkbox;
import cute.settings.ColorPicker;
import cute.settings.Mode;
import cute.settings.Slider;
import cute.util.RenderUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityItem;

public class ESPItem extends Module 
{
	public ESPItem()
	{
		super("Item ESP", Category.RENDER, "Highlights items");
	}
	
	public static Mode mode = new Mode("Mode", "Hitbox");


    public static Checkbox items = new Checkbox("Items", true);
    public static ColorPicker itemsPicker = new ColorPicker(items, "Item Picker", new Color(199, 196, 19));
    public static Checkbox itemCount = new Checkbox("Item Counter", true);

    public static Slider itemCountRadius = new Slider("Item Count Radius", 0D, 1D, 20D, 1);
    
    public static Slider lineWidth = new Slider("Line Width", 0.1D, 2.5D, 5.0D, 1);
    
    
    @Override
    public void setup() 
	{
        addSetting(mode);
        
        addSetting(items);
        addSetting(itemCount);
        addSetting(itemCountRadius);
        
        addSetting(lineWidth);
    }
    
    public static class Grouped 
	{
		public EntityItem item;
		public int count;
		
		public Grouped() 
		{
			this.item = null;
			this.count = 0;
		}
		
		public Grouped(EntityItem i, int count)
		{
			this.item = i;
			this.count = count;
		}
	}
    
    @EventTarget
	public void onRenderWorld(RenderWorldLastEvent event) 
	{
		if(nullCheck())
			return;
		
		if(mode.getValue() != 0)
			return;
		
		GL11.glPushMatrix();
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
		GL11.glDepthMask(false);

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_ALPHA_TEST);

		GL11.glEnable(GL11.GL_LINE_SMOOTH);

		GL11.glLineWidth((float)lineWidth.getValue());

		
		ArrayList<Grouped> groups = new ArrayList();

        this.mc.theWorld.loadedEntityList.stream()
        	.filter(x -> x instanceof EntityItem)
        	.forEach(entity -> 
        {
        	if(items.getValue()) 
    		{
    			RenderUtil.setColor(itemsPicker.getColor());
    			RenderUtil.renderEntityHitbox(entity);
    		}
        		
			if (itemCount.getValue())
			{
				EntityItem eItem = (EntityItem)entity;
				int stackSize = eItem.getEntityItem().stackSize;
				
				for(Grouped i : groups)
				{
					if(i.item.getName().compareTo(eItem.getName()) == 0 && 
				       Math.abs(entity.posX - i.item.posX) < itemCountRadius.getValue() &&
				       Math.abs(entity.posY - i.item.posY) < itemCountRadius.getValue() &&
					   Math.abs(entity.posZ - i.item.posZ) < itemCountRadius.getValue()) 
					{
						i.count += stackSize;
						return;
					}
				}
				
				groups.add(new Grouped(eItem, stackSize));
				
				// // idk if this is more performant but it is really something
//				Optional a = g.stream()
//					.filter(x -> x.item.getName().compareTo(eItem.getName()) == 0)
//					.filter(i -> Math.abs(entity.posX - i.item.posX) < itemCountRadius.getValue() &&
//							     Math.abs(entity.posY - i.item.posY) < itemCountRadius.getValue() &&
//							     Math.abs(entity.posZ - i.item.posZ) < itemCountRadius.getValue())
//					.findFirst();
//				
//				if(a.isPresent())
//				{
//					((Grouped)a.get()).count += stackSize;
//					return;
//				}
				
				
			}
        });

        
		float f = 1.6F;
        float f1 = 0.016666668F * f;
        
        double doubleX = this.mc.thePlayer.lastTickPosX
                + (this.mc.thePlayer.posX - this.mc.thePlayer.lastTickPosX)
                * event.partialTicks;

        double doubleY = this.mc.thePlayer.lastTickPosY
                + (this.mc.thePlayer.posY - this.mc.thePlayer.lastTickPosY)
                * event.partialTicks;

        double doubleZ = this.mc.thePlayer.lastTickPosZ
                + (this.mc.thePlayer.posZ - this.mc.thePlayer.lastTickPosZ)
                * event.partialTicks;

        RenderManager renderManager = mc.getRenderManager();
		FontRenderer fontrenderer = renderManager.getFontRenderer();
		Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        
        for(Grouped group : groups)
        {
			String str = Integer.toString(group.count);
			
	        double x = group.item.posX - doubleX;
            double y = group.item.posY - doubleY;
            double z = group.item.posZ - doubleZ;
	        
            GlStateManager.pushMatrix();
            GlStateManager.translate(x, y, z);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(-f1, -f1, f1);
            
            int i = 0;
            int j = fontrenderer.getStringWidth(str) / 2;
	        
	        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
	        worldrenderer.pos((double)(-j - 1), (double)(-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
	        worldrenderer.pos((double)(-j - 1), (double)(8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
	        worldrenderer.pos((double)(j + 1), (double)(8 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
	        worldrenderer.pos((double)(j + 1), (double)(-1 + i), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
	        tessellator.draw();
	
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
	
	        // this is here so that it goes through walls
	        fontrenderer.drawString(str, -j, i, -1);
	        
	        GL11.glEnable(GL11.GL_DEPTH_TEST);
	        GL11.glDepthMask(true);
	        
	        // this is here so entities behind the name don't override it 
	        fontrenderer.drawString(str, -j, i, -1);
	        
	        GL11.glDepthMask(false);
	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glDisable(GL11.GL_DEPTH_TEST);
	        
            GlStateManager.popMatrix();
        }

        
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glDisable(GL11.GL_BLEND);
		
		GL11.glDepthMask(true);
		GL11.glPopMatrix();	
		
		// prevents hotbar / hand from being messed up by color changes 
		RenderUtil.resetColor();
    }
}
