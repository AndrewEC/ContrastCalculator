package gov.intra.net.frame;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class TrayTask {

	private boolean supported = true;

	public TrayTask(EventDispatcher frameEvent, EventDispatcher panelEvent) {
		if (!SystemTray.isSupported()) {
			supported = false;
			System.err.println("The system tray is not supported on this OS.");
			return;
		}
		buildIcon(frameEvent, panelEvent);
	}

	private void buildIcon(EventDispatcher frameEvent, EventDispatcher panelEvent) {
		try {
			TrayIcon icon = new TrayIcon(ImageIO.read(TrayTask.class.getResourceAsStream("/resources/icon.png")));
			icon.setImageAutoSize(true);
			SystemTray tray = SystemTray.getSystemTray();
			icon.setPopupMenu(buildMenu(frameEvent, panelEvent));
			tray.add(icon);
		} catch (IOException e) {
			e.printStackTrace();
			supported = false;
			String err = "An error occured while trying to add a system tray icon:\n" + e.getMessage();
			JOptionPane.showMessageDialog(null, err, "Error", JOptionPane.ERROR_MESSAGE);
		} catch (AWTException e) {
			e.printStackTrace();
			supported = false;
			String err = "An error occured while trying to add a system tray icon:\n" + e.getMessage();
			JOptionPane.showMessageDialog(null, err, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private PopupMenu buildMenu(EventDispatcher frameEvent, EventDispatcher panelEvent) {
		PopupMenu menu = new PopupMenu();

		MenuItem windowMag = new MenuItem("Open Window Magnifier");
		windowMag.setActionCommand("magnify window");
		windowMag.addActionListener(panelEvent);

		MenuItem openImage = new MenuItem("Open Image");
		openImage.setActionCommand("open image");
		openImage.addActionListener(panelEvent);

		MenuItem exit = new MenuItem("Exit");
		exit.setActionCommand("menu exit");
		exit.addActionListener(frameEvent);

		menu.add(windowMag);
		menu.add(openImage);
		menu.addSeparator();
		menu.add(exit);

		return menu;
	}

	public boolean isSupported() {
		return supported;
	}

}
