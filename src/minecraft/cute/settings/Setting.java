package cute.settings;

import java.util.ArrayList;
import java.util.List;

import cute.eventapi.EventManager;
import cute.events.SettingChangedEvent;
import cute.settings.enums.SettingType;

public class Setting 
{
	private static int SETTING_ID_COUNTER = 0;
	
	protected String _name;
	protected boolean _opened;
	protected SettingType _settingType;
	
	protected List<SubSetting> _subs = new ArrayList();
	
	protected final int SETTING_ID;
	
	public Setting()
	{
		this.SETTING_ID = (++SETTING_ID_COUNTER);
	}
	
	public final int getID()
	{
		return this.SETTING_ID;
	}
	
	public void update(boolean added, Object... args)
	{
		EventManager.call(new SettingChangedEvent(SETTING_ID, _name, _settingType, added, args));
	}

	public SettingType getSettingType()
	{
		return this._settingType;
	}
	
	public List<SubSetting> getSubSettings()
	{
		return this._subs;
	}
	
	public boolean hasSubSettings() 
	{
		return this._subs.size() > 0;
	}
	
	public void addSub(SubSetting s) 
	{
		this._subs.add(s);
	}

	public void toggleState() 
	{
		this._opened = !this._opened;
	}
	
	public boolean isOpened() 
	{
		return this._opened;
	}
	
	public String getName() 
	{
		return this._name;
	}
}
