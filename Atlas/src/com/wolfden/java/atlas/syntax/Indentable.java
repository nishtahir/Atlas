package com.wolfden.java.atlas.syntax;

import org.eclipse.swt.custom.StyledText;

/**
 * 
 * @author Nish
 *
 */
public interface Indentable {
	public void indentStart();
	public void indentEnd();
	public void fixIndentation();
	public void updateIndentation(StyledText text);
}
