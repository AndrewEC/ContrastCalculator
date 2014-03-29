package gov.intra.net.window;

import gov.intra.net.gui.Frame;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class WindowMagnifier extends JFrame implements ChangeListener, ComponentListener {

	@SuppressWarnings("rawtypes")
	public JList windowList;
	public JButton btnView, btnRefresh, btnSave;
	public JTextField txtFileName;

	private WindowMagImagePanel imagePanel;
	private WindowMagEventHandler event;
	private JSeparator separator_1;

	private JRadioButton rbJpg, rbGif, rbPng;
	private ButtonGroup ext;

	private JLabel lblZoom, lblViewDelay;
	private JSlider zoomSlider, delaySlider;

	private final Frame frame;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public WindowMagnifier(Frame frame) {
		super("Magnifier");

		this.frame = frame;

		try {
			InputStream in = WindowMagnifier.class.getResourceAsStream("/resources/icon.png");
			BufferedImage image = ImageIO.read(in);
			setIconImage(image);
		} catch (IOException e) {
			e.printStackTrace();
		}

		setVisible(false);
		setSize(800, 638);
		setResizable(false);
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		addComponentListener(this);

		event = new WindowMagEventHandler(this);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(19, 16, 145, 176);
		getContentPane().add(scrollPane);

		windowList = new JList(new DefaultListModel());
		scrollPane.setViewportView(windowList);

		btnView = new JButton("View");
		btnView.setBounds(18, 198, 145, 23);
		btnView.addActionListener(event);
		getContentPane().add(btnView);

		btnRefresh = new JButton("Refresh");
		btnRefresh.setBounds(18, 223, 144, 23);
		btnRefresh.addActionListener(event);
		getContentPane().add(btnRefresh);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setBounds(177, 1, 616, 608);
		getContentPane().add(scrollPane_1);

		imagePanel = new WindowMagImagePanel();
		scrollPane_1.setViewportView(imagePanel);

		btnSave = new JButton("Save");
		btnSave.setBounds(19, 263, 142, 23);
		btnSave.addActionListener(event);
		getContentPane().add(btnSave);

		txtFileName = new JTextField();
		txtFileName.setBounds(20, 309, 134, 28);
		getContentPane().add(txtFileName);
		txtFileName.setColumns(10);

		JLabel lblSaveName = new JLabel("Save Name:");
		lblSaveName.setBounds(10, 290, 99, 16);
		getContentPane().add(lblSaveName);

		lblZoom = new JLabel("Zoom: 2");
		lblZoom.setBounds(13, 447, 119, 16);
		getContentPane().add(lblZoom);

		zoomSlider = new JSlider();
		zoomSlider.addChangeListener(this);
		zoomSlider.setValue(20);
		zoomSlider.setMajorTickSpacing(2);
		zoomSlider.setMinimum(1);
		zoomSlider.setMaximum(50);
		zoomSlider.setBounds(12, 471, 154, 21);
		getContentPane().add(zoomSlider);

		JSeparator separator = new JSeparator();
		separator.setBounds(7, 437, 160, 6);
		getContentPane().add(separator);

		separator_1 = new JSeparator();
		separator_1.setBounds(9, 253, 159, 8);
		getContentPane().add(separator_1);

		JLabel lblImageType = new JLabel("Image Type:");
		lblImageType.setBounds(10, 345, 152, 16);
		getContentPane().add(lblImageType);

		rbJpg = new JRadioButton(".jpg");
		rbJpg.setBounds(24, 368, 115, 18);
		getContentPane().add(rbJpg);

		rbGif = new JRadioButton(".gif");
		rbGif.setBounds(24, 389, 115, 18);
		getContentPane().add(rbGif);

		rbPng = new JRadioButton(".png");
		rbPng.setBounds(24, 410, 115, 18);
		rbPng.setSelected(true);
		getContentPane().add(rbPng);

		ext = new ButtonGroup();
		ext.add(rbJpg);
		ext.add(rbGif);
		ext.add(rbPng);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(6, 499, 160, 6);
		getContentPane().add(separator_2);

		lblViewDelay = new JLabel("View Delay: 500 Millis");
		lblViewDelay.setBounds(8, 513, 161, 16);
		getContentPane().add(lblViewDelay);

		delaySlider = new JSlider();
		delaySlider.setValue(500);
		delaySlider.setMinimum(500);
		delaySlider.setMaximum(3000);
		delaySlider.setBounds(9, 535, 154, 21);
		delaySlider.addChangeListener(this);
		getContentPane().add(delaySlider);
	}

	public String getExt() {
		for (Enumeration<AbstractButton> e = ext.getElements(); e.hasMoreElements();) {
			JRadioButton r = (JRadioButton) e.nextElement();
			if (r.isSelected()) {
				return r.getText();
			}
		}
		return "";
	}

	public WindowMagEventHandler getEvent() {
		return event;
	}

	public WindowMagImagePanel getImagePanel() {
		return imagePanel;
	}

	public Frame getParentFrame() {
		return frame;
	}

	public int getDelay() {
		return delaySlider.getValue();
	}

	public void openMagnifier() {
		setVisible(true);
		event.getTimer().start();
	}

	public void closeMagnifier() {
		setVisible(false);
		event.getTimer().stop();
	}

	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == zoomSlider) {
			double val = zoomSlider.getValue() / 10.0;
			lblZoom.setText(String.format("Zoom: %.2f", val));
			imagePanel.setZoom(val);
		} else if (e.getSource() == delaySlider) {
			lblViewDelay.setText(String.format("View Delay: %d Millis", delaySlider.getValue()));
		}
	}

	public void componentHidden(ComponentEvent e) {
		imagePanel.setImage(null);
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
	}

	public void componentShown(ComponentEvent e) {
	}
}
