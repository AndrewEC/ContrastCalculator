package gov.intra.net.drop;

import gov.intra.net.util.Contraster;
import gov.intra.net.util.Shot;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
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

		Rectangle r = Shot.getMultiSize();
		setSize(Constants.DROPPER_MAG_SIZE, Constants.DROPPER_MAG_SIZE);

		dPanel = new DropperPanel(new Dimension(r.width, r.height), this, result);
		add(dPanel);
		addKeyListener(dPanel.getKey());

		BufferedImage image = new BufferedImage(5, 5, BufferedImage.TYPE_INT_ARGB);
		Point p = new Point(0, 0);
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(image, p, "");
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
			this.image = Contraster.convertImage(Shot.getMultiScreenShot(this), colour);
		}else{
			this.image = Shot.getMultiScreenShot(this);
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
