package com.wolfden.java.atlastext.syntax;

import org.antlr.v4.runtime.CommonTokenStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;

import java.util.ArrayList;

public abstract class StyledTokenReader {
	protected ArrayList<StyleRange> styles = new ArrayList<StyleRange>();
	public abstract StyleRange[] getStyles(CommonTokenStream tokens, StyleRange[] styleRange);

	/**
	 * Applies style to token
	 * 
	 * @param start starting position of the token
	 * @param stop stopping position of the token
	 * @param foreground text foreground color
	 */
	protected void addStyle(int start, int stop, Color foreground) {
		addStyle(start, stop, foreground, SWT.NONE);
	}

	/**
	 * Applies style to token
	 *
	 * @param start starting position of the token
	 * @param stop stopping position of the token
	 * @param foreground text foreground color
	 * @param style font style to apply to token
	 */
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
