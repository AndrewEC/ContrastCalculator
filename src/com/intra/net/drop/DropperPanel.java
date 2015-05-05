package com.intra.net.drop;

import com.intra.net.util.Capturer;
import com.intra.net.util.Contraster;
import com.intra.net.util.Key;
import com.intra.net.util.Mouse;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Timer;

import resources.Constants;

@SuppressWarnings("serial")
public class DropperPanel extends JPanel implements ActionListener {

	private BufferedImage mag;
	private int width, height, x, y;
	private Timer timer;
	private Key key;
	private Mouse mouse;
	private Dropper dropper;
	private IDropperResult result;

	public DropperPanel(Dimension size, Dropper dropper, IDropperResult result) {
		this.dropper = dropper;
		width = size.width;
		height = size.height;
		
		this.result = result;

		setBounds(0, 0, size.width, size.height);

		mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);

		key = new Key();
		addKeyListener(key);

		timer = new Timer(20, this);
	}

	public Key getKey() {
		return key;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		if (dropper.isInvertable()) {
			Color c = Contraster.invert(Capturer.extractColour(dropper.getImage(), x, y));
			g2.setColor(c);
		} else {
			g2.setColor(Color.black);
		}
		g2.setBackground(new Color(0, 0, 0, 0));
		g2.clearRect(0, 0, width, height);
		if (dropper.getImage() != null) {
			if (mag != null) {
				g2.drawImage(mag, 0, 0, Constants.DROPPER_MAG_SIZE, Constants.DROPPER_MAG_SIZE, this);

				g2.drawRect(Constants.DROPPER_CENTER_MOD, Constants.DROPPER_CENTER_MOD, 7, 7);

				g2.drawLine(96 + 7, 96 + 7, 96 + 28, 96 + 28);
				g2.drawLine(96, 96, 96 - 21, 96 - 21);
				g2.drawLine(96, 96 + 7, 96 - 21, 96 + 28);
				g2.drawLine(96 + 7, 96, 96 + 28, 96 - 21);
			}
		}
		g.dispose();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer) {
			updateMag();
			repaint();
			if (key.isQuit() || mouse.isRight()) {
				result.onError(new Exception(Constants.USER_CANCEL_MESSAGE));
			}
		}
	}

	private void updateMag() {
		if (dropper.getImage() != null) {
			Point p = MouseInfo.getPointerInfo().getLocation();
			int mx = p.x;
			int my = p.y;
			if (mx != x || my != y) {
				x = mx;
				y = my;
				final int centerMod = Constants.DROPPER_CENTER_MOD;
				dropper.setLocation(x - centerMod, y - centerMod);
				try {
					final int grabSize = Constants.DROPPER_GRAB_SIZE;
					mag = dropper.getImage().getSubimage(x - grabSize / 2, y - grabSize / 2, grabSize, grabSize);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if (mouse.isLeft()) {
				Color c = Capturer.extractColour(dropper.getImage(), x, y);
				result.onColourObtained(c);
			}
		}
	}

	public Timer getTimer() {
		return timer;
	}

}
