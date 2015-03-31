package com.wolfden.java.atlas;

import org.eclipse.swt.SWT;

/**
 * Helper class to help manage Menu Accelerators
 * 
 * @author Nish
 *
 */

public class SelectionHelper {

	public static final int SWT_CUT = SWT.MOD1 + 'X';
	public static final int SWT_COPY = SWT.MOD1 + 'C';
	public static final int SWT_PASTE = SWT.MOD1 + 'V';

	public static final int SWT_SELECT_ALL = SWT.MOD1 + 'A';

	public static final int SWT_OPEN = SWT.MOD1 + 'O';
	public static final int SWT_NEW = SWT.MOD1 + 'N';
	public static final int SWT_SAVE = SWT.MOD1 + 'S';
	public static final int SWT_SAVE_AS = SWT.MOD1 + SWT.MOD2 + 'S';
	public static final int SWT_QUIT = SWT.MOD1 + 'Q';

	public static final int SWT_UNDO = SWT.MOD1 + 'Z';
	public static final int SWT_REDO = SWT.MOD1 + SWT.MOD2 + 'Z';

	public static final int SWT_FORMAT_CODE = SWT.MOD1 + SWT.MOD2 + 'F';
	
}
