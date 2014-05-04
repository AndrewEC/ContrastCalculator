package gov.intra.net.frame;

import gov.intra.net.dialogs.About;
import gov.intra.net.dialogs.ColourPicker;
import gov.intra.net.dialogs.Guide;
import gov.intra.net.dialogs.Ratios;
import gov.intra.net.dialogs.Shortcuts;

import java.awt.Component;
import java.awt.Point;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class DialogEventHandle extends FrameEventBase {

	private JDialog about, ratios, guide, shortcuts;
	private JFrame colourPicker;

	public DialogEventHandle(final Frame frame) {
		super(frame);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				about = new About(frame);
				ratios = new Ratios(frame);
				guide = new Guide(frame);
				shortcuts = new Shortcuts(frame);
				colourPicker = new ColourPicker(frame);
			}
		});
	}

	public void onEvent(String command) {
		if (command.equals("menu about")) {
			showDialog(about);
		} else if (command.equals("menu guide")) {
			showDialog(guide);
		} else if (command.equals("menu guidelines")) {
			showDialog(ratios);
		} else if (command.equals("menu shortcuts")) {
			showDialog(shortcuts);
		} else if (command.equals("open colour picker")) {
			showDialog(colourPicker);
		}
	}

	private void showDialog(Component dialog) {
		if (!dialog.isVisible()) {
			Point p = frame.getLocationOnScreen();
			dialog.setLocation(p);
			dialog.setVisible(true);
			dialog.requestFocus();
		}
	}

}
