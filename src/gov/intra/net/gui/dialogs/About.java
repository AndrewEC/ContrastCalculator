package gov.intra.net.gui.dialogs;

import gov.intra.net.gui.Frame;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JEditorPane;

@SuppressWarnings("serial")
public class About extends GenericDialog {

	public About(Frame parent) {
		super(parent, "About Contrast Calculator");
		setBounds(100, 100, 283, 282);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(this);
		btnClose.setBounds(188, 225, 85, 25);
		btnClose.setActionCommand("close");
		registerForClose(btnClose);
		getContentPane().add(btnClose);

		JEditorPane content = new JEditorPane();
		Font font = new Font("Verdana", Font.PLAIN, 14);
		content.setFont(font);
		content.setContentType("text/html");
		content.setText("<html>\r\n\t<style>span{ font-family:Verdana; } div{ padding-left: 10px; }</style>\r\n\t<span>Contrast Calculator v1.2.1</span><br>\r\n\t<div>\r\n\t\t<span>3rd Party Libraries<span>\r\n\t\t\t<div><span>JNA 4.0</span></div>\r\n\t\t\t<div><span>Weblaf 1.2.5 Look and Feel</span></div>\r\n\t</div>\r\n\t<br>\r\n<span>&copy; 2014 - Ministry of community and Social Services I&IT Cluster - Accessibility Center of Excellence.</span>\r\n\t<br><br>\r\n<span>Designer: Andrew Cumming<br>\r\nEmail: andrew.cumming@ontario.ca</span>\r\n</html>");
		content.setBounds(2, 7, 271, 214);
		getContentPane().add(content);

		JButton focus = new JButton("");
		focus.setActionCommand("focus");
		focus.setBounds(-100, -100, 0, 0);
		registerForFocus(focus, content);
	}
}
