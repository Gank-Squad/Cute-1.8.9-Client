package cute.modules.gui.hud;

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
	private static HudManager hudManager;

		
	@Override
    public void setup() 
	{
		this.addSetting(draggable);
    }

	
	@EventTarget
	public void modeChangedEvent(SettingChangedEvent e)
	{
		if(e.settingName != draggable.getName())
			return;
		
		if (e.args.length == 0)
		{
			return;
		}
		
		if (!(boolean)e.args[0])
		{
			return;
		}
		
		hudManager = HudManager.INSTANCE;
		mc.displayGuiScreen(null);
		hudManager.openConfigScreen();
	}
	
}
