package gov.intra.net.panel;

import gov.intra.net.frame.EventBase;
import gov.intra.net.frame.Frame;

public abstract class PanelEventBase implements EventBase {

	protected final Frame frame;
	protected final Panel panel;

	public PanelEventBase(Frame frame, Panel panel) {
		this.frame = frame;
		this.panel = panel;
	}

}
