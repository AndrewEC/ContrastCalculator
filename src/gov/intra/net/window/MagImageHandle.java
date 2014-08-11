package gov.intra.net.window;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import gov.intra.net.persist.ImageWriter;
import gov.intra.net.util.Contraster;

public class MagImageHandle extends MagEventBase {

	private ImageWriter iw;

	public MagImageHandle(WindowMagnifier window) {
		super(window);
	}

	public void onEvent(String command) {
		if (command.equals("open image")) {
			loadImage();
		} else if (command.equals("save image")) {
			saveImage();
		}
	}

	private void saveImage() {
		ImageWriter iw = new ImageWriter();
		iw.setRememberPath(true);
		iw.setAutoTrim(true);
		iw.setParent(window);
		iw.setName(window.getFileName().getText());
		iw.setExt(window.getExt());
		iw.saveImage(window.getImagePanel().getImage());
	}

	public void loadImage() {
		iw = new ImageWriter();
		iw.setRememberPath(true);
		iw.setEnforceDirectory(false);
		iw.setParent(window);
		File file = iw.promptForFile();
		if (file != null && !file.isDirectory()) {
			try {
				BufferedImage image = ImageIO.read(file);
				BufferedImage con = Contraster.convertImage(image, window.getParentFrame().getBlindColour());
				window.getImagePanel().setImage(con);
			} catch (IOException e) {
				e.printStackTrace();
				String err = "An error occured while loading the image:\n" + e.getMessage();
				JOptionPane.showMessageDialog(window, err, "Error", JOptionPane.ERROR_MESSAGE);
			}

		}
	}

}
