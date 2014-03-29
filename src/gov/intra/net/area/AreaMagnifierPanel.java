package gov.intra.net.area;

import gov.intra.net.util.Key;
import gov.intra.net.util.Mouse;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import resources.Constants;

@SuppressWarnings("serial")
public class AreaMagnifierPanel extends JPanel implements ActionListener {

	private Timer timer;
	private Mouse mouse;
	private Key key;
	private int x, y, size;
	private AreaMagnifier mag;
	private AlphaComposite normal, alpha;

	public AreaMagnifierPanel(AreaMagnifier mag) {
		this.mag = mag;

		size = 100;

		mouse = new Mouse();
		addMouseMotionListener(mouse);
		addMouseListener(mouse);
		addMouseWheelListener(mouse);

		key = new Key();
		addKeyListener(key);

		normal = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
		alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);

		timer = new Timer(30, this);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setBackground(new Color(0, 0, 0, 0));
		g2.setColor(Color.black);
		g2.clearRect(0, 0, getSize().width, getSize().height);
		g2.setStroke(new BasicStroke(5));
		g2.setComposite(alpha);
		g2.fillRect(0, 0, size, size);
		g2.setComposite(normal);
		g2.drawRect(0, 0, size, size);
		g.dispose();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer) {
			Point p = MouseInfo.getPointerInfo().getLocation();
			x = p.x;
			y = p.y;
			mag.setLocation(x - size / 2, y - size / 2);
			if (mouse.isLeft() && isVisible()) {
				mag.setCoord(new Point(x, y));
			}
			repaint();

			int notches = mouse.getNotches();

			if (notches != 0) {
				if (notches < 0 && size > Constants.MIN_AREA_MAG_SIZE) {
					size += notches * 10;
				} else if (notches > 0 && size < Constants.MAX_AREA_MAG_SIZE) {
					size += notches * 10;
				}
				mag.setSize(size, size);
			}

			if (mouse.isRight() || key.isQuit()) {
				mag.setCoord(null);
			}
		}
	}

	public Timer getTimer() {
		return timer;
	}

	public Key getKey() {
		return key;
	}
	
	public int getAreaSize(){
		return size;
	}

}
