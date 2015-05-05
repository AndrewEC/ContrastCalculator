package com.intra.net.util;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JOptionPane;

import com.sun.jna.platform.win32.WinDef.RECT;

public class Capturer {

	public static BufferedImage getScreenShot(Component parent) {
		BufferedImage image = null;
		Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		try {
			image = new Robot().createScreenCapture(screen);
		} catch (AWTException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "Could not grab image of screen.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return image;
	}

	public static Rectangle getMultiSize() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] screens = ge.getScreenDevices();
		Rectangle bounds = new Rectangle(0, 0, 0, 0);
		for (GraphicsDevice screen : screens) {
			Rectangle b = screen.getDefaultConfiguration().getBounds();
			bounds.width += b.width;
			bounds.height = Math.max(b.height, bounds.height);
		}
		return bounds;
	}

	public static BufferedImage getScreenShot(int x, int y, int width, int height, Component parent) {
		BufferedImage image = null;
		Rectangle rect = new Rectangle(x, y, width, height);
		try {
			image = new Robot().createScreenCapture(rect);
		} catch (AWTException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "Could not grab image of screen.\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		return image;
	}

	public static BufferedImage getScreenShot(RECT r, Component parent) {
		int width = r.right - r.left;
		int height = r.bottom - r.top;
		return getScreenShot(r.left, r.top, width, height, parent);
	}

	public static BufferedImage getMultiScreenShot(Component parent) {
		BufferedImage image = null;
		try {
			image = new Robot().createScreenCapture(getMultiSize());
		} catch (AWTException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "Could not grab image of screen.\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		return image;
	}

	public static Color extractColour(BufferedImage image, int x, int y) {
		Color c = null;
		int col = image.getRGB(x, y);
		int red = (col & 0x00FF0000) >> 16;
		int green = (col & 0x0000FF00) >> 8;
		int blue = (col & 0x000000FF);
		c = new Color(red, green, blue);
		return c;
	}

}
