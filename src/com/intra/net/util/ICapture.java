package com.intra.net.util;

import java.awt.image.BufferedImage;

public interface ICapture {

	public void onCapture(BufferedImage image);
	public void onCaptureFail(Exception e);
	
}
