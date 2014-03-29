package gov.intra.net.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Preview extends JPanel {

	private Color backCol, fontCol;
	private Font font;
	private String text;

	public Preview(int fontSize, String text) {
		backCol = Color.black;
		fontCol = Color.white;
		font = new Font("Verdana", Font.PLAIN, fontSize);
		this.text = text;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(font);
		g2.setBackground(backCol);
		g2.clearRect(0, 0, getSize().width, getSize().height);
		g2.setColor(fontCol);
		FontMetrics metrics = g2.getFontMetrics(font);
		g2.drawString(text, getSize().width / 2 - metrics.stringWidth(text) / 2, getSize().height / 2 + metrics.getHeight() / 3);
		g.dispose();
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setBackgroundColour(Color backCol) {
		this.backCol = backCol;
	}

	public void setFontColour(Color fontCol) {
		this.fontCol = fontCol;
	}

}
