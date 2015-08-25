package com.wolfden.java.atlas.test;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.junit.Test;

import com.wolfden.java.atlas.util.ColorUtils;

public class ColorUtilsTest {

	@Test
	public void test_getColorFromHex() {
		Color color = ColorUtils.getColorFromHex("000000");
		Color colorRed = ColorUtils.getColorFromHex("FF0000");
		Color colorGreen = ColorUtils.getColorFromHex("00FF00");
		Color colorBlue = ColorUtils.getColorFromHex("0000FF");
		
		assertEquals(color, new Color(Display.getCurrent(), 0, 0, 0));
		assertEquals(colorRed, new Color(Display.getCurrent(), 255, 0, 0));
		assertEquals(colorGreen, new Color(Display.getCurrent(), 0, 255, 0));
		assertEquals(colorBlue, new Color(Display.getCurrent(), 0, 0, 255));
	}

}
