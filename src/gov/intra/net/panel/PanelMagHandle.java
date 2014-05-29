package gov.intra.net.panel;

import gov.intra.net.area.AreaSnipper;
import gov.intra.net.area.AreaSnipperResult;
import gov.intra.net.area.IAreaSnipperResult;
import gov.intra.net.frame.Frame;
import gov.intra.net.window.WindowMagnifier;

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.SwingUtilities;

public class PanelMagHandle extends PanelEventBase implements IAreaSnipperResult {

	private AreaSnipper areaSnipper;
	private AreaSnipperResult areaSnipperResult;
	private WindowMagnifier windowMagnifier;
	private final IAreaSnipperResult thisHandle;

	public PanelMagHandle(final Frame frame, Panel panel) {
		super(frame, panel);

		thisHandle = this;
	}

	public void onEvent(String command) {
		if (command.equals("snip area")) {
			if (areaSnipper == null) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						areaSnipper = new AreaSnipper(thisHandle);
						if (!areaSnipper.isVisible()) {
							areaSnipper.openMagnifier();
						}
					}
				});
			} else if (!areaSnipper.isVisible()) {
				areaSnipper.openMagnifier();
			}
		} else if (command.equals("magnify window")) {
			if (windowMagnifier == null) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						windowMagnifier = new WindowMagnifier(frame);
						if (!windowMagnifier.isVisible()) {
							windowMagnifier.openMagnifier();
						}
					}
				});
			} else if (!windowMagnifier.isVisible()) {
				windowMagnifier.openMagnifier();
			}
		}
	}

	private void processResult(Rectangle r) {
		areaSnipper.closeMagnifier();
		Point loc = panel.getLocationOnScreen();
		areaSnipperResult.setValues(r, frame.getEvent().getBlindColour());
		areaSnipperResult.setLocation(loc);
		areaSnipperResult.setVisible(true);
	}

	public void onError(Exception e) {
		areaSnipper.closeMagnifier();
	}

	public void onAreaSelected(final Rectangle r) {
		if (areaSnipperResult == null) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					areaSnipperResult = new AreaSnipperResult();
					processResult(r);
				}
			});
		} else {
			processResult(r);
		}
	}

}
