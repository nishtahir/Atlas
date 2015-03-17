package com.wolfden.java.atlas.util;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class ErrorUtils {
	
	public static void showErrorMessageBox(Exception e, Shell context){
		MessageBox messageBox = new MessageBox(context, SWT.ICON_ERROR | SWT.OK);
		if(e instanceof IOException){
			messageBox.setMessage("An I/O error occured");
		} else {
			messageBox.setMessage("An Unexpected error occured");
		}
	    messageBox.open();
	}
}
