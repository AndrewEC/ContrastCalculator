package gov.intra.net.gui.dialogs;

import gov.intra.net.gui.Panel;

import java.awt.Color;

import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class ColourPicker extends JFrame implements ChangeListener {

	private JPanel contentPane;
	private JColorChooser colorChooser;
	private Panel panel;
	private JCheckBox cbForeground, cbBackground;

	public ColourPicker(Panel panel) {
		setTitle("Colour Picker");
		setVisible(false);
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 451, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		colorChooser = new JColorChooser();
		colorChooser.getSelectionModel().addChangeListener(this);
		colorChooser.setBounds(4, 1, 425, 279);
		contentPane.add(colorChooser);

		cbForeground = new JCheckBox("Bind to Foreground Colour");
		cbForeground.setBounds(5, 282, 417, 23);
		contentPane.add(cbForeground);

		cbBackground = new JCheckBox("Bind to Background Colour");
		cbBackground.setBounds(6, 304, 420, 23);
		contentPane.add(cbBackground);

		this.panel = panel;
	}

	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == colorChooser.getSelectionModel()) {
			Color c = colorChooser.getColor();
			if (cbForeground.isSelected()) {
				panel.getEventHandle().setForeColour(c, true);
			}

			if (cbBackground.isSelected()) {
				panel.getEventHandle().setBackColour(c, true);
			}
		}
	}
}
