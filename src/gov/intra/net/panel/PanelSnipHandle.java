package gov.intra.net.panel;

import gov.intra.net.area.AreaSnipper;
import gov.intra.net.area.AreaSnipperResult;
import gov.intra.net.area.ISnipperListener;
import gov.intra.net.frame.Frame;
import gov.intra.net.window.WindowMagnifier;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import resources.Constants;

public class PanelSnipHandle extends PanelEventBase implements ISnipperListener {

	private AreaSnipper areaSnipper;
	private AreaSnipperResult areaSnipperResult;
	private WindowMagnifier windowMagnifier;
	private final ISnipperListener thisHandle;

	public PanelSnipHandle(final Frame frame, Panel panel) {
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
							frame.setVisible(false);
							areaSnipper.openMagnifier();
						}
					}
				});
			} else if (!areaSnipper.isVisible()) {
				frame.setVisible(false);
				areaSnipper.openMagnifier();
			}
		} else if (command.equals("magnify window")) {
			if (windowMagnifier == null) {
				openMagnifier(false);
			} else if (!windowMagnifier.isVisible()) {
				windowMagnifier.setVisible(true);
			}
		} else if (command.equals("open image")) {
			if (windowMagnifier == null) {
				openMagnifier(true);
			} else if (windowMagnifier != null) {
				windowMagnifier.setVisible(true);
				JButton b = new JButton();
				b.setActionCommand("open image");
				ActionEvent e = new ActionEvent(b, 0, "");
				windowMagnifier.getEvent().actionPerformed(e);
			}
		}
	}

	private void openMagnifier(final boolean load) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				windowMagnifier = new WindowMagnifier(frame);
				if (!windowMagnifier.isVisible()) {
					windowMagnifier.setVisible(true);
					if (load) {
						JButton b = new JButton();
						b.setActionCommand("open image");
						ActionEvent e = new ActionEvent(b, 0, "");
						windowMagnifier.getEvent().actionPerformed(e);
					}
				}
			}
		});
	}

	private void processResult(Rectangle r) {
		areaSnipper.closeMagnifier();
		areaSnipperResult.setValues(r, frame.getBlindColour());
		areaSnipperResult.setVisible(true);
	}

	public void onError(Exception e) {
		if (!frame.isVisible()) {
			frame.setVisible(true);
		}
		if (!e.getMessage().equals(Constants.USER_CANCEL_MESSAGE)) {
			String mess = "An error occured: \n" + e.getMessage();
			System.err.println(mess);
			JOptionPane.showMessageDialog(panel, mess, "Error", JOptionPane.ERROR_MESSAGE);
		}
		areaSnipper.closeMagnifier();
	}

	public void onAreaSelected(final Rectangle r) {
		if (areaSnipperResult == null) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					areaSnipperResult = new AreaSnipperResult();
					processResult(r);
					if (!frame.isVisible()) {
						frame.setVisible(true);
					}
				}
			});
		} else {
			processResult(r);
			if (!frame.isVisible()) {
				frame.setVisible(true);
			}
		}
	}
}
