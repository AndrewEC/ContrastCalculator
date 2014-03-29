package gov.intra.net.gui.dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;

public class DialogAction {

	private AbstractAction aa;
	private JComponent jc;

	@SuppressWarnings("serial")
	public DialogAction(final JDialog dialog) {
		aa = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton) e.getSource();
				String command = b.getActionCommand();
				if (command != null) {
					if (command.equals("close")) {
						dialog.setVisible(false);
					} else if (command.equals("focus")) {
						jc.requestFocus();
					}
				}
			}
		};
	}

	public void registerItem(JButton b) {
		finalizeRegister(b, KeyEvent.VK_D);
	}
	
	private void finalizeRegister(JButton b, int key){
		String name = b.getActionCommand();
		InputMap in = b.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		in.put(KeyStroke.getKeyStroke(key, InputEvent.SHIFT_DOWN_MASK), name);
		ActionMap am = b.getActionMap();
		am.put(name, aa);
	}
	
	public void registerForFocus(JButton b, JComponent jc){
		this.jc = jc;
		finalizeRegister(b, KeyEvent.VK_F);
	}

}
