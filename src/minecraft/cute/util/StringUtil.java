package cute.util;

public class StringUtil 
{
	public static String clearNameFormat(String s) 
    {
        return s
        		// hypixel bedwars name format
        		.replaceAll("(§.§.. §.§.)", "")
        		
        		// any color
        		.replaceAll("§.", "");
    }
}
