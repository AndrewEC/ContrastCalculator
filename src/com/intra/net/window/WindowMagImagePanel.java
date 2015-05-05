package com.intra.net.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class WindowMagImagePanel extends JPanel {

	private BufferedImage image;
	private double zoom = 2;

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setBackground(Color.black);
		g2.clearRect(0, 0, getSize().width, getSize().height);
		if (image != null) {
			g2.drawImage(image, 0, 0, (int) (image.getWidth() * zoom), (int) (image.getHeight() * zoom), this);
		}
		g.dispose();
	}

	public void setImage(BufferedImage image) {
		this.image = image;
		updateSize();
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
		updateSize();
	}

	public BufferedImage getImage() {
		return image;
	}

	private void updateSize() {
		if (image != null) {
			this.setPreferredSize(new Dimension((int) (image.getWidth() * zoom), (int) (image.getHeight() * zoom)));
			this.getParent().revalidate();
		}
		repaint();
	}

}
