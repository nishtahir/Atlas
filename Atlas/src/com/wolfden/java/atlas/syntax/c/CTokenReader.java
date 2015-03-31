package com.wolfden.java.atlas.syntax.c;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;

import com.wolfden.java.atlas.syntax.StyledTokenReader;
import com.wolfden.java.atlas.syntax.Theme;
import com.wolfden.java.atlas.syntax.ThemeManager;
import com.wolfden.java.atlas.syntax.java.JavaLexer;
import com.wolfden.java.atlas.syntax.java.JavaParser;
import com.wolfden.java.atlas.syntax.java.JavaTokenListener;
import com.wolfden.java.atlas.syntax.java.JavaTokenListener.StylableTokenListener;

public class CTokenReader extends StyledTokenReader {
	ThemeManager manager = ThemeManager.getInstance();
	Theme theme = manager.getThemes().get(0);

	private final String KEYWORDS = "auto|break|case|const|continue|default|do|enum|extern|for|goto|register|return|sizeof|static|struct|switch|typedef|union|volatile|while";
	private final String VARIABLES = "void|char|short|int|long|float|double|signed|unsigned";
	private final String OPERATORS = "[*!~+#\\-/:|&?^=><]+";

	@Override
	public StyleRange[] getStyles(CommonTokenStream tokens,
			StyleRange[] curStyles) {

		styles.clear();
		tokens.fill();
		for (Token token : tokens.getTokens()) {
			switch (token.getType()) {
			case CLexer.StringLiteral:
				styleToken(token, "StringLiteral");
				break;

			case CLexer.Int:
				styleToken(token, "IntegerLiteral");
				break;

			case CLexer.If:
			case CLexer.Else:
				styleToken(token, "IfElse");
				break;

			default:
				System.out.println("Text:" + token.getText());
				if (token.getText().matches(VARIABLES)) {
					styleToken(token, "VARIABLES");
				} else if (token.getText().matches(OPERATORS)) {
					styleToken(token, "OPERATORS", SWT.BOLD);
				} else if (token.getText().matches(KEYWORDS)) {
					styleToken(token, "KEYWORDS");
				}
			}

		}

		CParser parser = new CParser(tokens);
		ParserRuleContext ctx = parser.primaryExpression();
		ParseTreeWalker walker = new ParseTreeWalker();
		CTokenListener extractor = new CTokenListener(
		// new StylableTokenListener() {
		//
		// @Override
		// public void onExitAnnotation(int start, int stop) {
		// styleRange(start, stop, "ANNOTATIONS");
		// }
		// }
		);
		walker.walk(extractor, ctx); // initiate walk of tree with listener

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

	private void styleToken(Token token, String key, int style) {
		Color color = theme.getHashTable().get(key);
		addStyle(token.getStartIndex(), token.getStopIndex(), color, SWT.BOLD);
	}

	private void styleToken(Token token, String key) {
		Color color = theme.getHashTable().get(key);
		addStyle(token.getStartIndex(), token.getStopIndex(), color);
	}

	private void styleRange(int startIndex, int stopIndex, String key) {
		Color color = theme.getHashTable().get(key);
		addStyle(startIndex, stopIndex, color);
	}

}
