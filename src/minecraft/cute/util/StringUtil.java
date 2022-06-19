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
	public static int getNameColor(String s)
	{
		// Â§cÂ§lR Â§rÂ§cGooseCosmos
		s.replaceAll("(§.§.. §.§.)", "");
		s.trim();
		
		// theoretically, next 2 chars should be &c
		String c = s.substring(0, 2);
		
		if (c.equals("§4"))
			return 0xBE0000;
		if (c.equals("§c"))
			return 0xFE3F3F;
		if (c.equals("§6"))
			return 0xD9A334;
		if (c.equals("§e"))
			return 0xFEFE3F;
		if (c.equals("§2"))
			return 0x00BE00;
		if (c.equals("§a"))
			return 0x3FFE3F;
		if (c.equals("§b"))
			return 0x3FFEFE;
		if (c.equals("§3"))
			return 0x00BEBE;
		if (c.equals("§1"))
			return 0x0000BE;
		if (c.equals("§9"))
			return 0x3F3FFE;
		if (c.equals("§d"))
			return 0xFE3FFE;
		if (c.equals("§5"))
			return 0xBE00BE;
		if (c.equals("§f"))
			return 0xFFFFFF;
		if (c.equals("§7"))
			return 0xBEBEBE;
		if (c.equals("§8"))
			return 0x3F3F3F;
		if (c.equals("§0"))
			return 0x000000;
		
		
		// not sure what default should be
		return 0;
	}
}
