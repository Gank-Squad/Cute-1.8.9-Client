package cute.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import static org.lwjgl.opengl.GL11.glColor4d;

import java.awt.Color;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;



public class RenderUtil 
{
	 private static final Minecraft mc = Minecraft.getMinecraft();
	 public static ICamera camera = new Frustum();

	 
	 
	 
	 	public static void beginRenderRect()
	 	{
	 		GL11.glDisable(GL11.GL_TEXTURE_2D);
	        
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glEnable(GL11.GL_LINE_SMOOTH);
	        
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        GL11.glPushMatrix();
	 	}
	 
	 	public static void endRenderRect()
	 	{
	 		GL11.glPopMatrix();
	        
	        GL11.glDisable(GL11.GL_LINE_SMOOTH);
	        GL11.glDisable(GL11.GL_BLEND);
	        
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
	 	}
	 	
	 	
	 	// renders a single rectangle
	 	public static void renderRectSingle(float x1, float y1, float x2, float y2)
	 	{
	 		beginRenderRect();
	 		renderRect(x1, y1, x2, y2);
	 		endRenderRect();
	 	}
	 	
	 	// renders a single rectangle
	 	public static void renderRectSingle(double x1, double y1, double x2, double y2) 
	    {
	 		beginRenderRect();
	 		renderRect(x1, y1, x2, y2);
	 		endRenderRect();
	 	}
	 	
		// renders a rectangle on an existing matrix, 
		// this should only be used after the use of 'beginRenderRect' or something similar
	    public static void renderRect(float x1, float y1, float x2, float y2) 
	    {
	        GL11.glBegin(GL11.GL_QUADS);
	        GL11.glVertex2d(x2, y1);
	        GL11.glVertex2d(x1, y1);
	        GL11.glVertex2d(x1, y2);
	        GL11.glVertex2d(x2, y2);
	        GL11.glEnd();   
	    }

	    // renders a rectangle on an existing matrix, 
 		// this should only be used after the use of 'beginRenderRect' or something similar
	    public static void renderRect(double x1, double y1, double x2, double y2) 
	    {
	        GL11.glBegin(GL11.GL_QUADS);
	        GL11.glVertex2d(x2, y1);
	        GL11.glVertex2d(x1, y1);
	        GL11.glVertex2d(x1, y2);
	        GL11.glVertex2d(x2, y2);
	        GL11.glEnd();   
	    }
	    
	    
	    
	    
	    
	    
	    // sets the glColor4d to this color 
	    public static void setColor(Color c) 
	    {
	        glColor4d(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f);
	    }
	
	    
	    

		public static void beginRenderHitbox(float lineWidth, Color c) 
		{
			beginRenderHitbox();
			GL11.glLineWidth(lineWidth);
			setColor(c);
		}
		
		public static void beginRenderHitbox(float lineWidth) 
		{
			beginRenderHitbox();
			GL11.glLineWidth(lineWidth);
		}
		
		public static void beginRenderHitbox() 
		{
			GL11.glPushMatrix();
	        
	        GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glDisable(GL11.GL_DEPTH_TEST);
	        
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glEnable(GL11.GL_LINE_SMOOTH);
	        
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        GL11.glDepthMask(false);        
		}
		
		public static void endRenderHitbox() 
		{
			GL11.glDisable(GL11.GL_LINE_SMOOTH);
	        GL11.glDisable(GL11.GL_BLEND);
	        
	        GL11.glEnable(GL11.GL_DEPTH_TEST);
	        GL11.glEnable(GL11.GL_TEXTURE_2D);
	        
	        GL11.glDepthMask(true);
	        GL11.glPopMatrix();	
		}
		
	    public static void renderEntityHitbox(Entity e) 
	    {
	    	double renderPosX = mc.getRenderManager().viewerPosX;
			double renderPosY = mc.getRenderManager().viewerPosY;
			double renderPosZ = mc.getRenderManager().viewerPosZ;
	        
			double hw = e.width / 2;
			
	    	double x1 = e.posX + hw - renderPosX;
			double x2 = e.posX - hw - renderPosX;
			
			double y1 = e.posY            - renderPosY;
			double y2 = e.posY + e.height - renderPosY;
			
			double z1 = e.posZ + hw - renderPosZ;
			double z2 = e.posZ - hw - renderPosZ;
			
			GL11.glBegin(2);
			
			GL11.glVertex3d(x1, y1, z1);
			GL11.glVertex3d(x2, y1, z1);
			GL11.glVertex3d(x2, y1, z2);
			GL11.glVertex3d(x1, y1, z2);
			
			GL11.glEnd();
			
			GL11.glBegin(1);
			
			GL11.glVertex3d(x1, y1, z1);
			GL11.glVertex3d(x1, y2, z1);
			
			GL11.glVertex3d(x1, y1, z2);
			GL11.glVertex3d(x1, y2, z2);
			
			GL11.glVertex3d(x2, y1, z1);
			GL11.glVertex3d(x2, y2, z1);
			
			GL11.glVertex3d(x2, y1, z2);
			GL11.glVertex3d(x2, y2, z2);
			
			GL11.glEnd();
			
			GL11.glBegin(2);
			GL11.glVertex3d(x1, y2, z1);
			GL11.glVertex3d(x2, y2, z1);
			GL11.glVertex3d(x2, y2, z2);
			GL11.glVertex3d(x1, y2, z2);
			
			GL11.glEnd();
	    }
	
