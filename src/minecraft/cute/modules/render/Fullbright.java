package cute.modules.render;

import cute.eventapi.EventTarget;
import cute.events.SettingChangedEvent;
import cute.modules.Module;
import cute.modules.enums.Category;
import cute.settings.Mode;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class Fullbright extends Module 
{
	public Fullbright()
	{
		super("Fullbright", Category.RENDER, "Provides night vision");
	}
	
	
	private float _oldBrightness = -1;
	
	// 0 for gamma, 1 for potion
	public static Mode Mode = new Mode("Mode", "Gamma", "Potion");
	
	private boolean hadNV = false;
	
	
	@Override
	public void setup() 
	{
		this.addSetting(Fullbright.Mode);
	}
	
	@Override
	public void onEnable() 
	{
		super.onEnable();
		enable(Fullbright.Mode.getValue());
	}

	@Override
	public void onDisable() 
	{
		super.onDisable();
	
		if(nullCheck())
			return;
	
		// restore setting / potion status 
		this.mc.thePlayer.removePotionEffect(Potion.nightVision.id);
		
		if(this._oldBrightness == -1)
			return;
		
		this.mc.gameSettings.saturation = this._oldBrightness;	
		this._oldBrightness = -1;
	}
	
	@EventTarget
	public void modeChangedEvent(SettingChangedEvent e)
	{
		if (e.settingID != Mode.getId())
			return;
		
		enable(Fullbright.Mode.getValue());
	}

	public void enable(int mode) 
	{		
		switch(mode)
		{
			case 0:
				
				// save the old brightness setting 
				if(this._oldBrightness == -1) 
				{
					this._oldBrightness = this.mc.gameSettings.saturation;
				}
				
				if(this.hadNV) 
				{
					if(!nullCheck())
					{
						this.mc.thePlayer.removePotionEffect(Potion.nightVision.id);
						this.hadNV = false;
					}
				}
				
				this.mc.gameSettings.saturation = 100;
				return;
		
			case 1:
				
				if(nullCheck())
					return;
				
				mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, Integer.MAX_VALUE, 1, false, false));
				
				this.hadNV = true;
				return;
		}
	}
}






