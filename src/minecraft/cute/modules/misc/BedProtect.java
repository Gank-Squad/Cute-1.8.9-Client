package cute.modules.misc;

import com.google.common.collect.ImmutableMap;

import cute.eventapi.EventTarget;
import cute.events.RenderWorldLastEvent;
import cute.modules.Module;
import cute.modules.enums.Category;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;

public class BedProtect extends Module 
{
	public BedProtect()
	{
		super("Bed Place Assist", Category.MISC, "Protects your bed.");
	}
	
//	public static Slider SearchRadi  = new Slider("X-Z Radius", 0.0D, 45D, 200D, 1);
	
	// GL11 Buffer 
	public static int DisplayListId = 0; 
	
	int zLen = 7;
	int xLen = 8;
	
	private static int _cooldownTicks = 0; 
	private static boolean bedFind = false;
	
	Item[] ItemMaterials = new Item[] 
	{
		 Item.getItemById(121), //Endstone
		 
		 Item.getItemById(5), //Wood
		 
		 Item.getItemById(20), //Glass
		 
		 Item.getItemById(35), //Wool
	};
	
	int[] ItemIndices = new int[] //Hotbar slots 
	{
		0, 1 , 2 , 3, 4
	};
	
		
	int [][][] layout = {
			{ //Base layer
				{3,3,1,1,1,3,3},
				{3,1,1,0,1,1,3},
				{3,1,0,2,0,1,3},
				{1,0,2,-1,2,0,1},
				{1,0,2,-1,2,0,1},
				{3,1,0,2,0,1,3},
				{3,1,1,0,1,1,3},
				{3,3,1,1,1,3,3}
			},
			{ //1
				{3,3,3,1,3,3,3},	
				{3,3,1,1,1,3,3},
				{3,1,1,0,1,1,3},
				{3,1,0,2,0,1,3},
				{3,1,0,2,0,1,3},
				{3,1,1,0,1,1,3},
				{3,3,1,1,1,3,3},
				{3,3,3,1,3,3,3},
			},
			{
				{3,3,3,3,3,3,3},
				{3,3,3,3,3,3,3},
				{3,3,1,1,1,3,3},
				{3,3,1,0,1,3,3},
				{3,3,1,0,1,3,3},
				{3,3,1,1,1,3,3},
				{3,3,3,3,3,3,3},
				{3,3,3,3,3,3,3},
			},
			{
				{4,4,4,4,4,4,4},
				{4,4,4,4,4,4,4},
				{4,4,4,1,4,4,4},
				{4,4,1,1,1,4,4},
				{4,4,1,1,1,4,4},
				{4,4,4,1,4,4,4},
				{4,4,4,4,4,4,4},
				{4,4,4,4,4,4,4}
			}
			
	};
	
	int bedVertiOffset = 4;
	int bedHorizOffset = 3;
	
	
	//Fixing Weird Rounding Errors 
	
	//Float -> Int Version
	
	private static int smartRoundInt(float value) 
	{
		float absolute = Math.abs(value);
		
		if(value > 0) 
		{
			return (int) absolute;
		} 
		else 
		{
			return (( (int) absolute )+1) * -1;
		}	
	}
	
	//Float -> Float Version
	
	private static float smartRoundFloat(float value)
	{
		if(value > 0) 
		{
			return value;
		} 
		
		return value + 1;	
	}	
	
	public int[] relative2index(int relX, int relZ)
	{
		int nX = ( relX * -1 ) + bedVertiOffset;
		int nZ = relZ + bedHorizOffset;
		
		return new int[] { nX, nZ };
	}
	
	
	
	
//	@Override
//	public void onUpdate() 
//	{
//		if(_cooldownTicks < 1) 
//		{
//			this.compileDL();
//			_cooldownTicks = (int)10;
//		}
//		
//		if(!ESPBlocks.IntervalRefresh.getValue()) 
//		{
//			return;
//		}
//		
//		_cooldownTicks--;
//	}
	
//	private void compileDL() {
//		
//		//ESP Render all beds, also get proximity positions for all beds
//		int xyRadi = 10;
//		VirtualBlock vb;
//		Block proxbId;
//		
//		WorldClient world = this.mc.theWorld;
//		
//		GL11.glNewList(DisplayListId, GL11.GL_COMPILE);
//
//        GL11.glDisable( GL11.GL_TEXTURE_2D );
//		GL11.glDisable( GL11.GL_DEPTH_TEST );
//		GL11.glDisable( GL11.GL_CULL_FACE );
//		GL11.glEnable( GL11.GL_BLEND );
//		GL11.glBlendFunc( GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA );
//		GL11.glDepthMask(false);
//		
//		
//        for (int i = (int)mc.thePlayer.posX - xyRadi; i <=  (int)mc.thePlayer.posX + xyRadi; ++i) 
//        {
//        	// z
//            for (int j = (int)mc.thePlayer.posZ - xyRadi; j <= (int)mc.thePlayer.posZ + xyRadi; ++j) 
//            {
//                // y
//                for (int k = Math.max(0, (int)mc.thePlayer.posY - xyRadi); k <= (int)mc.thePlayer.posY + xyRadi; ++k) 
//                {
//                    BlockPos blockPos = new BlockPos(	i , j , k  );
//					IBlockState blockState = world.getBlockState(blockPos);
//					proxbId = blockState.getBlock();
//                    
//                    if (proxbId == Blocks.air)
//                        continue;
//                    
//                    //If it's a bed, render the esp
//                    if(proxbId == Blocks.bed)
//                    {
//                    	System.out.println("Gets here! Compile");
//                    	GL11.glColor4ub((byte)0, (byte)255, (byte)255, (byte)255);
//            			RenderUtil.renderBlock(
//            					i + 1, k + 1, j + 1, 
//            					i , k , j);
//                    }
//                    
//                }
//                
//            }
//            
//        }
//        
//        GL11.glEnd();
//        GL11.glDepthMask(true);
//		GL11.glDisable( GL11.GL_BLEND );
//		GL11.glEnable( GL11.GL_TEXTURE_2D );
//		GL11.glEnable( GL11.GL_DEPTH_TEST );
//		GL11.glEnable( GL11.GL_CULL_FACE );
//
//        GL11.glEndList();
//	}
	