	    public static void renderBlock(int x, int y, int z, VirtualBlock block) 
	    {
	    	renderBlock(x, y, z, new Color(block.r, block.g, block.b, block.a));
	    }
	    
	    public static void renderBlock(int x, int y, int z, Color color) 
	    {
	    	if(color.getAlpha() <= 0)
	    		return;
	    	
	    	GL11.glColor4ub(
	    			(byte) color.getRed(), 
	    			(byte) color.getGreen(), 
	    			(byte) color.getBlue(), 
	    			(byte) color.getAlpha());
	
	        GL11.glVertex3f(x, y, z);
	        GL11.glVertex3f(x + 1, y, z);
	
	        GL11.glVertex3f(x + 1, y, z);
	        GL11.glVertex3f(x + 1, y, z + 1);
	
	        GL11.glVertex3f(x, y, z);
	        GL11.glVertex3f(x, y, z + 1);
	
	        GL11.glVertex3f(x, y, z + 1);
	        GL11.glVertex3f(x + 1, y, z + 1);
	
	        GL11.glVertex3f(x, y + 1, z);
	        GL11.glVertex3f(x + 1, y + 1, z);
	
	        GL11.glVertex3f(x + 1, y + 1, z);
	        GL11.glVertex3f(x + 1, y + 1, z + 1);
	
	        GL11.glVertex3f(x, y + 1, z);
	        GL11.glVertex3f(x, y + 1, z + 1);
	
	        GL11.glVertex3f(x, y + 1, z + 1);
	        GL11.glVertex3f(x + 1, y + 1, z + 1);
	
	        GL11.glVertex3f(x, y, z);
	        GL11.glVertex3f(x, y + 1, z);
	
	        GL11.glVertex3f(x, y, z + 1);
	        GL11.glVertex3f(x, y + 1, z + 1);
	
	        GL11.glVertex3f(x + 1, y, z);
	        GL11.glVertex3f(x + 1, y + 1, z);
	
	        GL11.glVertex3f(x + 1, y, z + 1);
	        GL11.glVertex3f(x + 1, y + 1, z + 1);
	    }
	    
	    public static void renderTracerFromPlayer(Entity entity, double hDistanceMax, double vDistanceMax, Color c) 
	    {
	    	Vec3 vec = mc.thePlayer.getPositionVector();
	
			double mx = vec.xCoord;
			double mz = vec.zCoord;
			double my = vec.yCoord + mc.thePlayer.getEyeHeight() - 0.35;
			
			if (!mc.getRenderManager().options.thirdPersonView) 
			{
				double drawBeforeCameraDist = 100;
				double pitch = ((mc.thePlayer.rotationPitch + 90) * Math.PI) / 180;
				double yaw = ((mc.thePlayer.rotationYaw + 90) * Math.PI) / 180;
				mx += Math.sin(pitch) * Math.cos(yaw) * drawBeforeCameraDist;
				mz += Math.sin(pitch) * Math.sin(yaw) * drawBeforeCameraDist;
				my += Math.cos(pitch) * drawBeforeCameraDist;
			}
			
			renderTracer(mx, my, mz, entity, hDistanceMax, vDistanceMax, c);
	    }
	    
	    public static void renderTracer(double fx, double fy, double fz, Entity entity, double hDistanceMax, double vDistanceMax, Color c)
	    {
			double distance = entity.getDistanceToEntity(mc.thePlayer);
			
			if (distance > hDistanceMax) 
			{
				return;
			}
	
			if (Math.abs((mc.thePlayer.posY - entity.posY)) > vDistanceMax) 
			{
				return;
			}
	
			// calculate alpha based on distance - the further the more transparent
			float alpha = 1 - (float)(distance / (hDistanceMax / 2D));
			
			if (alpha < 0.01F) 
			{
				alpha = 0.01F;
			}
			else if (alpha > 1F) 
			{
				alpha = 1F;
			}
			
			float f2 = (entity.height / 2f)  + (entity.isSneaking() ? 0.25F : 0.0F);
			
			setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), (int)(alpha * 255)));
			GL11.glVertex3d(fx, fy, fz);
			GL11.glVertex3d(entity.lastTickPosX, entity.posY + f2, entity.lastTickPosZ);
	    }

}

