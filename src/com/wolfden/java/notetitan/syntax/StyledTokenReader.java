package com.wolfden.java.notetitan.syntax;

import java.util.ArrayList;

import org.antlr.v4.runtime.CommonTokenStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;

public abstract class StyledTokenReader {
	protected ArrayList<StyleRange> styles = new ArrayList<StyleRange>();

	public abstract StyleRange[] getStyles(CommonTokenStream tokens, StyleRange[] styleRange);

	protected void addStyle(int start, int stop, Color foreground) {
		addStyle(start, stop, foreground, SWT.NONE);
	}

	protected void addStyle(int start, int stop, Color foreground, int style) {
		int length = stop - start + 1;
		StyleRange styleRange = new StyleRange();
		styleRange.start = start;
		styleRange.length = length;
		styleRange.foreground = foreground;
		styleRange.fontStyle = style;
		styles.add(styleRange);
	}
}
