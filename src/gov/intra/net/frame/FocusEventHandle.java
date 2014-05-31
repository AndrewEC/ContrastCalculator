package gov.intra.net.frame;

public class FocusEventHandle extends FrameEventBase {

	public FocusEventHandle(Frame frame) {
		super(frame);
	}

	public void onEvent(String command) {
		if (command.equals("get focus")) {
			frame.getPanel().requestFocus();
		} else if (command.equals("focus name")) {
			frame.getResultsName().requestFocus();
		} else if (command.equals("focus slider")) {
			frame.getPanel().getFrSlider().requestFocus();
		}
	}
}
