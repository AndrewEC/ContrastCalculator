package gov.intra.net.window;

import gov.intra.net.frame.EventHandle;

public abstract class MagEventBase implements EventHandle {

	protected final WindowMagnifier window;

	public MagEventBase(WindowMagnifier window) {
		this.window = window;
	}

}
