package cute.modules.gui;

import cute.eventapi.EventTarget;
import cute.events.SettingChangedEvent;
import cute.managers.HudManager;
import cute.modules.Module;
import cute.modules.enums.Category;
import cute.settings.Checkbox;

public class Hud extends Module
{
	public Hud() 
	{
		super("Hud", Category.CLIENT, "a in game hud");
		
	}

	public static Checkbox draggable = new Checkbox("Draggable", false);

		
	@Override
    public void setup() 
	{
		this.addSetting(draggable);
    }

	
	@EventTarget
	public void modeChangedEvent(SettingChangedEvent e)
	{
		if(e.settingID != draggable.getId())
			return;

		HudManager.INSTANCE.openConfigScreen();
	}
	
}
