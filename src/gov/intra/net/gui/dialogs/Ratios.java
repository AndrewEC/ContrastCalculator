package gov.intra.net.gui.dialogs;

import gov.intra.net.gui.Frame;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class Ratios extends GenericDialog {

	public Ratios(Frame frame) {
		super(frame, "About Contrast Requirements");
		setSize(563, 373);
		getContentPane().setLayout(new BorderLayout());

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(this);
		btnClose.setBounds(464, 320, 89, 23);
		btnClose.setActionCommand("close");
		registerForClose(btnClose);
		panel.add(btnClose);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(2, 3, 554, 311);
		panel.add(scrollPane);

		JEditorPane content = new JEditorPane();
		scrollPane.setViewportView(content);
		content.setContentType("text/html");
		content.setText("<html>\r\n<style>\r\ndiv{\r\n\tpadding-left: 20px;\r\n\tfont-family: Verdana;\r\n}\r\nh1{ font-family: Verdana; }\r\nspan{ font-family: Verdana }\r\nli { font-family: Verdana; }\r\n</style>\r\n<h1>Contrast Ratios</h1>\r\n<ul>\r\n\t<li>AA Small requires a contrast ratio of at least 4.5:1</li>\r\n\t<li>AA Large requires a contrast ratio of at least 3.0:1</li>\r\n\t<li>AAA Small requires a contrast ratio of at least 7.0:1</li>\r\n\t<li>AAA Large requires a contrast ratio of at least 4.5:1.</li>\r\n</ul>\r\n<br>\r\n<h1>Font Sizes</h1>\r\n<ul>\r\n\t<li>Font size need to be at least 12pt or higher</li>\r\n\t<li>Large font is considered 18pt or 14pt bold and larger</li>\r\n</ul>\r\n<h1>Contrast Calculation</h1>\r\n<span>The contrast ratio is calculated using the relative luminance of the background\r\nand foreground colours. The equation for this calculation can be found below:</span><br><br>\r\n\r\n<div>\r\n L = 0.2126 * R + 0.7152 * G + 0.0722 * B where R, G and B are<br><br>\r\nif (RsRGB <= 0.03928) then<br>\r\n<div>R = RsRGB/12.92 else R = ((RsRGB+0.055)/1.055) ^ 2.4</div><br>\r\nif (GsRGB <= 0.03928) then<br>\r\n<div>G = GsRGB/12.92 else G = ((GsRGB+0.055)/1.055) ^ 2.4</div><br>\r\nif BsRGB <= 0.03928 then<br>\r\n<div>B = BsRGB/12.92 else B = ((BsRGB+0.055)/1.055) ^ 2.4</div><br>\r\n</div><br>\r\n\r\n<div>\r\nif (L1 > L2) then\r\n<div>Contrast Ratio = (L1 / L2) : 1</div>\r\nelse\r\n<div>Contrast Ratio = (L2 / L1) : 1</div>\r\n</div><br>\r\n\r\n<span>In this scenario R is red, G is green, and B is blue. L represents the luminance and this calculation will be done for each of the two specified colours and then divided by one another to return the left side of the contrast ratio.<br></span>\r\n\r\n<h1>WCAG Guidelines</h1>\r\n<span>1.4.1 Use of Color: Color is not used as the only visual means of conveying information, indicating an action, prompting a response, or distinguishing a visual element. (Level A)<span><br><br>\r\n<span>1.4.3 Contrast (Minimum): The visual presentation of text and images of text has a contrast ratio of at least 4.5:1</span><br><br>\r\n<span>1.4.6 Contrast (Enhanced): The visual presentation of text and images of text has a contrast ratio of at least 7:1</span>\r\n</html>");

		JButton focus = new JButton("");
		focus.setBounds(-100, -100, 0, 0);
		focus.setActionCommand("focus");
		panel.add(focus);
		registerForFocus(focus, content);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				scrollPane.getVerticalScrollBar().setValue(0);
			}
		});
	}
}
