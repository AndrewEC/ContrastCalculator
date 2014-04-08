package resources;

public class Constants {
	
	public static final int MAG_RESULT_WIDTH_MOD = 22, MAG_RESULT_HEIGHT_MOD = 85;
	public static final int MIN_AREA_MAG_SIZE = 10, MAX_AREA_MAG_SIZE = 500;
	public static final int DEFAULT_AREA_MAG_RESULT_SIZE = 300;
	
	public static final int DROPPER_MAG_SIZE = 192, DROPPER_GRAB_SIZE = 24;
	public static final int DROPPER_CENTER_MOD = 96;
	
	public static final int IMAGE_SCROLL_PANE_MARGIN_RIGHT = 195, IMAGE_SCROLL_PANE_MARGIN_BOTTOM = 40;
	
	public static enum BlindColour{
		
		NORMAL("Normal", new double[] { 1, 0, 0, 0, 1, 0, 0, 0, 1 }),
		INVERT("Invert", new double[] { }),
		PROTANOPIA("Protanopia", new double[] { 0.567, 0.433, 0, 0.558, 0.442, 0, 0, 0.242, 0.758 }),
		PROTANOMALY("Protanomaly", new double[] { 0.817, 0.183, 0, 0.333, 0.667, 0, 0, 0.125, 0.875 }),
		DEUTERANOPIA("Deuteranopia", new double[] { 0.625, 0.375, 0, 0.7, 0.3, 0, 0, 0.3, 0.7 }),
		DEUTERANOMALY("Deuteranomaly", new double[] { 0.8, 0.2, 0, 0.258, 0.742, 0, 0, 0.142, 0.858 }),
		TRITANOPIA("Tritanopia", new double[] { 0.95, 0.05, 0, 0, 0.433, 0.567, 0, 0.183, 0.817 }),
		TRITANOMALY("Tritanomaly", new double[] { 0.967, 0.033, 0, 0, 0.733, 0.267, 0, 0.183, 0.817 }),
		ACHROMATOPSIA("Achromatopsia", new double[] { 0.299, 0.587, 0.114, 0.299, 0.587, 0.114, 0.299, 0.587, 0.114 }),
		ACHROMATOMALY("Achromatomaly", new double[] { 0.618, 0.32, 0.062, 0.163, 0.775, 0.062, 0.163, 0.32, 0.516 });
		
		public String value;
		public double[] modifier;
		
		private BlindColour(String value, double[] modifier){
			this.value = value;
			this.modifier = modifier;
		}
	}

}
