package gov.intra.net.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import resources.Constants.BlindColour;

public class EventDispatcher implements ActionListener {

	private List<EventBase> events;
	private AbstractAction aa;
	private final Frame frame;

	@SuppressWarnings("serial")
	public EventDispatcher(Frame frame) {
		this.frame = frame;
		events = new ArrayList<EventBase>();
		aa = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				invokeEvent(e);
			}
		};
	}

	public void addEvent(EventBase e) {
		if (e != null && !events.contains(e)) {
			events.add(e);
		}
	}

	protected void invokeEvent(ActionEvent e) {
		AbstractButton b = (AbstractButton) e.getSource();
		String command = b.getActionCommand();
		if (command == null) {
			return;
		}
		command = command.trim();
		if (command.equals("")) {
			return;
		}
		for (EventBase event : events) {
			event.onEvent(command);
		}
	}

	public void actionPerformed(ActionEvent e) {
		invokeEvent(e);
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
