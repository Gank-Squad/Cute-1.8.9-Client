package alice.cute.setting;

import java.util.ArrayList;
import java.util.List;

import alice.cute.setting.enums.SettingType;

public class Mode extends Setting
{
	private String[] _modes;
	private int _mode;
		
	public Mode(String name, String... modes) 
	{
		this._name = name;
		this._modes = modes;
		this._opened = false;
		this._settingType = SettingType.MODE;
	}
	
	public String getMode()
	{
		return this._modes[this._mode];
	}
	
	public String getMode(int modeIndex) 
	{
		return this._modes[modeIndex];
	}
	
	public void setMode(int mode) 
	{
		this._mode = mode;
	}
	
	public int getValue() 
	{
		return this._mode;
	}
	
	public int getNextMode() 
	{
		return this._mode + 1 >= this._modes.length ? 0 : this._mode + 1;
	}
	
	public void nextMode()
	{
		this._mode = this.getNextMode();
	}
}
