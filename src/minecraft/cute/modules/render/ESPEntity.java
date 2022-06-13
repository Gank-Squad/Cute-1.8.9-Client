package cute.modules.render;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

import cute.eventapi.EventManager;
import cute.eventapi.EventTarget;
import cute.events.RenderLivingModelEvent;
import cute.events.RenderNameTagEvent;
import cute.events.RenderWorldLastEvent;
import cute.modules.Module;
import cute.modules.client.Players;
import cute.modules.enums.Category;
import cute.settings.Checkbox;
import cute.settings.ColorPicker;
import cute.settings.Mode;
import cute.settings.Slider;
import cute.util.EntityUtil;
import cute.util.RenderUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;


public class ESPEntity<T extends Entity> extends Module
{
	public ESPEntity() 
	{
		super("Entity ESP", Category.RENDER, "Highlights entities");
	}
	
	public static Mode mode = new Mode("Mode", "Hitbox", "Outline", "Wire Frame");

    public static Checkbox players = new Checkbox("Players", true);
    public static ColorPicker playerPicker = new ColorPicker(players, "Player Picker", new Color(215, 46, 46));

    public static Checkbox animals = new Checkbox("Animals", true);
    public static ColorPicker animalPicker = new ColorPicker(animals, "Animal Picker", new Color(0, 200, 0));

    public static Checkbox mobs = new Checkbox("Mobs", true);
    public static ColorPicker mobsPicker = new ColorPicker(mobs, "Mob Picker", new Color(131, 19, 199));

    public static Checkbox neutral = new Checkbox("Neutral Creatures", true);
    public static ColorPicker neutralPicker = new ColorPicker(neutral, "Neutral Picker", new Color(255, 255, 255));
    
    public static Checkbox vehicles = new Checkbox("Vehicles", true);
    public static ColorPicker vehiclesPicker = new ColorPicker(vehicles, "Vehicle Picker", new Color(0, 255, 255));
    
    public static Checkbox items = new Checkbox("Items", true);
    public static ColorPicker itemsPicker = new ColorPicker(items, "Item Picker", new Color(199, 196, 19));
    public static Checkbox itemCount = new Checkbox("Item Counter", true);

    public static Slider itemCountRadius = new Slider("Item Count Radius", 0D, 1D, 20D, 1);
    
    public static Slider invisAlpha = new Slider("Invis Opacity", 0D, 0.5D, 1.0D, 1);
    
    public static Slider lineWidth = new Slider("Line Width", 0.1D, 2.5D, 5.0D, 1);
    
    public static Checkbox forceRender = new Checkbox("Force Render", true);
    
    private static boolean globalEnabled = false;
    
 	public static boolean isOn()
 	{
 		return globalEnabled;
 	}
 	
    
    @Override
    public void setup() 
	{
        addSetting(mode);
        addSetting(players);
        addSetting(animals);
        addSetting(mobs);
        addSetting(neutral);
        addSetting(vehicles);
        addSetting(items);
        addSetting(itemCount);
        addSetting(itemCountRadius);
        addSetting(invisAlpha);
        addSetting(lineWidth);
        addSetting(forceRender);
    }
    

	@Override
	public void onEnable()
	{
		super.onEnable();
		
		globalEnabled = true;
	}
	
	@Override 
	public void onDisable()
	{
		super.onDisable();
		
		globalEnabled = false;
	}
	
    
    @Override
    public boolean nullCheck() 
    {
    	return mc.thePlayer == null ||
    		   mc.theWorld == null ||
    		   mc.getRenderManager() == null || 
			   mc.getRenderManager().options == null;
    }
    

    @EventTarget
    public void entityModelRender(RenderLivingModelEvent e)
    {
    	if(mode.getValue() < 1)
    		return;
    	
    	Entity entity = e.entityLivingBaseIn;
    	
    	if(entity instanceof EntityPlayerSP || !(entity instanceof EntityLivingBase) || entity.isDead || !entity.isEntityAlive())
    		return;

    	if(entity instanceof EntityPlayer) 
    	{
    		if(!players.getValue() || entity.getName() == this.mc.thePlayer.getName()) 
    			return;
    		RenderUtil.setColor(playerPicker.getColor());
    	}
    	else
    	if(EntityUtil.isHostileMob(entity))
    	{
    		if(!mobs.getValue()) 
    			return;
    		RenderUtil.setColor(mobsPicker.getColor());
    	}
    	else
    	if(EntityUtil.isPassive(entity)) 
    	{
    		if(!animals.getValue()) 
    			return;
    		RenderUtil.setColor(animalPicker.getColor());
    	}
    	else
    	if(EntityUtil.isNeutralMob(entity)) 
    	{
    		if(!neutral.getValue()) 
    			return;
    		RenderUtil.setColor(neutralPicker.getColor());
    	}  
    	
    	GL11.glLineWidth((float)lineWidth.getValue());
    	
    	switch(mode.getValue())
    	{
	    	case 1:
	    		
	    		final int STENCIL_MASK = 15;
                
                // https://learnopengl.com/Advanced-OpenGL/Stencil-testing
                GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
                
                // tf does this do, i have no idea
                Framebuffer fbo = mc.getFramebuffer();
                
                if (fbo != null && fbo.depthBuffer > -1) 
                {
                	// disable the depthBuffer?????
                	EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);  
                	int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
  
                	EXTFramebufferObject.glBindRenderbufferEXT(GL30.GL_RENDERBUFFER, stencil_depth_buffer_ID);
                	EXTFramebufferObject.glRenderbufferStorageEXT(GL30.GL_RENDERBUFFER, GL30.GL_DEPTH_STENCIL, mc.displayWidth, mc.displayHeight);                        	    
                	EXTFramebufferObject.glFramebufferRenderbufferEXT(GL30.GL_FRAMEBUFFER, GL30.GL_STENCIL_ATTACHMENT, GL30.GL_RENDERBUFFER, stencil_depth_buffer_ID);
                	EXTFramebufferObject.glFramebufferRenderbufferEXT(GL30.GL_FRAMEBUFFER, EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, GL30.GL_RENDERBUFFER, stencil_depth_buffer_ID);
                	fbo.depthBuffer = -1;
                }
                
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_LIGHTING);
                
