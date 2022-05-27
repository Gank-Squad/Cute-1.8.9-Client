package cute.events;

import cute.eventapi.events.Event;
import cute.settings.enums.SettingType;

public class SettingChangedEvent implements Event 
{
	public final Object[] args;
	public final boolean added;
	public final SettingType type;
	public final String settingName;
	public final int settingID;
	
	public SettingChangedEvent(int settingId, String name, SettingType type, boolean added, Object... args)
	{
		this.settingID = settingId;
		this.settingName = name;
		this.type = type;
		this.added = added;
		this.args = args;
	}
}
