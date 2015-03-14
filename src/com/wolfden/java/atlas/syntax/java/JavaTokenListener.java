package com.wolfden.java.atlas.syntax.java;

import com.wolfden.java.atlas.syntax.java.JavaParser.AnnotationContext;
import com.wolfden.java.atlas.syntax.java.JavaParser.LiteralContext;
import com.wolfden.java.atlas.syntax.java.JavaParser.ModifierContext;
import com.wolfden.java.atlas.syntax.java.JavaParser.PrimitiveTypeContext;
import com.wolfden.java.atlas.syntax.java.JavaParser.TypeContext;

public class JavaTokenListener extends JavaBaseListener {

	StylableTokenListener listener;
	public JavaTokenListener(StylableTokenListener listener) {
		this.listener = listener;
	}

	@Override
	public void exitAnnotation(AnnotationContext ctx) {
		// TODO Auto-generated method stub
		super.enterAnnotation(ctx);
		System.out.println("Enter Annotation:" + ctx.getText()
				+ " INDEX start:" + ctx.getStart().getStartIndex()
				+ " INDEX stop:" + ctx.getStop().getStopIndex());
		listener.onExitAnnotation(ctx.getStart().getStartIndex(), ctx.getStop().getStopIndex());
	}
	
	
	
	@Override
	public void exitModifier(ModifierContext ctx) {
		// TODO Auto-generated method stub
		super.exitModifier(ctx);
		System.out.println("Modifier: " + ctx.getText());

	}

	@Override
	public void exitType(TypeContext ctx) {
		// TODO Auto-generated method stub
		super.exitType(ctx);
		System.out.println("TYPE: " + ctx.getText());
	}

	@Override
	public void exitPrimitiveType(PrimitiveTypeContext ctx) {
		// TODO Auto-generated method stub
		super.exitPrimitiveType(ctx);
		System.out.println("Primitive: " + ctx.getText());

	}

	@Override
	public void exitLiteral(LiteralContext ctx) {
		// TODO Auto-generated method stub
		super.exitLiteral(ctx);
		System.out.println("Literal: " + ctx.getText());
	}

	public interface StylableTokenListener{
		public void onExitAnnotation(int start, int stop);
	}

}
