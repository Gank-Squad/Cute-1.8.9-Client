package cute.modules.gui.hud;

public interface IRender extends IRenderConfig
{
	int getWidth();
	int getHeight();
	
	void render(ScreenPosition pos);
	
	void renderDummy(ScreenPosition pos);
//	{
//		render(pos);
//	}
	
	public default boolean isEnabled()
	{
		return true;
	}
	
}
