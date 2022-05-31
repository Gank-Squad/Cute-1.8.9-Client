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
	public static Slider portal = new Slider("Portal", 0, 0.25, 0.5, 1);
	public static Slider fire = new Slider("Fire", 0, 1, 2, 1);
	public static Slider water = new Slider("Water", 0, 0.5, 0.75, 1);
	public static Slider lava = new Slider("Lava", 0, 0.2, 0.4, 1);
	
	public static boolean isOn()
	{
		return globalEnabled;
	}
	
	@Override
	public void setup()
	{
		
		this.addSetting(explosions);
		addSetting(portal);
		this.addSetting(fire);
		addSetting(water);
		addSetting(lava);
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
