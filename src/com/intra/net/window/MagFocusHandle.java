package com.intra.net.window;

public class MagFocusHandle extends MagEventBase {

	public MagFocusHandle(WindowMagnifier window) {
		super(window);
	}

	public void onEvent(String command) {
		if (command.equals("focus zoom")) {
			window.getZoomSlider().requestFocus();
		} else if (command.equals("focus format")) {
			window.getFirstFormat().requestFocus();
		} else if (command.equals("focus list")) {
			window.getWindowList().requestFocus();
		} else if (command.equals("focus name")) {
			window.getFileName().requestFocus();
		} else if (command.equals("focus delay")) {
			window.getDelaySlider().requestFocus();
		}
	}

}
