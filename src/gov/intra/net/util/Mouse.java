package gov.intra.net.util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

	private int x, y;
	private boolean left, right;
	private int notches = 0;

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

	public void mouseWheelMoved(MouseWheelEvent e) {
		notches = e.getWheelRotation();
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

	public int getNotches() {
		int x = notches;
		notches = 0;
		return x;
	}

}