	int[][] bedPosition = new int[][]{};
	
	public int[][] bedInProximity(double[] playerCoords, int XYZradius) 
	{
		WorldClient world = this.mc.theWorld; 
		
		int[] bed_head  = {};
		int [] bed_foot = {};
		
		// Triple nest to find 	
		for (int x = (int)playerCoords[0] - XYZradius; x <=  (int)playerCoords[0] + XYZradius; ++x) 
		{
			for (int y = Math.max(0, (int)playerCoords[1] - XYZradius); y <= (int)playerCoords[1] + XYZradius; ++y) 
			{
				for (int z = (int)playerCoords[2] - XYZradius; z <= (int)playerCoords[2] + XYZradius; ++z) 
				{
	        	  	BlockPos blockPos = new BlockPos(	x , y , z  );
					IBlockState blockState = world.getBlockState(blockPos);
	    			ImmutableMap bStateProps = blockState.getProperties();
					
					Block proxbId = blockState.getBlock();
					
					if(proxbId != Blocks.bed) 
					{
						for(Object entry : bStateProps.entrySet()) 
						{
							String entryStr = entry.toString();
							
							//Take last 4 digits of entry stringified and compare it to head
							String compareStr = entryStr.substring(entryStr.length()-4,entryStr.length() );
					
							if(compareStr.equals("head"))
								bed_head = new int[]{x+1,y,z+1};
							
							if(compareStr.equals("foot")) 
								bed_foot = new int[]{x+1,y,z+1};
						}
					}
				}
			}
		}

		if(bed_foot != null && bed_head != null) 
		{
			int[][] schema = {bed_head , bed_foot };
			return schema;  
		} 
		
		return null;
	}
	
	// ############################################################################
	//Module Hooks:
	// ############################################################################
	

//	@Override
//	public void onEnable() 
//	{
//		
//		this.compileDL();
//		
//		double[] pC = {mc.thePlayer.posX,mc.thePlayer.posY,mc.thePlayer.posZ};
//		
//		bedPosition = bedInProximity( pC , 10 );
//		mc.thePlayer.sendChatMessage("§a BED Found! ");
//	}
	
	@Override
	public void onDisable()
	{
		super.onDisable();
		bedFind = false;
	}
	
