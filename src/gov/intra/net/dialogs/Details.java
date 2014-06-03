package gov.intra.net.dialogs;

import gov.intra.net.frame.Frame;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Details extends GenericDialog implements IDialogHandle {

	private JTextArea text;

	public Details(Frame frame) {
		super(frame, "Details");
		setSize(239, 453);

		registerCallback(this);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 1, 233, 371);
		getContentPane().add(scrollPane);

		text = new JTextArea();
		text.setEditable(false);
		text.setTabSize(4);
		scrollPane.setViewportView(text);

		JButton btnCopy = new JButton("");
		try {
			btnCopy.setIcon(new ImageIcon(Details.class.getResource("/resources/CopyIcon.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		btnCopy.setBounds(137, 377, 40, 40);
		btnCopy.addActionListener(this);
		btnCopy.setActionCommand("copy");
		registerCommand(btnCopy, KeyEvent.VK_W);
		getContentPane().add(btnCopy);

		JButton btnClose = new JButton("");
		try {
			btnClose.setIcon(new ImageIcon(Details.class.getResource("/resources/CloseIcon.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		btnClose.addActionListener(this);
		btnClose.setBounds(182, 377, 40, 40);
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
