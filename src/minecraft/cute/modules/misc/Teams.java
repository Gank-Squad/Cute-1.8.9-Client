package cute.modules.misc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cute.eventapi.EventTarget;
import cute.events.SettingChangedEvent;
import cute.modules.Module;
import cute.modules.enums.Category;
import cute.settings.Checkbox;
import cute.util.EntityUtil;
import cute.util.StringUtil;

public class Teams extends Module
{
//	genuinely cannot think of any other possible modes, other than manually adding people in
//	public static Mode mode = new Mode("By Name Color");
	public static Checkbox update = new Checkbox("update teams", false);
	
    public static Map<String, CuteTeam> players = new HashMap<>();
    
    
	public Teams()
	{
		super("Teams", Category.MISC, "For grouping players into teams");
	}
	
	@Override
	public void setup()
	{
//		addSetting(mode);
		addSetting(update);
	}
	
	
	@EventTarget
	public void settingChanged(SettingChangedEvent e)
	{
		if (e.settingID != update.getID())
			return;
		// cache everyone currently in the tablist, so that it can get queried against currently loaded entities
		players.clear();
		List<String> l = EntityUtil.getTabMenuPlayerNames();
		for (String i : l)
		{
			// key = i
			// value = team
			int color = StringUtil.getNameColor(i);
			players.put(StringUtil.clearNameFormat(i), new CuteTeam(color, Integer.toString(color)));
		}
	}
}
