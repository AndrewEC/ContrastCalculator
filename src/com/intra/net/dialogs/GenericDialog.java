package com.intra.net.dialogs;

import com.intra.net.frame.Frame;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public abstract class GenericDialog extends JDialog implements ActionListener {

	protected AbstractAction aa;
	protected JComponent jc;
	protected IDialogHandle callback;

	public GenericDialog(Frame parent, String title) {
		super(parent, title);

		setVisible(false);
		setResizable(false);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		aa = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton) e.getSource();
				String command = b.getActionCommand();
				processAction(command);
			}
		};
	}

	public void processAction(String command) {
		if (command == null) {
			System.err.println("GenericDialog command was null.");
			return;
		}
		command = command.trim();
		if (command.equals("")) {
			System.err.println("GenericDialog command was an empty string.");
			return;
		}
		if (command.equals("close")) {
			setVisible(false);
		} else if (command.equals("focus") && jc != null) {
			jc.requestFocus();
		} else if (callback != null) {
			callback.onAction(command);
		}
	}

	public JButton buildCloseButton(Rectangle bounds) {
		JButton b = new JButton("Close");
		try {
			b.setIcon(new ImageIcon(GenericDialog.class.getResource("/resources/CloseIcon.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		b.setBounds(bounds);
		b.setActionCommand("close");
		b.setToolTipText("Close dialog");
		registerForClose(b);
		b.addActionListener(this);
		getContentPane().add(b);
		return b;
	}

	public void registerCallback(IDialogHandle callback) {
		this.callback = callback;
	}

	public void actionPerformed(ActionEvent e) {
		processAction(e.getActionCommand());
	}

	protected void registerCommand(AbstractButton button, int key) {
		button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK), button.getActionCommand());
		button.getActionMap().put(button.getActionCommand(), aa);
	}

	public void registerForClose(JButton b) {
		finalizeRegister(b, KeyEvent.VK_D);
	}

	public void registerForFocus(JButton b, JComponent jc) {
		this.jc = jc;
		finalizeRegister(b, KeyEvent.VK_F);
	}

	private void finalizeRegister(JButton b, int key) {
		String name = b.getActionCommand();
		InputMap in = b.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		in.put(KeyStroke.getKeyStroke(key, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK), name);
		ActionMap am = b.getActionMap();
		am.put(name, aa);
	}

}
