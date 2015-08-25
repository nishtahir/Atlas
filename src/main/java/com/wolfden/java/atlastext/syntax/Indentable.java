package com.wolfden.java.atlastext.syntax;

import org.eclipse.swt.custom.StyledText;

/**
 *
 */
public interface Indentable {
	void indentStart();
	void indentEnd();
	void fixIndentation();
	void updateIndentation(StyledText text);
}
