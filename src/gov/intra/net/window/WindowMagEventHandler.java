package gov.intra.net.window;

import gov.intra.net.persist.ImageWriter;
import gov.intra.net.util.Contraster;
import gov.intra.net.util.DelayedShot;
import gov.intra.net.util.ICapture;
import gov.intra.net.util.WindowIdentifier;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.win32.W32APIOptions;

public class WindowMagEventHandler implements ActionListener, ICapture {

	private WindowMagnifier mag;
	private DelayedShot shot;
	private User32 instance;
	private ImageWriter iw;

	public WindowMagEventHandler(WindowMagnifier mag) {
		this.mag = mag;
		instance = (User32) Native.loadLibrary("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mag.getRefreshButton()) {
			refreshWindowList();
		} else if (e.getSource() == mag.getViewButton()) {
			startCapture();
		} else if (e.getSource() == mag.getSaveButton()) {
			saveImage();
		} else if (e.getSource() == mag.getOpenButton()) {
			loadImage();
		}
	}

	private void startCapture() {
		if (mag.getWindowList().getSelectedIndex() == -1) {
			String mess = "Please select an item to view.";
			System.err.println(mess);
			JOptionPane.showMessageDialog(mag, mess, "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			String name = mag.getWindowList().getSelectedValue().toString();
			if (shot != null) {
				try {
					shot.join();
					shot = null;
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			shot = new DelayedShot(instance, this);
			shot.setDelay(mag.getDelay());
			shot.setWindowName(name);
			shot.start();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void refreshWindowList() {
		List<String> names = WindowIdentifier.getWindowList(instance);
		DefaultListModel model = (DefaultListModel) mag.getWindowList().getModel();
		model.clear();
		for (String n : names) {
			model.addElement(n);
		}
	}

	private void saveImage() {
		ImageWriter iw = new ImageWriter();
		iw.setRememberPath(true);
		iw.setAutoTrim(true);
		iw.setParent(mag);
		iw.setName(mag.getFileName().getText());
		iw.setExt(mag.getExt());
		iw.saveImage(mag.getImagePanel().getImage());
	}

	public void loadImage() {
		iw = new ImageWriter();
		iw.setRememberPath(true);
		iw.setEnforceDirectory(false);
		iw.setParent(mag);
		File file = iw.promptForFile();
		if (file != null && !file.isDirectory()) {
			try {
				BufferedImage image = ImageIO.read(file);
				BufferedImage con = Contraster.convertImage(image, mag.getParentFrame().getBlindColour());
				mag.getImagePanel().setImage(con);
			} catch (IOException e) {
				e.printStackTrace();
				String err = "An error occured while loading the image:\n" + e.getMessage();
				JOptionPane.showMessageDialog(mag, err, "Error", JOptionPane.ERROR_MESSAGE);
			}

		}
	}

	public void onCapture(BufferedImage image) {
		try {
			BufferedImage capture = Contraster.convertImage(image, mag.getParentFrame().getBlindColour());
			mag.getImagePanel().setImage(capture);
			mag.requestFocus();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void onCaptureFail(Exception e) {
		String mess = "An error occured:\n" + e.getMessage();
		System.err.println(mess);
		JOptionPane.showMessageDialog(mag, mess, "Error", JOptionPane.ERROR_MESSAGE);
		mag.requestFocus();
	}

}
