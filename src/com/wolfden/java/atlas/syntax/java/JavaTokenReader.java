package com.wolfden.java.atlas.syntax.java;

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
import com.wolfden.java.atlas.syntax.java.JavaTokenListener.StylableTokenListener;

public class JavaTokenReader extends StyledTokenReader {
	ThemeManager manager = ThemeManager.getInstance();
	Theme theme = manager.getThemes().get(0);

	private final String ACCESS_MODIFIERS = "public|private|protected";
	private final String KEYWORDS = "abstract|continue|for|new|switch|assert|default|"
			+ "goto|package|synchronized|boolean|do|this|break|double|implements|throw|"
			+ "import|throws|case|enum|instanceof|return|transient|catch|extends|try|final|"
			+ "interface|static|void|class|finally|strictfp|volatile|const|native|super|while";
	
	private final String VARIABLES = "int|float|double|boolean|char|long|short";
	private final String OPERATORS = "[*!~+#\\-/:|&?^=><]+";
	private final String ANNOTATIONS = "(@)((?:[a-z][a-z0-9_]*))";
	private final String ClASS_NAME = "[A-Z](.*)";

	@Override
	public StyleRange[] getStyles(CommonTokenStream tokens,
			StyleRange[] curStyles) {

		styles.clear();
		tokens.fill();
		for (Token token : tokens.getTokens()) {
			switch (token.getType()) {

			case JavaLexer.LINE_COMMENT:
				styleToken(token, "VARIABLES");
				break;
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
				System.out.println("Text:" + token.getText());
				if (token.getText().matches(ACCESS_MODIFIERS)) {
					styleToken(token, "ACCESS_MODIFIERS");
				} else if (token.getText().matches(VARIABLES)) {
					styleToken(token, "VARIABLES");
				} else if (token.getText().matches(ANNOTATIONS)) {
					styleToken(token, "ANNOTATIONS", SWT.BOLD);
				} else if (token.getText().matches(OPERATORS)) {
					styleToken(token, "OPERATORS", SWT.BOLD);
				} else if (token.getText().matches(KEYWORDS)) {
					styleToken(token, "KEYWORDS");
				} else if (token.getText().matches(ClASS_NAME)){
					styleToken(token, "ACCESS_MODIFIERS");
				}
			}

		}

		JavaParser parser = new JavaParser(tokens);
		ParserRuleContext ctx = parser.compilationUnit();
		ParseTreeWalker walker = new ParseTreeWalker();
		JavaTokenListener extractor = new JavaTokenListener(
				new StylableTokenListener() {

					@Override
					public void onExitAnnotation(int start, int stop) {
						styleRange(start, stop, "ANNOTATIONS");
					}
					
				});
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
