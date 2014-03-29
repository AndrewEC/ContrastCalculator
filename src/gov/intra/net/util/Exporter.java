package gov.intra.net.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Exporter {

	public static final String[] HEADINGS = new String[] { "Name", "Foreground", "Background", "Ratio", "AA", "AA (L)", "AAA", "AAA (L)" };
	private static final String HTML_DECL = "<!DOCTYPE html><html xml:lang='en-US' lang='en-US'>";
	private static final String HTML_STYLE = "<style>html{ text-align:center; }table{ margin: 0 auto; } table, th, td{ border: 1px solid gray; } .pass{ background-color: rgb(140, 255, 153); } .fail{ background-color: rgb(255, 127, 127); } .header{ background-color: #C6C6C6; }</style>";
	private static final String HTML_HEAD = "<head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/><meta http-equic='encoding' content='utf-8'/>" + HTML_STYLE + "</head>";
	private static final String HTML_BODY_START = "<body><h2>Contrast Testing Results</h2><table>";
	private static final String HTML_BODY_END = "</table></body></html>";

	public static void exportAsText(JTable table, File file, Component parent) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			for (int i = 0; i < model.getRowCount(); i++) {
				for (int j = 0; j < HEADINGS.length; j++) {
					String val = model.getValueAt(i, j).toString();
					writer.write(((j > 0) ? "\t" : "") + HEADINGS[j] + ": " + val);
					writer.newLine();
				}
				writer.newLine();
				writer.flush();
			}
			JOptionPane.showMessageDialog(parent, "Results Table was successfully exported as text to:\n" + file.getAbsolutePath(), "Export Complete", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "Could not export table.", "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void exportAsHtml(JTable table, File file, Component parent) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(HTML_DECL);
			writer.write(HTML_HEAD);
			writer.flush();
			writer.write(HTML_BODY_START);
			writer.flush();
			writer.write("<tr>");
			for (String s : HEADINGS) {
				writer.write("<th class='header'>" + s + "</th>");
			}
			writer.write("</tr>");
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			for (int i = 0; i < model.getRowCount(); i++) {
				writer.write("<tr>");
				for (int j = 0; j < model.getColumnCount(); j++) {
					String val = model.getValueAt(i, j).toString();
					writer.write(String.format("<td %s>", getColor(val)));
					writer.write(val);
					writer.write("</td>");
				}
				writer.write("</tr>");
				writer.flush();
			}
			writer.write(HTML_BODY_END);
			writer.flush();
			JOptionPane.showMessageDialog(parent, "Results Table was successfully exported as html to:\n" + file.getAbsolutePath(), "Export Completed", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "Could not export table.", "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static String getColor(String val) {
		if (val.contains("Pass")) {
			return "class='pass'";
		} else if (val.contains("Fail")) {
			return "class='fail'";
		} else if (Contraster.isValidHex(val)) {
			Color c = Contraster.invert(Contraster.hexToColour(val));
			return String.format("style='background-color: %s; color: rgb(%d, %d, %d);'", val, c.getRed(), c.getBlue(), c.getGreen());
		} else {
			return "background-color: white";
		}
	}

	public static void saveTextFile(String tname, String source, Component parent) {
		File file = preSaveChecks(tname, parent);
		if (file == null) {
			return;
		}

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			String br = System.getProperty("line.separator");
			String finalSource = source.replace("\n", br);
			writer.write(finalSource);
			writer.flush();
			JOptionPane.showMessageDialog(null, "File was successfully saved to:\n" + file.getAbsolutePath(), "Save Complete", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "Could not save text file " + file.getName(), "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void saveImage(String tname, BufferedImage image, String format, Component parent) {
		File file = preSaveChecks(tname, parent);
		if (file == null) {
			return;
		}

		try {
			ImageIO.write(image, format, file);
			JOptionPane.showMessageDialog(parent, String.format("Image has been successfully saved as:\n%s", file.getAbsolutePath()), "Image Saved", JOptionPane.PLAIN_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "Could not save file.\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static File preSaveChecks(String tname, Component parent) {
		String name = tname.trim();

		if (!checkName(tname, parent)) {
			return null;
		}

		File path = getUserFile(parent);
		if (path == null) {
			return null;
		}

		File file = new File(path, name);
		if (!checkOverwrite(file, parent)) {
			return null;
		}

		return file;
	}

	public static boolean checkName(String tname, Component parent) {
		String name = tname.trim();
		if (name.equals("")) {
			JOptionPane.showMessageDialog(parent, "Please enter a valid name", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

	public static File getUserFile(Component parent) {
		File path = null;
		JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = chooser.showOpenDialog(parent);
		if (result != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		path = chooser.getSelectedFile();
		if (!path.isDirectory()) {
			JOptionPane.showMessageDialog(parent, "Please select a directory to save in.", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return path;
	}

	public static boolean checkOverwrite(File file, Component parent) {
		if (file.exists()) {
			int result = JOptionPane.showConfirmDialog(parent, "Specified file already exists, do you want to overwrite it?", "Overwrite Existing File", JOptionPane.OK_CANCEL_OPTION);
			if (result != JOptionPane.OK_OPTION) {
				return false;
			}
		}
		return true;
	}

}
