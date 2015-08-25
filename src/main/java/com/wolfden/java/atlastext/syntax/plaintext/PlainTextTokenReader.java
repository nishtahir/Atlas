package com.wolfden.java.atlastext.syntax.plaintext;

import com.wolfden.java.atlastext.syntax.StyledTokenReader;
import org.antlr.v4.runtime.CommonTokenStream;
import org.eclipse.swt.custom.StyleRange;


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
