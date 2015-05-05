package com.intra.net.frame;

public class FocusEventHandle extends FrameEventBase {

	public FocusEventHandle(Frame frame) {
		super(frame);
	}

	public void onEvent(String command) {
		if (command.equals("focus slider")) {
			frame.getPanel().getFrSlider().requestFocus();
		}
	}
}
