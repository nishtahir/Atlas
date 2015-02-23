package com.wolfden.java.notetitan.util;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Display;

public class ColorUtils {
	public static ColorUtils instance;
	public static Device device = Display.getCurrent();

	public static Color getColorFromHex(String hex) {
		return new Color(device, Integer.valueOf(hex.substring(1, 3), 16),
				Integer.valueOf(hex.substring(3, 5), 16), Integer.valueOf(
						hex.substring(5, 7), 16));
	}
	
}
