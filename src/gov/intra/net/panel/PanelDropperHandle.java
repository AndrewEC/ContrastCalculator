package gov.intra.net.panel;

import gov.intra.net.dialogs.Details;
import gov.intra.net.drop.Dropper;
import gov.intra.net.drop.IDropperResult;
import gov.intra.net.frame.Frame;
import gov.intra.net.util.Contraster;
import gov.intra.net.util.HexValidator;

import java.awt.Color;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import resources.Constants;
import resources.Constants.ColourLayer;

public class PanelDropperHandle extends PanelEventBase implements IDropperResult {

	private ColourLayer layer;
	private Dropper dropper;
	private Details details;
	private Color fore, back;

	public PanelDropperHandle(final Frame frame, Panel panel) {
		super(frame, panel);

		final PanelDropperHandle handle = this;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				dropper = new Dropper(handle);
				details = new Details(frame);
			}
		});
	}

	public void onEvent(String command) {
		if (command.equals("select fore")) {
			layer = ColourLayer.FOREGROUND;
			dropper.openDropper(frame.getInvert(), frame.getEvent().getBlindColour(), frame.getBlindPicker().isSelected());
		} else if (command.equals("select back")) {
			layer = ColourLayer.BACKGROUND;
			dropper.openDropper(frame.getInvert(), frame.getEvent().getBlindColour(), frame.getBlindPicker().isSelected());
		} else if (command.equals("swap")) {
			swapColours();
		} else if (command.equals("focus fore")) {
			panel.getForeHex().requestFocus();
		} else if (command.equals("focus back")) {
			panel.getBackHex().requestFocus();
		} else if (command.equals("view details")) {
			startDetailsDialog();
		}
	}

	private void startDetailsDialog() {
		if (!details.isVisible()) {
			details.clear();
			appendText();
			details.setLocation(panel.getParent().getLocationOnScreen().x, panel.getParent().getLocationOnScreen().y);
			details.setVisible(true);
			details.requestFocus();
		}
	}

	private void appendText() {
		details.append("Comparing colours:");
		details.append("\tForeground: ");
		details.append("\t\tHex: " + panel.getForeHex().getText());
		details.append("\t\tRGB: " + String.format("(%s, %s, %s)", panel.getForeR().getText(), panel.getForeG().getText(), panel.getForeB().getText()));
		details.append("\tBackground:");
		details.append("\t\tHex: " + panel.getBackHex().getText());
		details.append("\t\tRGB: " + String.format("(%s, %s, %s)", panel.getBackR().getText(), panel.getBackG().getText(), panel.getBackB().getText()));
		details.append("\nContrast Ratio: " + panel.getRatio().getText());
		details.append("\nSuccessful Criteria:");

		double ratio = Double.parseDouble(panel.getRatio().getText().split(":")[0]);

		double[] vals = { 4.5, 3.0, 7.0, 4.5 };

		for (int i = 0; i < vals.length; i++) {
			String text = "";
			switch (i) {
			case 0:
				text = panel.getAASmall().getText();
				break;
			case 1:
				text = panel.getAALarge().getText();
				break;
			case 2:
				text = panel.getAAASmall().getText();
				break;
			case 3:
				text = panel.getAAALarge().getText();
				break;
			}

			details.append("\t" + text);
			details.append(String.format("\t\tRequires: %.1f:1", vals[i]));
			if (text.contains("Fail")) {
				details.append("\t\tDifference: " + String.format("-%.2f", (vals[i] - ratio)));
			} else if (text.contains("Pass")) {
				details.append("\t\tDifference: " + String.format("+%.2f", (ratio - vals[i])));
			}
		}
	}

	private void swapColours() {
		String fore = panel.getForeHex().getText();
		String back = panel.getBackHex().getText();
		if (!HexValidator.isValid6Hex(fore) || !HexValidator.isValid6Hex(back)) {
			JOptionPane.showMessageDialog(frame, "Could not swap colours due to invalid hex value.", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			PanelUtil.setForeColour(panel, HexValidator.hexToColour(back), true);
			PanelUtil.setBackColour(panel, HexValidator.hexToColour(fore), true);
		}
	}

	public void onColourObtained(Color c) {
		dropper.closeDropper();
		if (layer == ColourLayer.FOREGROUND) {
			fore = c;
			PanelUtil.setForeColour(panel, fore, true);
		} else if (layer == ColourLayer.BACKGROUND) {
			back = c;
			PanelUtil.setBackColour(panel, back, true);
		}
		passFail();
	}

	public void onError(Exception e) {
		if (!e.getMessage().equals(Constants.IDROPPER_USER_CANCEL_MESSAGE)) {
			JOptionPane.showMessageDialog(dropper, "An error occured while grabbing pixel:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		dropper.closeDropper();
	}

	private void passFail() {
		if (back != null && fore != null) {
			Contraster con = new Contraster();
			con.setForeground(fore);
			con.setBackground(back);

			double val = con.calculateContrast();
			PanelUtil.setRatio(panel, val);
		}
	}

}
