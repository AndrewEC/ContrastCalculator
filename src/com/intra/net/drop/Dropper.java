package com.intra.net.drop;

import com.intra.net.util.Capturer;
import com.intra.net.util.Contraster;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import resources.Constants;
import resources.Constants.BlindColour;

@SuppressWarnings("serial")
public class Dropper extends JFrame {

	private DropperPanel dPanel;
	private boolean invertable = false;
	private BufferedImage image;

	public Dropper(IDropperResult result) {
		super("Colour Selector");
		setAlwaysOnTop(true);
		setUndecorated(true);
		setVisible(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		try {
			InputStream in = Dropper.class.getResourceAsStream("/resources/icon.png");
			BufferedImage image = ImageIO.read(in);
			setIconImage(image);
		} catch (IOException e) {
			e.printStackTrace();
		}

		setSize(Constants.DROPPER_MAG_SIZE, Constants.DROPPER_MAG_SIZE);

		dPanel = new DropperPanel(new Dimension(Constants.DROPPER_MAG_SIZE, Constants.DROPPER_MAG_SIZE), this, result);
		add(dPanel);
		addKeyListener(dPanel.getKey());

		BufferedImage image = new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB);
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0, 0), "");
		setCursor(cursor);
	}

	public boolean isInvertable() {
		return invertable;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void openDropper(boolean invertable, BlindColour colour, boolean blindMod) {
		if (blindMod) {
			this.image = Contraster.convertImage(Capturer.getMultiScreenShot(this), colour);
		} else {
			this.image = Capturer.getMultiScreenShot(this);
		}
		this.invertable = invertable;
		setVisible(true);
		dPanel.getTimer().start();
		System.gc();
	}

	public void closeDropper() {
		setVisible(false);
		dPanel.getTimer().stop();
		image = null;
		System.gc();
	}
}
