package cute.settings;

import java.util.ArrayList;
import java.util.List;

import cute.eventapi.EventManager;
import cute.events.SettingChangedEvent;
import cute.settings.enums.SettingType;

public class Setting 
{
	protected String _name;
	protected boolean _opened;
	protected SettingType _settingType;
	
	protected List<SubSetting> _subs = new ArrayList();
	
	public void update()
	{
		EventManager.call(new SettingChangedEvent());
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