	@EventTarget
	public void render(RenderWorldLastEvent e) 
	{
		if(!bedFind) 
		{
			double[] pC = {mc.thePlayer.posX,mc.thePlayer.posY,mc.thePlayer.posZ};
			
			bedPosition = bedInProximity( pC , 10 );
			mc.thePlayer.sendChatMessage("BED Found! ");
			bedFind = true;
		}
		
		InventoryPlayer inv = mc.thePlayer.getInventoryOfPlayer();
		
		//Relative positions from bed [ X , Y , Z ]
		float[] relPos = new float[] 
		{
			(float) (bedPosition[0][0]-mc.thePlayer.posX),
			(float) (bedPosition[0][1]-mc.thePlayer.posY),
			(float) (bedPosition[0][2]-mc.thePlayer.posZ)				
		};

		MovingObjectPosition mouseOver = mc.objectMouseOver;
		
		float[] playerViewPos = new float[]
		{
			(float)	smartRoundFloat(mouseOver.getBlockPos().getX()),
			(float)	mouseOver.getBlockPos().getY(),
			(float)	smartRoundFloat(mouseOver.getBlockPos().getZ())
		};
		
		float[] relativeViewPos = new float[]
		{
			(float) (bedPosition[0][0] - playerViewPos[0]),
			(float) (bedPosition[0][1] - playerViewPos[1]),
			(float) (bedPosition[0][2] - playerViewPos[2]),				
		};
		
		// Check if the player is looking at a bed, if so make them sneak automatically
		WorldClient world = this.mc.theWorld;
		
		if(mouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) 
		{
            BlockPos blockPos = new BlockPos(
            		(int) mouseOver.getBlockPos().getX(),
            		(int) mouseOver.getBlockPos().getY(),
            		(int) mouseOver.getBlockPos().getZ()
            );
            
			IBlockState blockState = world.getBlockState(blockPos);
			Block bId = blockState.getBlock();	
			
			mc.gameSettings.keyBindSneak.setPressed(bId == Blocks.bed);			
		} 

		// Hits the sweet spot:
		if(relPos[0] < 1 && relPos[2] < 1)
		{	
			//Shift click all hotbar items to the top
			for(int j = 36; j < 45; j++) 
			{
				mc.playerController.windowClick(
					mc.thePlayer.inventoryContainer.windowId,
					j, 
					0, 
					1, 
					mc.thePlayer);
			}
			
			for(int i = 0; i < ItemMaterials.length; i++)
			{
				int itemLocation = inv.getInventorySlotContainItem(ItemMaterials[i]);
				
				//Shift clicks all the items
				if(itemLocation != -1) 
				{
					mc.playerController.windowClick(
							mc.thePlayer.inventoryContainer.windowId,
							itemLocation, 
							0, 
							1, 
							mc.thePlayer);
				}
			}
		}
		
		//Check hotbar
		for(int j = 36; j < 45; j++) 
		{
			Slot hotbarEntry = mc.thePlayer.inventoryContainer.getSlot(j);
			
			int slotOffset = j-36;
			
			if(slotOffset < ItemMaterials.length && hotbarEntry.getHasStack() == false ) 
			{
				//Try and find the item again (maybe there is another stack in the player's inventory)
				int itemLocation = inv.getInventorySlotContainItem(ItemMaterials[slotOffset]);
				
				if(itemLocation != -1) 
				{
					mc.playerController.windowClick(
							mc.thePlayer.inventoryContainer.windowId,
							itemLocation, 
							0, 
							1, 
							mc.thePlayer);
				}
			}
		}	
			
//			inv.getStackInSlot(36)
		
		 //Initialization block reached, sequencing
				
		int[] relativeInt = new int[] 
		{
			(int) relativeViewPos[0],
			(int) relativeViewPos[1],
			(int) relativeViewPos[2]
		};

		// SideHit skewing:
		switch(mouseOver.sideHit) 
		{
			case WEST:
				relativeInt[0] += 1; 
				break;
			case EAST:
				relativeInt[0] -= 1; 
				break;
			case NORTH:
				relativeInt[2] += 1; 
				break;
			case SOUTH:
				relativeInt[2] -= 1; 
				break;
			case UP:
				relativeInt[1] -= 1;
				break;
			case DOWN:
				relativeInt[1] += 1;
				break;
			default:
		}
			
		//Get block 
		int[] relIndex = relative2index(relativeInt[0],relativeInt[2]);
		
		int height = ((relativeInt[1])*-1);

//			System.out.println(relIndex[0] + " - "+relIndex[1]);
			
		int block = layout[ height ][ relIndex[0] ][ relIndex[1]  ];
			
		inv.setHeldItem(block);
			
			
		//Jump and place

			
		
		//Finding sweet spot

		
//		if( relPos[2] < ((midWidth-2)*-1) ) 
//		{
//			keyWrap("backward",true);
//			keyWrap("place",true);
//		}
//		
//		if(relPos[2] > midWidth+1)
//		{
//			keyWrap("place",false); //STOP Place blocks
//			keyWrap("backward",false); //STOP Move back
//		}
		

		//System.out.println(mc.thePlayer.getHeldItem());
		//mc.gameSettings.keyBindLeft.setPressed(true);
		//mc.gameSettings.keyBindDrop.setPressed(true);  
//		mc.thePlayer.rotationYaw = 0F;
//		mc.thePlayer.rotationPitch = 45F;
//		mc.thePlayer.replaceItemInInventory(1, STONE_itemstack );
		//System.out.println("Target:");
		//System.out.println(mc.thePlayer.getCurrentEquippedItem() );
		//System.out.println(mc.getMinecraft().objectMouseOver.getBlockPos());
		
		//System.out.println("Get Item stack:");
		//System.out.println(inv.getCurrentItem());
//		System.out.println("X rel pos");
//		System.out.println(relPos[0]);
//		System.out.println(midWidth);
//		System.out.println("Z rel pos");
//		System.out.println(relPos[2]);
//		System.out.println(midLength);
	        
	}
	
}
