package com.wolfden.java.atlas.syntax;

import com.wolfden.java.atlas.preferences.PreferenceManager;
import com.wolfden.java.atlas.syntax.java.JavaTokenReader;
import com.wolfden.java.atlas.syntax.plaintext.PlainTextTokenReader;

public class SyntaxManager {
	private PreferenceManager preferenceManager;

	/**
	 * Checks the preference manager for the language set in the configuration
	 * file
	 * 
	 * @return appropriate token reader for the language. Default is plain text
	 */
	public StyledTokenReader getSyntaxTokenReader() {
		preferenceManager = PreferenceManager.getInstance();
		String preference = preferenceManager
				.getPreference(PreferenceManager.KEY_LANGUAGE);

		switch (preference) {
		case Languages.JAVA:
			return new JavaTokenReader();

		default:
			return new PlainTextTokenReader();
		}

	}

	/**
	 * Holds the list of supported languages They will also be used as keys
	 * 
	 * @author Nish
	 */
	public interface Languages {
		public static final String JAVA = "Java";
		public static final String PLAIN_TEXT = "Plain text";
	}

	public static final String[] SUPPORTED_LANGUAGES = { Languages.JAVA,
			Languages.PLAIN_TEXT };

}
