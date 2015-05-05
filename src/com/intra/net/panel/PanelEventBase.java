package com.intra.net.panel;

import com.intra.net.frame.EventHandle;
import com.intra.net.frame.Frame;

public abstract class PanelEventBase implements EventHandle {

	protected final Frame frame;
	protected final Panel panel;

	public PanelEventBase(Frame frame, Panel panel) {
		this.frame = frame;
		this.panel = panel;
	}

}
