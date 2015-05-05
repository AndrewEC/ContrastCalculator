package com.intra.net.persist;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ImageWriter {

	private boolean autoTrim = false, useParent = false, rememberPath = false;
	private String name, ext;
	private Component parent;
	private boolean enforceDirectory;

	private static File oldPath;

	public boolean isEnforceDirectory() {
		return enforceDirectory;
	}

	public void setEnforceDirectory(boolean enforceDirectory) {
		this.enforceDirectory = enforceDirectory;
	}

	public void setRememberPath(boolean rememberPath) {
		this.rememberPath = rememberPath;
	}

	public boolean isRemeberPath() {
		return rememberPath;
	}

	public boolean isAutoTrim() {
		return autoTrim;
	}

	public void setAutoTrim(boolean autoTrim) {
		this.autoTrim = autoTrim;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = (autoTrim) ? name.trim() : name;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = (autoTrim) ? ext.trim() : ext;
	}

	public void setParent(Component parent) {
		if (parent != null) {
			useParent = true;
		}
	}

	public boolean validate() {
		if (name.equals("")) {
			String mess = "The specified file name cannot be null, empty or white space.";
			System.err.println(mess);
			if (useParent) {
				JOptionPane.showMessageDialog(parent, mess, "Error", JOptionPane.ERROR_MESSAGE);
			}
			return false;
		}

		if (ext.equals("") || !ext.contains(".")) {
			String mess = "The speficied file extension cannot be null or blank\nand must contain a '.'";
			System.err.println(mess);
			if (useParent) {
				JOptionPane.showMessageDialog(parent, mess, "Error", JOptionPane.ERROR_MESSAGE);
			}
			return false;
		}

		return true;
	}

	public File promptForFile() {
		File path = null;
		JFileChooser chooser = null;

		if (rememberPath && oldPath != null) {
			chooser = new JFileChooser(oldPath.getAbsolutePath());
		} else {
			chooser = new JFileChooser(System.getProperty("user.dir"));
		}

		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int res = chooser.showOpenDialog((useParent) ? parent : null);
		if (res != JFileChooser.APPROVE_OPTION) {
			return null;
		}

		path = chooser.getSelectedFile();

		if (enforceDirectory && !path.isDirectory()) {
			String mess = "Selected path has to be a directory.";
			System.err.println(mess);
			if (useParent) {
				JOptionPane.showMessageDialog(parent, mess, "Error", JOptionPane.ERROR_MESSAGE);
			}
			return null;
		}

		if (rememberPath) {
			oldPath = path;
			if (!path.isDirectory()) {
				String full = oldPath.getAbsolutePath();
				int index = full.lastIndexOf("\\");
				oldPath = new File(full.substring(0, index));
			}
		}

		return path;
	}

	public boolean willOverwrite(String name, String ext, File path) {
		String fName = name + ext;
		File full = new File(path, fName);
		if (full.exists()) {
			return true;
		}
		return false;
	}

	public boolean approveOverwrite() {
		int res = JOptionPane.showConfirmDialog(null, "Specified file already exists, overwrite file?", "Confirm Overwrite", JOptionPane.OK_CANCEL_OPTION);
		if (res == JOptionPane.OK_OPTION) {
			return true;
		}
		return false;
	}

	public void saveImage(BufferedImage image) {
		boolean save = true;
		if (image == null) {
			String mess = "Specified image to save cannot be null.";
			System.err.println(mess);
			if (useParent) {
				JOptionPane.showMessageDialog(parent, mess, "Error", JOptionPane.ERROR_MESSAGE);
			}
			save = false;
		}

		if (!validate()) {
			save = false;
		}

		if (save) {
			File path = promptForFile();

			if (path == null) {
				return;
			}

			if (willOverwrite(name, ext, path)) {
				if (!approveOverwrite()) {
					return;
				}
			}
			try {
				File ff = new File(path, name + ext);
				ImageIO.write(image, ext.replace(".", ""), ff);
				String mess = "Image was successfully saved to\n" + ff.getAbsolutePath();
				System.out.println(mess);
				if (useParent) {
					JOptionPane.showMessageDialog(parent, mess, "Save Complete", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
