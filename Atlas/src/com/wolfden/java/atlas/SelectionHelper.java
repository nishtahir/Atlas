package com.wolfden.java.atlas;

import org.eclipse.swt.SWT;

/**
 * Helper class to help manage Menu Accelerators
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

}
