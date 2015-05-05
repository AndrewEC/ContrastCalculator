package com.intra.net.frame;

import com.intra.net.panel.Panel;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import resources.Constants;

public class FrameItemListener implements ItemListener {

	private final Frame frame;

	public FrameItemListener(Frame frame) {
		this.frame = frame;
	}

	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == frame.getCbTop()) {
			frame.setAlwaysOnTop(frame.getCbTop().isSelected());
		} else if (e.getSource() == frame.getCbShowSliders()) {
			Panel panel = frame.getPanel();
			if (frame.getCbShowSliders().isSelected()) {
				panel.getSliderPanel().setVisible(true);
				panel.getMainPanel().setLocation(panel.getMainPanel().getLocation().x, 182);
				frame.setSize(frame.getSize().width, Constants.FRAME_WITH_SLIDERS_HEIGHT);
			} else {
				panel.getSliderPanel().setVisible(false);
				panel.getMainPanel().setLocation(panel.getMainPanel().getLocation().x, 91);
				frame.setSize(frame.getSize().width, Constants.FRAME_NO_SLIDERS_HEIGHT);
			}
		}
	}

}
