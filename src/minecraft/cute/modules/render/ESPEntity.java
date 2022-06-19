package cute.modules.render;

import java.awt.Color;

import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.EXTPackedDepthStencil;
import org.lwjgl.opengl.GL11;

import cute.eventapi.EventTarget;
import cute.events.RenderLivingModelEvent;
import cute.events.RenderWorldLastEvent;
import cute.modules.Module;
import cute.modules.bot.AntiBot;
import cute.modules.client.Players;
import cute.modules.enums.Category;
import cute.settings.Checkbox;
import cute.settings.ColorPicker;
import cute.settings.Mode;
import cute.settings.Slider;
import cute.util.EntityUtil;
import cute.util.RenderUtil;
import cute.util.StringUtil;
import cute.util.types.EntityType;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;


public class ESPEntity<T extends Entity> extends Module
{
	public ESPEntity() 
	{
		super("Entity ESP", Category.RENDER, "Highlights entities");
	}
	
	public static Mode mode = new Mode("Mode", "Hitbox", "2D", "Outline", "Wire Frame");

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
        
    public static Slider invisAlpha = new Slider("Invis Opacity", 0D, 0.5D, 1.0D, 1);
    
    public static Slider lineWidth = new Slider("Line Width", 0.1D, 2.5D, 5.0D, 1);
    
    public static Checkbox forceRender = new Checkbox("Force Render", true);
    
    public static Checkbox nameColor = new Checkbox("Name Color Outline", true);
    
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
        addSetting(invisAlpha);
        addSetting(lineWidth);
        addSetting(forceRender);
        addSetting(nameColor);
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
    	if(mode.getValue() < 2)
    		return;
    	
    	EntityLivingBase entity = e.entityLivingBaseIn;
    	
    	if(entity instanceof EntityPlayerSP || AntiBot.isBot((EntityLivingBase)entity) || entity.isDead || !entity.isEntityAlive())
    		return;


    	switch(entity.getEntityType())
    	{
        	case PLAYER:
        		if(!players.getValue() || entity.getName() == this.mc.thePlayer.getName()) 
        			return;
        		int c = StringUtil.getNameColor(entity.getName());
        		if(nameColor.getValue() && c != 0)
        			RenderUtil.setColor(c);
        		else
        			RenderUtil.setColor(playerPicker.getColor());
        		break;
        		
        	case HOSTILE:
        		if(!mobs.getValue()) 
        			return;
        		RenderUtil.setColor(mobsPicker.getColor());
        		break;
        		
        	case NEUTRAL:
        		if(!neutral.getValue()) 
        			return;
        		RenderUtil.setColor(neutralPicker.getColor());
        		break;
        		
        	case PASSIVE:
        		if(!animals.getValue()) 
        			return;
        		RenderUtil.setColor(animalPicker.getColor());
        		break;
        	
        	
        	case VEHICLE:        		
        	case AMBIENT:
        	case PROJECTILE:
        	case ITEM:
        	case BOSS:
        	case OTHER:
        			break;
    	}
    	
    	
//    	if(entity instanceof EntityPlayer) 
//    	{
//    		if(!players.getValue() || entity.getName() == this.mc.thePlayer.getName()) 
//    			return;
//    		RenderUtil.setColor(playerPicker.getColor());
//    	}
//    	else
//    	if(EntityUtil.isHostileMob(entity))
//    	{
//    		if(!mobs.getValue()) 
//    			return;
//    		RenderUtil.setColor(mobsPicker.getColor());
//    	}
//    	else
//    	if(EntityUtil.isPassive(entity)) 
//    	{
//    		if(!animals.getValue()) 
//    			return;
//    		RenderUtil.setColor(animalPicker.getColor());
//    	}
//    	else
//    	if(EntityUtil.isNeutralMob(entity)) 
//    	{
//    		if(!neutral.getValue()) 
//    			return;
//    		RenderUtil.setColor(neutralPicker.getColor());
//    	}  
    	
    	GL11.glLineWidth((float)lineWidth.getValue());
    	
