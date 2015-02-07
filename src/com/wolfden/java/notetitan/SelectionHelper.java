package com.wolfden.java.notetitan;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.MessageBox;

/**
 * Helper class to help manage Selection listener implementations and Menu
 * Accelerators
 * 
 * @author Nish
 *
 */

public class SelectionHelper {

	public static final int SWT_CUT = SWT.COMMAND + 'X';
	public static final int SWT_COPY = SWT.COMMAND + 'C';
	public static final int SWT_PASTE = SWT.COMMAND + 'V';

	public static final int SWT_SELECT_ALL = SWT.COMMAND + 'A';

	public static final int SWT_OPEN = SWT.COMMAND + 'O';
	public static final int SWT_NEW = SWT.COMMAND + 'N';
	public static final int SWT_SAVE = SWT.COMMAND + 'S';
	public static final int SWT_SAVE_AS = SWT.COMMAND + SWT.SHIFT + 'S';
	public static final int SWT_QUIT = SWT.COMMAND + 'Q';

	public static final int SWT_UNDO = SWT.COMMAND + 'Z';
	public static final int SWT_REDO = SWT.COMMAND + SWT.SHIFT + 'Z';

	static class Open implements SelectionListener {
		@Override
		public void widgetSelected(SelectionEvent e) {
			NoteTitan.getInstance().openFile();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {

		}

	}

	static class New implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			NoteTitan.getInstance().newFile();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {

		}

	}

	static class Save implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			NoteTitan.getInstance().save();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {

		}
	}

	static class SaveAs implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			NoteTitan.getInstance().saveAs();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {

		}
	}

	static class Quit implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			// TODO - Implement this
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {

		}
	}

	static class SelectAll implements SelectionListener {
		@Override
		public void widgetSelected(SelectionEvent e) {
			NoteTitan.getInstance().selectAll();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {

		}
	}

	static class Cut implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			NoteTitan.getInstance().cut();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {

		}
	}

	static class Copy implements SelectionListener {
		@Override
		public void widgetSelected(SelectionEvent e) {
			NoteTitan.getInstance().copy();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {

		}
	}

	static class Paste implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			NoteTitan.getInstance().paste();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {

		}
	}
	
	static class Undo implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			NoteTitan.getInstance().undo();
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
			MessageBox about = new MessageBox(NoteTitan.getInstance()
					.getShell());
			about.setText("About Note Titan");
			about.setMessage("Copyright Nish Tahir 2015. \n Version 1.0");
			about.open();
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {

		}
	}

}
