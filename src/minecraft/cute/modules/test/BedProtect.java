package cute.modules.test;

import cute.eventapi.EventTarget;
import cute.events.RenderWorldLastEvent;
import cute.modules.Module;
import cute.modules.enums.Category;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;

public class BedProtect extends Module {

	// 		mc.gameSettings.keyBindDrop.setPressed(true);  -> Place
	
	public BedProtect()
	{
		super("Bed Protect", Category.BOT, "Protects your bed.");
	}
	
	int[] bedPosition = new int[]{ -1699 , 76 , -96 };
	int width = 5;
	int length = 6;
	
	
	private void keyWrap(String action, Boolean toggle ) 
	{	
		switch(action)
		{
		case "forward":
			mc.gameSettings.keyBindLeft.setPressed(toggle);
			break;
			
		case "backward":
			mc.gameSettings.keyBindRight.setPressed(toggle);
			break;
			
		case "left":
			mc.gameSettings.keyBindBack.setPressed(toggle);
			break;	
			
		case "right":
			mc.gameSettings.keyBindJump.setPressed(toggle);
			break;
		
		case "place":
			mc.gameSettings.keyBindDrop.setPressed(toggle);
			break;	
			
		case "jump":
			mc.gameSettings.keyBindSneak.setPressed(toggle);
			break;	
		}	
	}
	
	private Item STONE_item = Item.getItemById(1);
	
	private ItemStack STONE_itemstack = new ItemStack(STONE_item, 1);
	
	
	Item[] ItemMaterials = new Item[] 
	{
		 Item.getItemById(1), //Stone
		 Item.getItemById(5), //Wood
		 Item.getItemById(20), //Glass
		 Item.getItemById(35), //Wool
	};
	
	int[] ItemIndices = new int[] 
	{
		0, 1 , 2 , 3
	};
	
	
	int[][][] layout = {
		{
		{0,0,0,0,0},
		{0,1,2,1,0},
		{0,2,-1,2,0},
		{0,2,-1,2,0},
		{0,1,2,1,0},
		{0,0,0,0,0},
		},
		{
		{0,0,0,0,0},
		{0,1,2,1,0},
		{0,2,2,2,0},
		{0,2,2,2,0},
		{0,1,2,1,0},
		{0,0,0,0,0},
		},
	};
	
	private static int _cooldownTicks = 0; 
	
	private boolean buildSwitch = false;
	
	
	@EventTarget
	public void render(RenderWorldLastEvent e) 
	{
		boolean sortCheck = false;
		InventoryPlayer inv = mc.thePlayer.getInventoryOfPlayer();
		
		//Relative positions from bed [ X , Y , Z ]
		float[] relPos = new float[] 
		{
				(float) (bedPosition[0]-mc.thePlayer.posX),
				(float) (bedPosition[1]-mc.thePlayer.posY),
				(float) (bedPosition[2]-mc.thePlayer.posZ)
		};

//		System.out.println(buildSwitch);
			
		int midWidth = (int) Math.floor( (width) / 2 );
		int midLength = (int) Math.ceil( (length+1) / 2 );
		
		
		if(!buildSwitch) 
		{
			//Hits the sweet spot:
			if(relPos[0] >= midLength && relPos[0] < midLength+1 
			&& relPos[2] >= midWidth && relPos[2] < midWidth+1) 
					{
						if( sortCheck == false) {
								
							//Shift click all hotbar items to the top
							for(int j = 30; j < 40; j++) {
								mc.getMinecraft().playerController.windowClick(
										mc.getMinecraft().thePlayer.inventoryContainer.windowId,
										j, 0, 1, mc.getMinecraft().thePlayer);
							}
							
							
							for(int i = 0; i < ItemMaterials.length; i++)
							{
								int itemLocation = inv.getInventorySlotContainItem(ItemMaterials[i]);
								System.out.println("Item @ "+ itemLocation);
								//Shift clicks all the items
								mc.getMinecraft().playerController.windowClick(
										mc.getMinecraft().thePlayer.inventoryContainer.windowId,
										itemLocation, 0, 1, mc.getMinecraft().thePlayer);
							}
							
							sortCheck = true;
							
						}
						buildSwitch = true;
					}
		} else {
//			System.out.println("Sequencing");
			inv.setHeldItem(2);
			mc.thePlayer.rotationYaw = 0F;
			mc.thePlayer.rotationPitch = 0F;
			
		}
		
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
