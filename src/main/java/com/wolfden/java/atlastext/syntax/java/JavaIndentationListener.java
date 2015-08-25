package com.wolfden.java.atlastext.syntax.java;

import com.wolfden.java.atlastext.syntax.Indentable;
import org.eclipse.swt.custom.StyledText;

/**
 *
 */
public class JavaIndentationListener extends com.wolfden.java.atlastext.syntax.java.JavaBaseListener implements
        Indentable {

	@Override
	public void enterBlock(com.wolfden.java.atlastext.syntax.java.JavaParser.BlockContext ctx) {
		super.enterBlock(ctx);

	}

	@Override
	public void indentStart() {

	}

	@Override
	public void indentEnd() {

	}

	@Override
	public void fixIndentation() {

	}

	@Override
	public void updateIndentation(StyledText text) {
		System.out.println("Updating indentation");
	}
}
