package gov.intra.net.util;

import java.awt.Color;
import java.awt.image.BufferedImage;

import resources.Constants.BlindColour;

public class Contraster {

	private Color fore, back;
	private static char[] valid = new char[] { 'a', 'b', 'c', 'd', 'e', 'f' };

	public void setForeground(Color fore) {
		this.fore = fore;
	}

	public void setBackground(Color back) {
		this.back = back;
	}

	public double calculateContrast() {
		double r1 = fore.getRed() / 255.0, r2 = back.getRed() / 255.0;
		double g1 = fore.getGreen() / 255.0, g2 = back.getGreen() / 255.0;
		double b1 = fore.getBlue() / 255.0, b2 = back.getBlue() / 255.0;

		r1 = processColour(r1);
		r2 = processColour(r2);
		g1 = processColour(g1);
		g2 = processColour(g2);
		b1 = processColour(b1);
		b2 = processColour(b2);

		double l1 = 0.2126 * r1 + 0.7152 * g1 + 0.0722 * b1;
		double l2 = 0.2126 * r2 + 0.7152 * g2 + 0.0722 * b2;

		if (l1 > l2) {
			return (l1 + 0.05) / (l2 + 0.05);
		} else {
			return (l2 + 0.05) / (l1 + 0.05);
		}
	}

	private double processColour(double c) {
		if (c <= 0.03928) {
			return c / 12.92;
		} else {
			return Math.pow((c + 0.055) / 1.055, 2.4);
		}
	}

	public static Color hexToColour(String hex) {
		return new Color(Integer.valueOf(hex.substring(1, 3), 16), Integer.valueOf(hex.substring(3, 5), 16), Integer.valueOf(hex.substring(5, 7), 16));
	}

	public static Color invert(Color c) {
		return new Color(Math.abs(c.getRed() - 255), Math.abs(c.getGreen() - 255), Math.abs(c.getBlue() - 255));
	}

	public static boolean isValidHex(String hex) {
		if (hex.length() != 7) {
			return false;
		}
		if (!hex.startsWith("#")) {
			return false;
		}
		for (char c : hex.substring(1, hex.length() - 1).toLowerCase().toCharArray()) {
			if (!Character.isDigit(c)) {
				if (!isValidHexChar(c)) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean isValidHexChar(char ch) {
		for (char c : valid) {
			if (c == ch) {
				return true;
			}
		}
		return false;
	}

	public static Color getColour(String red, String green, String blue) {
		try {
			int r = Integer.parseInt(red);
			int g = Integer.parseInt(green);
			int b = Integer.parseInt(blue);
			return new Color(r, g, b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage modifyImage(BufferedImage buffer, BlindColour colour) {
		double[] mod = colour.modifier;
		for (int x = 0; x < buffer.getWidth(); x++) {
			for (int y = 0; y < buffer.getHeight(); y++) {
				int col = buffer.getRGB(x, y);
				int tr = (col & 0x00FF0000) >> 16;
				int tg = (col & 0x0000FF00) >> 8;
				int tb = (col & 0x000000FF);

				double red = (tr * mod[0] + tg * mod[1] + tb * mod[2]);
				double green = (tr * mod[3] + tg * mod[4] + tb * mod[5]);
				double blue = (tr * mod[6] + tg * mod[7] + tb * mod[8]);

				int r = Math.min((int) Math.round(red), 255);
				int g = Math.min((int) Math.round(green), 255);
				int b = Math.min((int) Math.round(blue), 255);

				Color c = new Color(r, g, b);
				buffer.setRGB(x, y, c.getRGB());
			}
		}
		return buffer;
	}

	public static BufferedImage invertImage(BufferedImage buffer) {
		for (int x = 0; x < buffer.getWidth(); x++) {
			for (int y = 0; y < buffer.getHeight(); y++) {
				int col = buffer.getRGB(x, y);
				int r = (col & 0x00FF0000) >> 16;
				int g = (col & 0x0000FF00) >> 8;
				int b = (col & 0x000000FF);
				r = Math.abs(r - 255);
				g = Math.abs(g - 255);
				b = Math.abs(b - 255);

				Color c = new Color(r, g, b);
				buffer.setRGB(x, y, c.getRGB());
			}
		}
		return buffer;
	}

	public static BufferedImage convertImage(BufferedImage buffer, BlindColour colour) {
		switch (colour) {
		case NORMAL:
			return buffer;
		case INVERT:
			return invertImage(buffer);
		default:
			return modifyImage(buffer, colour);
		}
	}
}
