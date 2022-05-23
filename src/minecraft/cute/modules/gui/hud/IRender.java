package cute.modules.gui.hud;

public interface IRender
{
	int getWidth();
	
	int getHeight();
	
	ScreenPosition getPos();
	
	void setPos(ScreenPosition pos);
	
	void render();
	
	void renderDummy(ScreenPosition pos);

	public default boolean isEnabled()
	{
		return true;
	}
	
}
