package gov.intra.net.panel;

import gov.intra.net.area.AreaMagnifier;
import gov.intra.net.area.AreaMagnifierResult;
import gov.intra.net.area.IAreaMagnifierResult;
import gov.intra.net.frame.Frame;
import gov.intra.net.window.WindowMagnifier;

import java.awt.Point;

import javax.swing.SwingUtilities;

public class PanelMagHandle extends PanelEventBase implements IAreaMagnifierResult {

	private AreaMagnifier areaMagnifier;
	private AreaMagnifierResult areaMagnifierResult;
	private WindowMagnifier windowMagnifier;

	public PanelMagHandle(final Frame frame, Panel panel) {
		super(frame, panel);
		
		final PanelMagHandle handle = this;
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				areaMagnifier = new AreaMagnifier(handle);
				areaMagnifierResult = new AreaMagnifierResult();
				windowMagnifier = new WindowMagnifier(frame);
			}
		});
	}

	public void onEvent(String command) {
		if (command.equals("magnify area")) {
			if (!areaMagnifier.isVisible()) {
				areaMagnifier.openMagnifier();
			}
		}else if (command.equals("magnify window")) {
			if (!windowMagnifier.isVisible()) {
				windowMagnifier.openMagnifier();
			}
		}
	}

	public void onPointObtained(Point p, int size) {
		areaMagnifier.closeMagnifier();
		Point loc = panel.getLocationOnScreen();
		areaMagnifierResult.setValues(p, size, frame.getEvent().getBlindColour());
		areaMagnifierResult.setLocation(loc);
		areaMagnifierResult.setVisible(true);
	}

	public void onError(Exception e) {
		areaMagnifier.closeMagnifier();
	}

}
