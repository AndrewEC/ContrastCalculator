package gov.intra.net.gui.dialogs;

import gov.intra.net.util.Exporter;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class Details extends JDialog implements ActionListener {

	private JTextArea text;
	private JTextField fileName;
	private AbstractAction aa;

	public Details(JFrame frame) {
		super(frame);
		setVisible(false);
		setTitle("Details");
		setResizable(false);
		setSize(239, 492);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		aa = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				AbstractButton ab = (AbstractButton) e.getSource();
				String command = ab.getActionCommand();
				if (command != null) {
					processCommand(command);
				}
			}
		};

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
		registerCommand(btnClose, KeyEvent.VK_D);
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
		registerCommand(focusName, KeyEvent.VK_F);
		getContentPane().add(focusName);
	}

	private void registerCommand(AbstractButton button, int key) {
		button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key, InputEvent.SHIFT_DOWN_MASK), button.getActionCommand());
		button.getActionMap().put(button.getActionCommand(), aa);
	}

	public void append(String t) {
		text.append(t + "\n");
	}

	public void clear() {
		text.setText("");
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command != null) {
			processCommand(command);
		}
	}

	private void processCommand(String command) {
		if (command.equals("close")) {
			dispose();
		} else if (command.equals("save")) {
			String name = fileName.getText().trim();
			if (name.equals("")) {
				JOptionPane.showMessageDialog(this, "Please enter a file name.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Exporter.saveTextFile(fileName.getText() + ".txt", text.getText(), this);
		} else if (command.equals("focus name")) {
			fileName.requestFocus();
		} else if (command.equals("copy")) {
			StringSelection s = new StringSelection(text.getText());
			Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
			c.setContents(s, null);
		}
	}
}
