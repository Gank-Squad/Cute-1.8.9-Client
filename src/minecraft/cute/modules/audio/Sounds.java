package cute.modules.audio;

import cute.modules.Module;
import cute.modules.enums.Category;
import cute.settings.Slider;

public class Sounds extends Module 
{
	public Sounds()
	{
		super("Volume", Category.SOUNDS, "lets you adjust volume");
	}
	
	private static boolean globalEnabled = false;
	
	public static Slider explosions = new Slider("Explosion", 0, 4, 4, 1);
	
	public static boolean isOn()
	{
		return globalEnabled;
	}
	
	@Override
	public void setup()
	{
		this.addSetting(explosions);
	}
	
	@Override
	public void onEnable()
	{
		super.onEnable();
		
		globalEnabled = true;
	}
	
	@Override 
	public void onDisable()
	{
		super.onDisable();
		
		globalEnabled = false;
	}
}
