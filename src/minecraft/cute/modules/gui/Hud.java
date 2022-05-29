package cute.modules.gui;

import cute.eventapi.EventTarget;
import cute.events.SettingChangedEvent;
import cute.managers.HudManager;
import cute.modules.Module;
import cute.modules.enums.Category;
import cute.settings.Checkbox;
import cute.ui.hud.display.DraggableObj;
import cute.ui.hud.display.components.ItemComponent;
import cute.ui.hud.display.components.TextComponent;
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
		
		HudManager.INSTANCE.register(positionHud);
		HudManager.INSTANCE.register(armorStatus);
	}
	
	@Override
    public void setup() 
	{
		this.addSetting(draggable);
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
    
	}
	
	@EventTarget
	public void modeChangedEvent(SettingChangedEvent e)
	{
		if(e.settingID != draggable.getId())
			return;

		HudManager.INSTANCE.openConfigScreen();
	}
	
}
