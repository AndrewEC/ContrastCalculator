package gov.intra.net.window;

import gov.intra.net.persist.ImageWriter;
import gov.intra.net.util.Contraster;
import gov.intra.net.util.DelayedShot;
import gov.intra.net.util.ICapture;
import gov.intra.net.util.WindowIdentifier;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.win32.W32APIOptions;

public class WindowMagEventHandler implements ActionListener, ICapture {

	private WindowMagnifier mag;
	private DelayedShot shot;
	private User32 instance;

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
		}
	}

	private void startCapture() {
		if (mag.getWindowList().getSelectedIndex() == -1) {
			JOptionPane.showMessageDialog(null, "Please select an item to view.", "Error", JOptionPane.ERROR_MESSAGE);
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
		JOptionPane.showMessageDialog(mag, "An error occured:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		mag.requestFocus();
	}

}
