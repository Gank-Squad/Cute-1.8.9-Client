package cute.modules.gui;

import cute.eventapi.EventTarget;
import cute.events.SettingChangedEvent;
import cute.managers.HudManager;
import cute.modules.Module;
import cute.modules.enums.Category;
import cute.modules.render.ProjectileTracer;
import cute.modules.render.XRay;
import cute.settings.Checkbox;
import cute.ui.hud.display.DraggableObj;
import cute.ui.hud.display.components.ItemComponent;
import cute.ui.hud.display.components.RectComponent;
import cute.ui.hud.display.components.TextComponent;
import cute.util.types.VirtualBlock;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;

public class Hud extends Module
{
	public Hud() 
	{
		super("Hud", Category.CLIENT, "an in game hud");
		
	}

	public static Checkbox draggable = new Checkbox("Draggable", false);

	public static Checkbox pos = new Checkbox("Position", false);
	
	public static DraggableObj positionHud = new DraggableObj();
	public static TextComponent positionHudText;
		
	public static DraggableObj armorStatus = new DraggableObj();
	public static ItemComponent helmet;
	public static ItemComponent chest;
	public static ItemComponent legs;
	public static ItemComponent boots;
	
	public static DraggableObj hitMarker = new DraggableObj(52000,48500);
	public static RectComponent hitSquare;
	
	public static DraggableObj arrowCount= new DraggableObj();
	public static ItemComponent arrowCountItem;
	
	public static Checkbox armorStatusCheck = new Checkbox("Armor", true);
	public static Checkbox positionCheck    = new Checkbox("Position", true);
	public static Checkbox arrowCheck       = new Checkbox("Hit Marker", true);
	
	public static Checkbox arrowCountCheck  = new Checkbox("Arrow Count", true);
	
	@Override
	public void delayedSetup()
	{
		positionHudText = new TextComponent(0, 0, 1f, 1f, "", -1);
		positionHud.addComponent(positionHudText);

		helmet = new ItemComponent(0, 0, 16, 16, null);
		chest  = new ItemComponent(16, 0, 16, 16, null);
		legs   = new ItemComponent(32, 0, 16, 16, null);
		boots  = new ItemComponent(48, 0, 16, 16, null);
		armorStatus.addComponent(helmet);
		armorStatus.addComponent(chest);
		armorStatus.addComponent(legs);
		armorStatus.addComponent(boots);
		
		hitSquare = new RectComponent(0,0,5,10,0x00000000);
		hitMarker.addComponent(hitSquare);
		
		arrowCountItem = new ItemComponent(0, 0, 16, 16, new ItemStack(Items.arrow));
		arrowCount.addComponent(arrowCountItem);
		
		if(armorStatusCheck.getValue())
			HudManager.INSTANCE.register(armorStatus);
		
		if(positionCheck.getValue())
			HudManager.INSTANCE.register(positionHud);
		
		if(arrowCheck.getValue())
			HudManager.INSTANCE.register(hitMarker);
		
		if(arrowCountCheck.getValue())
			HudManager.INSTANCE.register(arrowCount);
	}
	
	@Override
    public void setup() 
	{
		this.addSetting(draggable);
		this.addSetting(armorStatusCheck);
		this.addSetting(positionCheck);
		this.addSetting(arrowCheck);
		this.addSetting(arrowCountCheck);
    }


	@Override
	public void onEnable()
	{
		super.onEnable();
		
		if(armorStatusCheck.getValue())
			HudManager.INSTANCE.register(armorStatus);
		
		if(positionCheck.getValue())
			HudManager.INSTANCE.register(positionHud);
		
		if(arrowCheck.getValue())
			HudManager.INSTANCE.register(hitMarker);
		
		if(arrowCountCheck.getValue())
			HudManager.INSTANCE.register(arrowCount);
	}
	
	@Override 
	public void onDisable()
	{
		super.onDisable();
				
		HudManager.INSTANCE.unregister(armorStatus);
		HudManager.INSTANCE.unregister(positionHud);
		HudManager.INSTANCE.unregister(hitMarker);
		HudManager.INSTANCE.unregister(arrowCount);
	}
	
	
	@Override
	public void onUpdate()
	{
		if(nullCheck())
			return;
		
		positionHudText.setText(String.format("%.02f %.02f %.02f", mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ));
		
		helmet.setItem(this.mc.thePlayer.getEquipmentInSlot(4));
		chest.setItem(this.mc.thePlayer.getEquipmentInSlot(3));
		legs.setItem(this.mc.thePlayer.getEquipmentInSlot(2));
		boots.setItem(this.mc.thePlayer.getEquipmentInSlot(1));
		
		ItemStack m = arrowCountItem.getItem();
	
		m.stackSize = 0;
		
		for(ItemStack i : mc.thePlayer.inventory.mainInventory)
		{
			if(i != null && i.getItem() == Items.arrow)
			{
				m.stackSize += i.stackSize;
			}
		}
	
		
		Item item = this.mc.thePlayer.getHeldItem().getItem();

		// need this so the marker disappears when not holding a bow so screen isn't covered unnecessarily
		if (item instanceof ItemBow ||
				item instanceof ItemSnowball ||
				item instanceof ItemEgg ||
				item instanceof ItemPotion && ItemPotion.isSplash(this.mc.thePlayer.getHeldItem().getMetadata()) ||
				item instanceof ItemFishingRod
				)
		{
			if (ProjectileTracer.onTarget == 1)
			{
				hitSquare.setColor(0xFF0000CF);
			}
			else if (ProjectileTracer.onTarget == 2)
			{
				hitSquare.setColor(0x00FF00CF);
			}
			else
			{
				hitSquare.setColor(0x8D8D8D8D);
			}
		}
		else
		{
			hitSquare.setColor(0x00000000);
		}
		
	}
	
	@EventTarget
	public void modeChangedEvent(SettingChangedEvent e)
	{
		if(e.settingID == armorStatusCheck.getId())
		{
			if(armorStatusCheck.getValue())
			{
				HudManager.INSTANCE.register(armorStatus);
			}
			else 
			{
				HudManager.INSTANCE.unregister(armorStatus);
			}
			return;
		}
		
		if(e.settingID == positionCheck.getId())
		{
			if(positionCheck.getValue())
			{
				HudManager.INSTANCE.register(positionHud);
			}
			else 
			{
				HudManager.INSTANCE.unregister(positionHud);
			}
			return;
		}
		
		if(e.settingID == arrowCheck.getId())
		{
			if(arrowCheck.getValue())
			{
				HudManager.INSTANCE.register(hitMarker);
			}
			else 
			{
				HudManager.INSTANCE.unregister(hitMarker);
			}
			return;
		}
		
		if(e.settingID == arrowCountCheck.getId())
		{
			if(arrowCountCheck.getValue())
			{
				HudManager.INSTANCE.register(arrowCount);
			}
			else
			{
				HudManager.INSTANCE.unregister(arrowCount);
			}
			return;
		}
		
		if(e.settingID != draggable.getId())
			return;

		HudManager.INSTANCE.openConfigScreen();
	}
	
}
