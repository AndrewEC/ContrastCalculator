package gov.intra.net.dialogs;

import gov.intra.net.frame.Frame;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Details extends GenericDialog implements IDialogHandle {

	private JTextArea text;

	public Details(Frame frame) {
		super(frame, "Details");
		setSize(239, 467);

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
		}
	}

	private void copy() {
		StringSelection s = new StringSelection(text.getText());
		Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
		c.setContents(s, null);
	}

}
