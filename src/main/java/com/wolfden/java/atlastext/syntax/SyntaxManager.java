package com.wolfden.java.atlastext.syntax;

import com.wolfden.java.atlastext.preferences.PreferenceManager;
import com.wolfden.java.atlastext.syntax.java.JavaTokenReader;
import com.wolfden.java.atlastext.syntax.plaintext.PlainTextTokenReader;

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
     * Holds a list of supported languages They will also be used as keys
     *
     * @author Nish
     */
    public interface Languages {
        String C = "C";
        String CPP = "C++";
        String JAVA = "Java";
        String JAVA_SCRIPT = "Javascript";
        String PLAIN_TEXT = "Plain text";
    }

    public static final String[] SUPPORTED_LANGUAGES = {
            Languages.C, Languages.CPP, Languages.JAVA,
            Languages.JAVA_SCRIPT, Languages.PLAIN_TEXT};
}
