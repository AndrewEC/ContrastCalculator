package gov.intra.net.area;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class AreaMagnifier extends JFrame {

	private AreaMagnifierPanel panel;
	private boolean has = false;
	private Point coord;

	public AreaMagnifier() {
		super("Area Magnifier");
		try {
			InputStream in = AreaMagnifier.class.getResourceAsStream("/resources/icon.png");
			BufferedImage image = ImageIO.read(in);
			setIconImage(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setAlwaysOnTop(true);
		setDefaultLookAndFeelDecorated(false);
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));
		setBounds(0, 0, 100, 100);

		panel = new AreaMagnifierPanel(this);
		add(panel);

		addKeyListener(panel.getKey());
	}

	public void setCoord(Point coord) {
		this.coord = coord;
		has = true;
	}

	public boolean hasCoord() {
		return has;
	}

	public Point getCoord() {
		has = false;
		return coord;
	}
	
	public void openMagnifier(){
		panel.getTimer().start();
		setVisible(true);
	}
	
	public void closeMagnifier(){
		setVisible(false);
		panel.getTimer().stop();
	}
	
	public int getAreaSize(){
		return panel.getAreaSize();
	}

}
