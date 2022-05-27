package cute.modules.gui;

import cute.eventapi.EventTarget;
import cute.events.SettingChangedEvent;
import cute.managers.HudManager;
import cute.modules.Module;
import cute.modules.enums.Category;
import cute.settings.Checkbox;
import cute.ui.hud.display.DraggableObj;
import cute.ui.hud.display.components.TextComponent;

public class Hud extends Module
{
	public Hud() 
	{
		super("Hud", Category.CLIENT, "a in game hud");
		
	}

	public static Checkbox draggable = new Checkbox("Draggable", false);

	public static Checkbox pos = new Checkbox("Position", false);
	
	public static DraggableObj positionHud = new DraggableObj();
	public static TextComponent positionHudText;
		
	@Override
	public void delayedSetup()
	{
		positionHudText = new TextComponent(0, 0, 2.5f, 2.5f, String.format("%.02f %.02f %.02f", 0f,0f,0f), -1);
		positionHud.addComponent(positionHudText);

		HudManager.INSTANCE.register(positionHud);
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
	}
	
	@EventTarget
	public void modeChangedEvent(SettingChangedEvent e)
	{
		if(e.settingID != draggable.getId())
			return;

		HudManager.INSTANCE.openConfigScreen();
	}
	
}
