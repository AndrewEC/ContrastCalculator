package gov.intra.net.dialogs;

import gov.intra.net.frame.Frame;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class Guide extends GenericDialog {

	public Guide(Frame frame) {
		super(frame, "Contraster Calculator Guide");
		setBounds(100, 100, 600, 650);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(this);
		btnClose.setActionCommand("close");
		btnClose.setBounds(495, 593, 85, 25);
		getContentPane().add(btnClose);

		JButton focus = new JButton("");
		focus.setBounds(-100, -100, 0, 0);
		focus.setActionCommand("focus");
		getContentPane().add(focus);
		registerForClose(btnClose);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(8, 7, 574, 579);
		getContentPane().add(scrollPane);

		JEditorPane content = new JEditorPane();
		scrollPane.setViewportView(content);
		content.setContentType("text/html");
		content.setText("<html>\r\n\t<style>h2{ font-family: Verdana } h1{ font-family: Verdana } span{ font-family: Verdana }</style>\r\n\t<h1>User Guide</h1>\r\n\t<span>Here you will find a quick guide to the Contrast Calculator, its functions, and the WCAG guidelines to which it supports.<br></span>\r\n\r\n\t<h2>Shortcut Keys</h2>\r\n\t<span>There are a large number of shortcut keys that make most of the options and features available through the use of only a keyboard.<br>\r\n\tTo view the list of shortcut keys you can click <strong>Help -> Shortcuts</strong> on the menu bar or press <strong>Shift + U</strong>.<br>\r\n\tThough some of the shortcuts will be explained in other sections of this guide.<br></span>\r\n\r\n\t<h2>Guidelines for Contrast Analysis</h2>\r\n\t<span>There are a few guidelines for contrast analysis. These can be found using <strong>Help -> Guidelines</strong> or by pressing <strong>Shift + G</strong>.</span>\r\n\r\n\t<h2>Pixel Dropper</h2>\r\n\t<span>The pixel dropper is the main tool you will be using. The pixel dropper is used to select a single pixel on the screen to get the colour of the pixel RGB and Hex representations. This way we can calculate the contrast ratio between the two.<br><br>\r\nTo use the pixel dropper click the dropper tool icon beside the foreground or background labels then <strong>Left-Click</strong> on a pixel to select it or <strong>Right-Click</strong> or press <strong>Escape</strong> to close the pixel dropper when it's open.<br>\r\nAlternatively you can press <strong>Shift + Z</strong> to open the pixel dropper to select a foreground colour or <strong>Shift + X</strong> to select a background colour.<br></span>\r\n\r\n\t<h2>Area Snipper</h2>\r\n\t<span>The area snipper is a tool to capture a specified area of the screen. The captured area will have a colour blind filter applied if one is selcted.<br>\r\nTo open the area snipper click the <strong>Area Snipper</strong> button. Alternatively you could press <strong>Shift + A</strong> to open the area snipper.<br><br>\r\nOnce the area snipper is open you will notice the screen is slightly darker. This is an overlay from which you can select the area to capture. To select the area simply drag the mouse over the specified area and click the mouse when you are satisfied with the result.<br><br>On the following result screen you can save the image if you so choose.\r\n</span>\r\n\r\n\t<h2>Window Magnifier</h2>\r\n\t<span>The window magnifier works much like the area magnifier except that it will let you magnify a single window rather than a chosen square area.<br><br>\r\nTo use the window magnifier first it much be enabled. To enable it you can <strong>Options -> Other -> Enable Window Magnifier</strong> or press <strong>Shift + E</strong>.<br>\r\nThen press the magnify window button or press <strong>Shift + C</strong>.<br><br>\r\nOnce open you will be presented with a larger window containing some sliders, buttons and the like.<br><br>\r\nTo get started press the refresh button. This will fill our list with the current set of windows that can be identified by the magnifier.<br>\r\nNot that we have the list we can select something from it and press the view button. This action will bring that window to the front and take a screen shot of just that window then it will bring the contrast calculator back to the front.<br></br>\r\nNow that the image is taken it will appear within the scroll area. You can not use the scroll bars and the zoom slider to navigate the image and increase or descrease its scale.<br>\r\nAlternatively you can also save the image in one of the three formats provided.<br></span>\r\n\r\n\t<h2>Colour Blind Options</h2>\r\n\t<span>The Contrast Calculator also features the ability to view colours from the perspective of someone who is colour blind in one of the more common manners.<br><br>\r\nTo use this feature go to <strong>Options -> Colour Blind</strong> then select a colour blind perspective, or press <strong> Shift + (0-9) </strong>.<br><br>\r\nWith a colour blind perspective chosen the pixel dropper, area magnifier, and window magnifier will all apply this filter the next time you use them.<br><br>\r\nAlternatively is you don't want the colour blind option to affect the pixel dropper tool you can disable the colour blind filter just for the tool. You can go to <strong>Options -> Pixel Dropper</strong> and check <strong>Enable Colour Blind Dropper</strong> or press <strong> Shift + P</strong> to enable or disable the filter.</span>\r\n\r\n\t<h2>Notes</h2>\r\n<span>You can use the area magnifier, window magnifier, and pixel dropper all in combination with one another giving you a lot of flexibility over how you wish to use the program for analyzing colours.</span>\r\n</html>");
		registerForFocus(focus, content);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				scrollPane.getVerticalScrollBar().setValue(0);
			}
		});
	}
}
