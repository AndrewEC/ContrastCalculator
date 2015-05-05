package com.intra.net.area;

import java.awt.Rectangle;

public interface ISnipperListener {
	
	public void onAreaSelected(Rectangle r);
	public void onError(Exception e);

}
