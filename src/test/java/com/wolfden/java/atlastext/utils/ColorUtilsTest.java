package com.wolfden.java.atlastext.utils;


import junit.framework.TestCase;

import java.util.Arrays;

/**
 * Created by Nish on 8/24/15.
 */
public class ColorUtilsTest extends TestCase {
    String amethystHex = "#9b59b6";
    short[] amethystRGB = {155, 89, 182};

    public void testGetColorFromHex_withValidRGB_ReturnsCorrectColor() throws Exception {
        //TODO  - Find way to test on SWT UI thread
        //ColorUtils.getColorFromHex(Display.getCurrent(), amethystHex);
    }

    public void testGetRGBFromHex_WithValidHexColor_ReturnsCorrectRGB() {
        short[] result = ColorUtils.getRGBFromHex(amethystHex);
        assertTrue(Arrays.equals(amethystRGB, result));
    }

    public void testGetRGBFromHex_WithInvalidHexColor_ThrowsIllegalArgumentException() {
        try {
            ColorUtils.getRGBFromHex("$");
            fail("IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException iae) {

        }
    }

    public void testIsValidHexCode_withValidHexCode_ReturnsTrue() {
        assertTrue(ColorUtils.isValidHexCode("#FFFFFF"));
        assertTrue(ColorUtils.isValidHexCode("#FED"));
    }

    public void testIsValidHexCode_withInValidHexCode_ReturnsFalse() throws Exception {
        assertFalse(ColorUtils.isValidHexCode("G"));
        assertFalse(ColorUtils.isValidHexCode("#QQQQQQ"));
        assertFalse(ColorUtils.isValidHexCode("President Trump 2016"));
    }
}