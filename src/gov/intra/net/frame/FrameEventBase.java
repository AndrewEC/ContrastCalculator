package gov.intra.net.frame;

public abstract class FrameEventBase implements EventBase {
	
	protected final Frame frame;
	
	public FrameEventBase(Frame frame){
		this.frame = frame;
	}
	
}
