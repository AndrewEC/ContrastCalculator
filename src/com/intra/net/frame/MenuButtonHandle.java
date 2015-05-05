package com.intra.net.frame;

import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;

public class MenuButtonHandle extends FrameEventBase {

	public MenuButtonHandle(Frame frame) {
		super(frame);
	}

	public void onEvent(String command) {
		if (command.equals("menu exit")) {
			System.exit(0);
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
		} else if (command.equals("switch on top")) {
			JCheckBox c = frame.getCbTop();
			c.setSelected(!c.isSelected());
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

}
