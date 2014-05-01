package gov.intra.net.gui;

import gov.intra.net.gui.dialogs.About;
import gov.intra.net.gui.dialogs.ColourPicker;
import gov.intra.net.gui.dialogs.Guide;
import gov.intra.net.gui.dialogs.Ratios;
import gov.intra.net.gui.dialogs.Shortcuts;
import gov.intra.net.util.Contraster;
import gov.intra.net.util.Exporter;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.Enumeration;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import resources.Constants.BlindColour;

public class FrameEventHandle implements ActionListener, ChangeListener, ItemListener {

	private final Frame frame;
	private File path;
	private JDialog dgAbout, dgShortcut, dgRatios, dgGuide;
	private JFrame colourPicker;
	private AbstractAction aa;

	@SuppressWarnings("serial")
	public FrameEventHandle(final Frame frame) {
		this.frame = frame;
		path = new File(System.getProperty("user.dir"));

		// initialize dialogs
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				dgAbout = new About(frame);
				dgShortcut = new Shortcuts(frame);
				dgRatios = new Ratios(frame);
				dgGuide = new Guide(frame);
				colourPicker = new ColourPicker(frame.getPanel());
			}
		});
		// initialize dialogs

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

	public String getBlindOption() {
		for (Enumeration<AbstractButton> buttons = frame.getBlindGroup().getElements(); buttons.hasMoreElements();) {
			AbstractButton b = buttons.nextElement();
			if (b.isSelected()) {
				return b.getText();
			}
		}
		return "";
	}

	public BlindColour getBlindColour() {
		String name = getBlindOption();
		for (BlindColour c : BlindColour.values()) {
			if (c.value.equals(name)) {
				return c;
			}
		}
		return BlindColour.NORMAL;
	}

	public void actionPerformed(ActionEvent e) {
		AbstractButton b = (AbstractButton) e.getSource();
		String command = b.getActionCommand();
		if (command != null && !command.equals("")) {
			processCommand(command);
		}
	}

	private void processCommand(String command) {
		if (command.equals("get focus")) {
			frame.getPanel().requestFocus();
		} else if (command.equals("menu about")) {
			openDialog(dgAbout);
		} else if (command.equals("menu exit")) {
			System.exit(0);
		} else if (command.equals("add result")) {
			addRow();
		} else if (command.equals("export location")) {
			File f = Exporter.getUserFile(frame);
			if (f != null) {
				path = f;
			}
		} else if (command.equals("export table")) {
			saveFile();
		} else if (command.equals("menu shortcuts")) {
			openDialog(dgShortcut);
		} else if (command.equals("menu guidelines")) {
			openDialog(dgRatios);
		} else if (command.equals("delete")) {
			deleteRow();
		} else if (command.equals("focus results")) {
			frame.getTabs().setSelectedIndex(1);
		} else if (command.equals("focus contraster")) {
			frame.getTabs().setSelectedIndex(0);
		} else if (command.equals("focus name")) {
			frame.getResultsName().requestFocus();
		} else if (command.equals("focus table")) {
			frame.getResultsTable().requestFocus();
		} else if (command.equals("export html")) {
			frame.getRbHtml().setSelected(true);
		} else if (command.equals("export text")) {
			frame.getRbText().setSelected(true);
		} else if (command.equals("switch on top")) {
			JCheckBox c = frame.getCbTop();
			c.setSelected(!c.isSelected());
		} else if (command.equals("switch window mag")) {
			JCheckBox b = frame.getCbEnableWindowMag();
			b.setSelected(!b.isSelected());
		} else if (command.equals("switch invert")) {
			JCheckBox b = frame.getInvertCheck();
			b.setSelected(!b.isSelected());
		} else if (command.equals("switch slider")) {
			JCheckBox b = frame.getCbShowSliders();
			b.setSelected(!b.isSelected());
		} else if (command.equals("menu guide")) {
			openDialog(dgGuide);
		} else if (command.equals("focus slider")) {
			frame.getPanel().getFrSlider().requestFocus();
		} else if (command.contains("set blind")) {
			String temp = command.split(" ")[2];
			setBlind(Integer.parseInt(temp));
		} else if (command.equals("toggle blind dropper")) {
			frame.getBlindPicker().setSelected(!frame.getBlindPicker().isSelected());
		} else if (command.equals("open colour picker")) {
			if (!colourPicker.isVisible()) {
				colourPicker.setVisible(true);
			}
		}
	}

	private void setBlind(int index) {
		ButtonGroup b = frame.getBlindGroup();
		Enumeration<AbstractButton> buttons = b.getElements();
		for (int i = 0; i < b.getButtonCount(); i++) {
			if (i == index) {
				buttons.nextElement().setSelected(true);
			} else {
				buttons.nextElement();
			}
		}
	}

	private void openDialog(JDialog dialog) {
		Point p = frame.getLocationOnScreen();
		dialog.setLocation(p);
		dialog.setVisible(true);
		dialog.requestFocus();
	}

	private String getExt() {
		if (frame.getRbText().isSelected()) {
			return ".txt";
		} else {
			return ".html";
		}
	}

	private void saveFile() {
		String name = frame.getExpName().getText().trim();
		if (!Exporter.checkName(name, frame)) {
			return;
		}

		File f = new File(path, name + getExt());

		if (!Exporter.checkOverwrite(f, frame)) {
			return;
		}

		if (frame.getRbText().isSelected()) {
			Exporter.exportAsText(frame.getResultsTable(), f, frame);
		} else if (frame.getRbHtml().isSelected()) {
			Exporter.exportAsHtml(frame.getResultsTable(), f, frame);
		}
	}

	private void addRow() {
		DefaultTableModel model = (DefaultTableModel) frame.getResultsTable().getModel();
		String name = frame.getResultsName().getText().trim();
		if (name.equals("")) {
			JOptionPane.showMessageDialog(frame, "Please enter a name", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		frame.getResultsName().setText("");

		Color fore = Contraster.getColour(frame.getPanel().getForeR().getText(), frame.getPanel().getForeG().getText(), frame.getPanel().getForeB().getText());
		Color back = Contraster.getColour(frame.getPanel().getBackR().getText(), frame.getPanel().getBackG().getText(), frame.getPanel().getBackB().getText());

		if (fore == null || back == null) {
			JOptionPane.showMessageDialog(frame, "Improper colour values.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		String foreHex = String.format("#%02x%02x%02x", fore.getRed(), fore.getGreen(), fore.getBlue());
		String backHex = String.format("#%02x%02x%02x", back.getRed(), back.getGreen(), back.getBlue());

		String ratio = frame.getPanel().getRatio().getText();
		String aa = frame.getPanel().getAASmall().getText().split(" ")[3];
		String aal = frame.getPanel().getAALarge().getText().split(" ")[3];
		String aaa = frame.getPanel().getAAASmall().getText().split(" ")[3];
		String aaal = frame.getPanel().getAAALarge().getText().split(" ")[3];
		model.addRow(new Object[] { name, foreHex, backHex, ratio, aa, aal, aaa, aaal });
	}

	private void deleteRow() {
		int row = frame.getResultsTable().getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(frame, "Select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		DefaultTableModel model = (DefaultTableModel) frame.getResultsTable().getModel();
		model.removeRow(row);
	}

	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == frame.getTabs()) {
			int height = 393;
			if (frame.getCbShowSliders() != null) {
				height = (frame.getCbShowSliders().isSelected() ? 485 : 393);
			}
			int x = frame.getTabs().getSelectedIndex();
			if (x == 1) {
				frame.setSize(700, 393);
			} else if (x == 0) {
				frame.setSize(413, height);
			}
		}
	}

	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == frame.getCbTop()) {
			frame.setAlwaysOnTop(frame.getCbTop().isSelected());
		} else if (e.getSource() == frame.getCbEnableWindowMag()) {
			frame.getPanel().getBtnMagnify().setEnabled(frame.getCbEnableWindowMag().isSelected());
		} else if (e.getSource() == frame.getCbShowSliders()) {
			Panel panel = frame.getPanel();
			if (frame.getCbShowSliders().isSelected()) {
				panel.getSliderPanel().setVisible(true);
				panel.getMainPanel().setLocation(panel.getMainPanel().getLocation().x, 182);
				frame.setSize(frame.getSize().width, 485);
			} else {
				panel.getSliderPanel().setVisible(false);
				panel.getMainPanel().setLocation(panel.getMainPanel().getLocation().x, 91);
				frame.setSize(frame.getSize().width, 393);
			}
		}
	}

	public void registerCommand(AbstractButton button, int key) {
		String command = button.getActionCommand();
		if (command.equals("get focus") || command.equals("focus slider")) {
			button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key, InputEvent.CTRL_DOWN_MASK), command);
		} else {
			button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key, InputEvent.SHIFT_DOWN_MASK), command);
		}
		button.getActionMap().put(command, aa);
	}

}
