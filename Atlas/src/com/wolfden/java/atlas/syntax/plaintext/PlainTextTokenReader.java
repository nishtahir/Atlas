package com.wolfden.java.atlas.syntax.plaintext;

import org.antlr.v4.runtime.CommonTokenStream;
import org.eclipse.swt.custom.StyleRange;

import com.wolfden.java.atlas.syntax.StyledTokenReader;

/**
 * Token reader for plain text. Not much to do here 
 * because plain text is not styled
 * @author Nish
 *
 */
public class PlainTextTokenReader extends StyledTokenReader {

	@Override
	public StyleRange[] getStyles(CommonTokenStream tokens,
			StyleRange[] styleRange) {
		
		// Do nothing here because it's plain text
		return null;
	}

}
