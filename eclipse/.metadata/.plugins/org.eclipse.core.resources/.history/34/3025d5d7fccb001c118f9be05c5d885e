package alice.cute.setting;

import java.awt.Color;

public class ColorPicker extends SubSetting
{
    private Color _color;
    private boolean _opened;

    public ColorPicker(Setting parent, String name, Color color) {
        this._name = name;
        this._parent = parent;
        this._color = color;
        this._opened = false;

        if (parent instanceof Checkbox) 
        {
            Checkbox p = (Checkbox) parent;
            p.addSub(this);
        }

        else if (parent instanceof Mode) 
        {
            Mode p = (Mode) parent;
            p.addSub(this);
        }

        else if (parent instanceof Slider) 
        {
            Slider p = (Slider) parent;
            p.addSub(this);
        }

        else if (parent instanceof Keybind) 
        {
            Keybind p = (Keybind) parent;
            p.addSub(this);
        }
    }

    public Color getColor() 
    {
        return this._color;
    }

    public int getRed() 
    {
        return this._color.getRed();
    }

    public void setRed(int r)
    {
    	this._color = new Color(
    			r,
    			this._color.getGreen(),
    			this._color.getBlue(),
    			this._color.getAlpha());
    }
    
    public int getGreen() 
    {
        return this._color.getGreen();
    }

    public void setGreen(int g)
    {
    	this._color = new Color(
    			this._color.getRed(),
    			g,
    			this._color.getBlue(),
    			this._color.getAlpha());
    }
    
    public int getBlue() 
    {
        return this._color.getBlue();
    }

    public void setBlue(int b)
    {
    	this._color = new Color(
    			this._color.getRed(),
    			this._color.getGreen(),
    			b,
    			this._color.getAlpha());
    }
    
    public int getAlpha() 
    {
        return this._color.getAlpha();
    }

    public void setAlpha(int a)
    {
    	this._color = new Color(
    			this._color.getRed(),
    			this._color.getGreen(),
    			this._color.getBlue(),
    			a);
    }
    
    public void setColor(Color color) 
    {
        this._color = color;
    }

    public void toggleState() 
    {
        this._opened = !this._opened;
    }

    public boolean isOpened() 
    {
        return this._opened;
    }
}
