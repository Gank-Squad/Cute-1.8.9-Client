package cute.modules.render;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import cute.eventapi.EventTarget;
import cute.events.BlockRenderEvent;
import cute.modules.Module;
import cute.modules.enums.Category;
import cute.settings.Checkbox;
import cute.settings.ListSelection;
import cute.settings.enums.ListType;
import cute.util.types.VirtualBlock;
import net.minecraft.block.Block;

/*

--- Changes vital for this to work ---

net.minecraft.client.renderer.BlockModelRender.java:
	
	public boolean renderModel -> in the return this.renderModel added !XRay.isOn() to the side check
   
   
net.minecraft.client.renderer.BlockRenderDispatcher.java:

	public boolean renderBlock -> added event hook for BlockRenderEvent (canceling event returns false)


net.minecraft.client.renderer.renderGlobal.java:

	public void setupTerrain -> changed enumfacing for loop at the bottom adding XRay.isOn() to the if statement
	
	
net.minecraft.client.renderer.worldRenderer.java:

	public void putColorMultiplier -> added if statement changing color to white if XRay.isOn()
	

net.minecraft.client.renderer.chunk.RenderChunk.java

	public void rebuildChunk -> added if statement setting aenumworldblocklayer[0] = EnumWorldBlockLayer.TRANSLUCENT if XRay.isOn()
*/

public class XRay extends Module 
{
	public static boolean globalEnabled = false;
	
	public XRay()
	{
		super("XRay", Category.RENDER, "");
	}
	
	// this is used so the minecraft files mentioned at the top can check if XRay is on 
	public static boolean isOn()
	{
		return XRay.globalEnabled;
	}
	
	public static Set<Block> blocks = new HashSet<Block>();

	public static ListSelection vblocks = new ListSelection<VirtualBlock>("Blocks", new ArrayList<VirtualBlock>(), ListType.BLOCK);
	public static Checkbox checkSides = new Checkbox("Check Sides", true);
	
	@Override
	public void setup()
	{
		this.addSetting(checkSides);
		this.addSetting(vblocks);
	}
	
	@Override
	public void onEnable()
	{
		super.onEnable();
		
		XRay.globalEnabled = true;

		for(Object o : vblocks.getEnabledItems())
		{
			VirtualBlock vb = (VirtualBlock)o;
			
			blocks.add(vb.block);
		}
		
		mc.renderGlobal.loadRenderers();
	}
	
	@Override 
	public void onDisable()
	{
		super.onDisable();
		
		XRay.globalEnabled = false;
		
		blocks.clear();
		
		mc.renderGlobal.loadRenderers();
	}
	
	@EventTarget
	public void onBlockRender(BlockRenderEvent e)
	{
		e.setCancelled(!blocks.contains(e.block));
	}
}










