package alice.cute.setting;

import java.util.ArrayList;
import java.util.Iterator;

import alice.cute.setting.enums.ListType;
import alice.cute.setting.enums.SettingType;

public class ListSelection<T> extends Setting 
{
	private ListType _type;
	private ArrayList<T> enabledItems   ;
	
	public ListSelection(String name, ArrayList<T> enabled, ListType type) 
	{
		this.enabledItems = enabled;
		
		this._name = name;
		this._opened = false;
		this._settingType = SettingType.LIST;
		this._type = type;
	}
	
	
	public String getName() 
	{
		return this._name;
	}
	
	public void disableItem(int index)
	{
		if(index < 0 || index >= this.enabledItems.size())
			return;
		
		this.enabledItems.remove(index);
	}
	
	
	public void disableItem(T item)
	{
		this.enabledItems.remove(item);
	}
	
	public void enableItem(T item)
	{
		this.enabledItems.add(item);
	}
	
	public Iterator<T> getIterator()
	{
		return this.enabledItems.iterator();
	}
	
	public ArrayList<T> getEnabledItems()
	{
		return this.enabledItems;
	}
	
	public T getItem(int index)
	{
		return this.enabledItems.get(index);
	}
	
	public ListType getListType()
	{
		return this._type;
	}
	
	public int getSize()
	{
		return this.enabledItems.size();
	}
}







