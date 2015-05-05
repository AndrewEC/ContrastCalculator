package com.intra.net.dialogs;

import com.intra.net.frame.Frame;
import com.intra.net.panel.Panel;
import com.intra.net.panel.PanelUtil;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class ColourPicker extends JDialog implements ChangeListener {

	private JPanel contentPane;
	private JColorChooser colorChooser;
	private Panel panel;
	private JCheckBox cbForeground, cbBackground;

	public ColourPicker(Frame frame) {
		super(frame, "Colour Picker");
		try {
			InputStream in = this.getClass().getResourceAsStream("/resources/icon.png");
			BufferedImage image = ImageIO.read(in);
			setIconImage(image);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setVisible(false);
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		panel = frame.getPanel();
		if (frame.isUsingWebLaF()) {
			setBounds(100, 100, 410, 350);
		} else {
			setBounds(100, 100, 651, 390);
		}

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		colorChooser = new JColorChooser();
		colorChooser.getSelectionModel().addChangeListener(this);
		if (frame.isUsingWebLaF()) {
			colorChooser.setBounds(4, 1, 400, 303);
		} else {
			colorChooser.setBounds(4, 1, 634, 303);
		}
		contentPane.add(colorChooser);

		cbForeground = new JCheckBox("Bind to Foreground Colour");
		if (frame.isUsingWebLaF()) {
			cbForeground.setBounds(15, 270, 417, 23);
		} else {
			cbForeground.setBounds(15, 310, 417, 23);
		}
		contentPane.add(cbForeground);

		cbBackground = new JCheckBox("Bind to Background Colour");
		if (frame.isUsingWebLaF()) {
			cbBackground.setBounds(15, 290, 420, 23);
		} else {
			cbBackground.setBounds(15, 335, 420, 23);
		}
		contentPane.add(cbBackground);
	}

	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == colorChooser.getSelectionModel()) {
			Color c = colorChooser.getColor();
			if (cbForeground.isSelected()) {
				PanelUtil.setForeColour(panel, c, true);
			}

			if (cbBackground.isSelected()) {
				PanelUtil.setBackColour(panel, c, true);
			}
		}
	}
}
