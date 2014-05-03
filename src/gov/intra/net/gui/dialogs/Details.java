package gov.intra.net.gui.dialogs;

import gov.intra.net.gui.Frame;
import gov.intra.net.util.Exporter;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Details extends GenericDialog implements IEventCallback {

	private JTextArea text;
	private JTextField fileName;

	public Details(Frame frame) {
		super(frame, "Details");
		setSize(239, 492);

		registerCallback(this);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 1, 233, 371);
		getContentPane().add(scrollPane);

		text = new JTextArea();
		text.setEditable(false);
		text.setTabSize(4);
		scrollPane.setViewportView(text);

		JButton btnCopy = new JButton("Copy to Clipboard");
		btnCopy.setBounds(38, 376, 170, 25);
		btnCopy.addActionListener(this);
		btnCopy.setActionCommand("copy");
		registerCommand(btnCopy, KeyEvent.VK_W);
		getContentPane().add(btnCopy);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(this);
		btnClose.setBounds(139, 407, 85, 25);
		btnClose.setActionCommand("close");
		registerForClose(btnClose);
		getContentPane().add(btnClose);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(this);
		btnSave.setBounds(139, 434, 85, 25);
		btnSave.setActionCommand("save");
		registerCommand(btnSave, KeyEvent.VK_S);
		getContentPane().add(btnSave);

		JLabel lblSaveName = new JLabel("Save Name:");
		lblSaveName.setBounds(6, 413, 91, 14);
		getContentPane().add(lblSaveName);

		fileName = new JTextField();
		fileName.setBounds(23, 433, 110, 25);
		getContentPane().add(fileName);
		fileName.setColumns(10);

		JButton focusName = new JButton("");
		focusName.setBounds(-100, -100, 1, 1);
		focusName.setActionCommand("focus name");
		registerForFocus(focusName, fileName);
		getContentPane().add(focusName);
	}

	public void append(String t) {
		text.append(t + "\n");
	}

	public void clear() {
		text.setText("");
	}

	public void onAction(String command) {
		if (command.equals("")) {
			return;
		}
		if (command.equals("copy")) {
			copy();
		} else if (command.equals("save")) {
			save();
		}
	}

	private void copy() {
		StringSelection s = new StringSelection(text.getText());
		Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
		c.setContents(s, null);
	}

	private void save() {
		String name = fileName.getText().trim();
		if (name.equals("")) {
			JOptionPane.showMessageDialog(this, "Please enter a file name.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		Exporter.saveTextFile(fileName.getText() + ".txt", text.getText(), this);
	}
}
