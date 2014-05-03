package gov.intra.net.util;

import java.awt.image.BufferedImage;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;

public class DelayedShot extends Thread implements Runnable {

	private String windowName;
	private final User32 instance;
	private final ICapture cap;
	private BufferedImage image;
	private int delay;

	public DelayedShot(User32 instance, int delay, ICapture cap) {
		if(instance == null){
			throw new IllegalArgumentException("User32 instance cannot be null.");
		}
		if(cap == null){
			throw new IllegalArgumentException("ICapture instance cannot be null.");
		}
		this.cap = cap;
		this.instance = instance;
		this.delay = delay;
	}
	
	public void setWindowName(String windowName) {
		this.windowName = windowName;
	}

	@Override
	public void run() {
		HWND hwnd = WindowIdentifier.getHandle(instance, windowName, null);
		if (hwnd == null) {
			cap.onCaptureFail(new Exception("Could not get window handle."));
			return;
		}
		WindowIdentifier.bringToFront(instance, hwnd);

		RECT r = WindowIdentifier.getPosition(instance, hwnd, null);
		if (r == null) {
			cap.onCaptureFail(new Exception("Could not get window boundaries."));
			return;
		}

		try {
			sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		image = Shot.getScreenShot(r, null);
		cap.onCapture(image);

		hwnd = WindowIdentifier.getHandle(instance, "Contrast Calculator", null);
		if (hwnd == null) {
			cap.onCaptureFail(new Exception("Could not bring Contrast Calculator window back to front."));
			return;
		}
		WindowIdentifier.bringToFront(instance, hwnd);
	}

}
