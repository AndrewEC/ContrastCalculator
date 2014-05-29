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
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class AreaSnipperPanel extends JPanel implements ActionListener, MouseListener {

	private Timer timer;
	private Mouse mouse;
	private Key key;
	private int x, y, ox, oy, drawX, drawY, width, height, w, h;
	private AlphaComposite normal, alpha;
	private IAreaSnipperResult result;
	private boolean down = false;

	public AreaSnipperPanel(AreaSnipper mag, IAreaSnipperResult result, int width, int height) {
		this.result = result;
		this.width = width;
		this.height = height;

		mouse = new Mouse();
		addMouseListener(this);

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
		g2.clearRect(0, 0, getSize().width, getSize().height);
		g2.setColor(Color.black);
		g2.setComposite(alpha);
		g2.fillRect(0, 0, width, height);
		g2.setComposite(normal);
		g2.setColor(Color.white);
		if (down) {
			g2.setStroke(new BasicStroke(3));
			g2.drawRect(drawX, drawY, w, h);
		}
		g.dispose();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer) {
			Point p = MouseInfo.getPointerInfo().getLocation();
			x = p.x;
			y = p.y;

			if (down) {
				if (x < ox) {
					drawX = x;
				}else{
					drawX = ox;
				}
				if (y < oy) {
					drawY = y;
				}else{
					drawY = oy;
				}
				w = (int) Math.abs(ox - x);
				h = (int) Math.abs(oy - y);
			}

			if (mouse.isRight() || key.isQuit()) {
				result.onError(new Exception("User cancelled the operation."));
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
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getModifiers() != 4) {
			down = false;
			if (w > 0 && h > 0) {
				result.onAreaSelected(new Rectangle(drawX, drawY, w, h));
				w = h = oy = ox = drawX = drawY = 0;
			}
		}
	}

}