    	switch(mode.getValue())
    	{
	    	case 2:
	    		
	    		final int STENCIL_MASK = 15;
                
                // https://learnopengl.com/Advanced-OpenGL/Stencil-testing
                GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
                
                // tf does this do, i have no idea
                Framebuffer fbo = mc.getFramebuffer();
                
                // Check if FBO isn't null
                // Checks if screen has been resized or new FBO has been created
                if (fbo != null && fbo.depthBuffer > -1) 
                {
                	// Sets up the FBO with depth and stencil extensions (24/8 bit)
                	
                	// Deletes old render buffer extensions such as depth
                    // Args: Render Buffer ID
                    EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
                    // Generates a new render buffer ID for the depth and stencil extension
                    int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
                    // Binds new render buffer by ID
                    // Args: Target (GL_RENDERBUFFER_EXT), ID
                    EXTFramebufferObject.glBindRenderbufferEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencil_depth_buffer_ID);
                    // Adds the depth and stencil extension
                    // Args: Target (GL_RENDERBUFFER_EXT), Extension (GL_DEPTH_STENCIL_EXT),
                    // Width, Height
                    EXTFramebufferObject.glRenderbufferStorageEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, EXTPackedDepthStencil.GL_DEPTH_STENCIL_EXT, mc.displayWidth, mc.displayHeight);
                    // Adds the stencil attachment
                    // Args: Target (GL_FRAMEBUFFER_EXT), Attachment
                    // (GL_STENCIL_ATTACHMENT_EXT), Target (GL_RENDERBUFFER_EXT), ID
                    EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_STENCIL_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencil_depth_buffer_ID);
                    // Adds the depth attachment
                    // Args: Target (GL_FRAMEBUFFER_EXT), Attachment
                    // (GL_DEPTH_ATTACHMENT_EXT), Target (GL_RENDERBUFFER_EXT), ID
                    EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencil_depth_buffer_ID);
                    
                    // Reset the ID to prevent multiple FBO's
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
	    		
	    	case 3:
		    	
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
		
		if(mode.getValue() >= 2)
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

        for(Entity entity : this.mc.theWorld.loadedEntityList) 
		{
        	if(entity instanceof EntityPlayerSP || 
             	   (entity instanceof EntityLivingBase) && 
            	   AntiBot.isBot((EntityLivingBase)entity))
        		continue;

        	
        	switch(entity.getEntityType())
        	{
        		default:
        			continue;
        			
	        	case PLAYER:
	        		if(players.getValue() && !Players.playerNameBlacklist.contains(entity.getName().toLowerCase())) 
	        		{
	        			RenderUtil.setColor(playerPicker.getColor());
	        			
	        			if(mode.getValue() == 0)
	        				RenderUtil.renderEntityHitbox(entity);
	        			else 
	        				RenderUtil.draw2dEsp(entity, event.partialTicks, false);
	        		}
	        		continue;
	        		
	        	case HOSTILE:
	        		if(mobs.getValue()) 
	        		{
	        			RenderUtil.setColor(mobsPicker.getColor());
	        			if(mode.getValue() == 0)
	        				RenderUtil.renderEntityHitbox(entity, event.partialTicks);
	        			else 
	        				RenderUtil.draw2dEsp(entity, event.partialTicks, false);
	        		}
	        		continue;
	        		
	        	case NEUTRAL:
	        		
            		if(neutral.getValue()) 
            		{
            			RenderUtil.setColor(neutralPicker.getColor());
            			if(mode.getValue() == 0)
            				RenderUtil.renderEntityHitbox(entity, event.partialTicks);
            			else 
            				RenderUtil.draw2dEsp(entity, event.partialTicks, false);
            		}
	            	
	        		continue;
	        		
	        	case PASSIVE:
	        		
            		if(animals.getValue()) 
            		{
            			RenderUtil.setColor(animalPicker.getColor());
            			if(mode.getValue() == 0)
            				RenderUtil.renderEntityHitbox(entity, event.partialTicks);
            			else 
            				RenderUtil.draw2dEsp(entity, event.partialTicks, false);
            		}
	        		continue;
	        	
	        	
	        	case VEHICLE:

            		if(vehicles.getValue()) 
            		{
            			RenderUtil.setColor(vehiclesPicker.getColor());

            			if(mode.getValue() == 0)
            				RenderUtil.renderEntityHitbox(entity);
            			else 
            				RenderUtil.draw2dEsp(entity, event.partialTicks, false);
            		}
	        		continue;
        	}
        	
//        	if(entity instanceof EntityPlayer) 
//        	{
//        		if(players.getValue() && !Players.playerNameBlacklist.contains(entity.getName().toLowerCase())) 
//        		{
//        			RenderUtil.setColor(playerPicker.getColor());
//        			
//        			if(mode.getValue() == 0)
//        				RenderUtil.renderEntityHitbox(entity);
//        			else 
//        				RenderUtil.draw2dEsp(entity, event.partialTicks, false);
//        		}
//        		continue;
//        	}
        	
//        	if(EntityUtil.isHostileMob(entity))
//        	{
//        		if(mobs.getValue()) 
//        		{
//        			RenderUtil.setColor(mobsPicker.getColor());
//        			if(mode.getValue() == 0)
//        				RenderUtil.renderEntityHitbox(entity);
//        			else 
//        				RenderUtil.draw2dEsp(entity, event.partialTicks, false);
//        		}
//        		continue;
//        	}
        	
//        	if(EntityUtil.isPassive(entity)) 
//        	{
//        		if(animals.getValue()) 
//        		{
//        			RenderUtil.setColor(animalPicker.getColor());
//        			if(mode.getValue() == 0)
//        				RenderUtil.renderEntityHitbox(entity);
//        			else 
//        				RenderUtil.draw2dEsp(entity, event.partialTicks, false);
//        		}
//        		continue;
//        	}
        	
//        	if(EntityUtil.isNeutralMob(entity)) 
//        	{
//        		if(neutral.getValue()) 
//        		{
//        			RenderUtil.setColor(neutralPicker.getColor());
//        			if(mode.getValue() == 0)
//        				RenderUtil.renderEntityHitbox(entity);
//        			else 
//        				RenderUtil.draw2dEsp(entity, event.partialTicks, false);
//        		}
//        		continue;
//        	}        	
        	
//        	if(EntityUtil.isVehicle(entity)) 
//        	{
//        		if(vehicles.getValue()) 
//        		{
//        			RenderUtil.setColor(vehiclesPicker.getColor());
//
//        			if(mode.getValue() == 0)
//        				RenderUtil.renderEntityHitbox(entity);
//        			else 
//        				RenderUtil.draw2dEsp(entity, event.partialTicks, false);
//        		}
//        	}
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





