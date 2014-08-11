package gov.intra.net.frame;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class EventDispatcher implements ActionListener {

	private List<EventHandle> events;
	private AbstractAction aa;
	private Frame frame;

	public EventDispatcher(Frame frame) {
		this.frame = frame;
		init();
	}

	public EventDispatcher() {
		init();
	}

	@SuppressWarnings("serial")
	private void init() {
		events = new ArrayList<EventHandle>();
		aa = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				invokeEvent(e);
			}
		};
	}

	public void addEventHandle(EventHandle e) {
		if (e != null && !events.contains(e)) {
			events.add(e);
		}
	}

	protected void invokeEvent(ActionEvent e) {
		String command = "";
		if (e.getSource() instanceof MenuItem) {
			if (frame != null && !frame.isVisible()) {
				frame.setVisible(true);
			}
			MenuItem item = (MenuItem) e.getSource();
			command = item.getActionCommand();
		} else {
			AbstractButton b = (AbstractButton) e.getSource();
			command = b.getActionCommand();
		}
		if (command == null) {
			System.err.println("EventDispatcher command passed was a null value.");
			return;
		}
		command = command.trim();
		if (command.equals("")) {
			System.err.println("EventDispatcher command passed was an empty string.");
			return;
		}
		for (EventHandle event : events) {
			event.onEvent(command);
		}
	}

	public void actionPerformed(ActionEvent e) {
		invokeEvent(e);
	}

	public void registerCommand(AbstractButton button, int key) {
		String command = button.getActionCommand();
		button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK), command);
		button.getActionMap().put(command, aa);
	}

}
