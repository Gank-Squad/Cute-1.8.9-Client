package alice.cute.setting;

import alice.cute.setting.enums.SettingType;

public class Slider extends Setting
{
	private double _min;
	private double _value;
	private double _max;
	private int    _scale;	
	
	public Slider(String name, double min, double value, double max, int scale) {
		this._name = name;
		this._min = min;
		this._value = scale == 0 ? (int) value : value;
		this._max = max;
		this._scale = scale;
		this._opened = false;
		this._settingType = SettingType.SLIDER;
	}
	
	public int getRoundingScale() 
	{
		return this._scale;
	}
	
	public double getValue() 
	{
		return this._value;
	}
	
	public double getMaxValue() 
	{
		return this._max;
	}
	
	public double getMinValue() 
	{
		return this._min;
	}
	
	public void setValue(double value) 
	{
		this._value = value;
	}
}
