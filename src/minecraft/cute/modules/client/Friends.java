package cute.modules.client;

import java.util.HashSet;

import cute.modules.Module;
import cute.modules.enums.Category;

public class Friends extends Module 
{
	public Friends()
	{
		super("Friends", Category.CLIENT, "For whitelisting players");
	}
	
	public static HashSet<String> playerNameWhitelist = new HashSet<String>();
}
