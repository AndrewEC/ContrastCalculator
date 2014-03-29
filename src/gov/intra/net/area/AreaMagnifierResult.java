package gov.intra.net.area;

import gov.intra.net.util.Contraster;
import gov.intra.net.util.Exporter;
import gov.intra.net.util.Shot;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import resources.Constants;
import resources.Constants.BlindColour;

@SuppressWarnings("serial")
public class AreaMagnifierResult extends JFrame implements ActionListener, ComponentListener {

	private BufferedImage image;
	private JTextField txtName;
	private JPanel panel;
	private JButton btnSave, btnClose;
	private int size, imageSize;
	private AbstractAction aa;

	public AreaMagnifierResult() {
		super("Area Magnifier Result");
		addComponentListener(this);
		size = Constants.DEFAULT_AREA_MAG_RESULT_SIZE;
		imageSize = Constants.DEFAULT_AREA_MAG_RESULT_SIZE;
		setSize(size, size);
		try {
			InputStream in = AreaMagnifierResult.class.getResourceAsStream("/resources/icon.png");
			BufferedImage image = ImageIO.read(in);
			setIconImage(image);
		} catch (IOException e) {
			e.printStackTrace();
		}

		aa = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				AbstractButton b = (AbstractButton) e.getSource();
				String command = b.getActionCommand();
				if (command != null && !command.equals("")) {
					processCommand(command);
				}
			}
		};

		panel = new JPanel() {
			@Override
			public void paint(Graphics g) {
				if (image != null) {
					g.drawImage(image, 0, 0, this.getSize().width, this.getSize().height, this);
				}
			}
		};
		panel.setBounds(8, 46, 100, 100);
		getContentPane().add(panel);

		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		btnSave = new JButton("Save");
		btnSave.setBounds(13, 7, 89, 23);
		btnSave.setActionCommand("save");
		btnSave.addActionListener(this);
		registerCommand(btnSave, KeyEvent.VK_S);
		getContentPane().add(btnSave);

		txtName = new JTextField();
		txtName.setBounds(150, 9, 86, 20);
		getContentPane().add(txtName);
		txtName.setColumns(10);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(114, 12, 46, 14);
		getContentPane().add(lblName);

		JSeparator separator = new JSeparator();
		separator.setBounds(11, 36, 234, 5);
		getContentPane().add(separator);

		btnClose = new JButton("Close");
		btnClose.setBounds(247, 8, 89, 23);
		btnClose.setActionCommand("close");
		btnClose.addActionListener(this);
		registerCommand(btnClose, KeyEvent.VK_D);
		getContentPane().add(btnClose);

		JButton focus = new JButton("");
		focus.setBounds(-100, -100, 0, 0);
		focus.setActionCommand("focus");
		registerCommand(focus, KeyEvent.VK_F);
		getContentPane().add(focus);
	}

	public void setValues(Point p, int size, BlindColour colour) {
		try {
			BufferedImage block = Shot.getScreenShot(p.x - size / 2, p.y - size / 2, size, size, this);
			image = Contraster.convertImage(block, colour);
			block = null;
			panel.repaint();
			System.gc();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Could not grab specified portion of the screen", "Error", JOptionPane.ERROR_MESSAGE);
			dispose();
		}
	}

	public void saveImage() {
		String name = txtName.getText().trim();
		if (name.equals("")) {
			JOptionPane.showMessageDialog(this, "Please enter a file name.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		BufferedImage buffer = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB);
		Graphics g = buffer.getGraphics();
		g.drawImage(image, 0, 0, imageSize, imageSize, this);
		Exporter.saveImage(name + ".png", buffer, "png", this);
	}

	public void actionPerformed(ActionEvent e) {
		AbstractButton jc = (AbstractButton) e.getSource();
		String command = jc.getActionCommand();
		if (command != null && !command.equals("")) {
			processCommand(command);
		}
	}

	private void processCommand(String command) {
		if (command.equals("save")) {
			saveImage();
		} else if (command.equals("close")) {
			setVisible(false);
		} else if (command.equals("focus")) {
			txtName.requestFocus();
		}
	}

	private void registerCommand(JButton b, int key) {
		String com = b.getActionCommand();
		b.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key, InputEvent.SHIFT_DOWN_MASK), com);
		b.getActionMap().put(com, aa);
	}

	public void componentHidden(ComponentEvent e) {
		image = null;
		System.gc();
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		final int widthMod = Constants.MAG_RESULT_WIDTH_MOD, heightMod = Constants.MAG_RESULT_HEIGHT_MOD;
		size = getSize().width;
		if (getSize().height - heightMod < getSize().width - widthMod) {
			imageSize = getSize().height - heightMod;
		} else {
			imageSize = size - widthMod;
		}
		panel.setSize(imageSize, imageSize);
		panel.repaint();
	}

	public void componentShown(ComponentEvent e) {
	}
}
