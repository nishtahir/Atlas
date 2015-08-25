package com.wolfden.java.atlastext.syntax.java;

public class JavaTokenListener extends com.wolfden.java.atlastext.syntax.java.JavaBaseListener {

	StylableTokenListener listener;
	public JavaTokenListener(StylableTokenListener listener) {
		this.listener = listener;
	}

	@Override
	public void exitAnnotation(com.wolfden.java.atlastext.syntax.java.JavaParser.AnnotationContext ctx) {
		super.enterAnnotation(ctx);
		System.out.println("Enter Annotation:" + ctx.getText()
				+ " INDEX start:" + ctx.getStart().getStartIndex()
				+ " INDEX stop:" + ctx.getStop().getStopIndex());
		listener.onExitAnnotation(ctx.getStart().getStartIndex(), ctx.getStop().getStopIndex());
	}
	
	
	
	@Override
	public void exitModifier(com.wolfden.java.atlastext.syntax.java.JavaParser.ModifierContext ctx) {
		super.exitModifier(ctx);
		System.out.println("Modifier: " + ctx.getText());

	}

	@Override
	public void exitType(com.wolfden.java.atlastext.syntax.java.JavaParser.TypeContext ctx) {
		super.exitType(ctx);
		System.out.println("TYPE: " + ctx.getText());
	}

	@Override
	public void exitPrimitiveType(com.wolfden.java.atlastext.syntax.java.JavaParser.PrimitiveTypeContext ctx) {
		super.exitPrimitiveType(ctx);
		System.out.println("Primitive: " + ctx.getText());

	}

	@Override
	public void exitLiteral(com.wolfden.java.atlastext.syntax.java.JavaParser.LiteralContext ctx) {
		super.exitLiteral(ctx);
		System.out.println("Literal: " + ctx.getText());
	}

	public interface StylableTokenListener{
        void onExitAnnotation(int start, int stop);
	}

}
