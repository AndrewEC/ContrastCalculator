package gov.intra.net.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Key implements KeyListener {

	private boolean quit = false;

	public Key() {
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 27) {
			quit = true;
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public boolean isQuit() {
		if (quit) {
			quit = false;
			return true;
		}
		return false;
	}

}
