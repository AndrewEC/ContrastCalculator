package com.intra.net.panel;

import java.awt.Color;

import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PanelChangeHandle implements ChangeListener {

	private final Panel panel;

	public PanelChangeHandle(Panel panel) {
		this.panel = panel;
	}

	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == panel.getFrSlider() || e.getSource() == panel.getFgSlider() || e.getSource() == panel.getFbSlider()) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					Color c = new Color(panel.getFrSlider().getValue(), panel.getFgSlider().getValue(), panel.getFbSlider().getValue());
					PanelUtil.setForeColour(panel, c, true);
				}
			});
		} else if (e.getSource() == panel.getBrSlider() || e.getSource() == panel.getBgSlider() || e.getSource() == panel.getBbSlider()) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					Color c = new Color(panel.getBrSlider().getValue(), panel.getBgSlider().getValue(), panel.getBbSlider().getValue());
					PanelUtil.setBackColour(panel, c, true);
				}
			});
		}
	}

}
