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
	public static boolean isColorCode(char c)
	{
		return '0' <= c && c <= '9' || 
			   'a' <= c && c <= 'f' ||
			   'A' <= c && c <= 'F' ||
			   'k' <= c && c <= 'o' ||
			   'K' <= c && c <= 'O' ||
			   'r' == c || c == 'R';
	}
	
	public static String getColorCodeNameStr(char c)
	{
		switch(c)
		{
		// 0 Black 0,0,0
		case '0':
			return "black";
			
		// 1 Dark Blue 0,0,170	
		case '1':
			return "dark blue";
			
		// 2 Dark Green 0,170,0
		case '2':
			return "dark green";
			
		// 3 Dark Aqua	0, 170, 170
		case '3':
			return "dark aqua";
			
		// 4 Dark Red 170, 0, 0
		case '4':
			return "dark red";
			
		// 5 Dark Purple 170, 0, 170
		case '5':
			return "dark purple";
			
		// 6 Gold 255, 170, 0
		case '6':
			return "gold";
			
		// 7 Gray 170, 170, 170
		case '7':
			return "grey";
			
		// 8 Dark Gray 85, 85, 85
		case '8':
			return "dark grey";
			
		// 9 Blue 85, 85, 255
		case '9':
			return "blue";
			
		// a Green 85, 255, 85
		case 'a':
			return "green";
			
		// b Aqua 85, 255, 255
		case 'b':
			return "aqua";
			
		// c Red 255, 85, 85
		case 'c':
			return "red";
			
		// d Light Purple 255, 85, 255
		case 'd':
			return "light purple";
			
		// e Yellow 255, 255, 85
		case 'e':
			return "yellow";
			
		// f White 255, 255, 255
		case 'f':
			return "white";
			
		default:
			return "none";
		}
	}
	
	public static char getNameColorCode(String s)
	{
		Matcher m = LAST_COLOR.matcher(s.trim());
		
		if(!m.find())
		{
			return 'r';
		}
		
		return m.group(1).toLowerCase().charAt(0);
	}
	
	public static int getNameColor(String s)
	{
		char c = getNameColorCode(s);
		
		switch(c)
		{
			// 0 Black 0,0,0
			case '0':
				return 0x000000FF;
				
			// 1 Dark Blue 0,0,170	
			case '1':
				return 0x0000AAFF;
				
			// 2 Dark Green 0,170,0
			case '2':
				return 0x00AA00FF;
				
			// 3 Dark Aqua	0, 170, 170
			case '3':
				return 0x00AAAAFF;
				
			// 4 Dark Red 170, 0, 0
			case '4':
				return 0xAA0000FF;
				
			// 5 Dark Purple 170, 0, 170
			case '5':
				return 0xAA00AAFF;
				
			// 6 Gold 255, 170, 0
			case '6':
				return 0xFFAA00FF;
				
			// 7 Gray 170, 170, 170
			case '7':
				return 0xAAAAAAFF;
				
			// 8 Dark Gray 85, 85, 85
			case '8':
				return 0x555555FF;
				
			// 9 Blue 85, 85, 255
			case '9':
				return 0x5555FFFF;
				
			// a Green 85, 255, 85
			case 'a':
				return 0x55FF55FF;
				
			// b Aqua 85, 255, 255
			case 'b':
				return 0x55FFFFFF;
				
			// c Red 255, 85, 85
			case 'c':
				return 0xFF5555FF;
				
			// d Light Purple 255, 85, 255
			case 'd':
				return 0xFF55FFFF;
				
			// e Yellow 255, 255, 85
			case 'e':
				return 0xFFFF55FF;
				
			// f White 255, 255, 255
			case 'f':
				return 0xFFFFFFFF;
				
			default:
				return 0;
		}
	}
}
