package gov.intra.net.dialogs;

import gov.intra.net.frame.Frame;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

@SuppressWarnings("serial")
public class Shortcuts extends GenericDialog {

	private JTable table;

	public Shortcuts(Frame frame) {
		super(frame, "Shortcuts");
		setBounds(100, 100, 450, 476);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 444, 377);
		getContentPane().add(scrollPane);

		DefaultTableModel data = new DefaultTableModel(new Object[][] {}, new String[] { "Shortcut", "Action" });
		data.addRow(new Object[] { "", "Main Window" });
		data.addRow(new Object[] { "Shift + Q", "Make the contraster tab active." });
		data.addRow(new Object[] { "Shift + W", "Make the results tab active." });
		data.addRow(new Object[] { "Shift + A", "Open the about dialog." });
		data.addRow(new Object[] { "Shift + G", "Open the guidelines dialog." });
		data.addRow(new Object[] { "Shift + U", "Open the shortcuts dialog." });
		data.addRow(new Object[] { "Shift + M", "Open the built in guide dialog." });
		data.addRow(new Object[] { "Shift + N", "Open the colour picker wheel." });
		data.addRow(new Object[] { "", "" });

		data.addRow(new Object[] { "", "Contraster Tab" });
		data.addRow(new Object[] { "Shift + Z", "Open pixel dropper tool to select foreground colour." });
		data.addRow(new Object[] { "Shift + X", "Open pixel dropper tool to select background colour." });
		data.addRow(new Object[] { "Shift + V", "Open area magnifier." });
		data.addRow(new Object[] { "Shift + C", "Open Window Magnifier." });
		data.addRow(new Object[] { "Shift + F", "Bring focus to the foreground hex text edit box." });
		data.addRow(new Object[] { "Shift + B", "Bring focus to the background hex text edit box." });
		data.addRow(new Object[] { "Shift + W", "Swap the background and foreground colours." });
		data.addRow(new Object[] { "Shift + D", "Open the details dialog window." });
		data.addRow(new Object[] { "", "" });

		data.addRow(new Object[] { "", "Results Tab" });
		data.addRow(new Object[] { "Shift + D", "Delete selected record from table." });
		data.addRow(new Object[] { "Shift + N", "Add current result to table." });
		data.addRow(new Object[] { "Shift + F", "Bring focus to the result name text edit box." });
		data.addRow(new Object[] { "Shift + S", "Export table." });
		data.addRow(new Object[] { "Shift + T", "Bring focus to the table." });
		data.addRow(new Object[] { "Shift + H", "Set table export type to html." });
		data.addRow(new Object[] { "Shift + J", "Set table export type to text." });
		data.addRow(new Object[] { "", "" });

		data.addRow(new Object[] { "", "Options" });
		data.addRow(new Object[] { "Control + O", "Bring focus to the main panel." });
		data.addRow(new Object[] { "Shift + Y", "Enable or disable forcing the window to stay on top." });
		data.addRow(new Object[] { "Shift + I", "Enable or disable invering pixel dropper outline." });
		data.addRow(new Object[] { "Shift + E", "Enable or diable window magnifier." });
		data.addRow(new Object[] { "Shift + L", "Select export location for results table." });
		data.addRow(new Object[] { "Shift + R", "Enable or disable RGB sliders." });
		data.addRow(new Object[] { "Shift + P", "Enable or diable colour blind picking." });
		data.addRow(new Object[] { "Shift + (0-9)", "Set the colour blind mask option." });
		data.addRow(new Object[] { "", "" });

		data.addRow(new Object[] { "", "Area Magnifier Result" });
		data.addRow(new Object[] { "Shift + F", "Bring focus to the save name text edit field." });
		data.addRow(new Object[] { "Shift + S", "Save the grabbed image in its current size." });
		data.addRow(new Object[] { "Shift + D", "Close the Area Magnifier Window" });
		data.addRow(new Object[] { "", "" });

		data.addRow(new Object[] { "", "Pixel Dropper and Area Magnifier" });
		data.addRow(new Object[] { "Escape", "Exit the dropper or area magnifier." });
		data.addRow(new Object[] { "Right Click", "Exit the dropper or area magnifier." });
		data.addRow(new Object[] { "", "" });

		data.addRow(new Object[] { "", "View Details Dialog" });
		data.addRow(new Object[] { "Shift + D", "Close the View Details dialog." });
		data.addRow(new Object[] { "Shift + F", "Bring focus to the enter name text edit box." });
		data.addRow(new Object[] { "Shift + S", "Save the current information in a text file." });
		data.addRow(new Object[] { "Shift + R", "Copy detailed information to clipboard." });
		data.addRow(new Object[] { "", "" });

		data.addRow(new Object[] { "", "About Dialog" });
		data.addRow(new Object[] { "Shift + D", "Close the About Dialog." });
		data.addRow(new Object[] { "Shift + F", "Focus on about text box." });
		data.addRow(new Object[] { "", "" });

		data.addRow(new Object[] { "", "Guidelines Dialog" });
		data.addRow(new Object[] { "Shift + D ", "Close the Guidelines Dialog." });
		data.addRow(new Object[] { "Shift + F", "Focus on guidelines text box." });
		data.addRow(new Object[] { "", "" });

		table = new JTable(data) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		TableColumnModel model = table.getColumnModel();
		model.getColumn(0).setPreferredWidth(40);
		model.getColumn(1).setPreferredWidth(300);
		table.setColumnModel(model);
		scrollPane.setViewportView(table);

		JLabel lblIfTheShortcut = new JLabel("<html>If the shortcut keys aren't working then click the \"Get Focus\"<br>button and try again.<html>");
		lblIfTheShortcut.setBounds(7, 395, 353, 31);
		getContentPane().add(lblIfTheShortcut);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(this);
		btnClose.setBounds(350, 418, 85, 25);
		btnClose.setActionCommand("close");
		registerForClose(btnClose);
		getContentPane().add(btnClose);
	}
}
