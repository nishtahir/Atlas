package com.wolfden.java.atlastext.utils;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class ColorUtils {

    /**
     * @param hex Hexadecimal color in RRGGBB format
     * @return Color
     */
    public static Color getColorFromHex(Display display, String hex) {
        short[] rgb = getRGBFromHex(hex);
        return new Color(display, rgb[0], rgb[1], rgb[2]);
    }

    /**
     * @param hex color represented int hexadecimal format
     * @return
     */
    public static short[] getRGBFromHex(String hex) {
        if (!isValidHexCode(hex)) {
            throw new IllegalArgumentException("Invalid Hex color code.");
        }

        String value = hex.substring(1);

        byte radix = 16;
        final short[] rgb = new short[3];

        for (int i = 0; i < 3; i++) {
            rgb[i] = Short.parseShort(value.substring(i * 2, i * 2 + 2), radix);
        }

        return rgb;
    }

    /**
     * @param hexCode hexadecimal color code starting with the # sign
     * @return true if color is valid Hex. Otherwise false
     */
    public static boolean isValidHexCode(String hexCode) {
        final String HEX_CODE_PATTERN = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";

        Pattern hexCodePattern = Pattern.compile(HEX_CODE_PATTERN);
        Matcher matcher = hexCodePattern.matcher(hexCode);
        return matcher.matches();
    }
}
