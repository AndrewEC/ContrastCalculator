package gov.intra.net.area;

import gov.intra.net.persist.ClipboardImage;
import gov.intra.net.persist.ImageWriter;
import gov.intra.net.util.Contraster;
import gov.intra.net.util.Shot;

import java.awt.Graphics;
import java.awt.Rectangle;
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
public class AreaSnipperResult extends JFrame implements ActionListener, ComponentListener {

	private BufferedImage image;
	private JTextField txtName;
	private JPanel panel;
	private JButton btnSave, btnClose;
	private AbstractAction aa;
	private JButton btnCopyImage;

	public AreaSnipperResult() {
		super("Area Snipper Result");

		try {
			InputStream in = AreaSnipperResult.class.getResourceAsStream("/resources/icon.png");
			BufferedImage image = ImageIO.read(in);
			setIconImage(image);
		} catch (IOException e) {
			e.printStackTrace();
		}

		setResizable(false);
		addComponentListener(this);

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
					g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), this);
				}
			}
		};
		panel.setBounds(11, 75, 100, 100);
		getContentPane().add(panel);

		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		btnSave = new JButton("Save");
		btnSave.setBounds(8, 7, 89, 23);
		btnSave.setActionCommand("save");
		btnSave.addActionListener(this);
		registerCommand(btnSave, KeyEvent.VK_S);
		getContentPane().add(btnSave);

		txtName = new JTextField();
		txtName.setBounds(141, 9, 86, 20);
		getContentPane().add(txtName);
		txtName.setColumns(10);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(102, 12, 46, 14);
		getContentPane().add(lblName);

		JSeparator separator = new JSeparator();
		separator.setBounds(11, 63, 234, 5);
		getContentPane().add(separator);

		btnClose = new JButton("Close");
		btnClose.setBounds(128, 33, 89, 23);
		btnClose.setActionCommand("close");
		btnClose.addActionListener(this);
		registerCommand(btnClose, KeyEvent.VK_D);
		getContentPane().add(btnClose);

		JButton focus = new JButton("");
		focus.setBounds(-100, -100, 0, 0);
		focus.setActionCommand("focus");
		registerCommand(focus, KeyEvent.VK_F);
		getContentPane().add(focus);

		btnCopyImage = new JButton("Copy Image");
		btnCopyImage.setBounds(8, 33, 115, 23);
		btnCopyImage.addActionListener(this);
		btnCopyImage.setActionCommand("copy image");
		getContentPane().add(btnCopyImage);
	}

	public void setValues(Rectangle r, BlindColour colour) {
		try {
			BufferedImage block = Shot.getScreenShot(r.x, r.y, r.width, r.height, this);
			image = Contraster.convertImage(block, colour);
			block = null;
			sizeComponents(r.width, r.height);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Could not grab specified portion of the screen", "Error", JOptionPane.ERROR_MESSAGE);
			dispose();
		}
	}

	private void sizeComponents(int width, int height) {
		if (width < 350) {
			width = 350;
		}
		if (height < 100) {
			height = 100;
		}
		panel.setSize(width, height);
		this.setSize(width + Constants.MAG_RESULT_WIDTH_MOD + 10, height + Constants.MAG_RESULT_HEIGHT_MOD + 10);
		panel.repaint();
	}

	public void saveImage() {
		ImageWriter iw = new ImageWriter();
		iw.setExt(".png");
		iw.setName(txtName.getText());
		iw.setAutoTrim(true);
		iw.setRememberPath(true);
		iw.setParent(this);
		iw.saveImage(image, iw.promptForFile());
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
		} else if (command.equals("copy image")) {
			new ClipboardImage(image).addToClipboard();
		}
	}

	private void registerCommand(JButton b, int key) {
		String com = b.getActionCommand();
		b.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(key, InputEvent.SHIFT_DOWN_MASK), com);
		b.getActionMap().put(com, aa);
	}

	public void componentHidden(ComponentEvent e) {
		image = null;
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
	}

	public void componentShown(ComponentEvent e) {
	}
}
