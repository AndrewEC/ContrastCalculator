package gov.intra.net.area;

import java.awt.Rectangle;

public interface IAreaSnipperResult {
	
	public void onAreaSelected(Rectangle r);
	public void onError(Exception e);

}
