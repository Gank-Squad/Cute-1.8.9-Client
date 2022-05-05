package cute.modules.render;

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
		this.addSetting(this.Mode);
	}
	
	@Override
	public void onEnable() 
	{
		enable(this.Mode.getValue());
	}

	@Override
	public void onDisable() 
	{
		if(nullCheck())
			return;
	
		// restore setting / potion status 
		this.mc.thePlayer.removePotionEffect(Potion.nightVision.id);
		
		if(this._oldBrightness == -1)
			return;
		
		this.mc.gameSettings.saturation = this._oldBrightness;	
		this._oldBrightness = -1;
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
				
				mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, 80950, 1, false, false));
				
				this.hadNV = true;
				return;
		}
	}
}






