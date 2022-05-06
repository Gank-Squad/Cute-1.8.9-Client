package cute.events;

import cute.eventapi.events.Event;

public class KeyboardEvent implements Event
{
	private final int keyCode;
	
	public KeyboardEvent(int keyCode)
	{
		this.keyCode = keyCode;
	}
	
	public int getKeyCode() 
	{
		return this.keyCode;
	}
}
