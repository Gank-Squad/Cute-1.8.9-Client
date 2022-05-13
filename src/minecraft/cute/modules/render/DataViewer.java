package cute.modules.render;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import cute.eventapi.EventTarget;
import cute.events.RenderWorldLastEvent;
import cute.modules.Module;
import cute.modules.enums.Category;
import cute.settings.Checkbox;
import cute.settings.ColorPicker;
import cute.settings.Slider;
import cute.util.RenderUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;

public class DataViewer extends Module 
{
	public DataViewer()
	{
		super("Tile Entity ESP", Category.RENDER, "Highlights tile entities");
	}
	
	public static Checkbox color = new Checkbox("Color", true);
    public static ColorPicker colorPicker = new ColorPicker(color, "Color", new Color(215, 46, 46));
    public static Slider lineWidth         = new Slider("Line Width", 0.1D, 2.5D, 5.0D, 1);

	@Override
    public void setup() 
	{
		this.addSetting(color);
		this.addSetting(lineWidth);
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
    public void renderWorldLastEvent(RenderWorldLastEvent evt) 
    {
        if (nullCheck())
            return;
        
        double doubleX = this.mc.thePlayer.lastTickPosX
                + (this.mc.thePlayer.posX - this.mc.thePlayer.lastTickPosX)
                * evt.partialTicks;

        double doubleY = this.mc.thePlayer.lastTickPosY
                + (this.mc.thePlayer.posY - this.mc.thePlayer.lastTickPosY)
                * evt.partialTicks;

        double doubleZ = this.mc.thePlayer.lastTickPosZ
                + (this.mc.thePlayer.posZ - this.mc.thePlayer.lastTickPosZ)
                * evt.partialTicks;
        
        GL11.glPushMatrix();
        GL11.glTranslated(-doubleX, -doubleY, -doubleZ);

        GL11.glDisable( GL11.GL_TEXTURE_2D );
		GL11.glDisable( GL11.GL_DEPTH_TEST );
		GL11.glDisable( GL11.GL_CULL_FACE );
		GL11.glEnable( GL11.GL_BLEND );
		GL11.glBlendFunc( GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA );
		GL11.glDepthMask(false);
		
		RenderUtil.setColor(colorPicker.getColor());
		GL11.glLineWidth((float)DataViewer.lineWidth.getValue());
		
		GL11.glBegin(GL11.GL_LINES);
        
		for (TileEntity tileentity : mc.theWorld.loadedTileEntityList) 
		{
			BlockPos bpos = tileentity.getPos();

			RenderUtil.renderBlock(
					bpos.getX()     , bpos.getY()     , bpos.getZ(),
					bpos.getX() + 1f, bpos.getY() + 1f, bpos.getZ() + 1f);
			
		}
        
        GL11.glEnd();
        GL11.glDepthMask(true);
		GL11.glDisable( GL11.GL_BLEND );
		GL11.glEnable( GL11.GL_TEXTURE_2D );
		GL11.glEnable( GL11.GL_DEPTH_TEST );
		GL11.glEnable( GL11.GL_CULL_FACE );
        GL11.glPopMatrix();
 
        RenderUtil.resetColor();
    }
}








