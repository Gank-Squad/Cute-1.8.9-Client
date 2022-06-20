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
	public static boolean validNameChar(char c)
	{
		return "abcdefghijklmnopqrstuvwxyz_0123456789".contains("" + c);
	}
	public static boolean validColorCode(char c)
	{
		return "0123456789abcdef".contains(""+c);
	}
	
	public static int getNameColor(String s)
	{
		s = s.trim().toLowerCase();
		char c = ' ';
		for (int i = 0; i < s.length() - 3; i++)
		{
			if (s.charAt(i) == '§')
			{
				c = s.charAt(i + 1);
				if(!validColorCode(c))
					continue;
				if(validNameChar(c))
					break;
			}
		}
		switch(c)
		{
		case '0':
			return 0;
		case '1':
			return 0x0000AA;
		case '2':
			return 0x00AA00;
		case '3':
			return 0x00AAAA;
		case '4':
			return 0xAA0000;
		case '5':
			return 0xAA00AA;
		case '6':
			return 0xFFAA00;
		case '7':
			return 0xAAAAAA;
		case '8':
			return 0x555555;
		case '9':
			return 0x5555FF;
		case 'a':
			return 0x55FF55;
		case 'b':
			return 0x55FFFF;
		case 'c':
			return 0xFF5555;
		case 'd':
			return 0xFF55FF;
		case 'e':
			return 0xFFFF55;
		case 'f':
			return 0xFFFFFF;
		default:
			return 0;
		}
	}
}
