package gov.intra.net.gui;

import gov.intra.net.area.AreaMagnifier;
import gov.intra.net.area.AreaMagnifierResult;
import gov.intra.net.drop.Dropper;
import gov.intra.net.gui.dialogs.Details;
import gov.intra.net.util.Contraster;
import gov.intra.net.window.WindowMagnifier;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class PanelEventHandle extends AbstractAction implements ActionListener, ChangeListener {

	private Panel panel;
	private Timer timer;

	private String type = "";
	private Color back, fore;

	private Details details;

	private Dropper dropper;

	private WindowMagnifier windowMagnifier;

	private AreaMagnifier areaMagnifier;
	private AreaMagnifierResult areaMagnifierResult;

	private Frame frame;

	private AbstractAction aa;

	public PanelEventHandle(final Panel panel, final Frame frame) {
		this.frame = frame;
		this.panel = panel;
		timer = new Timer(20, this);
		timer.start();
		back = Color.black;
		fore = Color.white;

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				dropper = new Dropper();
				windowMagnifier = new WindowMagnifier(frame);
				areaMagnifier = new AreaMagnifier();
				areaMagnifierResult = new AreaMagnifierResult();

				details = new Details(frame);

				System.gc();
			}
		});

		aa = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				AbstractButton b = (AbstractButton) e.getSource();
				String command = b.getActionCommand();
				if (command != null) {
					processCommand(command);
				}
			}
		};
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() != null) {
			processCommand(e.getActionCommand());
		}

		if (e.getSource() == timer) {
			processDropper();
			processAreaMagnifier();
		}
	}

	private void processCommand(String command) {
		if (command.equals("select fore")) {
			type = "foreground";
			dropper.openDropper(frame.getInvert(), frame.getEvent().getBlindColour());
		} else if (command.equals("select back")) {
			type = "background";
			dropper.openDropper(frame.getInvert(), frame.getEvent().getBlindColour());
		} else if (command.equals("view details")) {
			startDetailsDialog();
		} else if (command.equals("magnify area")) {
			if (!areaMagnifier.isVisible()) {
				areaMagnifier.openMagnifier();
			}
		} else if (command.equals("swap")) {
			swapColours();
		} else if (command.equals("magnify window")) {
			if (!windowMagnifier.isVisible()) {
				windowMagnifier.openMagnifier();
			}
		} else if (command.equals("focus fore")) {
			panel.getForeHex().requestFocus();
		} else if (command.equals("focus back")) {
			panel.getBackHex().requestFocus();
		}
	}

	private void swapColours() {
		String fore = panel.getForeHex().getText();
		String back = panel.getBackHex().getText();
		if (!Contraster.isValidHex(fore) || !Contraster.isValidHex(back)) {
			JOptionPane.showMessageDialog(frame, "Could not swap colours due to invalid hex value.", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			setForeColour(Contraster.hexToColour(back), true);
			setBackColour(Contraster.hexToColour(fore), true);
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

	private void processDropper() {
		if (dropper.isVisible() && dropper.hasColour()) {
			dropper.closeDropper();
			if (type.equals("foreground")) {
				checkForFront();
			} else if (type.equals("background")) {
				checkForBack();
			}

			passFail();
		}
	}

	private void processAreaMagnifier() {
		if (areaMagnifier.hasCoord() && areaMagnifier.isVisible()) {
			areaMagnifier.closeMagnifier();
			Point p = areaMagnifier.getCoord();
			if (p != null) {
				int area = areaMagnifier.getAreaSize();
				Point loc = panel.getLocationOnScreen();
				areaMagnifierResult.setValues(p, area, frame.getEvent().getBlindColour());
				areaMagnifierResult.setLocation(loc);
				areaMagnifierResult.setVisible(true);
			}
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

	private void passFail() {
		if (back != null && fore != null) {
			Contraster con = new Contraster();
			con.setForeground(fore);
			con.setBackground(back);

			double val = con.calculateContrast();
			setRatio(val);
		}
	}

	private void checkForFront() {
		Color c = dropper.getColour();
		if (c != null) {
			fore = c;
			setForeColour(fore, true);
		}
	}

	private void checkForBack() {
		Color c = dropper.getColour();
		if (c != null) {
			back = c;
			setBackColour(back, true);
		}
	}

	public void setForeColour(Color c, boolean h) {
		panel.getForeR().setText("" + c.getRed());
		panel.getForeG().setText("" + c.getGreen());
		panel.getForeB().setText("" + c.getBlue());
		panel.getForeColourPreview().setBackground(c);
		if (h) {
			String hex = String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
			panel.getForeHex().setText(hex);
		}
		panel.getFrSlider().setValue(c.getRed());
		panel.getFgSlider().setValue(c.getGreen());
		panel.getFbSlider().setValue(c.getBlue());
		panel.getAASmall().setFontColour(c);
		panel.getAALarge().setFontColour(c);
		panel.getAAASmall().setFontColour(c);
		panel.getAAALarge().setFontColour(c);
		panel.repaint();
	}

	public void setBackColour(Color c, boolean h) {
		panel.getBackR().setText("" + c.getRed());
		panel.getBackG().setText("" + c.getGreen());
		panel.getBackB().setText("" + c.getBlue());
		panel.getBackColourPreview().setBackground(c);
		if (h) {
			String hex = String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
			panel.getBackHex().setText(hex);
		}
		panel.getBrSlider().setValue(c.getRed());
		panel.getBgSlider().setValue(c.getGreen());
		panel.getBbSlider().setValue(c.getBlue());
		panel.getAASmall().setBackgroundColour(c);
		panel.getAALarge().setBackgroundColour(c);
		panel.getAAASmall().setBackgroundColour(c);
		panel.getAAALarge().setBackgroundColour(c);
		panel.repaint();
	}

	public void setRatio(double val) {
		panel.getRatio().setText(String.format("%.2f:1", val));

		if (val >= 7) {
			panel.getAASmall().setText("AA Small - Pass");
			panel.getAALarge().setText("AA Large - Pass");
			panel.getAAASmall().setText("AAA Small - Pass");
			panel.getAAALarge().setText("AAA Large - Pass");
		} else if (val >= 4.5) {
			panel.getAASmall().setText("AA Small - Pass");
			panel.getAALarge().setText("AA Large - Pass");
			panel.getAAASmall().setText("AAA Small - Fail");
			panel.getAAALarge().setText("AAA Large - Pass");
		} else if (val >= 3) {
			panel.getAASmall().setText("AA Small - Fail");
			panel.getAALarge().setText("AA Large - Pass");
			panel.getAAASmall().setText("AAA Small - Fail");
			panel.getAAALarge().setText("AAA Large - Fail");
		} else {
			panel.getAASmall().setText("AA Small - Fail");
			panel.getAALarge().setText("AA Large - Fail");
			panel.getAAASmall().setText("AAA Small - Fail");
			panel.getAAALarge().setText("AAA Large - Fail");
		}
	}

	public void registerCommand(AbstractButton button, int key) {
		button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key, InputEvent.SHIFT_DOWN_MASK), button.getActionCommand());
		button.getActionMap().put(button.getActionCommand(), aa);
	}

	public Panel getPanel() {
		return panel;
	}

	public AbstractAction getAction() {
		return aa;
	}

	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == panel.getFrSlider() || e.getSource() == panel.getFgSlider() || e.getSource() == panel.getFbSlider()) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					Color c = new Color(panel.getFrSlider().getValue(), panel.getFgSlider().getValue(), panel.getFbSlider().getValue());
					setForeColour(c, true);
				}
			});
		} else if (e.getSource() == panel.getBrSlider() || e.getSource() == panel.getBgSlider() || e.getSource() == panel.getBbSlider()) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					Color c = new Color(panel.getBrSlider().getValue(), panel.getBgSlider().getValue(), panel.getBbSlider().getValue());
					setBackColour(c, true);
				}
			});
		}
	}

}
