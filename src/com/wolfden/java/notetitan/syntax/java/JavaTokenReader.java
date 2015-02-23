package com.wolfden.java.notetitan.syntax.java;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import com.wolfden.java.notetitan.syntax.StyledTokenReader;

public class JavaTokenReader extends StyledTokenReader {
	private static final String KEYWORDS = "abstract|continue|for|new|switch|assert"
			+ "|default|goto|package|synchronized|boolean|do|if|private|this|break"
			+ "|double|implements|protected|throw|byte|else|import|public|throws|case"
			+ "|enum|instanceof|return|transient|catch|extends|int|short|try|char|final"
			+ "|interface|static|void|class|finally|long|strictfp|volatile|const"
			+ "|float|native|super|while";

	@Override
	public StyleRange[] getStyles(CommonTokenStream tokens,
			StyleRange[] curStyles) {

		styles.clear();
		tokens.fill();

		for (Token t : tokens.getTokens()) {
			switch (t.getType()) {
			case JavaLexer.PUBLIC:
				addStyle(t.getStartIndex(), t.getStopIndex(),
						new Color(Display.getCurrent(), 255, 0, 0));
				break;
			case JavaLexer.COMMENT:
			case JavaLexer.LINE_COMMENT:
				addStyle(t.getStartIndex(), t.getStopIndex(),
						new Color(Display.getCurrent(), 255, 0, 0));
				break;
			// case Verilog2001Lexer.One_line_comment:
			// case Verilog2001Lexer.Block_comment:
			// addStyle(t.getStartIndex(), t.getStopIndex(),
			// Theme.commentColor);
			// break;
			// case Verilog2001Lexer.Real_number:
			// case Verilog2001Lexer.Hex_number:
			// case Verilog2001Lexer.Binary_number:
			// case Verilog2001Lexer.Octal_number:
			// case Verilog2001Lexer.Decimal_number:
			// addStyle(t.getStartIndex(), t.getStopIndex(),
			// Theme.valueColor);
			// break;
			// case Verilog2001Lexer.String:
			// addStyle(t.getStartIndex(), t.getStopIndex(),
			// Theme.stringColor);
			// break;
			default:
				System.out.println("Token: " + t.getText());
				if (t.getText().matches("[*!~+#\\-/:@|&{}?^=><\\]\\[,();]+")) {
					addStyle(t.getStartIndex(), t.getStopIndex(), new Color(
							Display.getCurrent(), 0, 0, 0));
				} else if (t.getText().matches(KEYWORDS)) {
					addStyle(t.getStartIndex(), t.getStopIndex(), new Color(
							Display.getCurrent(), 45, 45, 45), SWT.BOLD);
				}
				// } else if (t.getText().matches(VARIABLES)) {
				// addStyle(t.getStartIndex(), t.getStopIndex(),
				// Theme.varTypeColor);
				// } else if (t.getText().matches(MODULE)) {
				// addStyle(t.getStartIndex(), t.getStopIndex(),
				// Theme.moduleColor, SWT.BOLD);
			}
		}

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
}
