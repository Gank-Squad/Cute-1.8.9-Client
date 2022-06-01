package cute.settings;

import java.util.ArrayList;
import java.util.Iterator;

import cute.settings.enums.ListInputType;
import cute.settings.enums.ListType;
import cute.settings.enums.SettingType;

public class ListSelection<T> extends Setting 
{
	private ListType _type;
	private ArrayList<T> enabledItems;
	public boolean canToggleItems = false;
	
	public final ListInputType listInputType;
	
	public ListSelection(String name, ArrayList<T> enabled, ListType type) 
	{
		this.enabledItems = enabled;
		
		this._name = name;
		this._opened = false;
		this._settingType = SettingType.LIST;
		this._type = type;
		this.listInputType  = ListInputType.SEARCH;
	}
	
	public ListSelection(String name, ArrayList<T> enabled, ListType type, ListInputType ltype) 
	{
		this.enabledItems = enabled;
		
		this._name = name;
		this._opened = false;
		this._settingType = SettingType.LIST;
		this._type = type;
		this.listInputType = ltype;
	}
	
	public String getName() 
	{
		return this._name;
	}
	
	public void disableItem(int index)
	{
		if(index < 0 || index >= this.enabledItems.size())
			return;
		
		T item = this.enabledItems.remove(index);
		
		if (item != null)
			this.update(false, item);
	}
	
	
	public void disableItem(T item)
	{
		if(this.enabledItems.remove(item))
			this.update(false, item);
	}
	
	public void enableItem(T item)
	{
		if(this.enabledItems.contains(item))
			return;
		
		this.enabledItems.add(item);
		this.update(true, item);
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







