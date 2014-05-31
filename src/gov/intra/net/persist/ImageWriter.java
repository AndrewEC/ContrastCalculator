package gov.intra.net.persist;

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

	private static File oldPath;

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
		this.name = name;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public void setParent(Component parent) {
		if (parent != null) {
			useParent = true;
		}
	}

	public boolean validate() {
		if (name == null) {
			if (useParent) {
				JOptionPane.showMessageDialog(parent, "The specified file name cannot be null, empty or white space.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			return false;
		}

		if (ext == null) {
			if (useParent) {
				JOptionPane.showMessageDialog(parent, "The speficied file extension cannot be null or blank\nand must contain a '.'", "Error", JOptionPane.ERROR_MESSAGE);
			}
			return false;
		}

		if (autoTrim) {
			name = name.trim();
			ext = ext.trim();
		}

		if (ext.equals("") || !ext.contains(".")) {
			if (useParent) {
				JOptionPane.showMessageDialog(parent, "The speficied file extension cannot be null or blank\nand must contain a '.'", "Error", JOptionPane.ERROR_MESSAGE);
			}
			return false;
		}

		if (name.equals("")) {
			if (useParent) {
				JOptionPane.showMessageDialog(parent, "The specified file name cannot be null, empty or white space.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}

		return true;
	}

	public File promptForFile() {
		File path = null;
		JFileChooser chooser = null;

		if (rememberPath) {
			if (oldPath != null) {
				chooser = new JFileChooser(oldPath.getAbsolutePath());
			} else {
				chooser = new JFileChooser(System.getProperty("user.dir"));
			}
		}

		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int res = chooser.showOpenDialog((useParent) ? parent : null);
		if (res != JFileChooser.APPROVE_OPTION) {
			return null;
		}

		path = chooser.getSelectedFile();

		if (!path.isDirectory()) {
			if (useParent) {
				JOptionPane.showMessageDialog(parent, "Selected path has to be a directory.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			return null;
		}

		if (rememberPath) {
			oldPath = path;
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
	
	public void saveText(){
		
	}

	public void saveImage(BufferedImage image, File path) {
		if (image == null) {
			if (useParent) {
				JOptionPane.showMessageDialog(parent, "Specified image to save cannot be null.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			return;
		}

		if (!validate()) {
			return;
		}

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
			ImageIO.write(image, ext.replace(".", ""), new File(path, name + ext));
			if (useParent) {
				JOptionPane.showMessageDialog(parent, "Image was successfully saved to\n" + ff.getAbsolutePath(), "Save Complete", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
