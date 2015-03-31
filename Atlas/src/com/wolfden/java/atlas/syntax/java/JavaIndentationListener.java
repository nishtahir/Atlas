package com.wolfden.java.atlas.syntax.java;

import org.eclipse.swt.custom.StyledText;

import com.wolfden.java.atlas.syntax.Indentable;
import com.wolfden.java.atlas.syntax.java.JavaParser.BlockContext;

public class JavaIndentationListener extends JavaBaseListener implements
		Indentable {

	@Override
	public void enterBlock(BlockContext ctx) {
		super.enterBlock(ctx);

	}

	@Override
	public void indentStart() {
		// TODO Auto-generated method stub

	}

	@Override
	public void indentEnd() {
		// TODO Auto-generated method stub

	}

	@Override
	public void fixIndentation() {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateIndentation(StyledText text) {
		System.out.println("Updating indentation");
	}
}
