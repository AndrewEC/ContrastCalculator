package gov.intra.net.util;

import java.awt.Color;

public class HexValidator {
	
	private static char[] valid = new char[] { 'a', 'b', 'c', 'd', 'e', 'f' };
	
	public static Color hexToColour(String hex) {
		return new Color(Integer.valueOf(hex.substring(1, 3), 16), Integer.valueOf(hex.substring(3, 5), 16), Integer.valueOf(hex.substring(5, 7), 16));
	}
	
	public static boolean isValid6Hex(String hex) {
		return validHex(7, hex);
	}

	public static boolean isValid3Hex(String hex) {
		return validHex(4, hex);
	}

	public static boolean validHex(int length, String hex) {
		if (hex.length() != length) {
			return false;
		}
		if (!hex.startsWith("#")) {
			return false;
		}
		for (char c : hex.substring(1, hex.length() - 1).toLowerCase().toCharArray()) {
			if (!Character.isDigit(c)) {
				if (!isValidHexChar(c)) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean isValidHexChar(char ch) {
		for (char c : valid) {
			if (c == ch) {
				return true;
			}
		}
		return false;
	}

}
