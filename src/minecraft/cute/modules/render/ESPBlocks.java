package cute.modules.render;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import cute.eventapi.EventTarget;
import cute.events.RenderWorldLastEvent;
import cute.modules.Module;
import cute.modules.enums.Category;
import cute.settings.Checkbox;
import cute.settings.ListSelection;
import cute.settings.Slider;
import cute.settings.enums.ListType;
import cute.util.RenderUtil;
import cute.util.types.VirtualBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

public class ESPBlocks extends Module 
{
	public ESPBlocks() 
	{
		super("Block ESP", Category.RENDER, "Shows blocks");
	}

	
	public static ListSelection blocks = new ListSelection<VirtualBlock>("Blocks", new ArrayList<VirtualBlock>(), ListType.BLOCK);
	public static Checkbox IntervalRefresh = new Checkbox("Auto Refresh", false);
	public static Slider lineWidth         = new Slider("Line Width", 0.1D, 2.5D, 5.0D, 1);
	public static Slider RefreshInterval   = new Slider("Refresh", 1.0D, 30D, 500D, 1);
	public static Slider SearchRadiusX     = new Slider("X Radius", 0.0D, 45D, 200D, 1);
	public static Slider SearchRadiusY     = new Slider("Y Radius", 0.0D, 45D, 200D, 1);

	// GL11 Buffer 
	public static int DisplayListId = 0; 
	
	// the internal counter for the ticks
	private static int _cooldownTicks = 0; 

	private static boolean flush = false;
	
	@Override
    public void setup() 
	{
		blocks.canToggleItems = true;
		addSetting(blocks);
        addSetting(IntervalRefresh);
        addSetting(RefreshInterval);
        addSetting(SearchRadiusX);
        addSetting(SearchRadiusY);
        addSetting(lineWidth);
    }
	
	
	@Override
	public boolean nullCheck() 
	{
		return mc.thePlayer == null ||
	    	   mc.theWorld == null ||
	    	   mc.getRenderManager() == null || 
			   mc.getRenderManager().options == null;
	}
	
	
	@Override
	public void onEnable() 
	{
		super.onEnable();
		
		ESPBlocks.DisplayListId = GL11.glGenLists(GL11.GL_TRIANGLE_STRIP) + 3;
		
		ESPBlocks._cooldownTicks = 0;
		
		this.compileDL();
	}

	
	@Override
	public void onDisable() 
	{
		GL11.glDeleteLists(DisplayListId, 1);
		
		super.onDisable();
	}
	

	@Override
	public void onUpdate() 
	{
		if(ESPBlocks._cooldownTicks < 1) 
		{
			this.compileDL();
			ESPBlocks._cooldownTicks = (int)ESPBlocks.RefreshInterval.getValue();
		}
		
		if(!ESPBlocks.IntervalRefresh.getValue()) 
		{
			return;
		}
		
		ESPBlocks._cooldownTicks--;
	}
	
	

	@EventTarget
    public void renderWorldLastEvent(RenderWorldLastEvent evt) 
    {
        if (nullCheck() || flush)
            return;
        
        double doubleX = mc.getRenderManager().viewerPosX;
        double doubleY =  mc.getRenderManager().viewerPosY;
        double doubleZ = mc.getRenderManager().viewerPosZ;
        
        GL11.glLineWidth((float)ESPBlocks.lineWidth.getValue());
        GL11.glPushMatrix();
        GL11.glTranslated(-doubleX, -doubleY, -doubleZ);
        GL11.glCallList(DisplayListId);
        
        GL11.glPopMatrix();
        
        // prevents hotbar / hand from being messed up by color changes 
        RenderUtil.resetColor();
    }
	
	
	
