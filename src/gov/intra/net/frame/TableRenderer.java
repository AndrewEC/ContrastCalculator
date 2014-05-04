package gov.intra.net.frame;

import gov.intra.net.util.Contraster;
import gov.intra.net.util.HexValidator;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class TableRenderer extends DefaultTableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		String val = model.getValueAt(row, column).toString();
		c.setForeground(Color.black);
		c.setBackground(Color.white);
		if (isSelected) {
			JComponent jc = (JComponent) c;
			jc.setBorder(new MatteBorder(1, (column == 0) ? 1 : 0, 1, (column == 7) ? 1 : 0, Color.black));
		}
		if (HexValidator.isValid6Hex(val)) {
			Color col = HexValidator.hexToColour(val);
			c.setForeground(Contraster.invert(col));
			c.setBackground(col);
		} else if (val.contains("Pass")) {
			c.setBackground(new Color(140, 255, 153));
		} else if (val.contains("Fail")) {
			c.setBackground(new Color(255, 127, 127));
		}
		return c;
	}
}
