package gov.intra.net.frame;

import gov.intra.net.panel.Panel;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import resources.Constants.BlindColour;

@SuppressWarnings("serial")
public class Frame extends JFrame {

	private Panel panel;
	private ButtonGroup bgBlind;
	private JTextField txtResultName, txtExpName;
	private JCheckBox cbTop, cbEnableWindowMag, cbColourInvert, cbShowSliders, cbBlindPicker;
	private JMenu mnColourBlind;

	private FrameItemListener itemListener;
	private EventDispatcher event;
	private boolean usingWeblaf = true;

	public Frame(boolean loaded) {
		super("Contrast Calculator");
		usingWeblaf = loaded;
		try {
			InputStream in = Frame.class.getResourceAsStream("/resources/icon.png");
			BufferedImage image = ImageIO.read(in);
			setIconImage(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setBounds(200, 200, 413, 373);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		event = new EventDispatcher();
		itemListener = new FrameItemListener(this);
		event.addEvent(new DialogEventHandle(this));
		event.addEvent(new FocusEventHandle(this));
		event.addEvent(new MenuButtonHandle(this));

		panel = new Panel(this);
		getContentPane().add(panel);

		// initialize results panel
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mnColourPicker = new JMenuItem("Open Colour Picker");
		mnColourPicker.setActionCommand("open colour picker");
		mnColourPicker.addActionListener(event);
		mnFile.add(mnColourPicker);

		JMenuItem mnExit = new JMenuItem("Exit");
		mnExit.setActionCommand("menu exit");
		mnExit.addActionListener(event);
		mnFile.add(mnExit);

		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);

		// initialize colour blind options
		mnColourBlind = new JMenu("Colour Blind");
		mnOptions.add(mnColourBlind);

		JRadioButton rbNormal = new JRadioButton("Normal");
		rbNormal.setSelected(true);
		mnColourBlind.add(rbNormal);

		JRadioButton rbInvert = new JRadioButton("Invert");
		mnColourBlind.add(rbInvert);

		JRadioButton rbProtanopia = new JRadioButton("Protanopia");
		mnColourBlind.add(rbProtanopia);

		JRadioButton rbProtanomaly = new JRadioButton("Protanomaly");
		mnColourBlind.add(rbProtanomaly);

		JRadioButton rbDeuteranopia = new JRadioButton("Deuteranopia");
		mnColourBlind.add(rbDeuteranopia);

		JRadioButton rbDeuteranomaly = new JRadioButton("Deuteranomaly");
		mnColourBlind.add(rbDeuteranomaly);

		JRadioButton rbTritanopia = new JRadioButton("Tritanopia");
		mnColourBlind.add(rbTritanopia);

		JRadioButton rbTritanomaly = new JRadioButton("Tritanomaly");
		mnColourBlind.add(rbTritanomaly);

		JRadioButton rbAchromatopsia = new JRadioButton("Achromatopsia");
		mnColourBlind.add(rbAchromatopsia);

		JRadioButton rbAchromatomaly = new JRadioButton("Achromatomaly");
		mnColourBlind.add(rbAchromatomaly);
		// initialize colour blind options

		// initialize pixel dropper menu
		JMenu mnPixelDropper = new JMenu("Pixel Dropper");
		mnOptions.add(mnPixelDropper);

		cbColourInvert = new JCheckBox("Allow Colour Invert");
		cbColourInvert.setSelected(true);
		mnPixelDropper.add(cbColourInvert);

		cbBlindPicker = new JCheckBox("Enable Colour Blind Picker");
		mnPixelDropper.add(cbBlindPicker);
		// initialize pixel dropper menu

		// initialize other options
		JMenu mnOther = new JMenu("Other");
		mnOptions.add(mnOther);

		cbTop = new JCheckBox("Stay on Top");
		mnOther.add(cbTop);
		cbTop.addItemListener(itemListener);

		cbEnableWindowMag = new JCheckBox("Enable Window Magnifier");
		mnOther.add(cbEnableWindowMag);
		cbEnableWindowMag.addItemListener(itemListener);

		cbShowSliders = new JCheckBox("Show RGB Sliders");
		mnOther.add(cbShowSliders);
		cbShowSliders.addItemListener(itemListener);
		// initialize other options

		// initialize help menu
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mnAbout = new JMenuItem("About");
		mnAbout.addActionListener(event);
		mnAbout.setActionCommand("menu about");
		event.registerCommand(mnAbout, KeyEvent.VK_A);
		mnHelp.add(mnAbout);

		JMenuItem mnShortcuts = new JMenuItem("Shortcuts");
		mnShortcuts.addActionListener(event);
		mnShortcuts.setActionCommand("menu shortcuts");
		event.registerCommand(mnShortcuts, KeyEvent.VK_U);
		mnHelp.add(mnShortcuts);

		JMenuItem mnGuidelines = new JMenuItem("Guidelines");
		mnGuidelines.addActionListener(event);
		mnGuidelines.setActionCommand("menu guidelines");
		event.registerCommand(mnGuidelines, KeyEvent.VK_G);
		mnHelp.add(mnGuidelines);

		JMenuItem mnGuide = new JMenuItem("User Guide");
		mnGuide.addActionListener(event);
		mnGuide.setActionCommand("menu guide");
		event.registerCommand(mnGuide, KeyEvent.VK_M);
		mnHelp.add(mnGuide);

		JButton btnGetFocus = new JButton("Get Focus");
		btnGetFocus.addActionListener(event);
		btnGetFocus.setActionCommand("get focus");
		event.registerCommand(btnGetFocus, KeyEvent.VK_O);
		menuBar.add(btnGetFocus);
		// initialize help menu

		// initialize export menu
		JMenu mnExport = new JMenu("Export");
		mnOptions.add(mnExport);

		JMenu mnType = new JMenu("Type");
		mnExport.add(mnType);

		JMenu mnName = new JMenu("Name");
		mnExport.add(mnName);

		txtExpName = new JTextField();
		txtExpName.setText("Export");
		txtExpName.setColumns(10);
		mnName.add(txtExpName);

		JButton btnLocation = new JButton("Location");
		btnLocation.addActionListener(event);
		btnLocation.setActionCommand("export location");
		mnExport.add(btnLocation);
		// initialize export menu

		// initialize button groups
		bgBlind = new ButtonGroup();
		bgBlind.add(rbNormal);
		bgBlind.add(rbInvert);
		bgBlind.add(rbProtanopia);
		bgBlind.add(rbProtanomaly);
		bgBlind.add(rbDeuteranopia);
		bgBlind.add(rbDeuteranomaly);
		bgBlind.add(rbTritanopia);
		bgBlind.add(rbTritanomaly);
		bgBlind.add(rbAchromatopsia);
		bgBlind.add(rbAchromatomaly);
		// initialize button groups

		// initialize hidden buttons
		Rectangle r = new Rectangle(-100, -100, 0, 0);

		JButton hNameFocus = new JButton("");
		hNameFocus.setBounds(r);
		hNameFocus.setActionCommand("focus name");
		event.registerCommand(hNameFocus, KeyEvent.VK_F);
		panel.add(hNameFocus);

		JButton hConTab = new JButton("");
		hConTab.setBounds(r);
		hConTab.setActionCommand("focus contraster");
		event.registerCommand(hConTab, KeyEvent.VK_Q);
		panel.add(hConTab);

		JButton hTop = new JButton("");
		hTop.setBounds(r);
		hTop.setActionCommand("switch on top");
		event.registerCommand(hTop, KeyEvent.VK_Y);
		panel.add(hTop);

		JButton hWindowMag = new JButton("");
		hWindowMag.setBounds(r);
		hWindowMag.setActionCommand("switch window mag");
		event.registerCommand(hWindowMag, KeyEvent.VK_E);
		panel.add(hWindowMag);

		JButton hInvert = new JButton("");
		hInvert.setBounds(r);
		hInvert.setActionCommand("switch invert");
		event.registerCommand(hInvert, KeyEvent.VK_I);
		panel.add(hInvert);

		JButton hSlider = new JButton("");
		hSlider.setBounds(r);
		hSlider.setActionCommand("switch slider");
		event.registerCommand(hSlider, KeyEvent.VK_R);
		panel.add(hSlider);

		JButton hGuide = new JButton("");
		hGuide.setBounds(r);
		hGuide.setActionCommand("menu guide");
		event.registerCommand(hGuide, KeyEvent.VK_M);
		panel.add(hGuide);

		JButton hSFocus = new JButton("");
		hSFocus.setBounds(r);
		hSFocus.setActionCommand("focus slider");
		event.registerCommand(hSFocus, KeyEvent.VK_F);
		panel.add(hSFocus);

		JButton hBlindDropper = new JButton("");
		hBlindDropper.setBounds(r);
		hBlindDropper.setActionCommand("toggle blind dropper");
		event.registerCommand(hBlindDropper, KeyEvent.VK_P);
		panel.add(hBlindDropper);

		JButton hColourPicker = new JButton("");
		hColourPicker.setBounds(r);
		hColourPicker.setActionCommand("open colour picker");
		event.registerCommand(hColourPicker, KeyEvent.VK_N);
		panel.add(hColourPicker);

		for (int i = 0; i < 10; i++) {
			JButton bo = new JButton("");
			bo.setBounds(r);
			bo.setActionCommand("set blind " + i);
			event.registerCommand(bo, 0x30 + i);
			panel.add(bo);
		}
		// initialize hidden buttons
	}

	public ButtonGroup getBlindGroup() {
		return bgBlind;
	}

	public JCheckBox getCbEnableWindowMag() {
		return cbEnableWindowMag;
	}

	public JCheckBox getCbTop() {
		return cbTop;
	}

	public Panel getPanel() {
		return panel;
	}

	public EventDispatcher getEvent() {
		return event;
	}

	public JTextField getExpName() {
		return txtExpName;
	}

	public JTextField getResultsName() {
		return txtResultName;
	}

	public JCheckBox getInvertCheck() {
		return cbColourInvert;
	}

	public JCheckBox getCbShowSliders() {
		return cbShowSliders;
	}

	public boolean getInvert() {
		return cbColourInvert.isSelected();
	}

	public JCheckBox getBlindPicker() {
		return cbBlindPicker;
	}

	public boolean getWindowEnabled() {
		return cbEnableWindowMag.isSelected();
	}

	public boolean isUsingWebLaF() {
		return usingWeblaf;
	}
	
	public String getBlindOption() {
		for (Enumeration<AbstractButton> buttons = getBlindGroup().getElements(); buttons.hasMoreElements();) {
			AbstractButton b = buttons.nextElement();
			if (b.isSelected()) {
				return b.getText();
			}
		}
		return "";
	}
	
	public BlindColour getBlindColour() {
		String name = getBlindOption();
		for (BlindColour c : BlindColour.values()) {
			if (c.value.equals(name)) {
				return c;
			}
		}
		return BlindColour.NORMAL;
	}

	private static boolean loaded = true;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.alee.laf.WebLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
			loaded = false;
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Frame frame = new Frame(loaded);
				frame.setVisible(true);
				frame.requestFocus();
			}
		});
	}
}
