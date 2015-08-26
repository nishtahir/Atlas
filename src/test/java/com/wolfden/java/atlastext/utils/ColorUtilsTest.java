package com.wolfden.java.atlastext.utils;


import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Nish on 8/24/15.
 */
public class ColorUtilsTest {
    String amethystHex = "#9b59b6";
    short[] amethystRGB = {155, 89, 182};

    @Test
    public void testGetColorFromHex_withValidRGB_ReturnsCorrectColor(){
        //TODO  - Find way to test on SWT UI thread
        //ColorUtils.getColorFromHex(Display.getCurrent(), amethystHex);
    }

    @Test
    public void testGetRGBFromHex_WithValidHexColor_ReturnsCorrectRGB() {
        short[] result = ColorUtils.getRGBFromHex(amethystHex);
        assertTrue(Arrays.equals(amethystRGB, result));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetRGBFromHex_WithInvalidHexColor_ThrowsIllegalArgumentException() {
        ColorUtils.getRGBFromHex("$");
    }

    @Test
    public void testIsValidHexCode_withValidHexCode_ReturnsTrue() {
        assertTrue(ColorUtils.isValidHexCode("#FFFFFF"));
        assertTrue(ColorUtils.isValidHexCode("#FED"));
    }

    @Test
    public void testIsValidHexCode_withInValidHexCode_ReturnsFalse(){
        assertFalse(ColorUtils.isValidHexCode("G"));
        assertFalse(ColorUtils.isValidHexCode("#QQQQQQ"));
        assertFalse(ColorUtils.isValidHexCode("President Trump 2016"));
    }
}