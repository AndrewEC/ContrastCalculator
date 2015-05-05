package com.intra.net.util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Key implements KeyListener {

	private boolean quit = false;
	private boolean left, right, up, down;

	public Key() {
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 27) {
			quit = true;
		}
		
		if (e.getKeyChar() == 'a') {
			left = true;
		} else if (e.getKeyChar() == 'd') {
			right = true;
		}

		if (e.getKeyChar() == 's') {
			down = true;
		} else if (e.getKeyChar() == 'w') {
			up = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyChar() == 'a') {
			left = false;
		} else if (e.getKeyChar() == 'd') {
			right = false;
		}

		if (e.getKeyChar() == 's') {
			down = false;
		} else if (e.getKeyChar() == 'w') {
			up = false;
		}
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

	public boolean isRight() {
		return right;
	}

	public boolean isLeft() {
		return left;
	}

	public boolean isDown() {
		return down;
	}

	public boolean isUp() {
		return up;
	}

}
