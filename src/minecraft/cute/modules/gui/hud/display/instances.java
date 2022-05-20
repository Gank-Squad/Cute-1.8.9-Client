package cute.modules.gui.hud.display;

import cute.modules.gui.hud.HudManager;

public class instances 
{
	private static displayText dtext;
	
	public static void register(HudManager api)
	{
		dtext = new displayText();
		api.register(dtext);
	}
	
	public static displayText displaytext()
	{
		return dtext;
	}
}
