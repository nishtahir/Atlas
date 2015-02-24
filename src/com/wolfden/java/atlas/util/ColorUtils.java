package com.wolfden.java.atlas.util;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Display;

public class ColorUtils {
	public static Device device = Display.getCurrent();

	/**
	 * 
	 * @param hex
	 *            Hexadecimal color in RRGGBB format
	 * @return Color
	 */
	public static Color getColorFromHex(String hex) {
		int radix = 16;
		final int[] rgb = new int[3];
		for (int i = 0; i < 3; i++) {
			rgb[i] = Integer.parseInt(hex.substring(i * 2, i * 2 + 2), radix);
		}
		return new Color(device, rgb[0], rgb[1], rgb[2]);
	}

}
