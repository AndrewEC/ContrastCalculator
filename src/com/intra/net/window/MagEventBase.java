package com.intra.net.window;

import com.intra.net.frame.EventHandle;

public abstract class MagEventBase implements EventHandle {

	protected final WindowMagnifier window;

	public MagEventBase(WindowMagnifier window) {
		this.window = window;
	}

}
