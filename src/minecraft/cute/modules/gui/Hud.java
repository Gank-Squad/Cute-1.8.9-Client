package cute.modules.gui;

import java.util.ArrayList;
import java.util.Map;

import cute.eventapi.EventTarget;
import cute.events.SettingChangedEvent;
import cute.managers.HudManager;
import cute.modules.Module;
import cute.modules.enums.Category;
import cute.modules.misc.CuteTeam;
import cute.modules.misc.DetectionBox;
import cute.modules.misc.Teams;
import cute.modules.render.ProjectileTracer;
import cute.settings.Checkbox;
import cute.ui.hud.display.DraggableComponent;
import cute.ui.hud.display.DraggableObj;
import cute.ui.hud.display.components.ItemComponent;
import cute.ui.hud.display.components.RectComponent;
import cute.ui.hud.display.components.TextComponent;
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
		
	public static DraggableObj armorStatus = new DraggableObj(54500,84000);
	public static ItemComponent helmet;
	public static ItemComponent chest;
	public static ItemComponent legs;
	public static ItemComponent boots;
	
	public static DraggableObj hitMarker = new DraggableObj(52000,48500);
	public static RectComponent hitSquare;
	
	public static DraggableObj arrowCount= new DraggableObj(64500,94500);
	public static ItemComponent arrowCountItem;
	
	public static Checkbox armorStatusCheck = new Checkbox("Armor", true);
	public static Checkbox positionCheck    = new Checkbox("Position", true);
	public static Checkbox arrowCheck       = new Checkbox("Hit Marker", true);
	
	public static Checkbox arrowCountCheck  = new Checkbox("Arrow Count", true);
	public static Checkbox alertCheck		= new Checkbox("Detection Alerts", true);
	
	public static DraggableObj alert = new DraggableObj();
	public static TextComponent alertText;
	
	public static DraggableObj leaderboard = new DraggableObj();
	public static TextComponent names;
	
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
		
		alertText = new TextComponent(0,0,1f,1f,"",0xFFFFFFFF);
		alert.addComponent(alertText);
		
		HudManager.INSTANCE.register(leaderboard);
		
		if(armorStatusCheck.getValue())
			HudManager.INSTANCE.register(armorStatus);
		
		if(positionCheck.getValue())
			HudManager.INSTANCE.register(positionHud);
		
		if(arrowCheck.getValue())
			HudManager.INSTANCE.register(hitMarker);
		
		if(arrowCountCheck.getValue())
			HudManager.INSTANCE.register(arrowCount);
		
		if(alertCheck.getValue())
			HudManager.INSTANCE.register(alert);
	}
	
	@Override
    public void setup() 
	{
		this.addSetting(draggable);
		this.addSetting(armorStatusCheck);
		this.addSetting(positionCheck);
		this.addSetting(arrowCheck);
		this.addSetting(arrowCountCheck);
		this.addSetting(alertCheck);
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
		
		if(alertCheck.getValue())
			HudManager.INSTANCE.register(alert);
	}
	
	@Override 
	public void onDisable()
	{
		super.onDisable();
				
		HudManager.INSTANCE.unregister(armorStatus);
		HudManager.INSTANCE.unregister(positionHud);
		HudManager.INSTANCE.unregister(hitMarker);
		HudManager.INSTANCE.unregister(arrowCount);
		HudManager.INSTANCE.unregister(alert);
	}
	
	class teamList
	{
		public String name;
		public ArrayList<String> members;
		public int color;
		teamList(String name, ArrayList<String> members, int color)
		{
			this.name = name;
			this.members = members;
			this.color = color;
		}
	}
	
	@Override
	public void onUpdate()
	{
		if(nullCheck())
			return;
		
		leaderboard.setComponents(new ArrayList<DraggableComponent>());
		// background ig ? needs to be first since no way to set z level
		leaderboard.addComponent(new RectComponent(0,0,50,0,0x8d8d8d8d));
		ArrayList<teamList> list = new ArrayList<teamList>();
		
		// go through every value in hashmap
		for (Map.Entry<String, CuteTeam> s : Teams.players.entrySet())
		{
			boolean add = true;
			for(teamList l : list)
			{
				if (s.getValue().teamName.equals(l.name))
				{
					l.members.add(s.getKey());
					add = false;
					break;
				}
			}
			if (add)
				list.add(new teamList(
						s.getValue().teamName,
						new ArrayList<String>(),
						s.getValue().getColor()
					));
		}
		// iterate through list and add each component
		int y = 0;
		for (teamList l : list)
		{
			leaderboard.addComponent(new TextComponent(0,y,1f,1f,l.name,l.color));
			y += mc.fontRendererObj.FONT_HEIGHT;
			for(String s : l.members)
			{
				leaderboard.addComponent(new TextComponent(0,y,1f,1f,s,l.color));
				y += mc.fontRendererObj.FONT_HEIGHT;
			}
		}
		// this 100% doesn't work but I don't really have a way of testing it
		// its also completely useless since its basically just a colored tab menu
		// this needs to display what armor they have, if they're alive, dead, disconnected, or out of render distance
		// may also need to switch off of a hashmap though because honestly its a big pain to work with for little to no advantage
		// since I'm not taking advantage of its perks at all
		ArrayList<DraggableComponent> h = leaderboard.getComponents();
		DraggableComponent o = h.get(0);
		o.setHeight(leaderboard.getHeight());
		h.set(0, o);
		
		positionHudText.setText(String.format("%.02f %.02f %.02f", mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ));
		
		alertText.setText(DetectionBox.label);
		if (DetectionBox.label.equals(""))
		{
			alertText.setWidth(21);
			alertText.setHeight(7);
		}
		
		
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
		if(e.settingID == armorStatusCheck.getID())
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
		
		if(e.settingID == positionCheck.getID())
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
		
		if(e.settingID == arrowCheck.getID())
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
		
		if(e.settingID == arrowCountCheck.getID())
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
		
		if(e.settingID == alertCheck.getID())
		{
			if(alertCheck.getValue())
			{
				HudManager.INSTANCE.register(alert);
			}
			else
			{
				HudManager.INSTANCE.unregister(alert);
			}
			return;
		}
		
		if(e.settingID != draggable.getID())
			return;

		HudManager.INSTANCE.openConfigScreen();
	}
	
}
