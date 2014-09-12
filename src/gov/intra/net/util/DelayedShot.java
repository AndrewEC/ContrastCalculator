package gov.intra.net.util;

import java.awt.image.BufferedImage;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;

public class DelayedShot extends Thread {

	private volatile String windowName;
	private final User32 instance;
	private final ICapture cap;
	private volatile BufferedImage image;
	private volatile int delay;

	public DelayedShot(User32 instance, ICapture cap) {
		this.cap = cap;
		this.instance = instance;
	}

	public synchronized void setWindowName(String windowName) {
		this.windowName = windowName;
	}

	public synchronized void setDelay(int delay) {
		this.delay = delay;
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
			cap.onCaptureFail(new Exception("Could not pause thread for specified delay time of " + delay));
		}

		image = Capturer.getScreenShot(r, null);
		cap.onCapture(image);

		hwnd = WindowIdentifier.getHandle(instance, "Contrast Calculator", null);
		if (hwnd == null) {
			cap.onCaptureFail(new Exception("Could not bring Contrast Calculator window back to front."));
			return;
		}
		WindowIdentifier.bringToFront(instance, hwnd);
	}

}
