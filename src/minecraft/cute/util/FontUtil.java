package cute.util;


import net.minecraft.client.Minecraft;


public class FontUtil 
{
    private static Minecraft mc = Minecraft.getMinecraft();

    
    public static int getStringWidth(String text) 
    {
        return mc.fontRendererObj.getStringWidth(text);
    }

    public static int getFontHeight() 
    {
        return mc.fontRendererObj.FONT_HEIGHT;
    }

    public static void drawString(String text, double x, double y, int color) 
    {
    	mc.fontRendererObj.drawString(text, (int)x, (int)y, color);
    }

    public static void drawStringWithShadow(String text, double x, double y, int color) 
    {
        mc.fontRendererObj.drawStringWithShadow(text, (float) x, (float) y, color);
    }

    public static void drawCenteredString(String text, double x, double y, int color) 
    {
        drawString(text, x - mc.fontRendererObj.getStringWidth(text) / 2, y, color);
    }

    public static void drawCenteredStringWithShadow(String text, double x, double y, int color) 
    {
        drawStringWithShadow(text, x - mc.fontRendererObj.getStringWidth(text) / 2, y, color);
    }

    public static void drawTotalCenteredString(String text, double x, double y, int color) 
    {
        drawString(text, x - mc.fontRendererObj.getStringWidth(text) / 2, y - mc.fontRendererObj.FONT_HEIGHT / 2, color);
    }

    public static void drawTotalCenteredStringWithShadowMC(String text, double x, double y, int color) 
    {
        drawStringWithShadow(
        		text, 
        		x - mc.fontRendererObj.getStringWidth(text) / 2, 
        		y - (int) (mc.fontRendererObj.FONT_HEIGHT / 2F) - 1, 
        		color);
    }
}



