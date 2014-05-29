package gov.intra.net.frame;

import java.awt.Color;
import java.io.File;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import gov.intra.net.util.Contraster;
import gov.intra.net.util.Exporter;

public class MenuButtonHandle extends FrameEventBase {

	private File path;

	public MenuButtonHandle(Frame frame) {
		super(frame);
	}

	public void onEvent(String command) {
		if (command.equals("menu exit")) {
			System.exit(0);
		} else if (command.equals("export location")) {
			File f = Exporter.getUserFile(frame);
			if (f != null) {
				path = f;
			}
		} else if (command.equals("export table")) {
			saveFile();
		} else if (command.equals("toggle blind dropper")) {
			frame.getBlindPicker().setSelected(!frame.getBlindPicker().isSelected());
		} else if (command.contains("set blind")) {
			String temp = command.split(" ")[2];
			setBlind(Integer.parseInt(temp));
		} else if (command.equals("switch invert")) {
			JCheckBox b = frame.getInvertCheck();
			b.setSelected(!b.isSelected());
		} else if (command.equals("switch slider")) {
			JCheckBox b = frame.getCbShowSliders();
			b.setSelected(!b.isSelected());
		} else if (command.equals("switch window mag")) {
			JCheckBox b = frame.getCbEnableWindowMag();
			b.setSelected(!b.isSelected());
		} else if (command.equals("switch on top")) {
			JCheckBox c = frame.getCbTop();
			c.setSelected(!c.isSelected());
		} else if (command.equals("export html")) {
			frame.getRbHtml().setSelected(true);
		} else if (command.equals("export text")) {
			frame.getRbText().setSelected(true);
		} else if (command.equals("delete")) {
			deleteRow();
		} else if (command.equals("add result")) {
			addRow();
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

	private String getExt() {
		if (frame.getRbText().isSelected()) {
			return ".txt";
		} else {
			return ".html";
		}
	}

	private void setBlind(int index) {
		ButtonGroup b = frame.getBlindGroup();
		Enumeration<AbstractButton> buttons = b.getElements();
		for (int i = 0; i < b.getButtonCount(); i++) {
			if (i == index) {
				buttons.nextElement().setSelected(true);
				break;
			} else {
				buttons.nextElement();
			}
		}
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

}
