package cute.modules.misc;

import cute.modules.Module;
import cute.modules.enums.Category;
import cute.settings.Mode;

public class Sprint extends Module 
{
	public Sprint()
	{
		super("Sprint", Category.MISC, "sprints");
	}
	
	/*
	 * Toggle Sprint: Not bannable on hypixel, basically just holds the sprint key for you
	 * Auto Sprint: Bannable, sets the player sprinting
	 */
	public static Mode smode = new Mode("Mode", "Toggle Sprint", "Auto Sprint", "Sprint + Jump");
	
	@Override
	public void setup()
	{
		this.addSetting(smode);
	}
	
	@Override
	public void onEnable()
	{
		super.onEnable();
	}
	
	@Override
	public void onDisable()
	{
		super.onDisable();
		
		if(smode.getValue() == 0)
			mc.gameSettings.keyBindSprint.setKeyDown(false);
	}
	
	@Override
	public void onUpdate()
	{
		if(nullCheck())
			return;
		
		switch(smode.getValue())
		{
			case 0:
				mc.gameSettings.keyBindSprint.setKeyDown(true);
				break;
				
			case 1:
				if(mc.gameSettings.keyBindForward_.isKeyDown() && 
						  !mc.thePlayer.isSneaking() &&
						  !mc.thePlayer.isUsingItem() &&
						   mc.thePlayer.getFoodStats().getFoodLevel() > 6 )
				{
					mc.thePlayer.setSprinting(true);
				}
				break;
				
			case 2:
				mc.gameSettings.keyBindSprint.setKeyDown(true);
				mc.gameSettings.keyBindJump.setKeyDown(mc.gameSettings.keyBindForward_.isKeyDown() && 
						  !mc.thePlayer.isSneaking() &&
						  !mc.thePlayer.isUsingItem() &&
						   mc.thePlayer.getFoodStats().getFoodLevel() > 6 );
		}	
	}
}
