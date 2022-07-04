package cute.modules.misc;

import cute.util.StringUtil;

public class CuteTeam 
{
	private int color;

	public String teamName;
	
	private char colorCode;
	
	CuteTeam(int color, String teamName)
	{
		this.color = color;
		this.teamName = teamName;
		this.colorCode = getColorCode(this.color);
	}
	
	CuteTeam(char colorCode, String teamName)
	{
		this.colorCode = colorCode;
		this.teamName = teamName;
		this.color = getColor(colorCode);
	}
	
	public int getColor(char c)
	{
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
	
	public char getColorCode(int color)
	{
		// find color code closest to given color
		int min = -1 >>> 1;
		char c = 'r';
		
		if (Math.abs(0x000000FF - c) < min)
		{
			min = Math.abs(0x000000FF - c);
			c = '0';
		}
		if (Math.abs(0x0000AAFF - c) < min)
		{
			min = Math.abs(0x0000AAFF - c);
			c = '1';
		}
		if (Math.abs(0x00AA00FF - c) < min)
		{
			min = Math.abs(0x00AA00FF - c);
			c = '2';
		}
		if (Math.abs(0x00AAAAFF - c) < min)
		{
			min = Math.abs(0x00AAAAFF - c);
			c = '3';
		}
		if (Math.abs(0xAA0000FF - c) < min)
		{
			min = Math.abs(0xAA0000FF - c);
			c = '4';
		}
		if (Math.abs(0xAA00AAFF - c) < min)
		{
			min = Math.abs(0xAA00AAFF - c);
			c = '5';
		}
		if (Math.abs(0xFFAA00FF - c) < min)
		{
			min = Math.abs(0xFFAA00FF - c);
			c = '6';
		}
		if (Math.abs(0xAAAAAAFF - c) < min)
		{
			min = Math.abs(0xAAAAAAFF - c);
			c = '7';
		}
		if (Math.abs(0x555555FF - c) < min)
		{
			min = Math.abs(0x555555FF - c);
			c = '8';
		}
		if (Math.abs(0x5555FFFF - c) < min)
		{
			min = Math.abs(0x5555FFFF - c);
			c = '9';
		}
		if (Math.abs(0x55FF55FF - c) < min)
		{
			min = Math.abs(0x55FF55FF - c);
			c = 'a';
		}
		if (Math.abs(0x55FFFFFF - c) < min)
		{
			min = Math.abs(0x55FFFFFF - c);
			c = 'b';
		}
		if (Math.abs(0xFF5555FF - c) < min)
		{
			min = Math.abs(0xFF5555FF - c);
			c = 'c';
		}
		if (Math.abs(0xFF55FFFF - c) < min)
		{
			min = Math.abs(0xFF55FFFF - c);
			c = 'd';
		}
		if (Math.abs(0xFFFF55FF - c) < min)
		{
			min = Math.abs(0xFFFF55FF - c);
			c = 'e';
		}
		if (Math.abs(0xFFFFFFFF - c) < min)
		{
			min = Math.abs(0xFFFFFFFF - c);
			c = 'f';
		}
		
		return c;
	}

	public void setColor(int color)
	{
		this.color = color;
		this.colorCode = getColorCode(color);
	}
	public boolean setColorCode(char c)
	{
		if (!StringUtil.isHex(c))
			return false;
		this.colorCode = c;
		this.color = getColor(c);
		
		return true;
	}
	
	public int getColor()
	{
		return this.color;
	}
	public char getColorCode()
	{
		return this.colorCode;
	}
}
