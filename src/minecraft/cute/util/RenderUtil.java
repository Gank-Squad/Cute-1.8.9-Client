package cute.util;

import static org.lwjgl.opengl.GL11.glColor4d;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import cute.util.types.VirtualBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;



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
	    
	    
	    public static void renderRectOutline(double x1, double y1, double x2, double y2)
	    {
	    	GL11.glBegin(2);
	        GL11.glVertex2d(x1, y1);
	        GL11.glVertex2d(x2, y1);
	        
	        GL11.glVertex2d(x2, y2);
	        GL11.glVertex2d(x1, y2);
	        GL11.glEnd();
	    }
	    
	    public static void renderRectOutline(float x1, float y1, float x2, float y2)
	    {
	        GL11.glBegin(2);
	        GL11.glVertex2d(x1, y1);
	        GL11.glVertex2d(x2, y1);
	        
	        GL11.glVertex2d(x2, y2);
	        GL11.glVertex2d(x1, y2);
	        GL11.glEnd();

	        // this does the same thing as above 
//	        GL11.glBegin(GL11.GL_LINES);
//	        GL11.glVertex2d(x1, y1);
//	        GL11.glVertex2d(x2, y1);
//	        
//	        GL11.glVertex2d(x1, y2);
//	        GL11.glVertex2d(x2, y2);
//	        
//	        GL11.glVertex2d(x1, y1);
//	        GL11.glVertex2d(x1, y2);
//
//	        GL11.glVertex2d(x2, y1);
//	        GL11.glVertex2d(x2, y2);
//	        GL11.glEnd();   
	    }
	    
	    public static void renderRectTarget(double x1, double y1, double x2, double y2)
	    {
	    	double qHeight = Math.abs(y2 - y1) / 4d;
	    	double qWidth  = Math.abs(x2 - x1) / 4d;
	    	
	    	GL11.glBegin(GL11.GL_LINES);
	    	
	    	// top right 
	    	//         ___
	    	//            |
	    	//            |
			GL11.glVertex2d(x1, y2);
			GL11.glVertex2d(x1, y2 - qHeight);
			
			GL11.glVertex2d(x1, y2);
			GL11.glVertex2d(-qWidth, y2);
			
	    	// bottom right 
	    	//
	    	//            |
	    	//         ___|
			GL11.glVertex2d(x1, y1);
			GL11.glVertex2d(x1, y1 + qHeight);
			
			GL11.glVertex2d(x1, y1);
			GL11.glVertex2d(-qWidth, y1);
			
			// top left 
	    	//  ___
	    	// |
	    	// |
			GL11.glVertex2d(x2, y2);
			GL11.glVertex2d(x2, y2 - qHeight);
			
			GL11.glVertex2d(x2, y2);
			GL11.glVertex2d(qWidth, y2);
			// bottom left 
			//
	    	// |
	    	// |___  
			GL11.glVertex2d(x2, y1);
			GL11.glVertex2d(x2, y1 + qHeight);
			
			GL11.glVertex2d(x2, y1);
			GL11.glVertex2d(qWidth, y1);

			GL11.glEnd();
	    }
	    
	    public static void renderRectTarget(float x1, float y1, float x2, float y2)
	    {
	    	float qHeight = Math.abs(y2 - y1) / 4f;
	    	float qWidth  = Math.abs(x2 - x1) / 4f;
	    	
	    	GL11.glBegin(GL11.GL_LINES);
	    	
	    	// top right 
	    	//         ___
	    	//            |
	    	//            |
			GL11.glVertex2d(x1, y2);
			GL11.glVertex2d(x1, y2 - qHeight);
			
			GL11.glVertex2d(x1, y2);
			GL11.glVertex2d(-qWidth, y2);
			
	    	// bottom right 
	    	//
	    	//            |
	    	//         ___|
			GL11.glVertex2d(x1, y1);
			GL11.glVertex2d(x1, qHeight);
			
			GL11.glVertex2d(x1, y1);
			GL11.glVertex2d(-qWidth, y1);
			
			// top left 
	    	//  ___
	    	// |
	    	// |
			GL11.glVertex2d(x2, y2);
			GL11.glVertex2d(x2, y2 - qHeight);
			
			GL11.glVertex2d(x2, y2);
			GL11.glVertex2d(qWidth, y2);
			// bottom left 
			//
	    	// |
	    	// |___  
			GL11.glVertex2d(x2, y1);
			GL11.glVertex2d(x2, qHeight);
			
			GL11.glVertex2d(x2, y1);
			GL11.glVertex2d(qWidth, y1);

			GL11.glEnd();
	    }
	    

	    public static void setColor(Color c) 
	    {
	        glColor4d(c.getRed() / 255f, c.getGreen() / 255f, c.getBlue() / 255f, c.getAlpha() / 255f);
	    }
	    
	    public static void setColor(int c)
	    {
	    	glColor4d(
		    			(c >> 24 & 0xFF) / 255f,
		    			(c >> 16 & 0xFF) / 255f,
		    			(c >> 8 & 0xFF) / 255f,
		    			(c & 0xFF) / 255f
	    			);
	    }
	    
	    public static void resetColor()
	    {
	    	glColor4d(1, 1, 1, 1);
	    }
	    
	    

	    public static void draw2dEsp(Entity e, float partialTicks, boolean usePlayerMid) 
	    {
	    	final double doubleX = e.posX - mc.getRenderManager().viewerPosX;// + ((e.posX - e.lastTickPosX) * partialTicks);
	    	final double doubleY = e.posY - mc.getRenderManager().viewerPosY;// + ((e.posY - e.lastTickPosY) * partialTicks);
	    	final double doubleZ = e.posZ - mc.getRenderManager().viewerPosZ;// + ((e.posZ - e.lastTickPosZ) * partialTicks);
	    	
	    	final double x = e.posX - mc.thePlayer.posX;
	    	final double y = e.posY - mc.thePlayer.posY - (usePlayerMid ? mc.thePlayer.height / 2 : 0);
	    	final double z = e.posZ - mc.thePlayer.posZ;

	    	final float yaw = (float) Math.toDegrees(Math.atan2(z, x)) - 90.0F;
	        final float pitch = (float) Math.toDegrees(Math.atan2(y, Math.sqrt(x * x + z * z)));
	        
	        final double pPercent = Math.abs(pitch) / 90d ;
	        final double h = (1d - pPercent) * e.height + pPercent * e.width;
	        
	        GlStateManager.pushMatrix();
	        
	        GlStateManager.translate(doubleX, doubleY, doubleZ);
	        
	        GlStateManager.rotate(-yaw, 0.0F, 1F, 0.0F);
	        
	        GlStateManager.translate(0, e.height / 2, 0);

	        GlStateManager.rotate(-pitch, 1F, 0.0F, 0.0F);

			RenderUtil.renderRectTarget(-e.width / 2f, -h / 2, e.width / 2f, h / 2);

			GlStateManager.popMatrix();
		}
	
	    public static void renderBoundingBox(AxisAlignedBB b)
	    {
	    	GL11.glBegin(2);
			
			GL11.glVertex3d(b.minX, b.minY, b.minZ);
			GL11.glVertex3d(b.maxX, b.minY, b.minZ);
			GL11.glVertex3d(b.maxX, b.minY, b.maxZ);
			GL11.glVertex3d(b.minX, b.minY, b.maxZ);
			
			GL11.glEnd();
			
			GL11.glBegin(1);
			
			GL11.glVertex3d(b.minX, b.minY, b.minZ);
			GL11.glVertex3d(b.minX, b.maxY, b.minZ);
			
			GL11.glVertex3d(b.minX, b.minY, b.maxZ);
			GL11.glVertex3d(b.minX, b.maxY, b.maxZ);
			
			GL11.glVertex3d(b.maxX, b.minY, b.minZ);
			GL11.glVertex3d(b.maxX, b.maxY, b.minZ);
			
			GL11.glVertex3d(b.maxX, b.minY, b.maxZ);
			GL11.glVertex3d(b.maxX, b.maxY, b.maxZ);
			
			GL11.glEnd();
			
			GL11.glBegin(2);
			GL11.glVertex3d(b.minX, b.maxY, b.minZ);
			GL11.glVertex3d(b.maxX, b.maxY, b.minZ);
			GL11.glVertex3d(b.maxX, b.maxY, b.maxZ);
			GL11.glVertex3d(b.minX, b.maxY, b.maxZ);
			
			GL11.glEnd();
	    }
	    
	    public static void renderBoundingBox(Vec3 c1, Vec3 c2)
	    {
	    	GL11.glBegin(2);
			
			GL11.glVertex3d(c1.xCoord, c1.yCoord, c1.zCoord);
			GL11.glVertex3d(c2.xCoord, c1.yCoord, c1.zCoord);
			GL11.glVertex3d(c2.xCoord, c1.yCoord, c2.zCoord);
			GL11.glVertex3d(c1.xCoord, c1.yCoord, c2.zCoord);
			
			GL11.glEnd();
			
			GL11.glBegin(1);
			
			GL11.glVertex3d(c1.xCoord, c1.yCoord, c1.zCoord);
			GL11.glVertex3d(c1.xCoord, c2.yCoord, c1.zCoord);
			
			GL11.glVertex3d(c1.xCoord, c1.yCoord, c2.zCoord);
			GL11.glVertex3d(c1.xCoord, c2.yCoord, c2.zCoord);
			
			GL11.glVertex3d(c2.xCoord, c1.yCoord, c1.zCoord);
			GL11.glVertex3d(c2.xCoord, c2.yCoord, c1.zCoord);
			
			GL11.glVertex3d(c2.xCoord, c1.yCoord, c2.zCoord);
			GL11.glVertex3d(c2.xCoord, c2.yCoord, c2.zCoord);
			
			GL11.glEnd();
			
			GL11.glBegin(2);
			GL11.glVertex3d(c1.xCoord, c2.yCoord, c1.zCoord);
			GL11.glVertex3d(c2.xCoord, c2.yCoord, c1.zCoord);
			GL11.glVertex3d(c2.xCoord, c2.yCoord, c2.zCoord);
			GL11.glVertex3d(c1.xCoord, c2.yCoord, c2.zCoord);
			
			GL11.glEnd();
	    }
	    
	    /* 
	     * render's the entities hitbox,
	     * this function expects GL11.glPushMatrix(); or something similar has alreayd been used
	     */
		public static void renderEntityHitbox(Entity e, float partialTicks) 
	    {
			final double doubleX = mc.getRenderManager().viewerPosX;
	        final double doubleY = mc.getRenderManager().viewerPosY;
	        final double doubleZ = mc.getRenderManager().viewerPosZ;
	        
			final double hw = e.width / 2;
			
//			final float u = e.motionX
	    	final double x1 = e.posX + hw - doubleX ;// ((e.posX - e.lastTickPosX) * partialTicks);
			final double x2 = e.posX - hw - doubleX ; //((e.posX - e.lastTickPosX) * partialTicks);
			
			final double y1 = e.posY            - doubleY ;// + ((e.posY - e.lastTickPosY) * partialTicks);
			final double y2 = e.posY + e.height - doubleY ; //+ ((e.posY - e.lastTickPosY) * partialTicks);
			
			final double z1 = e.posZ + hw - doubleZ ;//+ ((e.posZ - e.lastTickPosZ) * partialTicks);
			final double z2 = e.posZ - hw - doubleZ ;//+ ((e.posZ - e.lastTickPosZ) * partialTicks);
			
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
		
		/* 
	     * render's the entities hitbox,
	     * this function expects GL11.glPushMatrix(); or something similar has alreayd been used
	     */
	    public static void renderEntityHitbox(Entity e) 
	    {
	    	final double renderPosX = mc.getRenderManager().viewerPosX;
			final double renderPosY = mc.getRenderManager().viewerPosY;
			final double renderPosZ = mc.getRenderManager().viewerPosZ;
	        
			final double hw = e.width / 2;
			
	    	final double x1 = e.posX + hw - renderPosX;
			final double x2 = e.posX - hw - renderPosX;
			
			final double y1 = e.posY            - renderPosY;
			final double y2 = e.posY + e.height - renderPosY;
			
			final double z1 = e.posZ + hw - renderPosZ;
			final double z2 = e.posZ - hw - renderPosZ;
			
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
	    
	    /* 
	     * render's the entities hitbox,
	     * this function expects GL11.glPushMatrix(); or something similar has alreayd been used
	     */
	    public static void renderEntityHitbox(Entity e, double eOffX, double eOffY, double eOffZ)
	    {
	    	final double renderPosX = mc.getRenderManager().viewerPosX;
	    	final double renderPosY = mc.getRenderManager().viewerPosY;
	    	final double renderPosZ = mc.getRenderManager().viewerPosZ;
	        
	    	final double hw = e.width / 2;
			
	    	final double x1 = e.posX + hw - renderPosX + eOffX;
	    	final double x2 = e.posX - hw - renderPosX + eOffX;
			
	    	final double y1 = e.posY            - renderPosY + eOffY;
	    	final double y2 = e.posY + e.height - renderPosY + eOffY;
			
	    	final double z1 = e.posZ + hw - renderPosZ + eOffZ;
	    	final double z2 = e.posZ - hw - renderPosZ + eOffZ;
			
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
	    
	    /* 
	     * render's the entities hitbox,
	     * this function expects GL11.glPushMatrix(); or something similar has alreayd been used
	     */
	    public static void renderEntityHitboxAbs(Entity e, double x, double y, double z)
	    {
	    	final double renderPosX = mc.getRenderManager().viewerPosX;
	    	final double renderPosY = mc.getRenderManager().viewerPosY;
	    	final double renderPosZ = mc.getRenderManager().viewerPosZ;
	        
	    	final double hw = e.width / 2;
			
	    	final double x1 = x + hw - renderPosX;
	    	final double x2 = x - hw - renderPosX;
			
	    	final double y1 = y            - renderPosY;
	    	final double y2 = y + e.height - renderPosY;
			
	    	final double z1 = z + hw - renderPosZ;
	    	final double z2 = z - hw - renderPosZ;
			
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
	    	renderBlock(x, y, z, new Color(block.getRed(), block.getGreen(), block.getBlue(), block.getAlpha()));
	    }
	    
	    public static void renderBlock(int x, int y, int z, Color color) 
	    {
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
	    
	    
	    public static void renderBlock(float x1, float y1, float z1, float x2, float y2, float z2) 
	    {
	        GL11.glVertex3f(x1, y1, z1);
	        GL11.glVertex3f(x2, y1, z1);
	
	        GL11.glVertex3f(x2, y1, z1);
	        GL11.glVertex3f(x2, y1, z2);
	
	        GL11.glVertex3f(x1, y1, z1);
	        GL11.glVertex3f(x1, y1, z2);
	
	        GL11.glVertex3f(x1, y1, z2);
	        GL11.glVertex3f(x2, y1, z2);
	
	        GL11.glVertex3f(x1, y2, z1);
	        GL11.glVertex3f(x2, y2, z1);
	
	        GL11.glVertex3f(x2, y2, z1);
	        GL11.glVertex3f(x2, y2, z2);
	
	        GL11.glVertex3f(x1, y2, z1);
	        GL11.glVertex3f(x1, y2, z2);
	
	        GL11.glVertex3f(x1, y2, z2);
	        GL11.glVertex3f(x2, y2, z2);
	
	        GL11.glVertex3f(x1, y1, z1);
	        GL11.glVertex3f(x1, y2, z1);
	
	        GL11.glVertex3f(x1, y1, z2);
	        GL11.glVertex3f(x1, y2, z2);
	
	        GL11.glVertex3f(x2, y1, z1);
	        GL11.glVertex3f(x2, y2, z1);
	
	        GL11.glVertex3f(x2, y1, z2);
	        GL11.glVertex3f(x2, y2, z2);
	    }
	    
	    public static void drawBoundingBox(WorldRenderer vb, float x1, float y1, float z1, float x2, float y2, float z2)
	    {
	    	drawBoundingBox(vb, new AxisAlignedBB(x1, y1, z1, x2, y2, z2));
	    }
	    
	    public static void drawBoundingBox(WorldRenderer vb, AxisAlignedBB axisalignedbb) 
	    {
//	        Tessellator ts = Tessellator.getInstance();
//	        WorldRenderer vb = ts.getWorldRenderer();
//	        vb.begin(7, DefaultVertexFormats.POSITION_TEX);// Starts X.
	        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
	        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
	        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
	        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
	        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
	        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
	        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
	        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
//	        ts.draw();
//	        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
	        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
	        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
	        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
	        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
	        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
	        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
	        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
	        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
//	        ts.draw();// Ends X.
//	        vb.begin(7, DefaultVertexFormats.POSITION_TEX);// Starts Y.
	        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
	        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
	        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
	        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
	        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
	        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
	        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
	        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
//	        ts.draw();
//	        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
	        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
	        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
	        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
	        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
	        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
	        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
	        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
	        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
//	        ts.draw();// Ends Y.
//	        vb.begin(7, DefaultVertexFormats.POSITION_TEX);// Starts Z.
	        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
	        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
	        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
	        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
	        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
	        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
	        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
	        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
//	        ts.draw();
//	        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
	        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
	        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
	        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
	        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
	        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).endVertex();
	        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).endVertex();
	        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).endVertex();
	        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).endVertex();
//	        ts.draw();// Ends Z.
	    }
	    
	    public static void renderTracerFromPlayer(Entity entity, double radius, double alphaSensitivity, Color c) 
	    {
	    	Vec3 vec = mc.thePlayer.getPositionVector();
	
			double mx = vec.xCoord;
			double mz = vec.zCoord;
			double my = vec.yCoord + mc.thePlayer.getEyeHeight() - 0.35;
			
			if (mc.getRenderManager().options.thirdPersonViewSetting == 0) 
			{
				double drawBeforeCameraDist = 100;
				double pitch = ((mc.thePlayer.rotationPitch + 90) * Math.PI) / 180;
				double yaw = ((mc.thePlayer.rotationYaw + 90) * Math.PI) / 180;
				mx += Math.sin(pitch) * Math.cos(yaw) * drawBeforeCameraDist;
				mz += Math.sin(pitch) * Math.sin(yaw) * drawBeforeCameraDist;
				my += Math.cos(pitch) * drawBeforeCameraDist;
			}
			
			renderTracer(mx, my, mz, entity, radius, alphaSensitivity, c);
	    }
	    
	    public static void renderTracer(double fx, double fy, double fz, Entity entity, double radius, double alphaSensitivity, Color c)
	    {
	    	double distance = entity.getDistanceToEntity(mc.thePlayer);
	    	
	    	if(distance > radius)
	    		return;

	    	alphaSensitivity = alphaSensitivity == 0 ? -1 : alphaSensitivity;
	    	
			double alpha = (1d - (distance / radius * alphaSensitivity)) * 255d;

			if (alpha < 26d) 
			{
				alpha = 26d; // 26 seems to be the lowest visible alpha 
			}
			else if (alpha > 255d) 
			{
				alpha = 255d;
			}

			float f2 = (entity.height / 2f)  + (entity.isSneaking() ? 0.25F : 0.0F);
			
			GL11.glColor4d(c.getRed()/255d, c.getGreen()/255d, c.getBlue()/255d, alpha/255);
			GL11.glVertex3d(fx, fy, fz);
			GL11.glVertex3d(entity.lastTickPosX, entity.posY + f2, entity.lastTickPosZ);
	    }
}

