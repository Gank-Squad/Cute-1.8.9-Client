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
	
	int[] bedPosition = new int[]{ -1698 , 76 , -96 };
	int width = 5;
	int length = 5;
	
	
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
		 Item.getItemById(4)
	};
	
	int[] ItemIndices = new int[] 
	{
		0, 1 , 2 , 3
	};
	
	private static int _cooldownTicks = 0; 
	private boolean sortCheck = false;

	
	@EventTarget
	public void render(RenderWorldLastEvent e) 
	{
		InventoryPlayer inv = mc.thePlayer.getInventoryOfPlayer();
			
		
		//Relative positions from bed [ X , Y , Z ]
		int[] relPos = new int[] 
		{
				(int) (bedPosition[0]-mc.thePlayer.posX),
				(int) (bedPosition[1]-mc.thePlayer.posY),
				(int) (bedPosition[2]-mc.thePlayer.posZ)
		};

		if( sortCheck == false) {
			for(int i = 0; i < ItemMaterials.length; i++)
			{
				int itemLocation = inv.getInventorySlotContainItem(ItemMaterials[i]);
				
				//Basically sending packets to the server:
				mc.getMinecraft().playerController.windowClick(
						mc.getMinecraft().thePlayer.inventoryContainer.windowId,
						itemLocation, 0, 1, mc.getMinecraft().thePlayer);

//				ItemStack itemMeta =  inv.getInventorySlotContainItemStack(ItemMaterials[i]);			
//				mc.thePlayer.replaceItemInInventory(itemLocation, Item.getItemById(0));
			}
			
		}
		
			
		
		int midWidth = (width-1) / 2;
		
		
		if( relPos[2] < ((midWidth-2)*-1) ) 
		{
			keyWrap("backward",true);
			keyWrap("place",true);
		}
		
		if(relPos[2] > midWidth+1)
		{
			keyWrap("place",false); //STOP Place blocks
			keyWrap("backward",false); //STOP Move back
		}
		

		//System.out.println(mc.thePlayer.getHeldItem());
		//mc.gameSettings.keyBindLeft.setPressed(true);
		//mc.gameSettings.keyBindDrop.setPressed(true);  
		mc.thePlayer.rotationYaw = 0F;
		mc.thePlayer.rotationPitch = 45F;
//		mc.thePlayer.replaceItemInInventory(1, STONE_itemstack );
		//System.out.println("Target:");
		//System.out.println(mc.thePlayer.getCurrentEquippedItem() );
		//System.out.println(mc.getMinecraft().objectMouseOver.getBlockPos());
		
		//System.out.println("Get Item stack:");
		//System.out.println(inv.getCurrentItem());
		
	}
	
}
