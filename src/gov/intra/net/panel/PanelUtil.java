package gov.intra.net.panel;

import java.awt.Color;

public class PanelUtil {
	
	public static void setForeColour(Panel panel, Color c, boolean h) {
		panel.getForeR().setText("" + c.getRed());
		panel.getForeG().setText("" + c.getGreen());
		panel.getForeB().setText("" + c.getBlue());
		panel.getForeColourPreview().setBackground(c);
		if (h) {
			String hex = String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
			panel.getForeHex().setText(hex);
		}
		panel.getFrSlider().setValue(c.getRed());
		panel.getFgSlider().setValue(c.getGreen());
		panel.getFbSlider().setValue(c.getBlue());
		panel.getAASmall().setFontColour(c);
		panel.getAALarge().setFontColour(c);
		panel.getAAASmall().setFontColour(c);
		panel.getAAALarge().setFontColour(c);
		panel.repaint();
	}
	
	public static void setRatio(Panel panel, double val) {
		panel.getRatio().setText(String.format("%.2f:1", val));

		if (val >= 7) {
			panel.getAASmall().setText("AA Small - Pass");
			panel.getAALarge().setText("AA Large - Pass");
			panel.getAAASmall().setText("AAA Small - Pass");
			panel.getAAALarge().setText("AAA Large - Pass");
		} else if (val >= 4.5) {
			panel.getAASmall().setText("AA Small - Pass");
			panel.getAALarge().setText("AA Large - Pass");
			panel.getAAASmall().setText("AAA Small - Fail");
			panel.getAAALarge().setText("AAA Large - Pass");
		} else if (val >= 3) {
			panel.getAASmall().setText("AA Small - Fail");
			panel.getAALarge().setText("AA Large - Pass");
			panel.getAAASmall().setText("AAA Small - Fail");
			panel.getAAALarge().setText("AAA Large - Fail");
		} else {
			panel.getAASmall().setText("AA Small - Fail");
			panel.getAALarge().setText("AA Large - Fail");
			panel.getAAASmall().setText("AAA Small - Fail");
			panel.getAAALarge().setText("AAA Large - Fail");
		}
	}

	public static void setBackColour(Panel panel, Color c, boolean h) {
		panel.getBackR().setText("" + c.getRed());
		panel.getBackG().setText("" + c.getGreen());
		panel.getBackB().setText("" + c.getBlue());
		panel.getBackColourPreview().setBackground(c);
		if (h) {
			String hex = String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
			panel.getBackHex().setText(hex);
		}
		panel.getBrSlider().setValue(c.getRed());
		panel.getBgSlider().setValue(c.getGreen());
		panel.getBbSlider().setValue(c.getBlue());
		panel.getAASmall().setBackgroundColour(c);
		panel.getAALarge().setBackgroundColour(c);
		panel.getAAASmall().setBackgroundColour(c);
		panel.getAAALarge().setBackgroundColour(c);
		panel.repaint();
	}

}
