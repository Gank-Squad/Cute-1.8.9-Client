package cute.modules;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import cute.Client;
import cute.eventapi.EventManager;
import cute.modules.enums.Category;
import cute.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class Module 
{
	protected Minecraft mc = Minecraft.getMinecraft();
	
	protected String     _name;
	protected Category   _category;
	protected String     _description;
	protected KeyBinding _key;

	protected boolean _enabled;
	protected boolean _opened;
	protected boolean _drawn;
	protected boolean _isKeyDown = false;
	protected boolean _isBinding;
	protected float   _remainingAnimation = 0.0f;

	protected List<Setting> _settingsList = new ArrayList<Setting>();

	public Module(String name, Category category, String description) 
	{
		this._name = name;
		this._category = category;
		this._description = description;
		this._enabled = false;
		this._opened = false;
		this._drawn = true;

		// VERY IMPORTANT YOU KEEP THIS NULL
		// if you don't use the 'initKeybinding' function below 
		// from within 'net.minecraft.client.settings.GameSettings' constructor
		// the game will crash if you open the control menu
		this._key = null; // new KeyBinding(name, Keyboard.KEY_NONE, Client.NAME + " Keybind");
		
		this.setup();	
	}

	
	
	// i would make this final and put it in the constructor but 
	// it's annoying to have to init modules in the gameSettings class
	// and i want to init them in the module manager instead
	private boolean __hasinitbindings = false;
	
	public KeyBinding initKeybinding()
	{
		if(this.__hasinitbindings)
			return null;
		
		this.__hasinitbindings = true;
		this._key = new KeyBinding(this._name, Keyboard.KEY_NONE, Client.NAME + " Keybind");
		return this._key;
	}
	
	public boolean nullCheck() 
	{
		return mc.theWorld == null || 
			   mc.thePlayer == null;
	}
	
	public void delayedSetup()
	{
		
	}
	
	public void setup() 
	{

	}

	public List<Setting> getSettings()
	{
		return this._settingsList;
	}
	
	public void addSetting(Setting s) 
	{
		this._settingsList.add(s);
	}

	public boolean isEnabled() 
	{
		return this._enabled;
	}

	public boolean isKeyDown() 
	{
		return this._isKeyDown;
	}

	public void setKeyCode(int key) 
	{
		this._key.setKeyCode(key);
	}
	
	public void unbindKey()
	{
		this._key.setKeyCode(Keyboard.KEY_NONE);
	}
	
	public void setKeyDown(boolean b) 
	{
		this._isKeyDown = b;
	}

	public void onEnable() 
	{
		this._remainingAnimation = 0.0f;
		EventManager.register(this);
	}

	public void onDisable() 
	{
		this._remainingAnimation = 0.0f;
		EventManager.unregister(this);
	}

	public void onToggle() 
	{
		this._remainingAnimation = 0.0f;
	}

	public void onUpdate() 
	{

	}

	public void onFastUpdate() 
	{

	}

	public void onServerUpdate() 
	{

	}

	public void onValueChange() 
	{

	}

	public void toggle() 
	{
		this._enabled = !this._enabled;
		
		try 
		{
			if (this.isEnabled()) 
			{
				this.onEnable();	
			}
			else 
			{
				this.onDisable();
			}
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}

	public void enable() 
	{
		if (this.isEnabled())
			return;
		
		this._enabled = true;
		
		try 
		{
			this.onEnable();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}

	public void disable() 
	{
		if (!this.isEnabled()) 
			return;
		
		this._enabled = false;
		
		try 
		{
			this.onDisable();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}

	public void setEnabled(boolean enabled) 
	{
		this._enabled = enabled;
		
		if(this._enabled) 
		{
			this.onEnable();
		}
		else 
		{
			this.onDisable();
		}
	}

	public String getName() 
	{
		return this._name;
	}

	public Category getCategory() 
	{
		return this._category;
	}

	public String getDescription() 
	{
		return this._description;
	}

	public int getKeyCode()
	{
		return this._key.getKeyCode();
	}
	
	public KeyBinding getKeybind() 
	{
		return this._key;
	}

	public String getHUDData() 
	{
		return "";
	}

	public boolean hasSettings() 
	{
		return this._settingsList.size() > 0;
	}

	public void toggleState() 
	{
		this._opened = !this._opened;
	}

	public boolean isOpened() 
	{
		return this._opened;
	}

	public boolean isBinding() 
	{
		return this._isBinding;
	}

	public boolean isDrawn() 
	{
		return this._drawn;
	}

	public void setBinding(boolean b) 
	{
		this._isBinding = b;
	}

	public void setDrawn(boolean in) 
	{
		this._drawn = in;
	}	
}