	private void compileDL() 
	{
		if(nullCheck())
			return;
		
		if(ESPBlocks.blocks.getSize() == 0) 
		{
			if(flush)
				return;
			
			flush = true;
		}
		else 
		{
			flush = false;
		}	
		
		WorldClient world = this.mc.theWorld;

        EntityPlayerSP player = this.mc.thePlayer;
    
		GL11.glNewList(DisplayListId, GL11.GL_COMPILE);

        GL11.glDisable( GL11.GL_TEXTURE_2D );
		GL11.glDisable( GL11.GL_DEPTH_TEST );
		GL11.glDisable( GL11.GL_CULL_FACE );
		GL11.glEnable( GL11.GL_BLEND );
		GL11.glBlendFunc( GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA );
		GL11.glDepthMask(false);
		
		// uncomment this and the stuff in the for loop below for solid blocks
//      Tessellator ts = Tessellator.getInstance();
//      WorldRenderer wr = ts.getWorldRenderer();
		
		// will need to remove this and it's end if you want solid blocks
		GL11.glBegin(GL11.GL_LINES);
        
        int x = (int)player.posX;
        int z = (int)player.posZ;
        int y = (int)player.posY;
        
        int radiusX = (int)ESPBlocks.SearchRadiusX.getValue();
        int radiusY = (int)ESPBlocks.SearchRadiusY.getValue();
        
        Block bId;
        VirtualBlock vb;
        
        // x 
        for (int i = x - radiusX; i <= x + radiusX; ++i) 
        {
        	// z
            for (int j = z - radiusX; j <= z + radiusX; ++j) 
            {
                // y
                for (int k = Math.max(0, y - radiusY); k <= y + radiusY; ++k) 
                {
                    BlockPos blockPos = new BlockPos(i, k, j);
					IBlockState blockState = world.getBlockState(blockPos);
					bId = blockState.getBlock();
					
                    if (bId == Blocks.air)
                        continue;


                	for (Object _block : ESPBlocks.blocks.getEnabledItems())
                    {
                		vb = (VirtualBlock)_block;
                		
                        if (!vb.enabled || vb.block != bId)
                        	continue;
                        
                        int meta = bId.getMetaFromState(blockState);
                        
                        if((vb.meta == -1) || (vb.meta == meta)) 
                        {	
                        	// if you want solid blocks uncomment the Tessellator and world renderer above, and the begin/end functions here
//                        	GL11.glBegin(GL11.GL_LINES);


                        	GL11.glColor4ub((byte)vb.getRed(), (byte)vb.getGreen(), (byte)vb.getBlue(), (byte)vb.getAlpha());
                	
                        	switch(vb.blockID)
                        	{
                        		default:

                        			RenderUtil.renderBlock(
                        					i + vb.x1, k + vb.y1, j + vb.z1, 
                        					i + vb.x2, k + vb.y2, j + vb.z2);
                        		break;
                        		
                        		
                        		case 44:  // basically all stone slabs 
                        		case 182: // the rest of the stone slabs
                        		case 126: // wooden slabs 
                        			
                        			if(blockState.getValue(BlockSlab.HALF) == BlockSlab.EnumBlockHalf.TOP)
                        			{
                        				RenderUtil.renderBlock(
                            					i        , k + vb.y2, j, 
                            					i + vb.x2, k + 1.0f , j + vb.z2);
                        			}
                        			else 
                        			{
                        				RenderUtil.renderBlock(
                            					i        , k        , j, 
                            					i + vb.x2, k + vb.y2, j + vb.z2);
                        			}

                        			break;
                        	}
                        	
//                        	
//                        	wr.begin(7, DefaultVertexFormats.POSITION_TEX);
//                        	
//                        	RenderUtil.setColor(new Color(0,255,255,255));
//                        	RenderUtil.drawBoundingBox(wr, i, k, j, i+1, k+1, j+1);
//                        	
//                        	ts.draw();
                        	
                        	
                            break;
                        }
                    }
                }
            }
        }
        
        GL11.glEnd();
        GL11.glDepthMask(true);
		GL11.glDisable( GL11.GL_BLEND );
		GL11.glEnable( GL11.GL_TEXTURE_2D );
		GL11.glEnable( GL11.GL_DEPTH_TEST );
		GL11.glEnable( GL11.GL_CULL_FACE );

        GL11.glEndList();
	}
}
