package com.intra.net.util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener {

	private int x, y;
	private boolean left, right;

	public void mouseDragged(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		if (e.getModifiers() == 4) {
			right = true;
		} else {
			left = true;
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getModifiers() == 4) {
			right = false;
		} else {
			left = false;
		}
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isLeft() {
		if (left) {
			left = false;
			return true;
		}
		return false;
	}

	public boolean isRight() {
		if (right) {
			right = false;
			return true;
		}
		return false;
	}

}
