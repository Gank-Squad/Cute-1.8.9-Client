package cute.events;

import cute.eventapi.events.Event;

public class KeyUpEvent extends KeyboardEvent implements Event 
{
	public KeyUpEvent(int keyCode) 
	{
		super(keyCode);
	}
}
