package cute.events;

import cute.eventapi.events.Cancellable;
import cute.eventapi.events.Event;
import net.minecraft.network.Packet;

public class PacketReceivedEvent implements Cancellable, Event
{
	private boolean canceled;
	
	public final Packet packet;
	
	public PacketReceivedEvent(Packet p)
	{
		this.packet = p;
		this.canceled = false;
	}
	
	public boolean isCancelled()
    {
    	return this.canceled;
    }

    public void setCancelled(boolean state)
    {
    	this.canceled = state;
    }
}
