package gov.intra.net.window;

import gov.intra.net.util.Contraster;
import gov.intra.net.util.DelayedShot;
import gov.intra.net.util.ICapture;
import gov.intra.net.util.WindowIdentifier;

import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import resources.Constants;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.win32.W32APIOptions;

public class MagCaptureHandle extends MagEventBase implements ICapture {

	private DelayedShot shot;
	private User32 instance;

	public MagCaptureHandle(WindowMagnifier window) {
		super(window);
		instance = (User32) Native.loadLibrary("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);
	}

	public void onEvent(String command) {
		if (command.equals("refresh")) {
			refreshWindowList();
		} else if (command.equals("view window")) {
			startCapture();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void refreshWindowList() {
		List<String> names = WindowIdentifier.getWindowList(instance);
		DefaultListModel model = (DefaultListModel) window.getWindowList().getModel();
		model.clear();
		for (String n : names) {
			model.addElement(n);
		}
	}

	private void startCapture() {
		if (window.getWindowList().getSelectedIndex() == -1) {
			String mess = "Please select an item to view.";
			System.err.println(mess);
			JOptionPane.showMessageDialog(window, mess, "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			String name = window.getWindowList().getSelectedValue().toString();
			if (shot != null) {
				try {
					shot.join();
					shot = null;
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			shot = new DelayedShot(instance, this);
			shot.setDelay(window.getDelay());
			shot.setWindowName(name);
			shot.start();
		}
	}

	public void onCapture(BufferedImage image) {
		try {
			BufferedImage capture = Contraster.convertImage(image, window.getParentFrame().getBlindColour());
			window.getImagePanel().setImage(capture);
			window.requestFocus();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void onCaptureFail(Exception e) {
		if (!e.getMessage().equals(Constants.USER_CANCEL_MESSAGE)) {
			String mess = "An error occured:\n" + e.getMessage();
			System.err.println(mess);
			JOptionPane.showMessageDialog(window, mess, "Error", JOptionPane.ERROR_MESSAGE);
		}
		window.requestFocus();
	}
}
