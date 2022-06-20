package cute.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil 
{
	// matches if a string contains color formatting 
    public static final Pattern HAS_COLOR = Pattern.compile("(§[a-f0-9])", Pattern.CASE_INSENSITIVE);
    
    // matches if a string is a vlid minecraft username
    public static final Pattern VALID_USERNAME = Pattern.compile("^([0-9a-z_]+)$", Pattern.CASE_INSENSITIVE);
    
    // matches to find the color of a name, match.group(1) returns the color code of the color just before the username 
    public static final Pattern LAST_COLOR = Pattern.compile("^.*§([a-f0-9])[a-z0-9_]+$", Pattern.CASE_INSENSITIVE);

    public static final Pattern HEX = Pattern.compile("^(0x)?([0-9a-f]+)$", Pattern.CASE_INSENSITIVE);
    
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
		return '0' <= c && c <= '9' || 
			   'a' <= c && c <= 'z' ||
			   'A' <= c && c <= 'Z' ||
			   '_' == c;
	}
	
	
	public static boolean isHex(char c)
	{
		return '0' <= c && c <= '9' || 
			   'a' <= c && c <= 'f' ||
			   'A' <= c && c <= 'F';
	}
	
	
	public static int getNameColor(String s)
	{
		Matcher m = LAST_COLOR.matcher(s.trim());
		
		if(!m.find())
			return -1;
		
		char c = m.group(1).toLowerCase().charAt(0);
		
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
				return -1;
		}
	}
}
