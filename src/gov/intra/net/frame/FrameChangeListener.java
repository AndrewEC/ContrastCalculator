package gov.intra.net.frame;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import resources.Constants;

public class FrameChangeListener implements ChangeListener {

	private final Frame frame;

	public FrameChangeListener(Frame frame) {
		this.frame = frame;
	}

	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == frame.getTabs()) {
			int height = Constants.MAIN_VIEW_HEIGHT;
			if (frame.getCbShowSliders() != null) {
				height = (frame.getCbShowSliders().isSelected() ? Constants.MAIN_VIEW_EXTENDED_HEIGHT : Constants.MAIN_VIEW_HEIGHT);
			}
			int x = frame.getTabs().getSelectedIndex();
			if (x == 1) {
				frame.setSize(Constants.RESULT_VIEW_WIDTH, Constants.RESULT_VIEW_HEIGHT);
			} else if (x == 0) {
				frame.setSize(Constants.MAIN_VIEW_WIDTH, height);
			}
		}
	}

}
