package gov.intra.net.util;

import java.awt.image.BufferedImage;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;

public class DelayedShot extends Thread implements Runnable {

	private String windowName;
	private final User32 instance;
	private boolean cap, failed;
	private BufferedImage image;
	private int delay;

	public DelayedShot(User32 instance) {
		this.instance = instance;
		delay = 500;
	}

	public DelayedShot(User32 instance, int delay) {
		this.instance = instance;
		this.delay = delay;
	}

	@Override
	public void run() {
		HWND hwnd = Windower.getHandle(instance, windowName, null);
		if (hwnd == null) {
			failed = true;
			return;
		}
		Windower.bringToFront(instance, hwnd);

		RECT r = Windower.getPosition(instance, hwnd, null);
		if (r == null) {
			failed = true;
			return;
		}

		try {
			sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		image = Shot.getScreenShot(r, null);
		cap = true;

		hwnd = Windower.getHandle(instance, "Contrast Calculator", null);
		if (hwnd == null) {
			failed = true;
			return;
		}
		Windower.bringToFront(instance, hwnd);
	}

	public void setWindowName(String windowName) {
		this.windowName = windowName;
	}

	public boolean hasImage() {
		return cap;
	}

	public boolean hasFailed() {
		return failed;
	}

	public BufferedImage getImage() {
		return image;
	}

}
