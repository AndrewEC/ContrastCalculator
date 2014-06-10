package gov.intra.net.dialogs;

import gov.intra.net.frame.Frame;

import java.awt.Rectangle;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

@SuppressWarnings("serial")
public class Shortcuts extends GenericDialog {

	private JTable table;

	public Shortcuts(Frame frame) {
		super(frame, "Shortcuts");
		setBounds(100, 100, 506, 435);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 498, 377);
		getContentPane().add(scrollPane);

		DefaultTableModel data = new DefaultTableModel(new Object[][] {}, new String[] { "Shortcut", "Action" });
		data.addRow(new Object[] { "", "Main Window" });
		data.addRow(new Object[] { "Control + Shift + A", "Open the about dialog." });
		data.addRow(new Object[] { "Control + Shift + G", "Open the guidelines dialog." });
		data.addRow(new Object[] { "Control + Shift + U", "Open the shortcuts dialog." });
		data.addRow(new Object[] { "Control + Shift + M", "Open the built in guide dialog." });
		data.addRow(new Object[] { "Control + Shift + N", "Open the colour picker wheel." });
		data.addRow(new Object[] { "", "" });

		data.addRow(new Object[] { "", "Contraster Tab" });
		data.addRow(new Object[] { "Control + Shift + Z", "Open pixel dropper tool to select foreground colour." });
		data.addRow(new Object[] { "Control + Shift + X", "Open pixel dropper tool to select background colour." });
		data.addRow(new Object[] { "Control + Shift + V", "Open area snipper." });
		data.addRow(new Object[] { "Control + Shift + C", "Open Window Magnifier." });
		data.addRow(new Object[] { "Control + Shift + F", "Bring focus to the foreground hex text edit box." });
		data.addRow(new Object[] { "Control + Shift + B", "Bring focus to the background hex text edit box." });
		data.addRow(new Object[] { "Control + Shift + W", "Swap the background and foreground colours." });
		data.addRow(new Object[] { "Control + Shift + D", "Open the details dialog window." });
		data.addRow(new Object[] { "", "" });

		data.addRow(new Object[] { "", "Options" });
		data.addRow(new Object[] { "Control + Shift + O", "Bring focus to the main panel." });
		data.addRow(new Object[] { "Control + Shift + Y", "Enable or disable forcing the window to stay on top." });
		data.addRow(new Object[] { "Control + Shift + I", "Enable or disable invering pixel dropper outline." });
		data.addRow(new Object[] { "Control + Shift + E", "Enable or diable window magnifier." });
		data.addRow(new Object[] { "Control + Shift + L", "Select export location for results table." });
		data.addRow(new Object[] { "Control + Shift + R", "Enable or disable RGB sliders." });
		data.addRow(new Object[] { "Control + Shift + P", "Enable or diable colour blind picking." });
		data.addRow(new Object[] { "Control + Shift + (0-9)", "Set the colour blind mask option." });
		data.addRow(new Object[] { "", "" });

		data.addRow(new Object[] { "", "Area Snipper Result" });
		data.addRow(new Object[] { "Control + Shift + F", "Bring focus to the save name text edit field." });
		data.addRow(new Object[] { "Control + Shift + S", "Save the grabbed image in its current size." });
		data.addRow(new Object[] { "Control + Shift + D", "Close the Area Snipper Result Window" });
		data.addRow(new Object[] { "", "" });

		data.addRow(new Object[] { "", "Pixel Dropper and Area Magnifier" });
		data.addRow(new Object[] { "Escape", "Exit the dropper or area magnifier." });
		data.addRow(new Object[] { "Right Click", "Exit the dropper or area magnifier." });
		data.addRow(new Object[] { "Left Click", "Grab the colour of the currently selected pixel." });
		data.addRow(new Object[] { "", "" });

		data.addRow(new Object[] { "", "View Details Dialog" });
		data.addRow(new Object[] { "Control + Shift + D", "Close the View Details dialog." });
		data.addRow(new Object[] { "Control + Shift + F", "Bring focus to the enter name text edit box." });
		data.addRow(new Object[] { "Control + Shift + S", "Save the current information in a text file." });
		data.addRow(new Object[] { "Control + Shift + R", "Copy detailed information to clipboard." });
		data.addRow(new Object[] { "", "" });

		data.addRow(new Object[] { "", "About Dialog" });
		data.addRow(new Object[] { "Control + Shift + D", "Close the About Dialog." });
		data.addRow(new Object[] { "Control + Shift + F", "Focus on about text box." });
		data.addRow(new Object[] { "", "" });

		data.addRow(new Object[] { "", "Guidelines Dialog" });
		data.addRow(new Object[] { "Control + Shift + D ", "Close the Guidelines Dialog." });
		data.addRow(new Object[] { "Control + Shift + F", "Focus on guidelines text box." });
		data.addRow(new Object[] { "", "" });

		table = new JTable(data) {
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		TableColumnModel model = table.getColumnModel();
		model.getColumn(0).setPreferredWidth(90);
		model.getColumn(1).setPreferredWidth(300);
		table.setColumnModel(model);
		scrollPane.setViewportView(table);

		buildCloseButton(new Rectangle(395, 380, 89, 23));
	}
}
