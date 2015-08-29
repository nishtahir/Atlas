package com.wolfden.java.atlastext.dialog;

import com.wolfden.java.atlastext.AtlasTextApplication;
import com.wolfden.java.atlastext.preferences.PreferenceManager;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

/**
 * About dialog
 */
public class AboutDialog extends MessageBox {

    public AboutDialog(Shell parent) {
        super(parent);

        setText("About " + AtlasTextApplication.APP_NAME);
        setMessage("Copyright Nish Tahir 2015. \n Version: "
                + PreferenceManager.getInstance()
                .getPreference(PreferenceManager.KEY_VERSION)
                + " alpha");
    }
}
