package cute.modules.gui.hud.display;

import cute.Client;
import cute.eventapi.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;


//import cute.modules.Module;
//import cute.modules.enums.Category;
//import cute.ui.ClickUI;

public class renderText
{
	private boolean isEnabled = true;
	
	protected final FontRenderer font;
	protected final Client client;
	protected final Minecraft mc;
	public renderText()
	{
		this.mc = Minecraft.getMinecraft();
		font = mc.fontRendererObj;
		client = Client.getInstance();
		setEnabled(isEnabled);
	}
	
	public void setEnabled(boolean isEnabled)
	{
		this.isEnabled = isEnabled;
		
		if (isEnabled)
		{
			EventManager.register(this);
		}
		else
		{
			EventManager.register(this);
		}
	}
}
