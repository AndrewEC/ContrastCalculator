package gov.intra.net.window;

import gov.intra.net.frame.Frame;

import java.awt.Dimension;
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
import javax.swing.ImageIcon;
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

import resources.Constants;

@SuppressWarnings("serial")
public class WindowMagnifier extends JFrame implements ChangeListener, ComponentListener {

	@SuppressWarnings("rawtypes")
	private JList windowList;
	private JButton btnView, btnRefresh, btnSave;
	private JTextField txtFileName;

	private WindowMagImagePanel imagePanel;
	private WindowMagEventHandler event;

	private JRadioButton rbJpg, rbGif, rbPng;
	private ButtonGroup ext;

	private JScrollPane imageScrollPanel;

	private JLabel lblZoom, lblViewDelay;
	private JSlider zoomSlider, delaySlider;

	private final Frame frame;
	private JSeparator separator_1;

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
		setMinimumSize(new Dimension(800, 630));
		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		addComponentListener(this);

		event = new WindowMagEventHandler(this);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(19, 16, 145, 176);
		getContentPane().add(scrollPane);

		windowList = new JList(new DefaultListModel());
		scrollPane.setViewportView(windowList);

		btnView = new JButton("View Selected");
		try {
			btnView.setIcon(new ImageIcon(WindowMagnifier.class.getResource("/resources/ViewIcon.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		btnView.setToolTipText("Capture the currently selected window");
		btnView.setBounds(22, 226, 140, 24);
		btnView.addActionListener(event);
		getContentPane().add(btnView);

		btnRefresh = new JButton("Refresh List");
		try {
			btnRefresh.setIcon(new ImageIcon(WindowMagnifier.class.getResource("/resources/SwapIcon.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		btnRefresh.setToolTipText("Refresh the list of currently open windows");
		btnRefresh.setBounds(22, 198, 140, 24);
		btnRefresh.addActionListener(event);
		getContentPane().add(btnRefresh);

		imageScrollPanel = new JScrollPane();
		imageScrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		imageScrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		imageScrollPanel.setBounds(177, 1, 616, 608);
		getContentPane().add(imageScrollPanel);

		imagePanel = new WindowMagImagePanel();
		imageScrollPanel.setViewportView(imagePanel);

		btnSave = new JButton();
		btnSave.setText("Save Capture");
		try {
			btnSave.setIcon(new ImageIcon(WindowMagnifier.class.getResource("/resources/SaveIcon.png")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		btnSave.setToolTipText("Save current image");
		btnSave.setBounds(21, 268, 140, 24);
		btnSave.addActionListener(event);
		getContentPane().add(btnSave);

		txtFileName = new JTextField();
		txtFileName.setBounds(21, 318, 134, 28);
		getContentPane().add(txtFileName);
		txtFileName.setColumns(10);

		JLabel lblSaveName = new JLabel("Save Name:");
		lblSaveName.setBounds(11, 299, 99, 16);
		getContentPane().add(lblSaveName);

		lblZoom = new JLabel("Zoom: 2");
		lblZoom.setBounds(14, 456, 119, 16);
		getContentPane().add(lblZoom);

		zoomSlider = new JSlider();
		zoomSlider.addChangeListener(this);
		zoomSlider.setValue(20);
		zoomSlider.setMajorTickSpacing(2);
		zoomSlider.setMinimum(1);
		zoomSlider.setMaximum(50);
		zoomSlider.setBounds(13, 480, 154, 21);
		getContentPane().add(zoomSlider);

		JSeparator separator = new JSeparator();
		separator.setBounds(8, 446, 160, 6);
		getContentPane().add(separator);

		JLabel lblImageType = new JLabel("Image Type:");
		lblImageType.setBounds(11, 354, 152, 16);
		getContentPane().add(lblImageType);

		rbJpg = new JRadioButton(".jpg");
		rbJpg.setBounds(25, 377, 115, 18);
		getContentPane().add(rbJpg);

		rbGif = new JRadioButton(".gif");
		rbGif.setBounds(25, 398, 115, 18);
		getContentPane().add(rbGif);

		rbPng = new JRadioButton(".png");
		rbPng.setBounds(25, 419, 115, 18);
		rbPng.setSelected(true);
		getContentPane().add(rbPng);

		ext = new ButtonGroup();
		ext.add(rbJpg);
		ext.add(rbGif);
		ext.add(rbPng);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(7, 508, 160, 6);
		getContentPane().add(separator_2);

		lblViewDelay = new JLabel("View Delay: 500 Millis");
		lblViewDelay.setBounds(9, 522, 161, 16);
		getContentPane().add(lblViewDelay);

		delaySlider = new JSlider();
		delaySlider.setValue(500);
		delaySlider.setMinimum(500);
		delaySlider.setMaximum(3000);
		delaySlider.setBounds(10, 544, 154, 21);
		delaySlider.addChangeListener(this);
		getContentPane().add(delaySlider);

		separator_1 = new JSeparator();
		separator_1.setBounds(19, 258, 144, 6);
		getContentPane().add(separator_1);
	}

	public JButton getViewButton() {
		return btnView;
	}

	public JButton getRefreshButton() {
		return btnRefresh;
	}

	public JButton getSaveButton() {
		return btnSave;
	}

	public JTextField getFileName() {
		return txtFileName;
	}

	@SuppressWarnings("rawtypes")
	public JList getWindowList() {
		return windowList;
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
		if (e.getSource() == this) {
			imageScrollPanel.setSize(this.getWidth() - Constants.IMAGE_SCROLL_PANE_MARGIN_RIGHT, this.getHeight() - Constants.IMAGE_SCROLL_PANE_MARGIN_BOTTOM);
			this.revalidate();
		}
	}

	public void componentShown(ComponentEvent e) {
	}
}
