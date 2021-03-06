package cute.modules.client;

import java.util.ArrayList;
import java.util.HashSet;

import cute.Client;
import cute.eventapi.EventManager;
import cute.eventapi.EventTarget;
import cute.events.SettingChangedEvent;
import cute.modules.Module;
import cute.modules.enums.Category;
import cute.settings.ListSelection;
import cute.settings.enums.ListInputType;
import cute.settings.enums.ListType;
import cute.util.StringUtil;

public class Players extends Module 
{
	public Players()
	{
		super("Players", Category.CLIENT, "For whitelisting players");
		EventManager.register(this);
	}
	
	public static HashSet<String> playerNameBlacklist = new HashSet<String>();
	
	public static ListSelection playerNames = new ListSelection<String>("Ignore ESP/Tracers", new ArrayList<String>(), ListType.PLAYERNAME, ListInputType.SEARCH_AND_TEXT);
	
	@Override
	public void setup()
	{
		this.addSetting(playerNames);
	}
	
	// we always want events so override this and hook using constructor 
	@Override
	public void onEnable()
	{
		this._enabled = true;
	}
	
	@Override
	public void onDisable()
	{	
		this._enabled = false;
	}
	
	@EventTarget
	public void settingChanged(SettingChangedEvent e)
	{
		if(e.settingID != playerNames.getID())
			return;
		
		switch(e.type)
		{
			case LIST:
				
				for(Object o : e.args)
				{
					if (o instanceof String)
					{
						String s = (String)o;
						StringUtil.getNameColor(s);
						if(e.added)
						{
							Players.playerNameBlacklist.add(StringUtil.clearNameFormat(s).toLowerCase().trim());
						}
						else 
						{
							Players.playerNameBlacklist.remove(StringUtil.clearNameFormat(s.toLowerCase().trim()));
						}
					}
				}
				
				break;
		}
	}
}
