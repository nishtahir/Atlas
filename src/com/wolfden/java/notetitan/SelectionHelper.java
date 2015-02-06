package com.wolfden.java.notetitan;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

/**
 * Helper class to help manage Selection listener
 * classes in strategy pattern
 * 
 * @author Nish
 *
 */

public class SelectionHelper {

	static class Open implements SelectionListener {
		@Override
		public void widgetSelected(SelectionEvent e) {
			Shell shell = NoteTitan.getInstance().getShell();
			FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
			fileDialog.setText("Open...");
			String filePath = fileDialog.open();
			if (filePath != null) {
				// FIXME - Not the best design because of the tight coupling
				// but it's 3AM... I'm tired :p
				NoteTitan.getInstance().loadFileFromPath(filePath);
			}
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {

		}

	}

	static class New implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			// TODO Implement this

		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {

		}

	}

	static class Save implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			//TODO - Implement this
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {

		}
	}

	static class Quit implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			//TODO - Implement this
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {

		}
	}

	static class SelectAll implements SelectionListener {
		@Override
		public void widgetSelected(SelectionEvent e) {
			NoteTitan.getInstance().getStyledText().selectAll();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {

		}
	}

	static class Cut implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			NoteTitan.getInstance().getStyledText().cut();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {

		}
	}

	static class Copy implements SelectionListener {
		@Override
		public void widgetSelected(SelectionEvent e) {
			NoteTitan.getInstance().getStyledText().copy();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {

		}
	}

	static class Paste implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			NoteTitan.getInstance().getStyledText().paste();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {

		}
	}

	static class Preferences implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {

		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {

		}
	}

	static class About implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			MessageBox about = new MessageBox(NoteTitan.getInstance().getShell());
			about.setMessage("Copyright Nish Tahir 2015. \n Version 1.0");
			about.open();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {

		}
	}

}
