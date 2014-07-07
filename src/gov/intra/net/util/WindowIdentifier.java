package gov.intra.net.util;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;

public class WindowIdentifier {

	public static List<String> getWindowList(final User32 instance) {
		final List<String> names = new ArrayList<String>();
		instance.EnumWindows(new WNDENUMPROC() {
			public boolean callback(HWND hwnd, Pointer pointer) {
				if (!instance.IsWindowVisible(hwnd)) {
					return true;
				}
				char[] windowText = new char[512];
				instance.GetWindowText(hwnd, windowText, 512);
				String name = new String(windowText);
				if (name.trim().isEmpty()) {
					return true;
				}
				names.add(name.trim());
				return true;
			}
		}, Pointer.NULL);
		return names;
	}

	public static HWND getHandle(User32 instance, String windowName, Component parent) {
		HWND hwnd = instance.FindWindow(null, windowName);
		if (hwnd == null) {
			String mess = "Failed to find specified window.";
			System.err.println(mess);
			JOptionPane.showMessageDialog(parent, mess, "Error", JOptionPane.ERROR_MESSAGE);
			return hwnd;
		}
		return hwnd;
	}

	public static void bringToFront(User32 instance, HWND hwnd) {
		if (hwnd != null) {
			instance.SetForegroundWindow(hwnd);
		}
	}

	public static RECT getPosition(User32 instance, HWND hwnd, Component parent) {
		RECT r = new RECT();
		boolean result = instance.GetWindowRect(hwnd, r);
		if (!result) {
			String mess = "Couldn't get boundaries of window for capture.";
			System.err.println(mess);
			JOptionPane.showMessageDialog(parent, mess, "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return r;
	}
}
