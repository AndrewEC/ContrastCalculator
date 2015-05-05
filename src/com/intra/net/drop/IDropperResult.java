package com.intra.net.drop;

import java.awt.Color;

public interface IDropperResult {
	
	public void onColourObtained(Color c);
	public void onError(Exception e);

}
