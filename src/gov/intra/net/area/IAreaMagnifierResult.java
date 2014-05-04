package gov.intra.net.area;

import java.awt.Point;

public interface IAreaMagnifierResult {
	
	public void onPointObtained(Point p, int size);
	public void onError(Exception e);

}
