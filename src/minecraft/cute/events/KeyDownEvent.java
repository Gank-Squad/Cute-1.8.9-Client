package cute.events;

import cute.eventapi.events.Event;

public class KeyDownEvent extends KeyboardEvent implements Event 
{
	public KeyDownEvent(int keyCode) 
	{
		super(keyCode);
	}
}
