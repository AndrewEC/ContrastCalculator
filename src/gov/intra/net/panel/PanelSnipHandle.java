package gov.intra.net.panel;

import gov.intra.net.area.AreaSnipper;
import gov.intra.net.area.AreaSnipperResult;
import gov.intra.net.area.ISnipperListener;
import gov.intra.net.frame.Frame;
import gov.intra.net.persist.ImageWriter;
import gov.intra.net.util.Contraster;
import gov.intra.net.window.WindowMagnifier;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import resources.Constants;

public class PanelSnipHandle extends PanelEventBase implements ISnipperListener {

	private AreaSnipper areaSnipper;
	private AreaSnipperResult areaSnipperResult;
	private WindowMagnifier windowMagnifier;
	private final ISnipperListener thisHandle;
	private ImageWriter iw;

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
							areaSnipper.openMagnifier();
						}
					}
				});
			} else if (!areaSnipper.isVisible()) {
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
				windowMagnifier.setExtendedState(JFrame.NORMAL);
				windowMagnifier.setVisible(true);
				loadImage();
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
						loadImage();
					}
				}
			}
		});
	}

	private void loadImage() {
		iw = new ImageWriter();
		iw.setRememberPath(true);
		iw.setEnforceDirectory(false);
		iw.setParent(windowMagnifier);
		File file = iw.promptForFile();
		if (file != null && !file.isDirectory()) {
			try {
				BufferedImage image = ImageIO.read(file);
				BufferedImage con = Contraster.convertImage(image, windowMagnifier.getParentFrame().getBlindColour());
				windowMagnifier.getImagePanel().setImage(con);
			} catch (IOException e) {
				e.printStackTrace();
				String err = "An error occured while loading the image:\n" + e.getMessage();
				JOptionPane.showMessageDialog(windowMagnifier, err, "Error", JOptionPane.ERROR_MESSAGE);
			}

		}
	}

	private void processResult(Rectangle r) {
		areaSnipper.closeMagnifier();
		Point loc = panel.getLocationOnScreen();
		areaSnipperResult.setValues(r, frame.getBlindColour());
		areaSnipperResult.setLocation(loc);
		areaSnipperResult.setVisible(true);
	}

	public void onError(Exception e) {
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
				}
			});
		} else {
			processResult(r);
		}
	}

}