                GL11.glEnable(GL11.GL_LINE_SMOOTH);
                GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
                GL11.glEnable(GL11.GL_STENCIL_TEST);
                GL11.glEnable(GL11.GL_BLEND);
                
                // like 90% sure this does nothing
                GL11.glClearStencil(STENCIL_MASK);

                GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);

                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                
                // draw 1s in the stencil buffer where the outlines goes (this is will be a wire frame of the model)
                GL11.glStencilFunc(GL11.GL_NEVER         , GL11.GL_ONE    , STENCIL_MASK);
                GL11.glStencilOp  (GL11.GL_REPLACE       , GL11.GL_REPLACE, GL11.GL_REPLACE);
                GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
                e.modelBase.render(e.entityLivingBaseIn, e.p2, e.p3, e.p4, e.p5, e.p6, e.scaleFactor);
                
                // draw 0s in the stencil buffer over the whole model to cover the wire frame above
                GL11.glStencilFunc(GL11.GL_NEVER         , GL11.GL_ZERO   , STENCIL_MASK);
                GL11.glStencilOp  (GL11.GL_REPLACE       , GL11.GL_REPLACE, GL11.GL_REPLACE);
                GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
                e.modelBase.render(e.entityLivingBaseIn, e.p2, e.p3, e.p4, e.p5, e.p6, e.scaleFactor);
                
                // only draw where the stencil buffer is equal to 1, and draw an outline
                GL11.glStencilFunc(GL11.GL_EQUAL         , GL11.GL_ONE , STENCIL_MASK);
                GL11.glStencilOp  (GL11.GL_KEEP          , GL11.GL_KEEP, GL11.GL_KEEP);
                GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);

                GL11.glDepthMask(false);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                
                e.modelBase.render(e.entityLivingBaseIn, e.p2, e.p3, e.p4, e.p5, e.p6, e.scaleFactor);
                
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glDepthMask(true);
                
                GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
                
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glDisable(GL11.GL_STENCIL_TEST);
                GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
                GL11.glDisable(GL11.GL_LINE_SMOOTH);

                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                
                GL11.glPopAttrib();
                RenderUtil.resetColor();
	    		break;
	    		
	    	case 2:
		    	
	    		GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                
                GL11.glEnable(GL11.GL_LINE_SMOOTH);
                GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
                GL11.glEnable(GL11.GL_STENCIL_TEST);
                GL11.glEnable(GL11.GL_BLEND);
                
		        GL11.glClear(GL11.GL_FRONT_LEFT);
		
		        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT,  GL11.GL_NICEST);
		        
		        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		        
				e.modelBase.render(e.entityLivingBaseIn, e.p2, e.p3, e.p4, e.p5, e.p6, e.scaleFactor);
				
				GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
				
				GL11.glDisable(GL11.GL_BLEND);
                GL11.glDisable(GL11.GL_STENCIL_TEST);
                GL11.glDisable(GL11.GL_POLYGON_SMOOTH);
                GL11.glDisable(GL11.GL_LINE_SMOOTH);

                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
				
				RenderUtil.resetColor();
	    		return;
    	}
    }
    
    
    @EventTarget
	public void onRenderWorld(RenderWorldLastEvent event) 
	{
		if(nullCheck())
			return;
		
		if(ESPEntity.mode.getValue() != 0)
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
		GL11.glEnable(GL32.GL_DEPTH_CLAMP);

		GL11.glLineWidth((float)lineWidth.getValue());

		
		ArrayList<EntityItem> groupItem = new ArrayList<EntityItem>();
		ArrayList<Integer> groupCount = new ArrayList<Integer>();
		
        for(Entity entity : this.mc.theWorld.loadedEntityList) 
		{
        	if(entity instanceof EntityPlayerSP )//|| !(entity instanceof EntityLivingBase) || entity.isDead || !entity.isEntityAlive())
        		continue;
        	
        	if(entity instanceof EntityPlayer) 
        	{
        		if(players.getValue() && !Players.playerNameBlacklist.contains(entity.getName().toLowerCase())) 
        		{
        			RenderUtil.setColor(playerPicker.getColor());
        			RenderUtil.renderEntityHitbox(entity);
        		}
        		continue;
        	}
        	
        	if(entity instanceof EntityItem) 
        	{
        		
        		if(items.getValue()) 
        		{
        			RenderUtil.setColor(itemsPicker.getColor());
        			RenderUtil.renderEntityHitbox(entity);
        		}
        		
    			if (itemCount.getValue())
    			{
    				// loop through groupItem & group location
    				// if its within the specified radius of another item in the lists
    				// add it to the count
    				// then loop through everything in the list at the end and render it
    				boolean exit = false;
    				int i;
    				for (i = 0; i < groupItem.size(); i++)
    				{
//        					if (groupItem.get(i).getName() == entity.getName())
    					if (groupItem.get(i).getName().compareTo(entity.getName()) == 0)
    					{
    						// check if its within range
    						if (
    								Math.abs(entity.posX - groupItem.get(i).posX) < itemCountRadius.getValue() &&
    								Math.abs(entity.posY - groupItem.get(i).posY) < itemCountRadius.getValue() &&
    								Math.abs(entity.posZ - groupItem.get(i).posZ) < itemCountRadius.getValue()
//        								entity.posX < groupItem.get(i).posX + radius && entity.posX > groupItem.get(i).posX - radius &&
//        								entity.posY < groupItem.get(i).posY + radius && entity.posY > groupItem.get(i).posY - radius &&	
//        								entity.posZ < groupItem.get(i).posZ + radius && entity.posZ > groupItem.get(i).posZ - radius
								)
    						{
    							ItemStack stack = ((EntityItem)entity).getEntityItem();
    							groupCount.set(i, groupCount.get(i) + stack.stackSize);
    							exit = true;
        						break;
    						}
    						
    					}
    				}
    				
    				// this is kinda bad but couldn't be bothered to do it better
    				if (exit)
    				{
    					continue;
    				}
    				
    				// couldn't find another group, so make a new one centered around the item
    				
    				groupItem.add((EntityItem)entity);
    				groupCount.add(groupItem.get(i).getEntityItem().stackSize);
    			}
      
        		continue;
        	}
        	
        	if(EntityUtil.isHostileMob(entity))
        	{
        		if(mobs.getValue()) 
        		{
        			RenderUtil.setColor(mobsPicker.getColor());
        			RenderUtil.renderEntityHitbox(entity);
        		}
        		continue;
        	}
        	
        	if(EntityUtil.isPassive(entity)) 
        	{
        		if(animals.getValue()) 
        		{
        			RenderUtil.setColor(animalPicker.getColor());
        			RenderUtil.renderEntityHitbox(entity);
        		}
        		continue;
        	}
        	
        	if(EntityUtil.isNeutralMob(entity)) 
        	{
        		if(neutral.getValue()) 
        		{
        			RenderUtil.setColor(neutralPicker.getColor());
        			RenderUtil.renderEntityHitbox(entity);
        		}
        		continue;
        	}        	
        	
        	if(EntityUtil.isVehicle(entity)) 
        	{
        		if(vehicles.getValue()) 
        		{
        			RenderUtil.setColor(vehiclesPicker.getColor());
        			RenderUtil.renderEntityHitbox(entity);
        		}
        	}
		}

//        for (EntityItem e : groupItem)
        for (int i2 = 0; i2 < groupItem.size(); i2++)
        {
			ItemStack stack = groupItem.get(i2).getEntityItem();
			String str = Integer.toString(groupCount.get(i2));
			RenderManager renderManager = mc.getRenderManager();
			FontRenderer fontrenderer = renderManager.getFontRenderer();//;mc.fontRendererObj;
            float f = 1.6F;
            float f1 = 0.016666668F * f;
            
            
            double x,y,z;
            
            
            double doubleX = this.mc.thePlayer.lastTickPosX
	                + (this.mc.thePlayer.posX - this.mc.thePlayer.lastTickPosX)
	                * event.partialTicks;

	        double doubleY = this.mc.thePlayer.lastTickPosY
	                + (this.mc.thePlayer.posY - this.mc.thePlayer.lastTickPosY)
	                * event.partialTicks;

	        double doubleZ = this.mc.thePlayer.lastTickPosZ
	                + (this.mc.thePlayer.posZ - this.mc.thePlayer.lastTickPosZ)
	                * event.partialTicks;

	        x = groupItem.get(i2).posX - doubleX;
            y = groupItem.get(i2).posY - doubleY;
            z = groupItem.get(i2).posZ - doubleZ;
	        
            GlStateManager.pushMatrix();
            GlStateManager.translate(x, y, z);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(-f1, -f1, f1);
            
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            
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
        
        
		GL11.glDisable(GL32.GL_DEPTH_CLAMP);
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





