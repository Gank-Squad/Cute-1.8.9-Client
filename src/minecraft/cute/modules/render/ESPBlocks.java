package cute.modules.render;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import cute.eventapi.EventManager;
import cute.eventapi.EventTarget;
import cute.events.ClientTickEvent;
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

	
	public static ListSelection blocks = new ListSelection<Block>("Blocks", new ArrayList<Block>(), ListType.BLOCK);
	public static Checkbox IntervalRefresh = new Checkbox("Auto Refresh", false);
	public static Slider lineWidth         = new Slider("Line Width", 0.1D, 2.5D, 5.0D, 1);
	public static Slider RefreshInterval   = new Slider("Refresh", 1.0D, 30D, 500D, 1);
	public static Slider SearchRadiusX     = new Slider("X Radius", 0.0D, 45D, 200D, 1);
	public static Slider SearchRadiusY     = new Slider("Y Radius", 0.0D, 45D, 200D, 1);

	// GL11 Buffer 
	public static int DisplayListId = 0; 
	
	// the internal counter for the ticks
	private static int _cooldownTicks = 0; 

	@Override
    public void setup() 
	{
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
		
		this.DisplayListId = GL11.glGenLists(GL11.GL_TRIANGLE_STRIP) + 3;
		
		this._cooldownTicks = 0;
		
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
		if(this._cooldownTicks < 1) 
		{
			this.compileDL();
			this._cooldownTicks = (int)this.RefreshInterval.getValue();
		}
		
		if(!this.IntervalRefresh.getValue()) 
		{
			return;
		}
		
		this._cooldownTicks--;
	}
	
	
//	@SideOnly(Side.CLIENT)
//	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	@EventTarget
    public void renderWorldLastEvent(RenderWorldLastEvent evt) 
    {
        if (nullCheck())
        {
            return;
        }
        
        double doubleX = this.mc.thePlayer.lastTickPosX
                + (this.mc.thePlayer.posX - this.mc.thePlayer.lastTickPosX)
                * evt.partialTicks;

        double doubleY = this.mc.thePlayer.lastTickPosY
                + (this.mc.thePlayer.posY - this.mc.thePlayer.lastTickPosY)
                * evt.partialTicks;

        double doubleZ = this.mc.thePlayer.lastTickPosZ
                + (this.mc.thePlayer.posZ - this.mc.thePlayer.lastTickPosZ)
                * evt.partialTicks;

        GL11.glLineWidth((float)this.lineWidth.getValue());
        GL11.glPushMatrix();
        GL11.glTranslated(-doubleX, -doubleY, -doubleZ);
        GL11.glCallList(DisplayListId);
        GL11.glPopMatrix();
    }
	
	private void compileDL() 
	{
		if(nullCheck())
			return;
		
		WorldClient world = this.mc.theWorld;

        EntityPlayerSP player = this.mc.thePlayer;
    
		// i have no idea what this is but i changed all the numbers to make more sense at least
		// https://javadoc.lwjgl.org/constant-values.html#org.lwjgl.opengl.GL11.GL_2_BYTES
		
		GL11.glNewList(DisplayListId, GL11.GL_COMPILE);
		
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(false);
        GL11.glBegin(GL11.GL_ONE);
        

        
        int x = (int)player.posX;
        int z = (int)player.posZ;
        int y = (int)player.posY;
        
        int radiusX = (int)this.SearchRadiusX.getValue();
        int radiusY = (int)this.SearchRadiusY.getValue();
        
        Block bId;
        
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

                    for (VirtualBlock block : VirtualBlock.vBlocks) 
                    {
                        if (!block.enabled || block.block != bId)
                        	continue;
                        
                        if((block.meta == -1) || (block.meta == bId.getMetaFromState(blockState))) 
                        {	
                        	RenderUtil.renderBlock(i, k, j, block);
                            break;
                        }
                    }
                }
            }
        }

        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEndList();
	}
}
