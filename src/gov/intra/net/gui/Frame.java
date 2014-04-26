package gov.intra.net.gui;

import gov.intra.net.util.Exporter;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class Frame extends JFrame {

	private JPanel panelTabRoot, panelResults;
	private JTabbedPane tabbedPane;
	private Panel panel;
	private ButtonGroup bgExport, bgBlind;
	private JRadioButton rbText, rbHTML;
	private JTable tbResults;
	private JTextField txtResultName, txtExpName;
	private JCheckBox cbTop, cbEnableWindowMag, cbColourInvert, cbShowSliders, cbBlindPicker;
	private JMenu mnColourBlind;

	private FrameEventHandle event;

	public Frame() {
		super("Contrast Calculator");
		try {
			InputStream in = Frame.class.getResourceAsStream("/resources/icon.png");
			BufferedImage image = ImageIO.read(in);
			setIconImage(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setBounds(200, 200, 413, 393);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		event = new FrameEventHandle(this);

		panelTabRoot = new JPanel();
		panelTabRoot.setLayout(null);
		getContentPane().add(panelTabRoot, BorderLayout.CENTER);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(-3, -2, 700, 459);
		tabbedPane.addChangeListener(event);
		panelTabRoot.add(tabbedPane);
		panel = new Panel(this);
		tabbedPane.addTab("Contraster", null, panel, null);

		// initialize results panel
		panelResults = new JPanel();
		tabbedPane.addTab("Results", null, panelResults, null);
		panelResults.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(1, 0, 690, 276);
		panelResults.add(scrollPane);

		tbResults = new JTable(new DefaultTableModel(new Object[][] {}, Exporter.HEADINGS));
		tbResults.setFont(new Font("Verdana", Font.PLAIN, 14));
		tbResults.setDefaultRenderer(Object.class, new TableRenderer());
		scrollPane.setViewportView(tbResults);

		JButton btnAddCurrentResult = new JButton("Add Current Result");
		btnAddCurrentResult.setBounds(203, 282, 155, 25);
		btnAddCurrentResult.addActionListener(event);
		btnAddCurrentResult.setActionCommand("add result");
		event.registerCommand(btnAddCurrentResult, KeyEvent.VK_N);
		panelResults.add(btnAddCurrentResult);

		txtResultName = new JTextField();
		txtResultName.setBounds(89, 283, 110, 25);
		txtResultName.setColumns(10);
		panelResults.add(txtResultName);

		JLabel lblResultName = new JLabel("Result Name:");
		lblResultName.setBounds(5, 288, 92, 16);
		panelResults.add(lblResultName);

		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(362, 282, 85, 25);
		btnDelete.addActionListener(event);
		btnDelete.setActionCommand("delete");
		event.registerCommand(btnDelete, KeyEvent.VK_D);
		panelResults.add(btnDelete);

		JButton btnExportTable = new JButton("Export Table");
		btnExportTable.setBounds(447, 282, 122, 25);
		btnExportTable.addActionListener(event);
		btnExportTable.setActionCommand("export table");
		event.registerCommand(btnExportTable, KeyEvent.VK_S);
		panelResults.add(btnExportTable);
		// initialize results panel

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mnExit = new JMenuItem("Exit");
		mnExit.addActionListener(event);
		mnExit.setActionCommand("menu exit");
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
		cbTop.addItemListener(event);

		cbEnableWindowMag = new JCheckBox("Enable Window Magnifier");
		mnOther.add(cbEnableWindowMag);
		cbEnableWindowMag.addItemListener(event);

		cbShowSliders = new JCheckBox("Show RGB Sliders");
		mnOther.add(cbShowSliders);
		cbShowSliders.addItemListener(event);
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

		rbText = new JRadioButton("Text");
		mnType.add(rbText);

		rbHTML = new JRadioButton("HTML");
		rbHTML.setSelected(true);
		mnType.add(rbHTML);

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
		bgExport = new ButtonGroup();
		bgExport.add(rbText);
		bgExport.add(rbHTML);

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

		JButton hResTab = new JButton("");
		hResTab.setBounds(r);
		hResTab.setActionCommand("focus results");
		event.registerCommand(hResTab, KeyEvent.VK_W);
		panel.add(hResTab);

		JButton hNameFocus = new JButton("");
		hNameFocus.setBounds(r);
		hNameFocus.setActionCommand("focus name");
		event.registerCommand(hNameFocus, KeyEvent.VK_F);
		panelTabRoot.add(hNameFocus);

		JButton hConTab = new JButton("");
		hConTab.setBounds(r);
		hConTab.setActionCommand("focus contraster");
		event.registerCommand(hConTab, KeyEvent.VK_Q);
		panelTabRoot.add(hConTab);

		JButton hTabFocus = new JButton("");
		hTabFocus.setBounds(r);
		hTabFocus.setActionCommand("focus table");
		event.registerCommand(hTabFocus, KeyEvent.VK_T);
		panelTabRoot.add(hTabFocus);

		JButton eHtml = new JButton("");
		eHtml.setBounds(r);
		eHtml.setActionCommand("export html");
		event.registerCommand(eHtml, KeyEvent.VK_H);
		panelTabRoot.add(eHtml);

		JButton eText = new JButton("");
		eText.setBounds(r);
		eText.setActionCommand("export text");
		event.registerCommand(eText, KeyEvent.VK_J);
		panelTabRoot.add(eText);

		JButton hTop = new JButton("");
		hTop.setBounds(r);
		hTop.setActionCommand("switch on top");
		event.registerCommand(hTop, KeyEvent.VK_Y);
		panelTabRoot.add(hTop);

		JButton hWindowMag = new JButton("");
		hWindowMag.setBounds(r);
		hWindowMag.setActionCommand("switch window mag");
		event.registerCommand(hWindowMag, KeyEvent.VK_E);
		panelTabRoot.add(hWindowMag);

		JButton hInvert = new JButton("");
		hInvert.setBounds(r);
		hInvert.setActionCommand("switch invert");
		event.registerCommand(hInvert, KeyEvent.VK_I);
		panelTabRoot.add(hInvert);

		JButton hLocation = new JButton("");
		hLocation.setBounds(r);
		hLocation.setActionCommand("export location");
		event.registerCommand(hLocation, KeyEvent.VK_L);
		panelTabRoot.add(hLocation);

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

		for (int i = 0; i < 10; i++) {
			JButton bo = new JButton("");
			bo.setBounds(r);
			bo.setActionCommand("set blind " + i);
			event.registerCommand(bo, 0x30 + i);
			panelTabRoot.add(bo);
		}
		// initialize hidden buttons
	}

	public ButtonGroup getBlindGroup() {
		return bgBlind;
	}

	public JTabbedPane getTabs() {
		return tabbedPane;
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

	public FrameEventHandle getEvent() {
		return event;
	}

	public JRadioButton getRbText() {
		return rbText;
	}

	public JTextField getExpName() {
		return txtExpName;
	}

	public JTable getResultsTable() {
		return tbResults;
	}

	public JRadioButton getRbHtml() {
		return rbHTML;
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

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.alee.laf.WebLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Frame frame = new Frame();
				frame.setVisible(true);
				frame.requestFocus();
			}
		});
	}
}
