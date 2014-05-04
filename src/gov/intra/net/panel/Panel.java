package gov.intra.net.panel;

import gov.intra.net.frame.EventDispatcher;
import gov.intra.net.frame.Frame;

import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

@SuppressWarnings("serial")
public class Panel extends JPanel {

	private JTextField foreB, foreG, foreR, foreHex;
	private JLabel lblForeground, lblForeHex, lblForeR, lblForeG, lblForeB;
	private JPanel foreCol;

	private JTextField backB, backG, backR, backHex;
	private JLabel lblBackground, lblBackHex, lblBackR, lblBackG, lblBackB;
	private JPanel backCol;

	private JTextField ratio;
	private JLabel lblContrastRatio;
	private JButton btnMagnifyWindow;

	private JSlider frSlider, fgSlider, fbSlider, brSlider, bgSlider, bbSlider;
	private JPanel sliderPanel;

	private JPanel panel;
	private TextPreview aaSmall, aaLarge, aaaSmall, aaaLarge;
	
	private EventDispatcher event;
	private PanelChangeHandle changeHandle;
	private DocumentEventHandle hex;
	private final Frame frame;

	public Panel(Frame frame) {
		//initialize general components and values
		this.frame = frame;
		setSize(410, 312);
		setDoubleBuffered(false);
		setLayout(null);

		event = new EventDispatcher(frame);
		event.addEvent(new PanelMagHandle(frame, this));
		event.addEvent(new PanelDropperHandle(frame, this));
		changeHandle = new PanelChangeHandle(this);
		hex = new DocumentEventHandle(this);
		//initialize general components and values

		//initialize foreground specific components
		JButton btnForeSelect = new JButton("");
		try {
			btnForeSelect.setIcon(new ImageIcon(Panel.class.getResource("/resources/DropperIcon.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		btnForeSelect.setBounds(109, 14, 40, 40);
		btnForeSelect.addActionListener(event);
		btnForeSelect.setActionCommand("select fore");
		event.registerCommand(btnForeSelect, KeyEvent.VK_Z);
		add(btnForeSelect);

		lblForeground = new JLabel("Foreground");
		lblForeground.setBounds(10, 10, 111, 14);
		add(lblForeground);

		lblForeHex = new JLabel("Hex:");
		lblForeHex.setBounds(10, 33, 46, 14);
		add(lblForeHex);

		lblForeR = new JLabel("R:");
		lblForeR.setBounds(10, 65, 20, 14);
		add(lblForeR);

		lblForeG = new JLabel("G:");
		lblForeG.setBounds(72, 65, 20, 14);
		add(lblForeG);

		lblForeB = new JLabel("B:");
		lblForeB.setBounds(133, 65, 20, 16);
		add(lblForeB);

		foreB = new JTextField();
		foreB.setText("255");
		foreB.setEditable(false);
		foreB.setBounds(150, 60, 40, 25);
		add(foreB);
		foreB.setColumns(10);

		foreG = new JTextField();
		foreG.setText("255");
		foreG.setEditable(false);
		foreG.setBounds(89, 60, 40, 25);
		add(foreG);
		foreG.setColumns(10);

		foreR = new JTextField();
		foreR.setText("255");
		foreR.setEditable(false);
		foreR.setBounds(30, 60, 40, 25);
		add(foreR);
		foreR.setColumns(10);

		foreHex = new JTextField();
		foreHex.setText("#ffffff");
		foreHex.setBounds(37, 28, 70, 25);
		foreHex.setDocument(new LimitedDocument(7, this));
		foreHex.getDocument().addDocumentListener(hex);
		add(foreHex);
		foreHex.setColumns(7);

		foreCol = new JPanel();
		foreCol.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		foreCol.setBounds(150, 14, 40, 40);
		add(foreCol);
		//initialize foreground specific components

		//initialize background specific components
		JButton btnBackSelect = new JButton("");
		try {
			btnBackSelect.setIcon(new ImageIcon(Panel.class.getResource("/resources/DropperIcon.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		btnBackSelect.setBounds(314, 14, 40, 40);
		btnBackSelect.addActionListener(event);
		btnBackSelect.setActionCommand("select back");
		event.registerCommand(btnBackSelect, KeyEvent.VK_X);
		add(btnBackSelect);

		lblBackground = new JLabel("Background");
		lblBackground.setBounds(215, 10, 111, 14);
		add(lblBackground);

		lblBackHex = new JLabel("Hex:");
		lblBackHex.setBounds(215, 33, 46, 14);
		add(lblBackHex);

		lblBackR = new JLabel("R:");
		lblBackR.setBounds(215, 65, 20, 14);
		add(lblBackR);

		lblBackG = new JLabel("G:");
		lblBackG.setBounds(278, 65, 20, 14);
		add(lblBackG);

		lblBackB = new JLabel("B:");
		lblBackB.setBounds(339, 65, 20, 16);
		add(lblBackB);

		backB = new JTextField();
		backB.setText("0");
		backB.setEditable(false);
		backB.setColumns(10);
		backB.setBounds(356, 60, 40, 25);
		add(backB);

		backG = new JTextField();
		backG.setText("0");
		backG.setEditable(false);
		backG.setColumns(10);
		backG.setBounds(296, 60, 40, 25);
		add(backG);

		backR = new JTextField();
		backR.setText("0");
		backR.setEditable(false);
		backR.setColumns(10);
		backR.setBounds(233, 60, 40, 25);
		add(backR);

		backHex = new JTextField();
		backHex.setText("#000000");
		backHex.setColumns(7);
		backHex.setBounds(242, 28, 70, 25);
		backHex.setDocument(new LimitedDocument(7, this));
		backHex.getDocument().addDocumentListener(hex);
		add(backHex);

		backCol = new JPanel();
		backCol.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		backCol.setBounds(355, 14, 40, 40);
		add(backCol);
		//initialize magnifier components

		//initialize appearance based components
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBackground(Color.BLACK);
		separator.setBounds(198, 6, 9, 79);
		add(separator);
		//initialize appearance based components

		//initialize other items
		JButton focusFore = new JButton("");
		focusFore.setBounds(-100, -100, 1, 1);
		focusFore.setActionCommand("focus fore");
		event.registerCommand(focusFore, KeyEvent.VK_F);
		add(focusFore);

		JButton focusBack = new JButton("");
		focusBack.setBounds(-100, -100, 1, 1);
		focusBack.setActionCommand("focus back");
		event.registerCommand(focusBack, KeyEvent.VK_B);
		add(focusBack);

		panel = new JPanel();
		panel.setBounds(5, 91, 399, 220);
		add(panel);
		panel.setLayout(null);
		//initialize background specific components

		//initialize result components
		JButton btnSwap = new JButton("Swap");
		btnSwap.setBounds(150, 0, 85, 25);
		panel.add(btnSwap);
		btnSwap.addActionListener(event);
		btnSwap.setActionCommand("swap");
		event.registerCommand(btnSwap, KeyEvent.VK_R);
		//initialize result components

		//initialize magnifier components
		btnMagnifyWindow = new JButton("Magnify Window");
		btnMagnifyWindow.setBounds(28, 35, 155, 25);
		panel.add(btnMagnifyWindow);
		btnMagnifyWindow.setEnabled(false);
		btnMagnifyWindow.addActionListener(event);
		btnMagnifyWindow.setActionCommand("magnify window");
		event.registerCommand(btnMagnifyWindow, KeyEvent.VK_C);

		JButton btnMagnifyArea = new JButton("Magnify Area");
		btnMagnifyArea.setBounds(200, 35, 124, 25);
		panel.add(btnMagnifyArea);
		btnMagnifyArea.addActionListener(event);
		btnMagnifyArea.setActionCommand("magnify area");
		event.registerCommand(btnMagnifyArea, KeyEvent.VK_V);

		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(3, 27, 396, 9);
		panel.add(separator_4);

		lblContrastRatio = new JLabel("Contrast Ratio:");
		lblContrastRatio.setBounds(141, 70, 157, 16);
		panel.add(lblContrastRatio);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(1, 65, 392, 12);
		panel.add(separator_1);

		JButton btnViewDetails = new JButton("View Details");
		btnViewDetails.setBounds(70, 91, 104, 25);
		panel.add(btnViewDetails);
		btnViewDetails.addActionListener(event);
		btnViewDetails.setActionCommand("view details");
		event.registerCommand(btnViewDetails, KeyEvent.VK_D);

		ratio = new JTextField();
		ratio.setBounds(192, 91, 82, 25);
		panel.add(ratio);
		ratio.setText("21.00:1");
		ratio.setEditable(false);
		ratio.setColumns(10);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(5, 123, 392, 14);
		panel.add(separator_2);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(196, 130, 9, 93);
		panel.add(separator_3);
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setBackground(Color.BLACK);

		aaSmall = new TextPreview(12, "AA Small - Pass");
		aaSmall.setBounds(52, 135, 125, 30);
		panel.add(aaSmall);
		aaSmall.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		aaLarge = new TextPreview(18, "AA Large - Pass");
		aaLarge.setBounds(2, 171, 175, 41);
		panel.add(aaLarge);
		aaLarge.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		aaaSmall = new TextPreview(12, "AAA Small - Pass");
		aaaSmall.setBounds(218, 135, 125, 30);
		panel.add(aaaSmall);
		aaaSmall.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		aaaLarge = new TextPreview(18, "AAA Large - Pass");
		aaaLarge.setBounds(218, 175, 175, 41);
		panel.add(aaaLarge);
		aaaLarge.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		sliderPanel = new JPanel();
		sliderPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		sliderPanel.setBounds(8, 91, 388, 84);
		sliderPanel.setLayout(null);
		sliderPanel.setVisible(false);
		add(sliderPanel);

		JLabel lblFRSlider = new JLabel("R:");
		lblFRSlider.setBounds(7, 7, 20, 15);
		sliderPanel.add(lblFRSlider);

		JLabel lblFGSlider = new JLabel("G:");
		lblFGSlider.setBounds(6, 34, 20, 15);
		sliderPanel.add(lblFGSlider);

		JLabel lblFBSlider = new JLabel("B:");
		lblFBSlider.setBounds(7, 61, 20, 15);
		sliderPanel.add(lblFBSlider);

		JSeparator separator_5 = new JSeparator();
		separator_5.setOrientation(SwingConstants.VERTICAL);
		separator_5.setBackground(Color.BLACK);
		separator_5.setBounds(188, 7, 9, 65);
		sliderPanel.add(separator_5);

		frSlider = new JSlider();
		frSlider.setMaximum(255);
		frSlider.setValue(255);
		frSlider.setBounds(27, 4, 151, 22);
		frSlider.addChangeListener(changeHandle);
		sliderPanel.add(frSlider);

		fgSlider = new JSlider();
		fgSlider.setMaximum(255);
		fgSlider.setValue(255);
		fgSlider.setBounds(26, 30, 151, 22);
		fgSlider.addChangeListener(changeHandle);
		sliderPanel.add(fgSlider);

		fbSlider = new JSlider();
		fbSlider.setMaximum(255);
		fbSlider.setValue(255);
		fbSlider.setBounds(26, 57, 151, 22);
		fbSlider.addChangeListener(changeHandle);
		sliderPanel.add(fbSlider);

		brSlider = new JSlider();
		brSlider.setMaximum(255);
		brSlider.setValue(0);
		brSlider.setBounds(219, 5, 151, 22);
		brSlider.addChangeListener(changeHandle);
		sliderPanel.add(brSlider);

		JLabel lblBR = new JLabel("R:");
		lblBR.setBounds(199, 8, 20, 15);
		sliderPanel.add(lblBR);

		JLabel lblBG = new JLabel("G:");
		lblBG.setBounds(198, 35, 20, 15);
		sliderPanel.add(lblBG);

		bgSlider = new JSlider();
		bgSlider.setValue(0);
		bgSlider.setMaximum(255);
		bgSlider.setBounds(218, 31, 151, 22);
		bgSlider.addChangeListener(changeHandle);
		sliderPanel.add(bgSlider);

		bbSlider = new JSlider();
		bbSlider.setValue(0);
		bbSlider.setMaximum(255);
		bbSlider.setBounds(218, 58, 151, 22);
		bbSlider.addChangeListener(changeHandle);
		sliderPanel.add(bbSlider);

		JLabel lblBB = new JLabel("B:");
		lblBB.setBounds(199, 62, 20, 15);
		sliderPanel.add(lblBB);
		//initialize other items

		PanelUtil.setForeColour(this, Color.white, true);
		PanelUtil.setBackColour(this, Color.black, true);
	}

	public JSlider getFrSlider() {
		return frSlider;
	}

	public JSlider getFgSlider() {
		return fgSlider;
	}

	public JSlider getFbSlider() {
		return fbSlider;
	}

	public JSlider getBrSlider() {
		return brSlider;
	}

	public JSlider getBgSlider() {
		return bgSlider;
	}

	public JSlider getBbSlider() {
		return bbSlider;
	}

	public JTextField getForeHex() {
		return foreHex;
	}

	public JTextField getBackHex() {
		return backHex;
	}

	public JTextField getRatio() {
		return ratio;
	}

	public TextPreview getAASmall() {
		return aaSmall;
	}

	public TextPreview getAALarge() {
		return aaLarge;
	}

	public TextPreview getAAASmall() {
		return aaaSmall;
	}

	public TextPreview getAAALarge() {
		return aaaLarge;
	}

	public JTextField getForeR() {
		return foreR;
	}

	public JTextField getForeG() {
		return foreG;
	}

	public JTextField getForeB() {
		return foreB;
	}

	public JTextField getBackR() {
		return backR;
	}

	public JTextField getBackG() {
		return backG;
	}

	public JTextField getBackB() {
		return backB;
	}

	public JButton getBtnMagnify() {
		return btnMagnifyWindow;
	}

	public Frame getFrame() {
		return frame;
	}

	public JPanel getForeColourPreview() {
		return foreCol;
	}

	public JPanel getBackColourPreview() {
		return backCol;
	}

	public JPanel getSliderPanel() {
		return sliderPanel;
	}

	public JPanel getMainPanel() {
		return panel;
	}
}
