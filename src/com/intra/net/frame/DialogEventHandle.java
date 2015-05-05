package com.intra.net.frame;

import com.intra.net.dialogs.Guide;
import com.intra.net.dialogs.About;
import com.intra.net.dialogs.ColourPicker;
import com.intra.net.dialogs.Guidelines;
import com.intra.net.dialogs.Shortcuts;

import java.awt.Component;
import java.awt.Point;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;

public class DialogEventHandle extends FrameEventBase {

	private JDialog about, ratios, guide, shortcuts, colourPicker;

	public DialogEventHandle(final Frame frame) {
		super(frame);
	}

	public void onEvent(String command) {
		if (command.equals("menu about")) {
			if (about == null) {
				init(command);
				return;
			}
			showDialog(about);
		} else if (command.equals("menu guide")) {
			if (guide == null) {
				init(command);
				return;
			}
			showDialog(guide);
		} else if (command.equals("menu guidelines")) {
			if (ratios == null) {
				init(command);
				return;
			}
			showDialog(ratios);
		} else if (command.equals("menu shortcuts")) {
			if (shortcuts == null) {
				init(command);
				return;
			}
			showDialog(shortcuts);
		} else if (command.equals("open colour picker")) {
			if (colourPicker == null) {
				init(command);
				return;
			}
			showDialog(colourPicker);
		} else if (command.equals("switch on top")) {
			if (colourPicker != null) {
				colourPicker.setAlwaysOnTop(frame.isAlwaysOnTop());
			}
		}
	}

	private void init(final String command) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (command.equals("menu about")) {
					about = new About(frame);
					showDialog(about);
				} else if (command.equals("menu guide")) {
					guide = new Guide(frame);
					showDialog(guide);
				} else if (command.equals("menu guidelines")) {
					ratios = new Guidelines(frame);
					showDialog(ratios);
				} else if (command.equals("menu shortcuts")) {
					shortcuts = new Shortcuts(frame);
					showDialog(shortcuts);
				} else if (command.equals("open colour picker")) {
					colourPicker = new ColourPicker(frame);
					colourPicker.setAlwaysOnTop(frame.isAlwaysOnTop());
					showDialog(colourPicker);
				}
			}
		});
	}

	private void showDialog(Component dialog) {
		if (dialog != null && !dialog.isVisible()) {
			Point p = frame.getLocationOnScreen();
			dialog.setLocation(p);
			dialog.setVisible(true);
			dialog.requestFocus();
		}
	}

}
