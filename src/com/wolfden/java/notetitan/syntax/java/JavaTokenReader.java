package com.wolfden.java.notetitan.syntax.java;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;

import com.wolfden.java.notetitan.syntax.StyledTokenReader;
import com.wolfden.java.notetitan.syntax.Theme;
import com.wolfden.java.notetitan.syntax.ThemeManager;

public class JavaTokenReader extends StyledTokenReader {
	ThemeManager manager = ThemeManager.getInstance();
	Theme theme = manager.getThemes().get(0);

	private final String ACCESS_MODIFIERS = "public|private|protected";
	private final String VARIABLES = "int|float|double|boolean|char|long|short";

	@Override
	public StyleRange[] getStyles(CommonTokenStream tokens,
			StyleRange[] curStyles) {

		styles.clear();
		tokens.fill();
		for (Token token : tokens.getTokens()) {
			switch (token.getType()) {

			case JavaLexer.StringLiteral:
				styleToken(token, "StringLiteral");
				break;

			case JavaLexer.IntegerLiteral:
				styleToken(token, "IntegerLiteral");
				break;

			case JavaLexer.BooleanLiteral:
				styleToken(token, "BooleanLiteral");
				break;

			case JavaLexer.IF:
			case JavaLexer.ELSE:
				styleToken(token, "IfElse");
				break;
			default:
				if (token.getText().matches(ACCESS_MODIFIERS)) {
					styleToken(token, "ACCESS_MODIFIERS");
				} else if (token.getText().matches(VARIABLES)) {
					styleToken(token, "VARIABLES");
				}
			}

		}

		// for (Token t : tokens.getTokens()) {
		// switch (t.getType()) {
		// case JavaLexer.PUBLIC:
		// addStyle(t.getStartIndex(), t.getStopIndex(),
		// new Color(Display.getCurrent(), 255, 0, 0));
		// break;
		// case JavaLexer.COMMENT:
		// case JavaLexer.LINE_COMMENT:
		// addStyle(t.getStartIndex(), t.getStopIndex(),
		// new Color(Display.getCurrent(), 255, 0, 0));
		// break;
		// default:
		// System.out.println("Token: " + t.getText());
		// if (t.getText().matches("[*!~+#\\-/:@|&{}?^=><\\]\\[,();]+")) {
		// addStyle(t.getStartIndex(), t.getStopIndex(), new Color(
		// Display.getCurrent(), 0, 0, 0));
		// } else if (t.getText().matches(KEYWORDS)) {
		// addStyle(t.getStartIndex(), t.getStopIndex(), new Color(
		// Display.getCurrent(), 45, 45, 45), SWT.BOLD);
		// }
		// }
		// }

		if (curStyles != null)
			styles.addAll(Arrays.asList(curStyles));

		Collections.sort(styles, new Comparator<StyleRange>() {
			@Override
			public int compare(StyleRange o1, StyleRange o2) {
				return o1.start - o2.start;
			}
		});
		return styles.toArray(new StyleRange[styles.size()]);

	}

	private void styleToken(Token token, String key) {
		Color color = theme.getHashTable().get(key);
		addStyle(token.getStartIndex(), token.getStopIndex(), color);
	}
}
