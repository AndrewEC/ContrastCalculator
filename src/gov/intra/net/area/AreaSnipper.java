package gov.intra.net.area;

import gov.intra.net.util.Shot;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class AreaSnipper extends JFrame {

	private AreaSnipperPanel panel;

	public AreaSnipper(IAreaSnipperResult result) {
		super("Area Magnifier");
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

		Rectangle r = Shot.getMultiSize();
		setBounds(0, 0, r.width, r.height);

		panel = new AreaSnipperPanel(this, result, r.width, r.height);
		add(panel);

		addKeyListener(panel.getKey());
	}

	public void openMagnifier() {
		panel.getTimer().start();
		setVisible(true);
	}

	public void closeMagnifier() {
		setVisible(false);
		panel.getTimer().stop();
	}

}
