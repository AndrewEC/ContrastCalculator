package gov.intra.net.area;

import gov.intra.net.util.Key;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Timer;

import resources.Constants;

@SuppressWarnings("serial")
public class AreaSnipperPanel extends JPanel implements ActionListener, MouseListener {

	private Timer timer;
	private Key key;
	private int x, y, ox, oy, w, h;
	private AlphaComposite normal, alpha;
	private ISnipperListener result;
	private boolean down = false;
	private AreaSnipper snipper;
	private final int size, half;

	public AreaSnipperPanel(AreaSnipper snipper, ISnipperListener result) {
		this.result = result;
		this.snipper = snipper;

		size = Constants.AREA_SNIPPER_SIZE;
		half = size / 2;

		setSize(snipper.getWidth(), snipper.getHeight());

		addMouseListener(this);

		key = new Key();
		addKeyListener(key);

		normal = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
		alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f);

		timer = new Timer(30, this);

		BufferedImage c = new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB);
		Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(c, new Point(0, 0), "blank");
		this.setCursor(blank);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setBackground(new Color(0, 0, 0, 0));
		g2.clearRect(0, 0, getSize().width, getSize().height);
		g2.setColor(Color.black);
		g2.setComposite(alpha);
		g2.fillRect(0, 0, getSize().width, getSize().height);
		g2.setComposite(normal);
		if (down) {
			Point loc = snipper.getLocation();
			int dx = ox - loc.x;
			int dy = oy - loc.y;
			
			g2.clearRect(dx + 3, dy + 3, w - 3, h - 3);
			
			g2.setColor(Color.red);
			g2.drawRect(dx, dy, w, h);
			
			g2.setColor(Color.black);
			g2.drawLine(dx + w - 10, dy + h, dx + w + 10, dy + h);
			g2.drawLine(dx + w, dy + h + 10, dx + w, dy + h - 10);
			
			g2.setColor(Color.red);
		}
		g2.drawLine(half - 10, half, half + 10, half);
		g2.drawLine(half, half - 10, half, half + 10);
		g.dispose();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer) {
			Point p = MouseInfo.getPointerInfo().getLocation();
			x = p.x;
			y = p.y;
			if (!down) {
				snipper.setLocation(new Point(x - half, y - half));
			}

			if (down) {
				w = (int) Math.abs(ox - x);
				h = (int) Math.abs(oy - y);
				snipper.setSize(new Dimension(size + w, size + h));
			}

			if (key.isQuit()) {
				result.onError(new Exception(Constants.USER_CANCEL_MESSAGE));
			}
			repaint();
		}
	}

	public Timer getTimer() {
		return timer;
	}

	public Key getKey() {
		return key;
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		if (e.getModifiers() != 4) {
			down = true;
			Point p = MouseInfo.getPointerInfo().getLocation();
			ox = p.x;
			oy = p.y;
		} else {
			result.onError(new Exception(Constants.USER_CANCEL_MESSAGE));
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getModifiers() != 4) {
			down = false;
			if (w > 0 && h > 0) {
				result.onAreaSelected(new Rectangle(ox, oy, w, h));
				w = h = oy = ox = 0;
			}
		}
	}

}
