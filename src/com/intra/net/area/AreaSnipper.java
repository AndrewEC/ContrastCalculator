package com.intra.net.area;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import resources.Constants;

@SuppressWarnings("serial")
public class AreaSnipper extends JFrame {

	private AreaSnipperPanel panel;

	public AreaSnipper(ISnipperListener result) {
		super("Area Snipper");
		try {
			InputStream in = AreaSnipper.class.getResourceAsStream("/resources/icon.png");
			BufferedImage image = ImageIO.read(in);
			setIconImage(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setAlwaysOnTop(true);
		setDefaultLookAndFeelDecorated(false);
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));

		final int size = Constants.AREA_SNIPPER_SIZE;
		setBounds(0, 0, size, size);

		panel = new AreaSnipperPanel(this, result);
		add(panel);

		addKeyListener(panel.getKey());
	}

	public void openMagnifier() {
		panel.getTimer().start();
		final int size = Constants.AREA_SNIPPER_SIZE;
		setBounds(0, 0, size, size);
		setVisible(true);
	}

	public void closeMagnifier() {
		setVisible(false);
		panel.getTimer().stop();
	}

}
