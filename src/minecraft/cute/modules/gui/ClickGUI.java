package cute.modules.gui;

import cute.ui.ClickUI;
import cute.modules.enums.Category;
import cute.modules.Module;

public class ClickGUI extends Module
{
	public ClickUI clickUI;

    public ClickGUI() 
    {
    	super("Click GUI", Category.CLIENT, "Provides a GUI");
    }

    @Override
    public void onEnable() 
    {
        if(this.clickUI == null) 
        {
            this.clickUI = new ClickUI();
            this.clickUI.closeOnKey = this.getKeybind().getKeyCode();
        }
        
        mc.displayGuiScreen(this.clickUI);
        
        toggle();
    }
    
    @Override
    public void onDisable()
    {
//    	this is for debug 
//    	this.clickUI = null;
    }
}